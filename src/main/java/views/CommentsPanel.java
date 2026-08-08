package views;

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

import interface_adapter.comments.CommentRow;
import interface_adapter.comments.CommentsController;
import interface_adapter.comments.CommentsState;
import interface_adapter.comments.CommentsViewModel;

/**
 * Swing panel for comments on a review.
 */
public final class CommentsPanel extends JPanel
        implements PropertyChangeListener {
    /**
     * The heart unselected.
     */
    private static final String HEART_UNSELECTED = "\u2661";
    /**
     * The heart selected.
     */
    private static final String HEART_SELECTED = "\u2665";

    /**
     * The card gap.
     */
    private static final int CARD_GAP = 10;
    /**
     * The comment indent.
     */
    private static final int COMMENT_INDENT = 24;
    /**
     * The reply indent.
     */
    private static final int REPLY_INDENT = 48;

    /**
     * The time_formatter.
     */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a z");

    /**
     * The comments view model.
     */
    private final CommentsViewModel commentsViewModel;
    /**
     * The error label.
     */
    private final JLabel errorLabel = new JLabel();
    /**
     * The comments panel.
     */
    private final JPanel commentsPanel = new JPanel();
    /**
     * The write comment button.
     */
    private final JButton writeCommentButton =
            new JButton(CommentsViewModel.WRITE_COMMENT_BUTTON_LABEL);
    /**
     * The comments controller.
     */
    private CommentsController commentsController;
    /**
     * The current username.
     */
    private String currentUsername = "";
    /**
     * The current display name.
     */
    private String currentDisplayName = "";
    /**
     * The loading comments.
     */
    private boolean loadingComments;

    /**
     * Handles this review or comment operation.
     * @param inputCommentsViewModel the inputCommentsViewModel
     */
    public CommentsPanel(final CommentsViewModel inputCommentsViewModel) {
        this.commentsViewModel = inputCommentsViewModel;
        this.commentsViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(CommentsViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(title);
        add(errorLabel);
        add(writeCommentButton);
        add(new JScrollPane(commentsPanel));

        writeCommentButton.addActionListener(new WriteCommentListener());

        updateView(inputCommentsViewModel.getState());
    }

    /**
     * Updates the panel when the comments state changes.
     * @param event the property change event
     */
    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        if ("state".equals(event.getPropertyName())) {
            updateView((CommentsState) event.getNewValue());
        }
    }

    /**
     * Returns the write comment button for app wiring.
     * @return the write comment button
     */
    public JButton getWriteCommentButton() {
        return writeCommentButton;
    }

    /**
     * Sets the controller for comment actions.
     * @param inputCommentsController the comments controller
     */
    public void setCommentsController(
            final CommentsController inputCommentsController) {
        this.commentsController = inputCommentsController;
    }

    /**
     * Sets the current user for write, delete, like, and unlike actions.
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
     * @param state the comments state
     */
    private void updateView(final CommentsState state) {
        if (state != null) {
            errorLabel.setText(state.getCommentsError());
            if (!refreshComments(state.getReviewId())) {
                setComments(state.getReviewId(), state.getComments());
            }
        }
    }

    /**
     * Loads persisted comments for one review into the view model state.
     * @param reviewId the review id to load comments for
     * @return true if fresh comments were requested
     */
    private boolean refreshComments(final String reviewId) {
        final boolean commentsRequested;
        if (!loadingComments && commentsController != null
                && !isBlank(reviewId)) {
            loadingComments = true;
            try {
                commentsController.loadReviewComments(reviewId);
            } finally {
                loadingComments = false;
            }
            commentsRequested = true;
        } else {
            commentsRequested = false;
        }
        return commentsRequested;
    }

    /**
     * Displays the given comment rows.
     * @param reviewId the review id whose comments should be displayed
     * @param comments the comment rows to display
     */
    private void setComments(final String reviewId,
            final List<CommentRow> comments) {
        commentsPanel.removeAll();

        boolean hasMatchingComments = false;
        for (CommentRow comment : comments) {
            if (belongsToReview(comment, reviewId)) {
                commentsPanel.add(createCommentCard(comment));
                commentsPanel.add(Box.createVerticalStrut(CARD_GAP));
                hasMatchingComments = true;
            }
        }

        if (!hasMatchingComments) {
            commentsPanel.add(new JLabel(
                    CommentsViewModel.EMPTY_COMMENTS_MESSAGE));
        }

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    /**
     * Creates the display card for one comment.
     * @param comment the comment row
     * @return the comment card
     */
    private Component createCommentCard(final CommentRow comment) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(0,
                getCommentIndent(comment), 0, 0));

        card.add(new JLabel(comment.getAuthorDisplayName()
                + " (@" + comment.getAuthorUsername() + ")"));
        card.add(new JLabel("Created: " + formatTime(comment.getCreatedAt())));
        card.add(new JLabel("Likes: " + comment.getLikeCount()));
        card.add(new JLabel(comment.getCommentText()));
        card.add(createButtonPanel(comment));

        return card;
    }

    /**
     * Creates action buttons for one comment.
     * @param comment the comment row
     * @return the button panel
     */
    private Component createButtonPanel(final CommentRow comment) {
        final JPanel buttonPanel = new JPanel();
        final JButton replyButton =
                new JButton(CommentsViewModel.REPLY_BUTTON_LABEL);
        final JButton deleteButton =
                new JButton(CommentsViewModel.DELETE_BUTTON_LABEL);
        final JToggleButton heartButton = createHeartButton();
        heartButton.setSelected(comment.isLikedBy(currentUsername));
        updateHeartButton(heartButton);

        replyButton.addActionListener(new SelectCommentListener(
                comment.getCommentId(), true));
        deleteButton.addActionListener(new SelectCommentListener(
                comment.getCommentId(), false));
        heartButton.addActionListener(new HeartCommentListener(
                comment.getCommentId()));

        buttonPanel.add(replyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(heartButton);
        return buttonPanel;
    }

    /**
     * Creates an unselected heart button for liking content.
     * @return the heart button
     */
    private JToggleButton createHeartButton() {
        final JToggleButton heartButton = new JToggleButton(HEART_UNSELECTED);
        heartButton.setToolTipText(CommentsViewModel.LIKE_BUTTON_LABEL);
        return heartButton;
    }

    /**
     * Updates the heart button's selected appearance.
     * @param heartButton the heart button
     */
    private void updateHeartButton(final JToggleButton heartButton) {
        if (heartButton.isSelected()) {
            heartButton.setText(HEART_SELECTED);
            heartButton.setForeground(Color.RED);
            heartButton.setToolTipText(CommentsViewModel.UNLIKE_BUTTON_LABEL);
        } else {
            heartButton.setText(HEART_UNSELECTED);
            heartButton.setForeground(null);
            heartButton.setToolTipText(CommentsViewModel.LIKE_BUTTON_LABEL);
        }
    }

    /**
     * Checks whether a comment belongs under the given review.
     * @param comment the comment row
     * @param reviewId the review id
     * @return true if the comment belongs to the review
     */
    private boolean belongsToReview(final CommentRow comment,
                                    final String reviewId) {
        return reviewId == null || reviewId.isEmpty()
                || comment.getReviewId().equals(reviewId);
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
     * Marks the next comment as a top-level comment.
     */
    private final class WriteCommentListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent event) {
            final CommentsState state = commentsViewModel.getState();
            state.setParentCommentId("");
            if (commentsController != null && hasCurrentUser()) {
                final String commentText = JOptionPane.showInputDialog(
                        CommentsPanel.this, "Comment text:");
                if (!isBlank(commentText)) {
                    commentsController.createComment(state.getReviewId(), "",
                            currentUsername, currentDisplayName, commentText);
                    commentsController.loadReviewComments(state.getReviewId());
                }
            }
            commentsViewModel.firePropertyChanged();
        }
    }

    /**
     * Selects a comment in the view model state.
     */
    private final class SelectCommentListener implements ActionListener {
        /**
         * The comment id.
         */
        private final String commentId;
        /**
         * The reply.
         */
        private final boolean reply;

        private SelectCommentListener(final String inputCommentId,
                                      final boolean inputReply) {
            this.commentId = inputCommentId;
            this.reply = inputReply;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final CommentsState state = commentsViewModel.getState();
            state.setSelectedCommentId(commentId);
            if (reply) {
                state.setParentCommentId(commentId);
                if (commentsController != null && hasCurrentUser()) {
                    final String commentText = JOptionPane.showInputDialog(
                            CommentsPanel.this, "Reply text:");
                    if (!isBlank(commentText)) {
                        commentsController.createComment(state.getReviewId(),
                                commentId, currentUsername,
                                currentDisplayName, commentText);
                        commentsController.loadReviewComments(
                                state.getReviewId());
                    }
                }
            } else if (commentsController != null
                    && !isBlank(currentUsername)) {
                commentsController.deleteComment(commentId, currentUsername);
                commentsController.loadReviewComments(state.getReviewId());
            }
            commentsViewModel.firePropertyChanged();
        }
    }

    /**
     * Toggles a comment heart and selects the comment.
     */
    private final class HeartCommentListener implements ActionListener {
        /**
         * The comment id.
         */
        private final String commentId;

        private HeartCommentListener(final String inputCommentId) {
            this.commentId = inputCommentId;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final JToggleButton heartButton =
                    (JToggleButton) event.getSource();
            final CommentsState state = commentsViewModel.getState();
            if (commentsController != null && !isBlank(currentUsername)) {
                if (heartButton.isSelected()) {
                    commentsController.likeComment(commentId, currentUsername);
                } else {
                    commentsController.unlikeComment(commentId,
                            currentUsername);
                }
                commentsController.loadReviewComments(state.getReviewId());
            }
            updateHeartButton(heartButton);

            state.setSelectedCommentId(commentId);
            commentsViewModel.firePropertyChanged();
        }
    }
}
