import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.DataStorageAPI;
import apis.ReadRequest;
import apis.ReadResponse;
import apis.WriteRequest;
import apis.WriteResponse;

public class TestDataStorageAPI {
    
    @Test
    void testReadSmoke() {

        DataStorageAPI api = new DataStorageApiImplementation();

        ReadRequest request = Mockito.mock(ReadRequest.class);

        ReadResponse response = api.read(request);

        Assertions.assertNull(response);

    }

    @Test
    void testWriteSmoke() {

        DataStorageAPI api = new DataStorageApiImplementation();

        WriteRequest request = Mockito.mock(WriteRequest.class);

        WriteResponse response = api.write(request);

        Assertions.assertNull(response);

    }

}
