package apis;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStorageAPI {
	
	// compute engine requests integers from storage
	ReadResponse read(ReadRequest request);
	
	// compute engine writes results to storage
	WriteResponse write(WriteRequest request);

}
