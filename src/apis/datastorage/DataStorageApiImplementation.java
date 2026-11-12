package apis.datastorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataStorageApiImplementation implements DataStorageAPI {

    private static final String DEFAULT_DELIMITER = ",";

    @Override
    public ReadResponse read(ReadRequest request) {

        if (request == null || request.inputSource == null || request.inputSource.isBlank()) {
            return new ReadResponseImplementation(new ArrayList<>(), ReadResponseCode.FAILED);
        }

        String filePath = request.inputSource;
        ArrayList<Integer> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // process input file
            String line;
            while ((line = br.readLine()) != null) {
                int value = Integer.parseInt(line.trim());
                data.add(value);
            }
            // check if input file was empty and no data was processed
            if (data.isEmpty()) {
                return new ReadResponseImplementation(data, ReadResponseCode.FAILED);
            } else {
                return new ReadResponseImplementation(data, ReadResponseCode.SUCCESS);
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            data.clear();
            return new ReadResponseImplementation(data, ReadResponseCode.FAILED);
        }
    }

    @Override
    public WriteResponse write(WriteRequest request) {

        if (request == null || request.outputDestination == null || request.outputDestination.isBlank() 
        || request.results == null || request.delimiter == null) {
            return new WriteResponseImplementation(WriteResponseCode.FAILED);
        }

        // assign default delimiter if not provided
        String delimiter = request.delimiter;
        if (delimiter.isEmpty()) {
            delimiter = DEFAULT_DELIMITER;
        }
        
        String filePath = request.outputDestination;
        ArrayList<Integer> data = request.results;
        
        try (BufferedWriter br = new BufferedWriter(new FileWriter(filePath))) {

            // process output file
            for (int i = 0; i < data.size(); i++) {

                Integer value = data.get(i);

                if (value == null) {
                    return fileCleanup(filePath);
                }
                br.write(String.valueOf(value));
                if (i != data.size() - 1) {
                    br.write(delimiter);
                }
            }
            return new WriteResponseImplementation(WriteResponseCode.SUCCESS);
            
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            return fileCleanup(filePath);
        }
    }

    // file cleanup helper method
    private WriteResponse fileCleanup(String filePath) {

        try {
            File file = new File(filePath);

            if (!file.delete()) {
                System.err.println("Unable to delete incomplete output file: " + file.getPath());
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return new WriteResponseImplementation(WriteResponseCode.FAILED);
    }
}
