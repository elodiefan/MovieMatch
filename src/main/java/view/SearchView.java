package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

/**
 * The View for searching movies.json.
 */
public class SearchView extends JPanel implements PropertyChangeListener {

    private SearchController searchController;

    private final String viewName = SearchViewModel.VIEW_NAME;

    private final SearchViewModel searchViewModel;

    private final JTextField searchInput;
    private final JButton searchConfirmButton;
    private final JButton backButton;
    private final JLabel errorMessage;

    public SearchView(SearchViewModel searchViewModel) {

        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Search");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchInput = new JTextField(20);
        searchConfirmButton = new JButton("confirm");
        backButton = new JButton("Back");
        errorMessage = new JLabel(" ");

        searchConfirmButton.addActionListener(
                event -> {
                    final String keyword = searchInput.getText();
                    searchController.execute(keyword);
                }
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
