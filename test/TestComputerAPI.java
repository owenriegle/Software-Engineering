import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.ComputerAPI;
import apis.ComputerRequest;
import apis.ComputerResponse;
import apis.DataStorageAPI;

public class TestComputerAPI {

    @Test
    void testCompute() {

        DataStorageAPI storage = Mockito.mock(DataStorageAPI.class);
        ComputerAPI api = new ComputerpApiImplementation(storage);

        ComputerRequest request = Mockito.mock(ComputerRequest.class);

        ComputerResponse response = api.compute(request);

        Assertions.assertNull(response);
        
    }

}
