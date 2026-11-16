package apis.computer;

import project.annotations.ConceptualAPIPrototype;

public class PrototypeComputerApi {
	
	@ConceptualAPIPrototype
	public void prototype(ComputerApi api) {
		
		// compute data
		ComputerResponse response = api.compute(new ComputerRequest());
		
	}

}
