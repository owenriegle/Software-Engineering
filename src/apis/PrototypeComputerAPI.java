package apis;

import project.annotations.ConceptualAPIPrototype;

public class PrototypeComputerAPI {
	
	@ConceptualAPIPrototype
	public void prototype(ComputerAPI api) {
		
		// compute data
		ComputerResponse response = api.compute(new ComputerRequest());
		
	}

}
