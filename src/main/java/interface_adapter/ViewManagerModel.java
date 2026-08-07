package interface_adapter;

/** Model for the View Manager. */
public class ViewManagerModel extends ViewModel<String> {

    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }

    /** Switches view. */
    public void switchView(String viewName) {
        setState(viewName);
        firePropertyChanged();
    }
}
