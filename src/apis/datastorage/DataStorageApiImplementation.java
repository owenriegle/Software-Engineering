package apis.datastorage;

public class DataStorageApiImplementation implements DataStorageAPI {

    @Override
    public WriteResponse write(WriteRequest request) {
        return new WriteResponseImplementation(WriteResponseCode.SUCCESS);
    }

    @Override
    public ReadResponse read(ReadRequest request) {
        return new ReadResponseImplementation();
    }

}
