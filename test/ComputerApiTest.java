import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.computer.ComputerApi;
import apis.computer.ComputerApiImpl;
import apis.computer.ComputerRequest;
import apis.computer.ComputerResponse;

public class ComputerApiTest {

    @Test
    void testCompute() {

        ComputerApi api = new ComputerApiImpl();

        ComputerRequest request = Mockito.mock(ComputerRequest.class);

        ComputerResponse response = api.compute(request);

        Assertions.assertNotNull(response);
        
    }

}
