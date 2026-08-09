package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

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
import interface_adapter.recommendation.RecommendationState;
import interface_adapter.recommendation.RecommendationViewModel;

/**
 * The short recommendation strip on the home page.
 */
public class HomeRecommendationsPanel extends JPanel implements PropertyChangeListener {

    private static final String SEE_ALL_LABEL = "See all recommendations";

    private final RecommendationViewModel recommendationViewModel;
    private RecommendationController recommendationController;

    private static final String LOADING_CARD = "loading";
    private static final String RESULTS_CARD = "results";

    private final JPanel rows;
    private final LoadingPanel loadingPanel;
    private final JPanel centre;
    private final JLabel message;
    private final JButton seeAllButton;

    /**
     * Stops a second load firing while the first is still running.
     */
    private boolean loading;

    /**
     * Who the strip is showing, so the full list can be opened for them too.
     */
    private String username = "";

    /**
     * Told about a request for the full list, since that screen loads separately.
     */
    private Consumer<String> seeAllHandler;

    /** Handles the screen switch to the full recommendation view. */
    private Runnable openFullListHandler;

    public HomeRecommendationsPanel(RecommendationViewModel recommendationViewModel) {
        this.recommendationViewModel = recommendationViewModel;
        this.recommendationViewModel.addPropertyChangeListener(this);

        rows = new JPanel();
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));

        message = new JLabel(" ", SwingConstants.CENTER);

        final JScrollPane scroll = new JScrollPane(rows);
        scroll.setBorder(BorderFactory.createTitledBorder(RecommendationViewModel.TITLE_LABEL));

        loadingPanel = new LoadingPanel(RecommendationViewModel.LOADING_LABEL);
        loadingPanel.setDetail("Looking at what you have watched and saved");

        centre = new JPanel(new CardLayout());
        centre.add(loadingPanel, LOADING_CARD);
        centre.add(scroll, RESULTS_CARD);

        seeAllButton = new JButton(SEE_ALL_LABEL);
        seeAllButton.addActionListener(event -> openFullList());
        final JPanel actions = new JPanel();
        actions.add(seeAllButton);

        this.setLayout(new BorderLayout());
        this.add(message, BorderLayout.NORTH);
        this.add(centre, BorderLayout.CENTER);
        this.add(actions, BorderLayout.SOUTH);
        showCard(LOADING_CARD);
    }

    private void showCard(String card) {
        ((CardLayout) centre.getLayout()).show(centre, card);
        loadingPanel.setAnimating(LOADING_CARD.equals(card));
    }

    /**
     * Asks for this user's recommendations, off the UI thread.
     *
     * @param username the username
     */
    public void loadFor(String username) {
        if (loading || recommendationController == null
                || username == null || username.isBlank()) {
            return;
        }
        this.username = username;
        loading = true;
        message.setText(" ");
        showCard(LOADING_CARD);

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                recommendationController.loadForHomePage(username);
                return null;
            }

            @Override
            protected void done() {
                loading = false;
                try {
                    get();
                }
                catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
                catch (ExecutionException exception) {
                    message.setText("Could not load recommendations.");
                    // Without this the spinner keeps turning after a failure,
                    // which reads as still working rather than given up.
                    showCard(RESULTS_CARD);
                }
            }
        }.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RecommendationState state = (RecommendationState) evt.getNewValue();
        rows.removeAll();

        if (state.getRecommendationError() != null) {
            message.setText(state.getRecommendationError());
        }
        else if (state.getRecommendations().isEmpty() && state.isLoaded()) {
            message.setText(RecommendationViewModel.EMPTY_LABEL);
        }
        else if (state.isLoaded()) {
            message.setText(" ");
            state.getRecommendations().forEach(this::addRow);
        }

        seeAllButton.setVisible(!state.getRecommendations().isEmpty());
        showCard(RESULTS_CARD);

        rows.revalidate();
        rows.repaint();
        this.revalidate();
        this.repaint();
    }

    private void addRow(RecommendationRow media) {
        rows.add(new RecommendationRowPanel(media));
    }

    /**
     * Opens the full list and starts it loading.
     */
    private void openFullList() {
        // The full screen has its own view model, so switching to it is not
        // enough; it has to be told to fetch, or it sits on its spinner.
        if (seeAllHandler != null) {
            seeAllHandler.accept(username);
        }
        if (openFullListHandler != null) {
            openFullListHandler.run();
        }
    }

    public void setRecommendationController(RecommendationController recommendationController) {
        this.recommendationController = recommendationController;
    }

    /**
     * Called with the username when the user asks for the full list.
     *
     * @param seeAllHandler the see all handler
     */
    public void setSeeAllHandler(Consumer<String> seeAllHandler) {
        this.seeAllHandler = seeAllHandler;
    }

    public void setOpenFullListHandler(Runnable openFullListHandler) {
        this.openFullListHandler = openFullListHandler;
    }
}
