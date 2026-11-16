package apis.userinput;

import apis.JobHandler;
import apis.computer.ComputerApi;
import apis.computer.ConcurrentComputerApi;
import apis.datastorage.DataStorageApi;

public class ConcurrentUserInputApi implements UserInputApi {

    private final JobHandler jobHandler;

    public ConcurrentUserInputApi(DataStorageApi dataStorageAPI, ComputerApi computerAPI) {

        this.jobHandler = new JobHandler(new ConcurrentComputerApi(computerAPI), dataStorageAPI);
    }

    @Override
    public InputResponse input(InputRequest inputRequest) {

        String status = jobHandler.handleJob(inputRequest);
        return new InputResponseImpl(status);
    }
}
