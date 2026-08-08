package interface_adapter;

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active. An initial state of "" is used.
 */
public class ViewManagerModel extends StateModel<String> {

    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }

    /**
     * Switches view.
     * @param viewName the target view name.
     */
    public void switchView(String viewName) {
        setState(viewName);
        firePropertyChanged();
    }
}
