package project.checkpointtests;

import apis.computer.ComputerApiImpl;
import apis.datastorage.DataStorageApiImpl;
import apis.userinput.InputRequest;
import apis.userinput.UserInputApiImpl;

public class ManualTestingFramework {

    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {

        ComputerApiImpl computerAPI = new ComputerApiImpl();
        DataStorageApiImpl dataStorageAPI = new DataStorageApiImpl();
        UserInputApiImpl userInputApi = new UserInputApiImpl(dataStorageAPI, computerAPI);

        InputRequest inputRequest = new InputRequest();
        inputRequest.inputSource = INPUT;
        inputRequest.outputDestination = OUTPUT;
        inputRequest.delimiter = ",";

        userInputApi.input(inputRequest);
    }
}
