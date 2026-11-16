package threadharness;

import java.io.File;

import apis.userinput.InputRequest;
import apis.userinput.UserInputApi;

public class TestUser {
	
	private final UserInputApi coordinator;

	public TestUser(UserInputApi coordinator) {
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ';';
		String inputPath = "test" + File.separatorChar + "testInputFile.test";

		InputRequest inputRequest = new InputRequest();
		inputRequest.delimiter = Character.toString(delimiter);
		inputRequest.inputSource = inputPath;
		inputRequest.outputDestination = outputPath;

		coordinator.input(inputRequest);
	}

}
