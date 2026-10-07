import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.computer.ComputerAPI;
import apis.datastorage.DataStorageAPI;
import apis.userinput.InputRequest;
import apis.userinput.InputResponse;
import apis.userinput.UserInputAPI;
import apis.userinput.UserInputApiImplementation;

public class TestUserInputAPI {

    @Test
    void testInputSmoke() {

        DataStorageAPI storage = Mockito.mock(DataStorageAPI.class);
        ComputerAPI computer = Mockito.mock(ComputerAPI.class);
        UserInputAPI api = new UserInputApiImplementation(storage, computer);

        InputRequest request = new InputRequest();

        InputResponse response = api.input(request);

        Assertions.assertNull(response);
        
    }
    
}
