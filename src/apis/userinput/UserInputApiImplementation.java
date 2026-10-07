package apis.userinput;

import apis.computer.ComputerAPI;
import apis.datastorage.DataStorageAPI;

public class UserInputApiImplementation implements UserInputAPI {

    private final DataStorageAPI dataStorageAPI;
    private final ComputerAPI computerAPI;

    public UserInputApiImplementation(DataStorageAPI dataStorageAPI, ComputerAPI computerAPI) {
        this.dataStorageAPI = dataStorageAPI;
        this.computerAPI = computerAPI;
    }

    @Override
    public InputResponse input(InputRequest inputRequest) {
        return null;
    }

}
