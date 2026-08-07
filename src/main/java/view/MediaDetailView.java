package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

/** The View for displaying media details. */
public class MediaDetailView extends JPanel implements PropertyChangeListener {

    private final String viewName = MediaDetailViewModel.VIEW_NAME;

    private final MediaDetailViewModel mediaDetailViewModel;
    private final LogMediaViewModel logMediaViewModel;

    private final JLabel titleLabel;
    private final JLabel releaseYearLabel;
    private final JLabel ratingLabel;
    private final JLabel genreLabel;
    private final JLabel languageLabel;
    private final MediaReviewsPanel mediaReviewsPanel;
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

        pageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel = new JLabel();

        releaseYearLabel = new JLabel();

        ratingLabel = new JLabel();

        genreLabel = new JLabel();

        languageLabel = new JLabel();

        errorLabel = new JLabel();
        logMediaLabel = new JLabel();

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

        this.setLayout(
                new BoxLayout(this, BoxLayout.Y_AXIS)
        );

        this.add(pageTitle);

        this.add(titleLabel);

        this.add(releaseYearLabel);

        this.add(ratingLabel);

        this.add(genreLabel);

        this.add(languageLabel);

        this.add(watchlistButton);

        this.add(watchHistoryButton);

        this.add(logMediaLabel);

        this.add(mediaReviewsPanel);

        this.add(backButton);

        this.add(errorLabel);
    }

    public String getViewName() {
        return viewName;
    }

    public void setMediaDetailController(
            MediaDetailController mediaDetailController) {
        this.mediaDetailController = mediaDetailController;
    }

    /** Sets the controller used to log media to user lists. */
    public void setLogMediaController(
            LogMediaController inputLogMediaController) {
        this.logMediaController = inputLogMediaController;
    }

    /** Sets the controller used by the embedded media reviews panel. */
    public void setMediaReviewsController(
            MediaReviewsController mediaReviewsController) {
        mediaReviewsPanel.setMediaReviewsController(mediaReviewsController);
    }

    /** Sets the controller used by the embedded comments panel. */
    public void setCommentsController(CommentsController commentsController) {
        mediaReviewsPanel.setCommentsController(commentsController);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == logMediaViewModel) {
            updateLogMediaStatus((LogMediaState) evt.getNewValue());
        } else {
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
                "Genres: "
                        + state.getGenres());

        languageLabel.setText(
                "Language: "
                        + state.getLanguage());

        errorLabel.setText(state.getMediaDetailError());
    }

    private void updateLogMediaStatus(LogMediaState state) {
        final String statusText;
        if (state.getError() == null || state.getError().isEmpty()) {
            statusText = state.getMessage();
        } else {
            statusText = state.getError();
        }
        logMediaLabel.setText(statusText);
    }

    private void addCurrentMediaToWatchlist() {
        final MediaDetailState state = mediaDetailViewModel.getState();
        if (logMediaController != null) {
            logMediaController.addToWatchlist(state.getMediaId(),
                    state.getMediaType(), state.getTitle());
        }
    }

    private void addCurrentMediaToWatchHistory() {
        final MediaDetailState state = mediaDetailViewModel.getState();
        if (logMediaController != null) {
            logMediaController.addToWatchHistory(state.getMediaId(),
                    state.getMediaType(), state.getTitle());
        }
    }
}
