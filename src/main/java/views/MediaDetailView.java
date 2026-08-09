package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import interface_adapter.comments.CommentsController;
import interface_adapter.comments.CommentsViewModel;
import interface_adapter.log_media.LogMediaController;
import interface_adapter.log_media.LogMediaState;
import interface_adapter.log_media.LogMediaViewModel;
import interface_adapter.media_detail.MediaDetailController;
import interface_adapter.media_detail.MediaDetailState;
import interface_adapter.media_detail.MediaDetailViewModel;
import interface_adapter.media_reviews.MediaReviewsController;
import interface_adapter.media_reviews.MediaReviewsViewModel;

/**
 * The View for displaying media details.
 */
public class MediaDetailView extends JPanel implements PropertyChangeListener {

    private static final String POSTER_BASE_URL =
            "https://image.tmdb.org/t/p/w342";
    private static final int POSTER_WIDTH = 140;
    private static final int POSTER_HEIGHT = 210;
    private static final int DETAIL_GAP = 12;
    private static final int NO_DETAIL_GAP = 0;
    private static final int LOG_MESSAGE_WIDTH = 360;
    private static final int LOG_MESSAGE_HEIGHT = 48;

    private final String viewName = MediaDetailViewModel.VIEW_NAME;

    private final MediaDetailViewModel mediaDetailViewModel;
    private final LogMediaViewModel logMediaViewModel;

    private final JLabel titleLabel;
    private final JLabel releaseYearLabel;
    private final JLabel ratingLabel;
    private final JLabel genreLabel;
    private final JLabel languageLabel;
    private final JTextArea overviewTextArea;
    private final JLabel posterLabel;
    private final MediaReviewsPanel mediaReviewsPanel;

    /**
     * How this screen finds out who is signed in.
     */
    private Supplier<String> usernameSource;
    private Function<String, String> displayNameSource;
    private MediaDetailController mediaDetailController;
    private LogMediaController logMediaController;

    private final JLabel errorLabel;
    private final JLabel logMediaLabel;

    private final JButton backButton;
    private final JButton watchlistButton;
    private final JButton watchHistoryButton;

    public MediaDetailView(MediaDetailViewModel mediaDetailViewModel,
                           MediaReviewsViewModel mediaReviewsViewModel,
                           CommentsViewModel commentsViewModel,
                           LogMediaViewModel logMediaViewModel) {

        this.mediaDetailViewModel = mediaDetailViewModel;
        this.logMediaViewModel = logMediaViewModel;
        this.mediaDetailViewModel.addPropertyChangeListener(this);
        this.logMediaViewModel.addPropertyChangeListener(this);
        mediaReviewsPanel =
                new MediaReviewsPanel(mediaReviewsViewModel,
                        commentsViewModel);

        final JLabel pageTitle =
                new JLabel(MediaDetailViewModel.TITLE_LABEL);

        pageTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        titleLabel = new JLabel();

        releaseYearLabel = new JLabel();

        ratingLabel = new JLabel();

        genreLabel = new JLabel();

        languageLabel = new JLabel();

        overviewTextArea = new JTextArea(5, 42);
        overviewTextArea.setEditable(false);
        overviewTextArea.setLineWrap(true);
        overviewTextArea.setWrapStyleWord(true);
        overviewTextArea.setOpaque(false);
        overviewTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        posterLabel = new JLabel();
        posterLabel.setPreferredSize(
                new Dimension(POSTER_WIDTH, POSTER_HEIGHT)
        );
        posterLabel.setHorizontalAlignment(JLabel.CENTER);
        posterLabel.setVerticalAlignment(JLabel.TOP);

        errorLabel = new JLabel();
        logMediaLabel = new JLabel();
        logMediaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logMediaLabel.setPreferredSize(new Dimension(LOG_MESSAGE_WIDTH,
                LOG_MESSAGE_HEIGHT));
        logMediaLabel.setMinimumSize(new Dimension(LOG_MESSAGE_WIDTH,
                LOG_MESSAGE_HEIGHT));

        backButton =
                new JButton(MediaDetailViewModel.BACK_BUTTON_LABEL);
        backButton.addActionListener(
                event -> mediaDetailController.backToSearchResultView()
        );
        watchlistButton =
                new JButton(LogMediaViewModel.WATCHLIST_BUTTON_LABEL);
        watchlistButton.addActionListener(
                event -> addCurrentMediaToWatchlist()
        );
        watchHistoryButton =
                new JButton(LogMediaViewModel.WATCH_HISTORY_BUTTON_LABEL);
        watchHistoryButton.addActionListener(
                event -> addCurrentMediaToWatchHistory()
        );

        this.setLayout(new BorderLayout());
        this.setBorder(
                BorderFactory.createEmptyBorder(
                        DETAIL_GAP,
                        DETAIL_GAP,
                        DETAIL_GAP,
                        DETAIL_GAP
                )
        );

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(pageTitle);

        final JPanel informationPanel = new JPanel();
        informationPanel.setLayout(
                new BoxLayout(informationPanel, BoxLayout.Y_AXIS)
        );
        informationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        releaseYearLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        genreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        languageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        informationPanel.add(titleLabel);
        informationPanel.add(releaseYearLabel);
        informationPanel.add(ratingLabel);
        informationPanel.add(genreLabel);
        informationPanel.add(languageLabel);
        informationPanel.add(overviewTextArea);

        final JPanel actionPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionPanel.add(watchlistButton);
        actionPanel.add(watchHistoryButton);
        informationPanel.add(actionPanel);
        informationPanel.add(logMediaLabel);

        final JPanel detailHeaderPanel =
                new JPanel(new BorderLayout(DETAIL_GAP, 0));
        detailHeaderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailHeaderPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        NO_DETAIL_GAP, 0, 0, 0
                )
        );
        detailHeaderPanel.add(posterLabel, BorderLayout.WEST);
        detailHeaderPanel.add(informationPanel, BorderLayout.CENTER);

        topPanel.add(detailHeaderPanel);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(mediaReviewsPanel, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomPanel.add(backButton);
        bottomPanel.add(errorLabel);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return viewName;
    }

    public void setMediaDetailController(
            MediaDetailController mediaDetailController) {
        this.mediaDetailController = mediaDetailController;
    }

    /**
     * Sets the controller used to log media to user lists.
     *
     * @param inputLogMediaController the input log media controller
     */
    public void setLogMediaController(
            LogMediaController inputLogMediaController) {
        this.logMediaController = inputLogMediaController;
    }

    /**
     * Sets the controller used by the embedded media reviews panel.
     *
     * @param mediaReviewsController the media reviews controller
     */
    public void setMediaReviewsController(
            MediaReviewsController mediaReviewsController) {
        mediaReviewsPanel.setMediaReviewsController(mediaReviewsController);
    }

    /**
     * Sets the controller used by the embedded comments panel.
     *
     * @param commentsController the comments controller
     */
    public void setCommentsController(CommentsController commentsController) {
        mediaReviewsPanel.setCommentsController(commentsController);
    }

    /**
     * Tells this screen how to find out who is signed in.
     *
     * The reviews and comments panels refuse to act without a signed-in user,
     * and nothing was ever telling them who that is, so writing a review did
     * nothing at all. Nobody is signed in when the screen is built, so this
     * takes suppliers and asks them each time the screen is shown.
     *
     * @param usernameSource the username source
     * @param displayNameSource the display name source
     */
    public void setCurrentUserSource(Supplier<String> usernameSource,
                                     Function<String, String> displayNameSource) {
        this.usernameSource = usernameSource;
        this.displayNameSource = displayNameSource;
    }

    /**
     * Passes the signed-in user down to the panels that need one.
     */
    private void refreshCurrentUser() {
        if (usernameSource == null) {
            return;
        }
        final String username = usernameSource.get();
        String displayName = "";
        if (username != null && !username.isEmpty() && displayNameSource != null) {
            displayName = displayNameSource.apply(username);
        }
        mediaReviewsPanel.setCurrentUser(username, displayName);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Who is signed in can change between visits, so it is read each time
        // rather than captured once when the screen was built.
        refreshCurrentUser();

        if (evt.getSource() == logMediaViewModel) {
            updateLogMediaStatus((LogMediaState) evt.getNewValue());
        }
        else {
            updateMediaDetails((MediaDetailState) evt.getNewValue());
        }
    }

    private void updateMediaDetails(MediaDetailState state) {

        titleLabel.setText(
                "Title: " + state.getTitle());

        releaseYearLabel.setText(
                "Release Year: " + state.getReleaseYear());

        ratingLabel.setText(
                "Average Rating: "
                        + state.getAverageRating());

        genreLabel.setText(
                "Genres: " + state.getGenreNames().stream()
                        .collect(Collectors.joining(", "))
        );

        languageLabel.setText(
                "Language: "
                        + state.getLanguage());

        overviewTextArea.setText(
                "Overview: " + state.getOverview());
        overviewTextArea.setCaretPosition(0);

        updatePoster(state.getPosterPath());

        errorLabel.setText(state.getMediaDetailError());
    }

    private void updatePoster(String posterPath) {
        posterLabel.setIcon(null);

        if (posterPath == null || posterPath.isEmpty()) {
            posterLabel.setText("Poster unavailable.");
            return;
        }

        posterLabel.setText("Loading poster...");

        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() {
                try {
                    final ImageIcon original =
                            new ImageIcon(URI.create(
                                    POSTER_BASE_URL + posterPath
                            ).toURL());
                    if (original.getIconWidth() <= 0) {
                        return null;
                    }
                    final Image scaled = original.getImage().getScaledInstance(
                            POSTER_WIDTH,
                            POSTER_HEIGHT,
                            Image.SCALE_SMOOTH
                    );
                    return new ImageIcon(scaled);
                }
                catch (MalformedURLException | IllegalArgumentException
                       exception) {
                    return null;
                }
            }

            @Override
            protected void done() {
                if (!posterPath.equals(
                        mediaDetailViewModel.getState().getPosterPath())) {
                    return;
                }

                try {
                    final ImageIcon poster = get();
                    posterLabel.setIcon(poster);
                    posterLabel.setText(
                            poster == null ? "Poster unavailable." : ""
                    );
                }
                catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    posterLabel.setText("Poster unavailable.");
                }
                catch (ExecutionException exception) {
                    posterLabel.setText("Poster unavailable.");
                }
            }
        }.execute();
    }

    private void updateLogMediaStatus(LogMediaState state) {
        final String statusText;
        if (state.getError() == null || state.getError().isEmpty()) {
            statusText = state.getMessage();
            logMediaLabel.setForeground(Color.BLACK);
        }
        else {
            statusText = state.getError();
            logMediaLabel.setForeground(Color.RED);
        }
        logMediaLabel.setText(formatWrappedLabelText(statusText));
    }

    /**
     * Formats status text so Swing can wrap it across multiple lines.
     *
     * @param text the status text
     * @return the wrapped label text
     */
    private String formatWrappedLabelText(String text) {
        final String labelText;
        if (text == null || text.isEmpty()) {
            labelText = "";
        }
        else {
            final String wrappedText = escapeHtml(text)
                    .replace("history. Remove", "history.<br>Remove")
                    .replace("before adding", "before<br>adding");
            labelText = "<html><body style='width: "
                    + LOG_MESSAGE_WIDTH + "px'>"
                    + wrappedText + "</body></html>";
        }
        return labelText;
    }

    /**
     * Escapes characters that have special meaning in HTML.
     *
     * @param text the text to escape
     * @return the escaped text
     */
    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private void addCurrentMediaToWatchlist() {
        final MediaDetailState state = mediaDetailViewModel.getState();
        if (logMediaController != null) {
            logMediaController.addToWatchlist(state.getMediaId(),
                    state.getMediaType(), state.getTitle(),
                    state.getPosterPath());
        }
    }

    private void addCurrentMediaToWatchHistory() {
        final MediaDetailState state = mediaDetailViewModel.getState();
        if (logMediaController != null) {
            logMediaController.addToWatchHistory(state.getMediaId(),
                    state.getMediaType(), state.getTitle(),
                    state.getPosterPath());
        }
    }
}
