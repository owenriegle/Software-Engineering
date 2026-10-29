import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.computer.ComputerAPI;
import apis.computer.ComputerApiImplementation;
import apis.computer.ComputerRequest;
import apis.computer.ComputerResponse;

public class TestComputerAPI {

    @Test
    void testCompute() {

        ComputerAPI api = new ComputerApiImplementation();

        ComputerRequest request = Mockito.mock(ComputerRequest.class);

        ComputerResponse response = api.compute(request);

        Assertions.assertNotNull(response);
        
    }

}
