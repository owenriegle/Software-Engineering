package apis.datastorage;

import java.util.ArrayList;

public class ReadResponseImpl implements ReadResponse {

    private final ArrayList<Integer> data;
    private final ReadResponseCode responseCode;

    public ReadResponseImpl(ArrayList<Integer> data, ReadResponseCode responseCode) {
        this.data = data;
        this.responseCode = responseCode;
    }

    @Override
    public ArrayList<Integer> getData() {
        return data;
    }

    @Override
    public ReadResponseCode getResponseCode() {
        return responseCode;
    }
}
