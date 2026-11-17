package apis.userinput;

import java.util.Objects;

public class InputResponseImpl implements InputResponse {

    private final String statusMessage;

    public InputResponseImpl(String statusMessage) {
        this.statusMessage = Objects.requireNonNull(statusMessage);
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

}
