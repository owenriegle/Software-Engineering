package apis;

import project.annotations.ConceptualAPIPrototype;

public class PrototypeComputerConceptualAPI {
	
	@ConceptualAPIPrototype
	public void prototype(ComputerAPI api) {
		
		// compute data
		ComputerResponse response = api.compute(new ComputerRequest());
		
	}

}
