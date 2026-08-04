package view;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapter.search.SearchViewModel;

/**
 * The View for searching movies.
 */
public class SearchView extends JPanel {

    private SearchController searchController;

    private final String viewName = SearchViewModel.VIEW_NAME;

    private final SearchViewModel searchViewModel;

    private final JTextField searchInput;
    private final JButton searchConfirmButton;
    private final JButton backButton;

    public SearchView(SearchViewModel searchViewModel) {

        this.searchViewModel = searchViewModel;

        final JLabel title = new JLabel("Search");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchInput = new JTextField(20);
        searchConfirmButton = new JButton("confirm");
        backButton = new JButton("Back");

        final JPanel searchPanel = new JPanel();

        searchPanel.add(searchInput);
        searchPanel.add(searchConfirmButton);

        final JPanel buttonPanel = new JPanel();

        buttonPanel.add(backButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(searchPanel);
        this.add(buttonPanel);
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    public String getViewName() {
        return viewName;
    }
}
