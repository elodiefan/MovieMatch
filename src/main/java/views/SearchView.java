package views;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.util.concurrent.ExecutionException;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

/**
 * The View for searching movies.
 */
public class SearchView extends JPanel implements PropertyChangeListener {

    private static final String CONFIRM_LABEL = "confirm";
    private static final String SEARCHING_LABEL = "searching...";

    private SearchController searchController;

    private final String viewName = SearchViewModel.VIEW_NAME;

    private final SearchViewModel searchViewModel;

    private final JTextField searchInput;
    private final JButton searchConfirmButton;
    private final JButton backButton;
    private final JLabel errorMessage;

    public SearchView(SearchViewModel searchViewModel,
                      ViewManagerModel viewManagerModel,
                      String homePageViewName) {

        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Search");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchInput = new JTextField(20);
        searchConfirmButton = new JButton(CONFIRM_LABEL);
        backButton = new JButton("Back");
        errorMessage = new JLabel(" ");

        searchConfirmButton.addActionListener(event -> runSearch());
        // Pressing Enter in the field is what most people try first.
        searchInput.addActionListener(event -> runSearch());

        // Without this the search screen is a dead end; there is no other way out.
        backButton.addActionListener(
                event -> viewManagerModel.switchView(homePageViewName)
        );

        final JPanel searchPanel = new JPanel();

        searchPanel.add(searchInput);
        searchPanel.add(searchConfirmButton);

        final JPanel buttonPanel = new JPanel();

        buttonPanel.add(backButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(searchPanel);
        this.add(errorMessage);
        this.add(buttonPanel);
    }

    /**
     * Searches on a background thread.
     *
     * Talking to TMDB takes long enough that doing it on the UI thread stops the
     * window repainting, which looks exactly like the application has hung. A
     * SwingWorker keeps the window alive and lets the button report progress.
     */
    private void runSearch() {
        final String keyword = searchInput.getText();

        searchConfirmButton.setEnabled(false);
        searchConfirmButton.setText(SEARCHING_LABEL);
        errorMessage.setText(" ");

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                searchController.execute(keyword);
                return null;
            }

            @Override
            protected void done() {
                searchConfirmButton.setEnabled(true);
                searchConfirmButton.setText(CONFIRM_LABEL);
                try {
                    get();
                }
                catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
                catch (ExecutionException exception) {
                    // Surfacing it here keeps the failure on screen instead of
                    // vanishing into the worker thread.
                    ErrorReporter.show(SearchView.this, exception.getCause());
                }
            }
        }.execute();
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        final SearchState state =
                (SearchState) evt.getNewValue();

        errorMessage.setText(state.getSearchError());
    }
}
