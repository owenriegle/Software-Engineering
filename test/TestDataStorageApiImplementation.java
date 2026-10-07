import java.util.List;

import apis.datastorage.DataStorageAPI;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponse;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponse;
import apis.datastorage.WriteResponseCode;

public class TestDataStorageApiImplementation implements DataStorageAPI {

    private final TestInputConfig inputConfig;
    private final TestOutputConfig outputConfig;

    public TestDataStorageApiImplementation(TestInputConfig inputConfig, TestOutputConfig outputConfig) {
        this.inputConfig = inputConfig;
        this.outputConfig = outputConfig;
    }

    @Override
    public ReadResponse read(ReadRequest request) {

        ReadResponseImplementation response = new ReadResponseImplementation();

        List<Integer> inputValues = inputConfig.getInput();
        response.getData().addAll(inputValues);

        return response;
    }

    @Override
    public WriteResponse write(WriteRequest request) {

        for (Integer value : request.results) {
            outputConfig.getOutput().add(value.toString());
        }

        return new WriteResponseImplementation(WriteResponseCode.SUCCESS);
    }
}
