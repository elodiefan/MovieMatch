package interface_adapter.change_display_name;

/**
 * State for the change display name interface adapter.
 */
public class ChangeDisplayNameState {

    private String username = "";
    private String newDisplayName = "";
    private String message = "";
    private String error = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewDisplayName() {
        return newDisplayName;
    }

    public void setNewDisplayName(String newPassword) {
        this.newDisplayName = newPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
