package apis;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputerAPI {

	ComputerResponse compute(ComputerRequest computerRequest);

}
