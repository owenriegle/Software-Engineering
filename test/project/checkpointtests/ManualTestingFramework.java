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

        // TODO 2:
        // Run a computation with an input file of <root project
        // dir>/manualTestInput.txt
        // and an output of <root project dir>/manualTestOutput.txt, with a delimiter of
        // ','
        //
        //
        // Helpful hint: the working directory of this program is <root project dir>,
        // so you can refer to the files just using the INPUT/OUTPUT constants. You do
        // not
        // need to (and should not) actually create those files in your repo
    }
}
