package apis.datastorage;

public class WriteResponseImplementation implements WriteResponse {

    private final WriteResponseCode responseCode;

    public WriteResponseImplementation(WriteResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public WriteResponseCode getResponseCode() {
        return responseCode;
    }
}
