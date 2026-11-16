package apis.datastorage;

import project.annotations.ProcessAPIPrototype;

public class PrototypeDataStorageApi {
	
	@ProcessAPIPrototype
	public void prototype(DataStorageApi api) {
		
		// compute engine requests integers from storage
		ReadResponse read = api.read(new ReadRequest());
		
		// compute engine writes integers to storage
		WriteResponse write = api.write(new WriteRequest());
		
	}

}
