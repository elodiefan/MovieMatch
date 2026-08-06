package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import interface_adapter.comments.CommentsController;
import interface_adapter.comments.CommentRow;
import interface_adapter.comments.CommentsViewModel;
import interface_adapter.media_reviews.MediaReviewsController;
import interface_adapter.media_reviews.MediaReviewRow;
import interface_adapter.media_reviews.MediaReviewsState;
import interface_adapter.media_reviews.MediaReviewsViewModel;

/**
 * Swing panel for reviews shown on a media page.
 */
public final class MediaReviewsPanel extends JPanel
        implements PropertyChangeListener {
    /** The heart unselected. */
    private static final String HEART_UNSELECTED = "\u2661";
    /** The heart selected. */
    private static final String HEART_SELECTED = "\u2665";

    /** The card gap. */
    private static final int CARD_GAP = 10;
    /** The comment gap. */
    private static final int COMMENT_GAP = 6;
    /** The comment indent. */
    private static final int COMMENT_INDENT = 24;
    /** The reply indent. */
    private static final int REPLY_INDENT = 48;
    /** MovieMatch review source label. */
    private static final String MOVIEMATCH_SOURCE = "moviematch";

    /** The time_formatter. */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a z");

    /** The media reviews view model. */
    private final MediaReviewsViewModel mediaReviewsViewModel;
    /** The comments view model. */
    private final CommentsViewModel commentsViewModel;
    /** The media title label. */
    private final JLabel mediaTitleLabel = new JLabel();
    /** The error label. */
    private final JLabel errorLabel = new JLabel();
    /** The reviews panel. */
    private final JPanel reviewsPanel = new JPanel();
    /** The write review button. */
    private final JButton writeReviewButton =
            new JButton(MediaReviewsViewModel.WRITE_REVIEW_BUTTON_LABEL);
    /** The media reviews controller. */
    private MediaReviewsController mediaReviewsController;
    /** The comments controller. */
    private CommentsController commentsController;
    /** The current username. */
    private String currentUsername = "";
    /** The current display name. */
    private String currentDisplayName = "";
    /** The loading content. */
    private boolean loadingContent;

    /**
     * Handles this review or comment operation.
     * @param inputMediaReviewsViewModel the inputMediaReviewsViewModel
     */
    public MediaReviewsPanel(
            final MediaReviewsViewModel inputMediaReviewsViewModel) {
        this(inputMediaReviewsViewModel, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputMediaReviewsViewModel the inputMediaReviewsViewModel
     * @param inputCommentsViewModel the inputCommentsViewModel
     */
    public MediaReviewsPanel(
            final MediaReviewsViewModel inputMediaReviewsViewModel,
            final CommentsViewModel inputCommentsViewModel) {
        this.mediaReviewsViewModel = inputMediaReviewsViewModel;
        this.commentsViewModel = inputCommentsViewModel;
        this.mediaReviewsViewModel.addPropertyChangeListener(this);
        if (inputCommentsViewModel != null) {
            this.commentsViewModel.addPropertyChangeListener(this);
        }

        final JLabel title = new JLabel(MediaReviewsViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediaTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(title);
        add(mediaTitleLabel);
        add(errorLabel);
        add(writeReviewButton);
        add(new JScrollPane(reviewsPanel));

        writeReviewButton.addActionListener(new WriteReviewListener());

        updateView(inputMediaReviewsViewModel.getState());
    }

    /**
     * Updates the panel when the media reviews state changes.
     * @param event the property change event
     */
    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        if ("state".equals(event.getPropertyName())) {
            updateView(mediaReviewsViewModel.getState());
        }
    }

    /**
     * Returns the write review button for app wiring.
     * @return the write review button
     */
    public JButton getWriteReviewButton() {
        return writeReviewButton;
    }

    /**
     * Sets the media reviews controller.
     * @param inputMediaReviewsController the media reviews controller
     */
    public void setMediaReviewsController(
            final MediaReviewsController inputMediaReviewsController) {
        this.mediaReviewsController = inputMediaReviewsController;
    }

    /**
     * Sets the comments controller.
     * @param inputCommentsController the comments controller
     */
    public void setCommentsController(
            final CommentsController inputCommentsController) {
        this.commentsController = inputCommentsController;
    }

    /**
     * Sets the current user for write, edit, delete, like, and unlike actions.
     * @param username the current username
     * @param displayName the current user's display name
     */
    public void setCurrentUser(final String username,
                               final String displayName) {
        currentUsername = trimToEmpty(username);
        currentDisplayName = trimToEmpty(displayName);
    }

    /**
     * Refreshes visible content from state.
     * @param state the media reviews state
     */
    private void updateView(final MediaReviewsState state) {
        if (state != null) {
            mediaTitleLabel.setText(state.getMediaTitle());
            errorLabel.setText(state.getMediaReviewsError());
            loadContent(state);
            setReviews(state.getReviews());
        }
    }

    /**
     * Loads persisted reviews and comments for the current media item.
     * @param state the media reviews state
     */
    private void loadContent(final MediaReviewsState state) {
        if (!loadingContent && mediaReviewsController != null
                && !isBlank(state.getMediaType())) {
            loadingContent = true;
            mediaReviewsController.loadMediaReviews(state.getMediaId(),
                    state.getMediaType());
            refreshCommentsForReviews(state);
            loadingContent = false;
        }
    }

    /**
     * Loads persisted comments for the displayed reviews.
     * @param state the media reviews state
     */
    private void refreshCommentsForReviews(final MediaReviewsState state) {
        if (commentsController != null && commentsViewModel != null) {
            for (MediaReviewRow review
                    : state.getReviews()) {
                commentsController.loadReviewComments(review.getReviewId());
            }
        }
    }

    /**
     * Displays the given review rows.
     * @param reviews the review rows to display
     */
    private void setReviews(
            final List<MediaReviewRow> reviews) {
        reviewsPanel.removeAll();

        if (reviews.isEmpty()) {
            reviewsPanel.add(new JLabel(
                    MediaReviewsViewModel.EMPTY_REVIEWS_MESSAGE));
        } else {
            for (MediaReviewRow review : reviews) {
                reviewsPanel.add(createReviewCard(review));
                reviewsPanel.add(Box.createVerticalStrut(CARD_GAP));
            }
        }

        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }

    /**
     * Creates the display card for one media review.
     * @param review the review row
     * @return the review card
     */
    private Component createReviewCard(
            final MediaReviewRow review) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(new JLabel(review.getAuthorDisplayName()
                + " (@" + review.getAuthorUsername() + ")"));
        card.add(new JLabel("Rating: " + review.getRating() + "%"));
        card.add(new JLabel("Created: " + formatTime(review.getCreatedAt())));
        card.add(new JLabel("Updated: " + formatTime(review.getUpdatedAt())));
        card.add(new JLabel("Likes: " + review.getLikeCount()));
        card.add(new JLabel(review.getReviewText()));
        if (isMovieMatchReview(review)) {
            card.add(createButtonPanel(review));
        } else {
            card.add(new JLabel("External TMDB review"));
        }
        card.add(createCommentsSection(review.getReviewId()));

        return card;
    }

    /**
     * Creates action buttons for one review.
     * @param review the review row
     * @return the button panel
     */
    private Component createButtonPanel(
            final MediaReviewRow review) {
        final JPanel buttonPanel = new JPanel();
        final JButton editButton =
                new JButton(MediaReviewsViewModel.EDIT_BUTTON_LABEL);
        final JButton deleteButton =
                new JButton(MediaReviewsViewModel.DELETE_BUTTON_LABEL);
        final JToggleButton heartButton = createHeartButton(
                MediaReviewsViewModel.LIKE_BUTTON_LABEL);

        editButton.addActionListener(new SelectReviewListener(
                review.getReviewId()));
        deleteButton.addActionListener(new SelectReviewListener(
                review.getReviewId()));
        heartButton.addActionListener(new HeartReviewListener(
                review.getReviewId()));

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(heartButton);
        return buttonPanel;
    }

    /**
     * Creates the nested comments section for one review.
     * @param reviewId the review id
     * @return the comments section
     */
    private Component createCommentsSection(final String reviewId) {
        final JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBorder(BorderFactory.createEmptyBorder(CARD_GAP, 0, 0, 0));

        if (commentsViewModel != null) {
            section.add(new JLabel(CommentsViewModel.TITLE_LABEL));
            boolean hasComments = false;
            for (CommentRow comment
                    : commentsViewModel.getState().getComments()) {
                if (comment.getReviewId().equals(reviewId)) {
                    section.add(createCommentCard(comment));
                    section.add(Box.createVerticalStrut(COMMENT_GAP));
                    hasComments = true;
                }
            }
            if (!hasComments) {
                section.add(new JLabel(
                        CommentsViewModel.EMPTY_COMMENTS_MESSAGE));
            }
        }

        return section;
    }

    /**
     * Creates the display card for one nested comment.
     * @param comment the comment row
     * @return the comment card
     */
    private Component createCommentCard(
            final CommentRow comment) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(0,
                getCommentIndent(comment), 0, 0));

        card.add(new JLabel(comment.getAuthorDisplayName()
                + " (@" + comment.getAuthorUsername() + ")"));
        card.add(new JLabel("Created: " + formatTime(comment.getCreatedAt())));
        card.add(new JLabel("Likes: " + comment.getLikeCount()));
        card.add(new JLabel(comment.getCommentText()));
        card.add(createCommentButtonPanel(comment));

        return card;
    }

    /**
     * Creates action buttons for one nested comment.
     * @param comment the comment row
     * @return the button panel
     */
    private Component createCommentButtonPanel(
            final CommentRow comment) {
        final JPanel buttonPanel = new JPanel();
        final JButton replyButton =
                new JButton(CommentsViewModel.REPLY_BUTTON_LABEL);
        final JButton deleteButton =
                new JButton(CommentsViewModel.DELETE_BUTTON_LABEL);
        final JToggleButton heartButton = createHeartButton(
                CommentsViewModel.LIKE_BUTTON_LABEL);

        replyButton.addActionListener(new SelectCommentListener(
                comment.getCommentId(), comment.getReviewId(), true));
        deleteButton.addActionListener(new SelectCommentListener(
                comment.getCommentId(), comment.getReviewId(), false));
        heartButton.addActionListener(new HeartCommentListener(
                comment.getCommentId()));

        buttonPanel.add(replyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(heartButton);
        return buttonPanel;
    }

    /**
     * Creates an unselected heart button for liking content.
     * @param tooltip the button tooltip
     * @return the heart button
     */
    private JToggleButton createHeartButton(final String tooltip) {
        final JToggleButton heartButton = new JToggleButton(HEART_UNSELECTED);
        heartButton.setToolTipText(tooltip);
        return heartButton;
    }

    /**
     * Checks whether a review was created inside MovieMatch.
     * @param review the review row
     * @return true if the review belongs to MovieMatch
     */
    private boolean isMovieMatchReview(
            final MediaReviewRow review) {
        return MOVIEMATCH_SOURCE.equals(review.getSource());
    }

    /**
     * Updates the heart button's selected appearance.
     * @param heartButton the heart button
     * @param selectedTooltip the tooltip for the selected state
     * @param unselectedTooltip the tooltip for the unselected state
     */
    private void updateHeartButton(final JToggleButton heartButton,
                                   final String selectedTooltip,
                                   final String unselectedTooltip) {
        if (heartButton.isSelected()) {
            heartButton.setText(HEART_SELECTED);
            heartButton.setForeground(Color.RED);
            heartButton.setToolTipText(selectedTooltip);
        } else {
            heartButton.setText(HEART_UNSELECTED);
            heartButton.setForeground(null);
            heartButton.setToolTipText(unselectedTooltip);
        }
    }

    /**
     * Returns the indentation for a top-level comment or reply.
     * @param comment the comment row
     * @return the indentation amount
     */
    private int getCommentIndent(final CommentRow comment) {
        final int indent;
        if (comment.getParentCommentId() == null
                || comment.getParentCommentId().isEmpty()) {
            indent = COMMENT_INDENT;
        } else {
            indent = REPLY_INDENT;
        }
        return indent;
    }

    /**
     * Formats a date and time for display.
     * @param dateTime the date and time
     * @return the formatted date and time
     */
    private String formatTime(final ZonedDateTime dateTime) {
        final String formattedTime;
        if (dateTime == null) {
            formattedTime = "";
        } else {
            formattedTime = dateTime.format(TIME_FORMATTER);
        }
        return formattedTime;
    }

    /**
     * Checks whether the current user is available for an action.
     * @return true if the user is available
     */
    private boolean hasCurrentUser() {
        return !isBlank(currentUsername) && !isBlank(currentDisplayName);
    }

    /**
     * Checks whether a value is null, empty, or only whitespace.
     * @param value the value to check
     * @return true if the value is blank
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Trims a value, or returns an empty string when null.
     * @param value the value to trim
     * @return the trimmed value
     */
    private String trimToEmpty(final String value) {
        final String trimmedValue;
        if (value == null) {
            trimmedValue = "";
        } else {
            trimmedValue = value.trim();
        }
        return trimmedValue;
    }

    /**
     * Clears the selected review when writing a new review.
     */
    private final class WriteReviewListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent event) {
            final MediaReviewsState state = mediaReviewsViewModel.getState();
            state.setSelectedReviewId("");
            if (mediaReviewsController != null && hasCurrentUser()) {
                final String ratingText = JOptionPane.showInputDialog(
                        MediaReviewsPanel.this, "Rating percentage:");
                final String reviewText = JOptionPane.showInputDialog(
                        MediaReviewsPanel.this, "Review text:");
                if (!isBlank(ratingText)) {
                    mediaReviewsController.createReview(state.getMediaId(),
                            state.getMediaType(), state.getMediaTitle(),
                            currentUsername, currentDisplayName,
                            Double.parseDouble(ratingText), reviewText);
                    mediaReviewsController.loadMediaReviews(state.getMediaId(),
                            state.getMediaType());
                }
            }
            mediaReviewsViewModel.firePropertyChanged();
        }
    }

    /**
     * Selects a review in the view model state.
     */
    private final class SelectReviewListener implements ActionListener {
        /** The review id. */
        private final String reviewId;

        private SelectReviewListener(final String inputReviewId) {
            this.reviewId = inputReviewId;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final MediaReviewsState state = mediaReviewsViewModel.getState();
            state.setSelectedReviewId(reviewId);
            if (mediaReviewsController != null && !isBlank(currentUsername)) {
                final String command =
                        ((JButton) event.getSource()).getText();
                if (MediaReviewsViewModel.DELETE_BUTTON_LABEL.equals(command)) {
                    mediaReviewsController.deleteReview(reviewId,
                            currentUsername);
                } else {
                    final String ratingText = JOptionPane.showInputDialog(
                            MediaReviewsPanel.this, "New rating percentage:");
                    final String reviewText = JOptionPane.showInputDialog(
                            MediaReviewsPanel.this, "New review text:");
                    if (!isBlank(ratingText)) {
                        mediaReviewsController.editReview(reviewId,
                                currentUsername, Double.parseDouble(ratingText),
                                reviewText);
                    }
                }
                mediaReviewsController.loadMediaReviews(state.getMediaId(),
                        state.getMediaType());
            }
            mediaReviewsViewModel.firePropertyChanged();
        }
    }

    /**
     * Toggles a review heart and selects the review.
     */
    private final class HeartReviewListener implements ActionListener {
        /** The review id. */
        private final String reviewId;

        private HeartReviewListener(final String inputReviewId) {
            this.reviewId = inputReviewId;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final JToggleButton heartButton =
                    (JToggleButton) event.getSource();
            final MediaReviewsState state = mediaReviewsViewModel.getState();
            if (mediaReviewsController != null && !isBlank(currentUsername)) {
                if (heartButton.isSelected()) {
                    mediaReviewsController.likeReview(reviewId,
                            currentUsername);
                } else {
                    mediaReviewsController.unlikeReview(reviewId,
                            currentUsername);
                }
                mediaReviewsController.loadMediaReviews(state.getMediaId(),
                        state.getMediaType());
            }
            updateHeartButton(heartButton,
                    MediaReviewsViewModel.UNLIKE_BUTTON_LABEL,
                    MediaReviewsViewModel.LIKE_BUTTON_LABEL);

            state.setSelectedReviewId(reviewId);
            mediaReviewsViewModel.firePropertyChanged();
        }
    }

    /**
     * Selects a comment in the comments view model state.
     */
    private final class SelectCommentListener implements ActionListener {
        /** The comment id. */
        private final String commentId;
        /** The review id. */
        private final String reviewId;
        /** The reply. */
        private final boolean reply;

        private SelectCommentListener(final String inputCommentId,
                                      final String inputReviewId,
                                      final boolean inputReply) {
            this.commentId = inputCommentId;
            this.reviewId = inputReviewId;
            this.reply = inputReply;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            if (commentsViewModel != null) {
                commentsViewModel.getState().setSelectedCommentId(commentId);
                if (reply) {
                    commentsViewModel.getState().setParentCommentId(commentId);
                    if (commentsController != null && hasCurrentUser()) {
                        final String commentText = JOptionPane.showInputDialog(
                                MediaReviewsPanel.this, "Reply text:");
                        if (!isBlank(commentText)) {
                            commentsController.createComment(reviewId,
                                    commentId, currentUsername,
                                    currentDisplayName, commentText);
                            commentsController.loadReviewComments(reviewId);
                        }
                    }
                } else if (commentsController != null
                        && !isBlank(currentUsername)) {
                    commentsController.deleteComment(commentId,
                            currentUsername);
                    commentsController.loadReviewComments(reviewId);
                }
                commentsViewModel.firePropertyChanged();
            }
        }
    }

    /**
     * Toggles a comment heart and selects the comment.
     */
    private final class HeartCommentListener implements ActionListener {
        /** The comment id. */
        private final String commentId;

        private HeartCommentListener(final String inputCommentId) {
            this.commentId = inputCommentId;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final JToggleButton heartButton =
                    (JToggleButton) event.getSource();
            if (commentsController != null && !isBlank(currentUsername)) {
                if (heartButton.isSelected()) {
                    commentsController.likeComment(commentId, currentUsername);
                } else {
                    commentsController.unlikeComment(commentId,
                            currentUsername);
                }
                refreshCommentsForReviews(mediaReviewsViewModel.getState());
            }
            updateHeartButton(heartButton,
                    CommentsViewModel.UNLIKE_BUTTON_LABEL,
                    CommentsViewModel.LIKE_BUTTON_LABEL);

            if (commentsViewModel != null) {
                commentsViewModel.getState().setSelectedCommentId(commentId);
                commentsViewModel.firePropertyChanged();
            }
        }
    }
}
