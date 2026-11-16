import java.util.ArrayList;

import apis.datastorage.DataStorageApi;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponse;
import apis.datastorage.ReadResponseCode;
import apis.datastorage.ReadResponseImpl;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponse;
import apis.datastorage.WriteResponseCode;
import apis.datastorage.WriteResponseImpl;

public class DataStorageApiImplTest implements DataStorageApi {

    private final InputConfigTest inputConfig;
    private final OutputConfigTest outputConfig;

    public DataStorageApiImplTest(InputConfigTest inputConfig, OutputConfigTest outputConfig) {
        this.inputConfig = inputConfig;
        this.outputConfig = outputConfig;
    }

    @Override
    public ReadResponse read(ReadRequest request) {

        ArrayList<Integer> inputValues = inputConfig.getInput();
        ReadResponseImpl response = new ReadResponseImpl(inputValues, ReadResponseCode.SUCCESS);

        return response;
    }

    @Override
    public WriteResponse write(WriteRequest request) {

        for (Integer value : request.results) {
            outputConfig.getOutput().add(value.toString());
        }

        return new WriteResponseImpl(WriteResponseCode.SUCCESS);
    }
}
