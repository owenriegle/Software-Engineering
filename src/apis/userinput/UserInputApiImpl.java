package apis.userinput;

import apis.JobHandler;
import apis.computer.ComputerService;
import apis.datastorage.DataStorageApi;

public class UserInputApiImpl implements UserInputApi {

    private final JobHandler jobHandler;

    public UserInputApiImpl(DataStorageApi dataStorageAPI, ComputerService computerService) {

        this.jobHandler = new JobHandler(computerService, dataStorageAPI);
    }

    @Override
    public InputResponse input(InputRequest inputRequest) {

        String status = jobHandler.handleJob(inputRequest);
        return new InputResponseImpl(status);
    }
}
