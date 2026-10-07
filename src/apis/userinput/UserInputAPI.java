package apis.userinput;

import project.annotations.NetworkAPI;

@NetworkAPI
public interface UserInputAPI {

	InputResponse input(InputRequest inputRequest);

}
