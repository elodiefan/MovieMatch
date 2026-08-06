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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import entity.Review;
import interface_adapter.user_reviews.UserReviewsController;
import interface_adapter.user_reviews.UserReviewsPresenter;
import interface_adapter.user_reviews.UserReviewsState;
import interface_adapter.user_reviews.UserReviewsViewModel;
import use_case.comment.UserCommentSummaryData;

/**
 * Swing view for a user's reviews.
 */
public class MyReviewsView extends JPanel implements PropertyChangeListener {
    private static final int CARD_GAP = 10;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a z");

    private final String viewName = UserReviewsViewModel.VIEW_NAME;
    private final UserReviewsViewModel userReviewsViewModel;
    private final UserReviewsPresenter userReviewsPresenter =
            new UserReviewsPresenter();
    private final JPanel reviewsPanel = new JPanel();
    private final JPanel commentsPanel = new JPanel();
    private final JLabel errorLabel = new JLabel();
    private final JButton backButton =
            new JButton(UserReviewsViewModel.BACK_BUTTON_LABEL);
    private UserReviewsController userReviewsController;

    public MyReviewsView(final UserReviewsViewModel userReviewsViewModel) {
        this.userReviewsViewModel = userReviewsViewModel;
        this.userReviewsViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(UserReviewsViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(UserReviewsViewModel.REVIEWS_TAB_LABEL,
                new JScrollPane(reviewsPanel));
        tabbedPane.addTab(UserReviewsViewModel.COMMENTS_TAB_LABEL,
                new JScrollPane(commentsPanel));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(title);
        add(errorLabel);
        add(backButton);
        add(tabbedPane);

        updateView(userReviewsViewModel.getState());
    }

    /**
     * Updates the view when the user reviews state changes.
     * @param event the property change event
     */
    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        if ("state".equals(event.getPropertyName())) {
            updateView((UserReviewsState) event.getNewValue());
        }
    }

    /**
     * Returns this view's name.
     * @return the view name
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Returns the back button so app wiring can attach navigation.
     * @return the back button
     */
    public JButton getBackButton() {
        return backButton;
    }

    /**
     * Sets the controller for user review actions.
     * @param userReviewsController the user reviews controller
     */
    public void setUserReviewsController(
            final UserReviewsController userReviewsController) {
        this.userReviewsController = userReviewsController;
    }

    /**
     * Refreshes all visible content from state.
     * @param state the user reviews state
     */
    private void updateView(final UserReviewsState state) {
        if (state != null) {
            errorLabel.setText(state.getUserReviewsError());
            refreshReviews(state);
            refreshComments(state);
            setReviews(state.getReviews());
            setComments(state.getComments());
        }
    }

    /**
     * Loads persisted user reviews into state.
     * @param state the user reviews state
     */
    private void refreshReviews(final UserReviewsState state) {
        if (userReviewsController != null && !isBlank(state.getUsername())) {
            final List<Review> reviews =
                    userReviewsController.getUserReviews(state.getUsername());
            state.setReviews(userReviewsPresenter.prepareReviews(reviews));
        }
    }

    /**
     * Loads persisted user comments into state.
     * @param state the user reviews state
     */
    private void refreshComments(final UserReviewsState state) {
        if (userReviewsController != null && !isBlank(state.getUsername())) {
            final List<UserCommentSummaryData> comments =
                    userReviewsController.getUserComments(state.getUsername());
            state.setComments(userReviewsPresenter.prepareComments(comments));
        }
    }

    /**
     * Displays the given review rows.
     * @param reviews the review rows to display
     */
    private void setReviews(
            final List<UserReviewsPresenter.UserReviewRow> reviews) {
        reviewsPanel.removeAll();

        if (reviews.isEmpty()) {
            reviewsPanel.add(new JLabel(
                    UserReviewsViewModel.EMPTY_REVIEWS_MESSAGE));
        } else {
            for (UserReviewsPresenter.UserReviewRow review : reviews) {
                reviewsPanel.add(createReviewCard(review));
                reviewsPanel.add(Box.createVerticalStrut(CARD_GAP));
            }
        }

        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }

    /**
     * Displays the given comment rows.
     * @param comments the comment rows to display
     */
    private void setComments(final List<UserReviewsState.CommentRow> comments) {
        commentsPanel.removeAll();

        if (comments.isEmpty()) {
            commentsPanel.add(new JLabel(
                    UserReviewsViewModel.EMPTY_COMMENTS_MESSAGE));
        } else {
            for (UserReviewsState.CommentRow comment : comments) {
                commentsPanel.add(createCommentCard(comment));
                commentsPanel.add(Box.createVerticalStrut(CARD_GAP));
            }
        }

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    /**
     * Creates the display card for one review.
     * @param review the review row to display
     * @return the review card
     */
    private Component createReviewCard(
            final UserReviewsPresenter.UserReviewRow review) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(new JLabel(review.getMediaTitle()));
        card.add(new JLabel("Rating: " + review.getRating() + "%"));
        card.add(new JLabel("Created: " + formatTime(review.getCreatedAt())));
        card.add(new JLabel("Updated: " + formatTime(review.getUpdatedAt())));
        card.add(new JLabel("Likes: " + review.getLikeCount()));
        card.add(new JLabel(review.getReviewText()));
        card.add(createButtonPanel(review));

        return card;
    }

    /**
     * Creates the display card for one comment.
     * @param comment the comment row to display
     * @return the comment card
     */
    private Component createCommentCard(
            final UserReviewsState.CommentRow comment) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(new JLabel(comment.getMediaTitle()));
        card.add(new JLabel("On review: " + comment.getReviewText()));
        card.add(new JLabel("Created: " + formatTime(comment.getCreatedAt())));
        card.add(new JLabel("Likes: " + comment.getLikeCount()));
        card.add(new JLabel(comment.getCommentText()));

        return card;
    }

    /**
     * Creates the action buttons for one review row.
     * @param review the review row
     * @return the button panel
     */
    private Component createButtonPanel(
            final UserReviewsPresenter.UserReviewRow review) {
        final JPanel buttonPanel = new JPanel();
        final JButton editButton =
                new JButton(UserReviewsViewModel.EDIT_BUTTON_LABEL);
        final JButton deleteButton =
                new JButton(UserReviewsViewModel.DELETE_BUTTON_LABEL);

        editButton.addActionListener(new SelectReviewListener(
                review.getReviewId()));
        deleteButton.addActionListener(new SelectReviewListener(
                review.getReviewId()));

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
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
     * Checks whether a value is null, empty, or only whitespace.
     * @param value the value to check
     * @return true if the value is blank
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
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
            final UserReviewsState state = userReviewsViewModel.getState();
            state.setSelectedReviewId(reviewId);
            if (userReviewsController != null && !isBlank(state.getUsername())) {
                final String command = ((JButton) event.getSource()).getText();
                if (UserReviewsViewModel.DELETE_BUTTON_LABEL.equals(command)) {
                    userReviewsController.deleteReview(reviewId,
                            state.getUsername());
                } else {
                    final String ratingText = JOptionPane.showInputDialog(
                            MyReviewsView.this, "New rating percentage:");
                    final String reviewText = JOptionPane.showInputDialog(
                            MyReviewsView.this, "New review text:");
                    if (!isBlank(ratingText)) {
                        userReviewsController.editReview(reviewId,
                                state.getUsername(),
                                Double.parseDouble(ratingText), reviewText);
                    }
                }
                refreshReviews(state);
            }
            userReviewsViewModel.firePropertyChanged();
        }
    }
}
