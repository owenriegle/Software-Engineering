package apis;

import java.util.ArrayList;

import apis.computer.ComputerAPI;
import apis.computer.ComputerRequest;
import apis.computer.ComputerResponse;
import apis.datastorage.DataStorageAPI;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponse;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponse;
import apis.datastorage.WriteResponseCode;
import apis.userinput.InputRequest;

public class JobHandler {

    private final ComputerAPI computerAPI;
    private final DataStorageAPI storageAPI;

    public static final String SUCCESS_MESSAGE = "Computation completed successfully. Results written to the specified destination.";
    public static final String FAILURE_MESSAGE = "Computation completed, but writing the results to the specified destination failed.";

    public JobHandler(ComputerAPI computerAPI, DataStorageAPI storageAPI) {

        this.computerAPI = computerAPI;
        this.storageAPI = storageAPI;

    }

    public String handleJob(InputRequest request) {

        // obtain input data from the specified source
        ReadRequest readRequest = new ReadRequest();
        readRequest.inputSource = request.inputSource;

        // read data from storage
        ReadResponse readResponse = storageAPI.read(readRequest);

        // pass the data to the compute engine for processing
        ComputerRequest computerRequest = new ComputerRequest();
        computerRequest.data = readResponse.getData();

        ComputerResponse computerResponse = computerAPI.compute(computerRequest);

        // write the results to the specified output destination
        WriteRequest writeRequest = new WriteRequest();
        writeRequest.outputDestination = request.outputDestination;
        writeRequest.delimiter = request.delimiter;

        ArrayList<Integer> results = computerResponse.getResults();
        writeRequest.results = results;

        WriteResponse writeResponse = storageAPI.write(writeRequest);

        // return results to the user
        if (writeResponse.getResponseCode() == WriteResponseCode.SUCCESS) {
            return SUCCESS_MESSAGE;
        } else {
            return FAILURE_MESSAGE;
        }
    }
}
