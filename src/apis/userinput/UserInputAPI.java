package apis.userinput;

import project.annotations.NetworkAPI;

@NetworkAPI
public interface UserInputApi {

	InputResponse input(InputRequest inputRequest);

}
