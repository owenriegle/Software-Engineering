import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.computer.ComputerService;
import apis.datastorage.DataStorageApi;
import apis.userinput.InputRequest;
import apis.userinput.InputResponse;
import apis.userinput.UserInputApi;
import apis.userinput.UserInputApiImpl;

public class TestUserInputAPI {

    @Test
    void testInputSmoke() {

        DataStorageApi storage = Mockito.mock(DataStorageApi.class);
        ComputerService computerService = Mockito.mock(ComputerService.class);
        UserInputApi api = new UserInputApiImpl(storage, computerService);

        InputRequest request = new InputRequest();

        InputResponse response = api.input(request);

        Assertions.assertNotNull(response);

    }

}
