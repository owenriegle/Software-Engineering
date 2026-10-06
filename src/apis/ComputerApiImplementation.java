package apis;

public class ComputerApiImplementation implements ComputerAPI {

    private final DataStorageAPI dataStorageAPI;
    
    public ComputerApiImplementation(DataStorageAPI dataStorageAPI) {
        this.dataStorageAPI = dataStorageAPI;
    }

    @Override
    public ComputerResponse compute(ComputerRequest computerRequest) {
        return null;
    }

}
