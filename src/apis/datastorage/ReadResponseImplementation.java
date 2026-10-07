package apis.datastorage;

import java.util.ArrayList;
import java.util.List;

public class ReadResponseImplementation implements ReadResponse {

    private final List<Integer> data = new ArrayList<>();

    @Override
    public List<Integer> getData() {
        return data;
    }
}
