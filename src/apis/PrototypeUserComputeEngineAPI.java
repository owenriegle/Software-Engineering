package apis;

import project.annotations.NetworkAPIPrototype;

public class PrototypeUserComputeEngineAPI {
	
	@NetworkAPIPrototype
	public void prototype(UserComputeEngineAPI api) {
		
		// user specifies integer input
		UserResponse response = api.input(new InputRequest());
		
		// user specifies output delimiters, with default options
		// user specifies output destination
	}

}
