package apis.computer;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputerApi {

	ComputerResponse compute(ComputerRequest computerRequest);

}
