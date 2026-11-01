package apis.datastorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataStorageApiImplementation implements DataStorageAPI {

    @Override
    public ReadResponse read(ReadRequest request) {

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
        } catch (IOException | NumberFormatException e) {

            data.clear();
            e.printStackTrace();
            return new ReadResponseImplementation(data, ReadResponseCode.FAILED);
        }
    }

    @Override
    public WriteResponse write(WriteRequest request) {
        
        String filePath = request.outputDestination;
        ArrayList<Integer> data = request.results;

        try (BufferedWriter br = new BufferedWriter(new FileWriter(filePath))) {

            // process output file
            for (int i = 0; i < data.size(); i++) {
                int value = data.get(i);
                br.write(String.valueOf(value));
                if (i != data.size() - 1) {
                    br.write(request.delimiter);
                }
            }
            return new WriteResponseImplementation(WriteResponseCode.SUCCESS);
            
        } catch (IOException e) {

            // clean up incomplete output file
            File file = new File(request.outputDestination);
            if (!file.delete()) {
                System.err.println("Unable to delete incomplete output file: " + file.getPath());
            }

            e.printStackTrace();
            return new WriteResponseImplementation(WriteResponseCode.FAILED);
        }
    }
}
