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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import interface_adapter.comments.CommentsPresenter;
import interface_adapter.comments.CommentsState;
import interface_adapter.comments.CommentsViewModel;

/**
 * Swing panel for comments on a review.
 */
public class CommentsPanel extends JPanel implements PropertyChangeListener {
    private static final int CARD_GAP = 10;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a z");

    private final CommentsViewModel commentsViewModel;
    private final JLabel errorLabel = new JLabel();
    private final JPanel commentsPanel = new JPanel();
    private final JButton writeCommentButton =
            new JButton(CommentsViewModel.WRITE_COMMENT_BUTTON_LABEL);

    public CommentsPanel(final CommentsViewModel commentsViewModel) {
        this.commentsViewModel = commentsViewModel;
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

        updateView(commentsViewModel.getState());
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
     * Refreshes visible content from state.
     * @param state the comments state
     */
    private void updateView(final CommentsState state) {
        if (state != null) {
            errorLabel.setText(state.getCommentsError());
            setComments(state.getComments());
        }
    }

    /**
     * Displays the given comment rows.
     * @param comments the comment rows to display
     */
    private void setComments(
            final List<CommentsPresenter.CommentRow> comments) {
        commentsPanel.removeAll();

        if (comments.isEmpty()) {
            commentsPanel.add(new JLabel(
                    CommentsViewModel.EMPTY_COMMENTS_MESSAGE));
        } else {
            for (CommentsPresenter.CommentRow comment : comments) {
                commentsPanel.add(createCommentCard(comment));
                commentsPanel.add(Box.createVerticalStrut(CARD_GAP));
            }
        }

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    /**
     * Creates the display card for one comment.
     * @param comment the comment row
     * @return the comment card
     */
    private Component createCommentCard(
            final CommentsPresenter.CommentRow comment) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

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
    private Component createButtonPanel(
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
     * Marks the next comment as a top-level comment.
     */
    private final class WriteCommentListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent event) {
            final CommentsState state = commentsViewModel.getState();
            state.setParentCommentId("");
            commentsViewModel.firePropertyChanged();
        }
    }

    /**
     * Selects a comment in the view model state.
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
            final CommentsState state = commentsViewModel.getState();
            state.setSelectedCommentId(commentId);
            if (reply) {
                state.setParentCommentId(commentId);
            }
            commentsViewModel.firePropertyChanged();
        }
    }
}
