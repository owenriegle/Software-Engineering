package apis.datastorage;

import java.util.ArrayList;

public class ReadResponseImplementation implements ReadResponse {

    private final ArrayList<Integer> data = new ArrayList<>();

    @Override
    public ArrayList<Integer> getData() {
        return data;
    }
}
