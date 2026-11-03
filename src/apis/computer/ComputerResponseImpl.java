package apis.computer;

import java.util.ArrayList;

public class ComputerResponseImpl implements ComputerResponse {

    private final ArrayList<Integer> results;
    private final ComputerResponseCode responseCode;

    public ComputerResponseImpl(ArrayList<Integer> results, ComputerResponseCode responseCode) {
        this.results = results;
        this.responseCode = responseCode;
    }

    @Override
    public ArrayList<Integer> getResults() {
        return results;
    }

    @Override
    public ComputerResponseCode getResponseCode() {
        return responseCode;
    }
}