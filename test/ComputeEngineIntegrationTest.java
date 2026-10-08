import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import apis.JobHandler;
import apis.computer.ComputerApiImplementation;
import apis.userinput.InputRequest;

public class ComputeEngineIntegrationTest {

    @Test
    void testComputeEngine() {
        
        // create user input and output lists
        List<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(10);
        input.add(25);
        List<String> output = new ArrayList<>();

        // instantiate inputConfig and outputConfig objects with input and output lists
        TestInputConfig inputConfig = new TestInputConfig(input);
        TestOutputConfig outputConfig = new TestOutputConfig(output);

        // instantiate api implementations
        TestDataStorageApiImplementation dataStorageImpl = new TestDataStorageApiImplementation(inputConfig, outputConfig);
        ComputerApiImplementation computer = new ComputerApiImplementation(dataStorageImpl);

        // instantiate jobHandler and inputRequest to process user input
        JobHandler jobHandler = new JobHandler(dataStorageImpl, computer);
        InputRequest request = new InputRequest();
        jobHandler.handleJob(request);

        // assert output
        assertEquals(List.of("0", "17", "100"), output);
        
    }
    
}
