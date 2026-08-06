package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entity.Media;
import interface_adapter.media_detail.MediaDetailController;
import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;

/**
 * View for displaying search results.
 */
public class SearchResultView extends JPanel implements PropertyChangeListener {

    private final String viewName = SearchResultViewModel.VIEW_NAME;

    private MediaDetailController mediaDetailController;
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
        resultsPanel.setLayout(
                new BoxLayout(resultsPanel, BoxLayout.Y_AXIS)
        );

        final JScrollPane scrollPane = new JScrollPane(resultsPanel);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        this.setLayout(new BorderLayout());

        this.add(title, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(backButton, BorderLayout.SOUTH);
    }

    public void setMediaDetailController(
            MediaDetailController mediaDetailController) {
        this.mediaDetailController = mediaDetailController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        final SearchResultState state =
                (SearchResultState) evt.getNewValue();

        resultsPanel.removeAll();

        if (state.getResults() != null) {

            state.getResults().forEach(this::addMediaResult);
        }

        this.revalidate();
        this.repaint();
    }

    private void addMediaResult(Media media) {

        final JPanel mediaPanel = new JPanel();

        final JLabel mediaLabel =
                new JLabel(
                        media.getTitle()
                                + " ("
                                + media.getReleaseYear()
                                + ")"
                );

        final JButton detailButton =
                new JButton("Detail");

        detailButton.addActionListener(
                event -> mediaDetailController.execute(media)
        );

        mediaPanel.add(mediaLabel);
        mediaPanel.add(detailButton);

        resultsPanel.add(mediaPanel);
    }

    public String getViewName() {
        return viewName;
    }
}
