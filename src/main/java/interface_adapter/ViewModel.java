package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** The ViewModel for our CA implementation. */
public class ViewModel<T> {

    private final String viewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    public T getState() {
        return this.state;
    }

    public void setState(T state) {
        this.state = state;
    }

    /** Fires a property changed event for the state of this ViewModel. */
    public void firePropertyChanged() {
        this.support.firePropertyChange("state", null, this.state);
    }

    /**
     * Fires a property changed event for the state of this ViewModel, which allows the user to specify a different propertyName.
     */
    public void firePropertyChanged(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    /** Adds a PropertyChangeListener to this ViewModel. */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
