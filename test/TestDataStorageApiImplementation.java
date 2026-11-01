import java.util.ArrayList;

import apis.datastorage.DataStorageAPI;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponse;
import apis.datastorage.ReadResponseCode;
import apis.datastorage.ReadResponseImplementation;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponse;
import apis.datastorage.WriteResponseCode;
import apis.datastorage.WriteResponseImplementation;

public class TestDataStorageApiImplementation implements DataStorageAPI {

    private final TestInputConfig inputConfig;
    private final TestOutputConfig outputConfig;

    public TestDataStorageApiImplementation(TestInputConfig inputConfig, TestOutputConfig outputConfig) {
        this.inputConfig = inputConfig;
        this.outputConfig = outputConfig;
    }

    @Override
    public ReadResponse read(ReadRequest request) {

        ArrayList<Integer> inputValues = inputConfig.getInput();
        ReadResponseImplementation response = new ReadResponseImplementation(inputValues, ReadResponseCode.SUCCESS);
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
