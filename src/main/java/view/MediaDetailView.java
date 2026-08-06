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
import interface_adapter.media_detail.MediaDetailController;
import interface_adapter.media_detail.MediaDetailState;
import interface_adapter.media_detail.MediaDetailViewModel;
import interface_adapter.media_reviews.MediaReviewsController;
import interface_adapter.media_reviews.MediaReviewsViewModel;

/**
 * The View for displaying media details.
 */
public class MediaDetailView extends JPanel implements PropertyChangeListener {

    private final String viewName = MediaDetailViewModel.VIEW_NAME;

    private final MediaDetailViewModel mediaDetailViewModel;

    private final JLabel titleLabel;
    private final JLabel releaseYearLabel;
    private final JLabel ratingLabel;
    private final JLabel genreLabel;
    private final JLabel languageLabel;
    private final MediaReviewsPanel mediaReviewsPanel;
    private MediaDetailController mediaDetailController;

    private final JLabel errorLabel;

    private final JButton backButton;

    public MediaDetailView(MediaDetailViewModel mediaDetailViewModel,
                           MediaReviewsViewModel mediaReviewsViewModel,
                           CommentsViewModel commentsViewModel) {

        this.mediaDetailViewModel = mediaDetailViewModel;
        this.mediaDetailViewModel.addPropertyChangeListener(this);
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

        backButton =
                new JButton(MediaDetailViewModel.BACK_BUTTON_LABEL);
        backButton.addActionListener(
                event -> mediaDetailController.backToSearchResultView()
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        final MediaDetailState state =
                (MediaDetailState) evt.getNewValue();

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
}
