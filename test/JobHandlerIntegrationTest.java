import apis.JobHandler;
import apis.computer.ComputerService;
import apis.datastorage.DataStorageApiImpl;
import apis.userinput.InputRequest;
import apis.userinput.InputResponse;
import apis.userinput.UserInputApiImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

public class JobHandlerIntegrationTest {

    @Test
    void testExceptionHandlingIntegration() throws Exception {

        ComputerService computerService = computerRequest -> {
            throw new IllegalStateException("Simulated computation failure");
        };

        DataStorageApiImpl dataStorageApiImplementation = new DataStorageApiImpl();

        UserInputApiImpl userInputApiImplementation = new UserInputApiImpl(
                dataStorageApiImplementation, computerService);

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

        } finally {
            Files.deleteIfExists(input.toPath());
            Files.deleteIfExists(output.toPath());
        }
    }
}