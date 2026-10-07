package apis;

import project.annotations.NetworkAPIPrototype;

public class PrototypeUserInputAPI {
	
	@NetworkAPIPrototype
	public void prototype(UserInputAPI api) {
		
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
