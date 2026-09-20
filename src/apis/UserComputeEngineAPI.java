package apis;

import project.annotations.NetworkAPI;

@NetworkAPI
public interface UserComputeEngineAPI {

	UserResponse input(InputRequest inputRequest);

}
