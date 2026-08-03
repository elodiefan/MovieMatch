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

    private final String viewName = "search";

    private final SearchViewModel searchViewModel;

    private final JTextField searchInput;
    private final JButton searchConfirmButton;

    public SearchView(SearchViewModel searchViewModel) {

        this.searchViewModel = searchViewModel;

        final JLabel title = new JLabel("Search");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchInput = new JTextField(20);
        searchConfirmButton = new JButton("confirm");

        final JPanel searchPanel = new JPanel();

        searchPanel.add(searchInput);
        searchPanel.add(searchConfirmButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(searchPanel);
    }

    public String getViewName() {
        return viewName;
    }
}
