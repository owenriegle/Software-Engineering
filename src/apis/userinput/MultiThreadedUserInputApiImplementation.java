package apis.userinput;

import apis.JobHandler;
import apis.computer.ComputerAPI;
import apis.computer.MultiThreadedComputerService;
import apis.datastorage.DataStorageAPI;

public class MultiThreadedUserInputApiImplementation implements UserInputAPI {

    private final JobHandler jobHandler;

    public MultiThreadedUserInputApiImplementation(DataStorageAPI dataStorageAPI, ComputerAPI computerAPI) {

        this.jobHandler = new JobHandler(new MultiThreadedComputerService(computerAPI), dataStorageAPI);
    }

    @Override
    public InputResponse input(InputRequest inputRequest) {

        String status = jobHandler.handleJob(inputRequest);
        return new InputResponseImpl(status);
    }
}
