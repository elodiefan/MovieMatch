package view;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import interface_adapter.comments.CommentsPresenter;
import interface_adapter.comments.CommentsViewModel;
import interface_adapter.media_reviews.MediaReviewsPresenter;
import interface_adapter.media_reviews.MediaReviewsState;
import interface_adapter.media_reviews.MediaReviewsViewModel;

/**
 * Swing panel for reviews shown on a media page.
 */
public class MediaReviewsPanel extends JPanel implements PropertyChangeListener {
    private static final int CARD_GAP = 10;
    private static final int COMMENT_GAP = 6;
    private static final int COMMENT_INDENT = 24;
    private static final int REPLY_INDENT = 48;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a z");

    private final MediaReviewsViewModel mediaReviewsViewModel;
    private final CommentsViewModel commentsViewModel;
    private final JLabel mediaTitleLabel = new JLabel();
    private final JLabel errorLabel = new JLabel();
    private final JPanel reviewsPanel = new JPanel();
    private final JButton writeReviewButton =
            new JButton(MediaReviewsViewModel.WRITE_REVIEW_BUTTON_LABEL);

    public MediaReviewsPanel(
            final MediaReviewsViewModel mediaReviewsViewModel) {
        this(mediaReviewsViewModel, null);
    }

    public MediaReviewsPanel(
            final MediaReviewsViewModel mediaReviewsViewModel,
            final CommentsViewModel commentsViewModel) {
        this.mediaReviewsViewModel = mediaReviewsViewModel;
        this.commentsViewModel = commentsViewModel;
        this.mediaReviewsViewModel.addPropertyChangeListener(this);
        if (commentsViewModel != null) {
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

        updateView(mediaReviewsViewModel.getState());
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
     * Refreshes visible content from state.
     * @param state the media reviews state
     */
    private void updateView(final MediaReviewsState state) {
        if (state != null) {
            mediaTitleLabel.setText(state.getMediaTitle());
            errorLabel.setText(state.getMediaReviewsError());
            setReviews(state.getReviews());
        }
    }

    /**
     * Displays the given review rows.
     * @param reviews the review rows to display
     */
    private void setReviews(
            final List<MediaReviewsPresenter.MediaReviewRow> reviews) {
        reviewsPanel.removeAll();

        if (reviews.isEmpty()) {
            reviewsPanel.add(new JLabel(
                    MediaReviewsViewModel.EMPTY_REVIEWS_MESSAGE));
        } else {
            for (MediaReviewsPresenter.MediaReviewRow review : reviews) {
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
            final MediaReviewsPresenter.MediaReviewRow review) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(new JLabel(review.getAuthorDisplayName()
                + " (@" + review.getAuthorUsername() + ")"));
        card.add(new JLabel("Rating: " + review.getRating() + "%"));
        card.add(new JLabel("Created: " + formatTime(review.getCreatedAt())));
        card.add(new JLabel("Updated: " + formatTime(review.getUpdatedAt())));
        card.add(new JLabel("Likes: " + review.getLikeCount()));
        card.add(new JLabel(review.getReviewText()));
        card.add(createButtonPanel(review));
        card.add(createCommentsSection(review.getReviewId()));

        return card;
    }

    /**
     * Creates action buttons for one review.
     * @param review the review row
     * @return the button panel
     */
    private Component createButtonPanel(
            final MediaReviewsPresenter.MediaReviewRow review) {
        final JPanel buttonPanel = new JPanel();
        final JButton editButton =
                new JButton(MediaReviewsViewModel.EDIT_BUTTON_LABEL);
        final JButton deleteButton =
                new JButton(MediaReviewsViewModel.DELETE_BUTTON_LABEL);
        final JButton likeButton =
                new JButton(MediaReviewsViewModel.LIKE_BUTTON_LABEL);
        final JButton unlikeButton =
                new JButton(MediaReviewsViewModel.UNLIKE_BUTTON_LABEL);

        editButton.addActionListener(new SelectReviewListener(
                review.getReviewId()));
        deleteButton.addActionListener(new SelectReviewListener(
                review.getReviewId()));
        likeButton.addActionListener(new SelectReviewListener(
                review.getReviewId()));
        unlikeButton.addActionListener(new SelectReviewListener(
                review.getReviewId()));

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(likeButton);
        buttonPanel.add(unlikeButton);
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
            for (CommentsPresenter.CommentRow comment
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
            final CommentsPresenter.CommentRow comment) {
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
            final CommentsPresenter.CommentRow comment) {
        final JPanel buttonPanel = new JPanel();
        final JButton replyButton =
                new JButton(CommentsViewModel.REPLY_BUTTON_LABEL);
        final JButton deleteButton =
                new JButton(CommentsViewModel.DELETE_BUTTON_LABEL);
        final JButton likeButton =
                new JButton(CommentsViewModel.LIKE_BUTTON_LABEL);
        final JButton unlikeButton =
                new JButton(CommentsViewModel.UNLIKE_BUTTON_LABEL);

        replyButton.addActionListener(new SelectCommentListener(
                comment.getCommentId(), true));
        deleteButton.addActionListener(new SelectCommentListener(
                comment.getCommentId(), false));
        likeButton.addActionListener(new SelectCommentListener(
                comment.getCommentId(), false));
        unlikeButton.addActionListener(new SelectCommentListener(
                comment.getCommentId(), false));

        buttonPanel.add(replyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(likeButton);
        buttonPanel.add(unlikeButton);
        return buttonPanel;
    }

    /**
     * Returns the indentation for a top-level comment or reply.
     * @param comment the comment row
     * @return the indentation amount
     */
    private int getCommentIndent(final CommentsPresenter.CommentRow comment) {
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
     * Clears the selected review when writing a new review.
     */
    private final class WriteReviewListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent event) {
            final MediaReviewsState state = mediaReviewsViewModel.getState();
            state.setSelectedReviewId("");
            mediaReviewsViewModel.firePropertyChanged();
        }
    }

    /**
     * Selects a review in the view model state.
     */
    private final class SelectReviewListener implements ActionListener {
        private final String reviewId;

        private SelectReviewListener(final String reviewId) {
            this.reviewId = reviewId;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final MediaReviewsState state = mediaReviewsViewModel.getState();
            state.setSelectedReviewId(reviewId);
            mediaReviewsViewModel.firePropertyChanged();
        }
    }

    /**
     * Selects a comment in the comments view model state.
     */
    private final class SelectCommentListener implements ActionListener {
        private final String commentId;
        private final boolean reply;

        private SelectCommentListener(final String commentId,
                                      final boolean reply) {
            this.commentId = commentId;
            this.reply = reply;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            if (commentsViewModel != null) {
                commentsViewModel.getState().setSelectedCommentId(commentId);
                if (reply) {
                    commentsViewModel.getState().setParentCommentId(commentId);
                }
                commentsViewModel.firePropertyChanged();
            }
        }
    }
}
