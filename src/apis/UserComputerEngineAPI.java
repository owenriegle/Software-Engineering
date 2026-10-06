package apis;

import project.annotations.NetworkAPI;

@NetworkAPI
public interface UserComputerEngineAPI {

	InputResponse input(InputRequest inputRequest);

}
