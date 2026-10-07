package apis;

public class UserComputerEngineAPIImplementation implements UserComputerEngineAPI {

    private final DataStorageAPI dataStorageAPI;
    private final ComputerAPI computerAPI;

    public UserComputerEngineAPIImplementation(DataStorageAPI dataStorageAPI, ComputerAPI computerAPI) {
        this.dataStorageAPI = dataStorageAPI;
        this.computerAPI = computerAPI;
    }

    @Override
    public InputResponse input(InputRequest inputRequest) {
        return null;
    }

}
