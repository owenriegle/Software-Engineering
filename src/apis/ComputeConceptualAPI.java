package apis;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputeConceptualAPI {

	ComputerResponse compute(ComputerRequest computerRequest);

}
