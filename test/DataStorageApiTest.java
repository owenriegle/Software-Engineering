import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.datastorage.DataStorageApi;
import apis.datastorage.DataStorageApiImpl;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponse;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponse;

public class DataStorageApiTest {
    
    @Test
    void testReadSmoke() {

        DataStorageApi api = new DataStorageApiImpl();

        ReadRequest request = Mockito.mock(ReadRequest.class);

        ReadResponse response = api.read(request);

        Assertions.assertNotNull(response);

    }

    @Test
    void testWriteSmoke() {

        DataStorageApi api = new DataStorageApiImpl();

        WriteRequest request = Mockito.mock(WriteRequest.class);

        WriteResponse response = api.write(request);

        Assertions.assertNotNull(response);

    }

}
