package apis.userinput;

import apis.JobHandler;
import apis.computer.ComputerAPI;
import apis.datastorage.DataStorageAPI;

public class UserInputApiImplementation implements UserInputAPI {

    private final JobHandler jobHandler;

    public UserInputApiImplementation(DataStorageAPI dataStorageAPI, ComputerAPI computerAPI) {

        this.jobHandler = new JobHandler(computerAPI, dataStorageAPI);
    }

    @Override
    public InputResponse input(InputRequest inputRequest) {

        String status = jobHandler.handleJob(inputRequest);
        return new InputResponseImpl(status);
    }
}
