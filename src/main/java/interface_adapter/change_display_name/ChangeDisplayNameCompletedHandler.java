package interface_adapter.change_display_name;

/**
 * What should happen once a display name is changed.
 */
public interface ChangeDisplayNameCompletedHandler {

    /**
     * Called after the new display name has been saved.
     * @param username the account whose display name was changed
     */
    void changeDisplayNameCompleted(String username);
}
