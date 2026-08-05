package view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;

/**
 * View for displaying search results.
 */
public class SearchResultView extends JPanel implements PropertyChangeListener {

    private final String viewName = SearchResultViewModel.VIEW_NAME;

    private final SearchResultViewModel searchResultViewModel;

    private final JPanel resultsPanel;
    private final JButton backButton;

    public SearchResultView(SearchResultViewModel searchResultViewModel) {

        this.searchResultViewModel = searchResultViewModel;
        this.searchResultViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Search Results");
        backButton = new JButton("Back");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new FlowLayout());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(resultsPanel);
        this.add(backButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        final SearchResultState state =
                (SearchResultState) evt.getNewValue();

        resultsPanel.removeAll();

        if (state.getResults() != null) {

            state.getResults().forEach(media -> {
                resultsPanel.add(
                        new JLabel(
                                media.getTitle() + " (" + media.getReleaseYear() + ")"
                        )
                );
            });

        }

        this.revalidate();
        this.repaint();
    }

    public String getViewName() {
        return viewName;
    }
}
