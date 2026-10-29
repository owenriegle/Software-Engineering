package apis.computer;

import java.util.ArrayList;

public class ComputerResponseImpl implements ComputerResponse {

    private final ArrayList<Integer> results;

    public ComputerResponseImpl(ArrayList<Integer> results) {
        this.results = results;
    }

    @Override
    public ArrayList<Integer> getResults() {
        return results;
    }
}