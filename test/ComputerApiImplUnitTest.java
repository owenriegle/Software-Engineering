import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import apis.computer.ComputerApiImplementation;
import apis.computer.ComputerResponse;
import apis.computer.ComputerResponseCode;

public class ComputerApiImplUnitTest {

    private ComputerApiImplementation computerApiImplementation = new ComputerApiImplementation();

    @Test
    void testNullInputRequest() {

        ComputerResponse response = computerApiImplementation.compute(null);

        Assertions.assertEquals(ComputerResponseCode.FAILED, response.getResponseCode());
        Assertions.assertTrue(response.getResults().isEmpty());
    }
}
