package apis;

import project.annotations.ProcessAPIPrototype;

public class PrototypeDataStorageAPI {
	
	@ProcessAPIPrototype
	public void prototype(DataStorageAPI api) {
		
		// compute engine requests integers from storage
		ReadResponse read = api.read(new ReadRequest());
		
		// compute engine writes integers to storage
		WriteResponse write = api.write(new WriteRequest());
		
	}

}
