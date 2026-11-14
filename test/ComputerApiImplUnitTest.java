import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import apis.computer.ComputerApiImplementation;
import apis.computer.ComputerRequest;
import apis.computer.ComputerResponse;
import apis.computer.ComputerResponseCode;

public class ComputerApiImplUnitTest {

    private ComputerApiImplementation computerApiImplementation;
    private ComputerRequest computerRequest;

    @BeforeEach
    void setUp() {
        computerApiImplementation = new ComputerApiImplementation();
        computerRequest = new ComputerRequest();
    }

    @Test
    void testNullInputRequest() {

        ComputerResponse response = computerApiImplementation.compute(null);

        Assertions.assertEquals(ComputerResponseCode.FAILED, response.getResponseCode());
        Assertions.assertTrue(response.getResults().isEmpty());
    }

    @Test
    void testRequestNullData() {

        ComputerResponse response = computerApiImplementation.compute(computerRequest);

        Assertions.assertEquals(ComputerResponseCode.FAILED, response.getResponseCode());
        Assertions.assertTrue(response.getResults().isEmpty());
    }

    @Test
    void testRequestEmptyData() {

        computerRequest.data = new ArrayList<>();

        ComputerResponse response = computerApiImplementation.compute(computerRequest);

        Assertions.assertEquals(ComputerResponseCode.FAILED, response.getResponseCode());
        Assertions.assertTrue(response.getResults().isEmpty());
    }

    @Test
    void testRequestNullElement() {

        computerRequest.data = new ArrayList<>();
        computerRequest.data.add(null);

        ComputerResponse response = computerApiImplementation.compute(computerRequest);

        Assertions.assertEquals(ComputerResponseCode.FAILED, response.getResponseCode());
        Assertions.assertTrue(response.getResults().isEmpty());
    }

    @Test
    void testInputLessThanOrEqualToOne() {
        computerRequest.data = new ArrayList<>();
        computerRequest.data.add(1);

        ComputerResponse response = computerApiImplementation.compute(computerRequest);

        Assertions.assertEquals(ComputerResponseCode.SUCCESS, response.getResponseCode());
        Assertions.assertEquals(1, response.getResults().size());
        Assertions.assertEquals(0, response.getResults().get(0));
    }
}
