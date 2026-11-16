import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import apis.JobHandler;
import apis.computer.ComputerApiImpl;
import apis.userinput.InputRequest;
import apis.userinput.InputResponse;
import apis.userinput.ConcurrentUserInputApi;
import apis.userinput.UserInputApiImpl;

public class ComputeEngineIntegrationTest {

    @Test
    void testComputeEngine() {

        // create user input and output lists
        ArrayList<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(10);
        input.add(25);
        ArrayList<String> output = new ArrayList<>();

        // instantiate inputConfig and outputConfig objects with input and output lists
        InputConfigTest inputConfig = new InputConfigTest(input);
        OutputConfigTest outputConfig = new OutputConfigTest(output);

        // instantiate api implementations
        DataStorageApiImplTest dataStorageImpl = new DataStorageApiImplTest(inputConfig,
                outputConfig);
        ComputerApiImpl computerImpl = new ComputerApiImpl();
        UserInputApiImpl inputImpl = new UserInputApiImpl(dataStorageImpl, computerImpl);

        // instantiate jobHandler and inputRequest to process user input
        InputRequest request = new InputRequest();
        InputResponse response = inputImpl.input(request);

        // assert output
        assertEquals(List.of("0", "17", "100"), output);
        assertEquals(JobHandler.SUCCESS_MESSAGE, response.getStatusMessage());
    }

    @Test
    void testMultiThreadedComputeEngine() {

        // create user input and output lists
        ArrayList<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(10);
        input.add(25);
        ArrayList<String> output = new ArrayList<>();

        // instantiate inputConfig and outputConfig objects with input and output lists
        InputConfigTest inputConfig = new InputConfigTest(input);
        OutputConfigTest outputConfig = new OutputConfigTest(output);

        // instantiate api implementations
        DataStorageApiImplTest dataStorageImpl = new DataStorageApiImplTest(inputConfig,
                outputConfig);
        ComputerApiImpl computerImpl = new ComputerApiImpl();
        ConcurrentUserInputApi inputImpl = new ConcurrentUserInputApi(dataStorageImpl,
                computerImpl);

        // instantiate jobHandler and inputRequest to process user input
        InputRequest request = new InputRequest();
        InputResponse response = inputImpl.input(request);

        // assert output
        assertEquals(List.of("0", "17", "100"), output);
        assertEquals(JobHandler.SUCCESS_MESSAGE, response.getStatusMessage());
    }
}
