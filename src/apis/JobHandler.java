package apis;

import java.util.ArrayList;
import apis.computer.ComputerAPI;
import apis.computer.ComputerRequest;
import apis.computer.ComputerResponse;
import apis.datastorage.DataStorageAPI;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponse;
import apis.datastorage.ReadResponseCode;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponse;
import apis.datastorage.WriteResponseCode;
import apis.userinput.InputRequest;

public class JobHandler {

    private final ComputerAPI computerAPI;
    private final DataStorageAPI storageAPI;

    public static final String INVALID_REQUEST_MESSAGE = "The input request is invalid or missing required information.";
    public static final String READ_FAILURE_MESSAGE = "Unable to read input data from the specified source.";
    public static final String COMPUTE_FAILURE_MESSAGE = "Unable to process the input data.";
    public static final String WRITE_FAILURE_MESSAGE = "Computation completed, but unable to write results to the specified destination.";
    public static final String SUCCESS_MESSAGE = "Computation completed successfully. Results written to the specified destination.";

    public JobHandler(ComputerAPI computerAPI, DataStorageAPI storageAPI) {

        this.computerAPI = computerAPI;
        this.storageAPI = storageAPI;

    }

    public String handleJob(InputRequest request) {

        if (request == null) {
            return INVALID_REQUEST_MESSAGE;
        }

        // obtain input data from the specified source
        ReadRequest readRequest = new ReadRequest();
        readRequest.inputSource = request.inputSource;

        // read data from storage
        ReadResponse readResponse = storageAPI.read(readRequest);
        // if readResponse null or code is fail, return read failure
        if (readResponse == null || readResponse.getResponseCode() != ReadResponseCode.SUCCESS) {
            return READ_FAILURE_MESSAGE;
        }

        // obtain data from readresponse for computation
        ComputerRequest computerRequest = new ComputerRequest();
        if (readResponse.getData() == null) {
            return READ_FAILURE_MESSAGE;
        }
        computerRequest.data = readResponse.getData();

        // pass the data to the compute engine for processing
        ComputerResponse computerResponse = computerAPI.compute(computerRequest);
        // if computerResponse null or data is null, return compute failure
        if (computerResponse == null || computerResponse.getResults() == null) {
            return COMPUTE_FAILURE_MESSAGE;
        }

        // write the results to the specified output destination
        WriteRequest writeRequest = new WriteRequest();
        writeRequest.outputDestination = request.outputDestination;
        writeRequest.delimiter = request.delimiter;

        ArrayList<Integer> results = computerResponse.getResults();
        writeRequest.results = results;

        WriteResponse writeResponse = storageAPI.write(writeRequest);

        // if writeResponse null or code is fail, return write failure
        if (writeResponse == null || writeResponse.getResponseCode() != WriteResponseCode.SUCCESS) {
            return WRITE_FAILURE_MESSAGE;
        } else {
            return SUCCESS_MESSAGE;
        }
    }
}
