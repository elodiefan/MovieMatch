package use_case.customize;

/**
 * The input data for customize.
 */

public class CustomizeInputData {

    private String username;
    private boolean customizable;

    public CustomizeInputData(String username, boolean customizable) {
        this.username = username;
        this.customizable = customizable;
    }

    public boolean isCustomizable() {
        return customizable;
    }

    public String getUsername() {
        return username;
    }
}
