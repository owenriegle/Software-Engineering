package apis.datastorage;

public class WriteResponseImpl implements WriteResponse {

    private final WriteResponseCode responseCode;

    public WriteResponseImpl(WriteResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public WriteResponseCode getResponseCode() {
        return responseCode;
    }
}
