package project.checkpointtests;

import apis.computer.ComputerApiImplementation;
import apis.datastorage.DataStorageApiImplementation;
import apis.userinput.InputRequest;
import apis.userinput.UserInputApiImplementation;

public class ManualTestingFramework {

    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {

        ComputerApiImplementation computerAPI = new ComputerApiImplementation();
        DataStorageApiImplementation dataStorageAPI = new DataStorageApiImplementation();
        UserInputApiImplementation userInputApi = new UserInputApiImplementation(dataStorageAPI, computerAPI);

        InputRequest inputRequest = new InputRequest();
        inputRequest.inputSource = INPUT;
        inputRequest.outputDestination = OUTPUT;
        inputRequest.delimiter = ",";

        userInputApi.input(inputRequest);
    }
}
