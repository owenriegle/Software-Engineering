package apis.datastorage;

import java.util.Objects;

public class WriteResponseImpl implements WriteResponse {

    private final WriteResponseCode responseCode;

    public WriteResponseImpl(WriteResponseCode responseCode) {
        this.responseCode = Objects.requireNonNull(responseCode);
    }

    @Override
    public WriteResponseCode getResponseCode() {
        return responseCode;
    }
}
