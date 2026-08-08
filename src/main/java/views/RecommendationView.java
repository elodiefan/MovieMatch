package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationRow;
import interface_adapter.recommendation.RecommendationSection;
import interface_adapter.recommendation.RecommendationState;
import interface_adapter.recommendation.RecommendationViewModel;

/**
 * The full recommendation screen, grouped into genre sections.
 */
public class RecommendationView extends JPanel implements PropertyChangeListener {

    private static final int SECTION_GAP = 14;
    private static final int HEADING_SIZE = 17;

    private final String viewName = RecommendationViewModel.VIEW_NAME;
    private final RecommendationViewModel recommendationViewModel;
    private RecommendationController recommendationController;

    private static final String LOADING_CARD = "loading";
    private static final String RESULTS_CARD = "results";

    private final JPanel sectionsPanel;
    private final JScrollPane resultsScroll;
    private final LoadingPanel loadingPanel;
    private final JPanel centre;
    private final JLabel message;
    private final JButton refreshButton;

    private String username = "";
    private Runnable backHandler;

    public RecommendationView(RecommendationViewModel recommendationViewModel) {
        this.recommendationViewModel = recommendationViewModel;
        this.recommendationViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(RecommendationViewModel.TITLE_LABEL, SwingConstants.CENTER);

        // Blank while loading, since the loading panel says so already. This is
        // for messages that outlive the spinner, like an error or an empty result.
        message = new JLabel(" ", SwingConstants.CENTER);

        final JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(title);
        header.add(message);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, SECTION_GAP, 0));

        sectionsPanel = new JPanel();
        sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.Y_AXIS));
        resultsScroll = new JScrollPane(sectionsPanel);

        loadingPanel = new LoadingPanel(RecommendationViewModel.LOADING_LABEL);
        loadingPanel.setDetail("Scoring titles against what you have watched");

        // One slot swapped between the two, so the screen is never showing an
        // empty list and a spinner at the same time.
        centre = new JPanel(new CardLayout());
        centre.add(loadingPanel, LOADING_CARD);
        centre.add(resultsScroll, RESULTS_CARD);

        refreshButton = new JButton(RecommendationViewModel.REFRESH_BUTTON_LABEL);
        refreshButton.addActionListener(event -> reload());

        final JButton backButton = new JButton(RecommendationViewModel.BACK_BUTTON_LABEL);
        backButton.addActionListener(event -> {
            if (backHandler != null) {
                backHandler.run();
            }
        });

        final JPanel actions = new JPanel();
        actions.add(refreshButton);
        actions.add(backButton);

        this.setLayout(new BorderLayout());
        this.add(header, BorderLayout.NORTH);
        this.add(centre, BorderLayout.CENTER);
        this.add(actions, BorderLayout.SOUTH);
    }

    /**
     * Shows either the spinner or the results, never both.
     *
     * @param card the card
     */
    private void showCard(String card) {
        ((CardLayout) centre.getLayout()).show(centre, card);
        loadingPanel.setAnimating(LOADING_CARD.equals(card));
    }

    /**
     * Loads the grouped recommendations for a user, off the UI thread.
     *
     * @param forUsername the for username
     */
    public void loadFor(String forUsername) {
        this.username = forUsername;
        reload();
    }

    private void reload() {
        if (recommendationController == null || username == null || username.isBlank()) {
            // Nothing to fetch for, so say so instead of leaving a spinner up.
            message.setText(RecommendationViewModel.EMPTY_LABEL);
            showCard(RESULTS_CARD);
            return;
        }
        refreshButton.setEnabled(false);
        message.setText(" ");
        showCard(LOADING_CARD);

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                recommendationController.loadDetailed(username);
                return null;
            }

            @Override
            protected void done() {
                refreshButton.setEnabled(true);
                try {
                    get();
                }
                catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
                catch (ExecutionException exception) {
                    message.setText("Could not load recommendations.");
                    showCard(RESULTS_CARD);
                    ErrorReporter.show(RecommendationView.this, exception.getCause());
                }
            }
        }.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RecommendationState state = (RecommendationState) evt.getNewValue();
        sectionsPanel.removeAll();

        if (state.getUsername() != null && !state.getUsername().isBlank()) {
            username = state.getUsername();
        }

        if (state.getRecommendationError() != null) {
            message.setText(state.getRecommendationError());
        }
        else if (state.getSections().isEmpty() && state.getRecommendations().isEmpty()
                && state.isLoaded()) {
            message.setText(RecommendationViewModel.EMPTY_LABEL);
        }
        else if (state.isLoaded()) {
            message.setText(" ");
            if (state.getSections().isEmpty()) {
                // Asked for ungrouped results, so show them as one list.
                state.getRecommendations().forEach(this::addResult);
            }
            else {
                state.getSections().forEach(this::addSection);
            }
        }

        showCard(RESULTS_CARD);

        sectionsPanel.revalidate();
        sectionsPanel.repaint();
        this.revalidate();
        this.repaint();
    }

    private void addSection(RecommendationSection section) {
        final JLabel heading = new JLabel(section.getHeading());
        heading.setFont(UiTheme.baseFont(Font.BOLD, HEADING_SIZE));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        heading.setBorder(BorderFactory.createEmptyBorder(SECTION_GAP, 0, 4, 0));
        sectionsPanel.add(heading);

        section.getRecommendations().forEach(this::addResult);
    }

    private void addResult(RecommendationRow media) {
        sectionsPanel.add(new RecommendationRowPanel(media));
    }

    public String getViewName() {
        return viewName;
    }

    public void setRecommendationController(RecommendationController recommendationController) {
        this.recommendationController = recommendationController;
    }

    public void setBackHandler(Runnable backHandler) {
        this.backHandler = backHandler;
    }
}
