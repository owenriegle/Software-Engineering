import apis.JobHandler;
import apis.computer.ComputerAPI;
import apis.computer.ComputerRequest;
import apis.datastorage.DataStorageApiImplementation;
import apis.userinput.InputRequest;
import apis.userinput.InputResponse;
import apis.userinput.UserInputApiImplementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

public class JobHandlerIntegrationTest {

    @Test
    void testExceptionHandlingIntegration() throws Exception {

        ComputerAPI computerAPI = Mockito.mock(ComputerAPI.class);

        Mockito.when(computerAPI.compute(Mockito.any()))
                .thenThrow(new IllegalStateException("Simulated computation failure"));

        DataStorageApiImplementation dataStorageApiImplementation = new DataStorageApiImplementation();

        UserInputApiImplementation userInputApiImplementation = new UserInputApiImplementation(
                dataStorageApiImplementation, computerAPI);

        File input = File.createTempFile("input", ".txt");
        File output = File.createTempFile("output", ".txt");

        try {
            try (BufferedWriter br = new BufferedWriter(new FileWriter(input))) {
                br.write("1\n2\n3");
            }

            InputRequest inputRequest = new InputRequest();
            inputRequest.inputSource = input.getAbsolutePath();
            inputRequest.outputDestination = output.getAbsolutePath();

            InputResponse inputResponse = userInputApiImplementation.input(inputRequest);

            Assertions.assertEquals(
                    JobHandler.GENERAL_FAILURE_MESSAGE,
                    inputResponse.getStatusMessage());

            Mockito.verify(computerAPI).compute(Mockito.any(ComputerRequest.class));

        } finally {
            Files.deleteIfExists(input.toPath());
            Files.deleteIfExists(output.toPath());
        }
    }
}