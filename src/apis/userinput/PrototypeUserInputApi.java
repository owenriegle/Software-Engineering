package apis.userinput;

import project.annotations.NetworkAPIPrototype;

public class PrototypeUserInputApi {
	
	@NetworkAPIPrototype
	public void prototype(UserInputApi api) {
		
		// user specifies integer input
		// user specifies output delimiters, with default options
		// user specifies output destination

		InputRequest request = new InputRequest();
		request.inputSource = "dummy-source";
		request.delimiter = ",";
		request.outputDestination = "dummy-destination";

		InputResponse response = api.input(request);

	}

}
