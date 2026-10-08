package apis;

import java.util.List;

import apis.computer.ComputerAPI;
import apis.computer.ComputerRequest;
import apis.computer.ComputerResponse;
import apis.datastorage.DataStorageAPI;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponse;
import apis.datastorage.WriteRequest;
import apis.userinput.InputRequest;
import apis.userinput.InputResponse;
import apis.userinput.UserInputAPI;

public class JobHandler {

    private final ComputerAPI computerAPI;
    private final DataStorageAPI storageAPI;
    private final UserInputAPI inputAPI;

    public JobHandler(ComputerAPI computerAPI, DataStorageAPI storageAPI, UserInputAPI inputAPI) {

        this.computerAPI = computerAPI;
        this.storageAPI = storageAPI;
        this.inputAPI = inputAPI;

    }

    public InputResponse handleJob(InputRequest request) {

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
        List<Integer> results = computerResponse.getResults();
        writeRequest.results = results;
        storageAPI.write(writeRequest);

        // eventually add WriteResponse logic
        // return results to the user
        return () -> results;

    }
}
