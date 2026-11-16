import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.computer.ComputerService;
import apis.datastorage.DataStorageAPI;
import apis.userinput.InputRequest;
import apis.userinput.InputResponse;
import apis.userinput.UserInputAPI;
import apis.userinput.UserInputApiImplementation;

public class TestUserInputAPI {

    @Test
    void testInputSmoke() {

        DataStorageAPI storage = Mockito.mock(DataStorageAPI.class);
        ComputerService computerService = Mockito.mock(ComputerService.class);
        UserInputAPI api = new UserInputApiImplementation(storage, computerService);

        InputRequest request = new InputRequest();

        InputResponse response = api.input(request);

        Assertions.assertNotNull(response);

    }

}
