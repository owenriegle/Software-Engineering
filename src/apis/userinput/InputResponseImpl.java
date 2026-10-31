package apis.userinput;

public class InputResponseImpl implements InputResponse {

    private String statusMessage;

    public InputResponseImpl(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

}
