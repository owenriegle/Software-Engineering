package apis;

import project.annotations.ConceptualAPIPrototype;

public class PrototypeComputeConceptualAPI {
	
	@ConceptualAPIPrototype
	public void prototype(ComputeConceptualAPI api) {
		
		// compute data
		ComputerResponse response = api.compute(new ComputerRequest());
		
	}

}
