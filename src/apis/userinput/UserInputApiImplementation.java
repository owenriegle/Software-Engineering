package apis.userinput;

import apis.JobHandler;
import apis.computer.ComputerService;
import apis.datastorage.DataStorageAPI;

public class UserInputApiImplementation implements UserInputAPI {

    private final JobHandler jobHandler;

    public UserInputApiImplementation(DataStorageAPI dataStorageAPI, ComputerService computerService) {

        this.jobHandler = new JobHandler(computerService, dataStorageAPI);
    }

    @Override
    public InputResponse input(InputRequest inputRequest) {

        String status = jobHandler.handleJob(inputRequest);
        return new InputResponseImpl(status);
    }
}
