import apis.DataStorageAPI;
import apis.ReadRequest;
import apis.ReadResponse;
import apis.WriteRequest;
import apis.WriteResponse;

public class TestDataStorageApiImplementation implements DataStorageAPI {

    private final TestInputConfig inputConfig;
    private final TestOutputConfig outputConfig;

    public TestDataStorageApiImplementation(TestInputConfig inputConfig, TestOutputConfig outputConfig) {
        this.inputConfig = inputConfig;
        this.outputConfig = outputConfig;
    }

    @Override
    public ReadResponse read(ReadRequest request) {
        
        ReadResponse response = new ReadResponse();
        response.setData(inputConfig.getInput());
        return response;

    }

    @Override
    public WriteResponse write(WriteRequest request) {

        WriteResponse response = new WriteResponse();

    }
    
}
