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

import interface_adapter.user_reviews.UserReviewsController;
import interface_adapter.user_reviews.UserCommentRow;
import interface_adapter.user_reviews.UserReviewRow;
import interface_adapter.user_reviews.UserReviewsState;
import interface_adapter.user_reviews.UserReviewsViewModel;

/**
 * Swing view for a user's reviews.
 */
public final class MyReviewsView extends JPanel
        implements PropertyChangeListener {
    /** The card gap. */
    private static final int CARD_GAP = 10;

    /** The time_formatter. */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a z");

    /** The view name. */
    private final String viewName = UserReviewsViewModel.VIEW_NAME;
    /** The user reviews view model. */
    private final UserReviewsViewModel userReviewsViewModel;
    /** The reviews panel. */
    private final JPanel reviewsPanel = new JPanel();
    /** The comments panel. */
    private final JPanel commentsPanel = new JPanel();
    /** The error label. */
    private final JLabel errorLabel = new JLabel();
    /** The back button. */
    private final JButton backButton =
            new JButton(UserReviewsViewModel.BACK_BUTTON_LABEL);
    /** The user reviews controller. */
    private UserReviewsController userReviewsController;
    /** The loading content. */
    private boolean loadingContent;

    /**
     * Handles this review or comment operation.
     * @param inputUserReviewsViewModel the inputUserReviewsViewModel
     */
    public MyReviewsView(final UserReviewsViewModel inputUserReviewsViewModel) {
        this.userReviewsViewModel = inputUserReviewsViewModel;
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

        updateView(inputUserReviewsViewModel.getState());
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
     * @param inputUserReviewsController the user reviews controller
     */
    public void setUserReviewsController(
            final UserReviewsController inputUserReviewsController) {
        this.userReviewsController = inputUserReviewsController;
    }

    /**
     * Refreshes all visible content from state.
     * @param state the user reviews state
     */
    private void updateView(final UserReviewsState state) {
        if (state != null) {
            errorLabel.setText(state.getUserReviewsError());
            loadContent(state);
            setReviews(state.getReviews());
            setComments(state.getComments());
        }
    }

    /**
     * Loads persisted user reviews and comments into state.
     * @param state the user reviews state
     */
    private void loadContent(final UserReviewsState state) {
        if (!loadingContent && userReviewsController != null
                && !isBlank(state.getUsername())) {
            loadingContent = true;
            userReviewsController.loadUserReviews(state.getUsername());
            userReviewsController.loadUserComments(state.getUsername());
            loadingContent = false;
        }
    }

    /**
     * Displays the given review rows.
     * @param reviews the review rows to display
     */
    private void setReviews(
            final List<UserReviewRow> reviews) {
        reviewsPanel.removeAll();

        if (reviews.isEmpty()) {
            reviewsPanel.add(new JLabel(
                    UserReviewsViewModel.EMPTY_REVIEWS_MESSAGE));
        } else {
            for (UserReviewRow review : reviews) {
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
    private void setComments(final List<UserCommentRow> comments) {
        commentsPanel.removeAll();

        if (comments.isEmpty()) {
            commentsPanel.add(new JLabel(
                    UserReviewsViewModel.EMPTY_COMMENTS_MESSAGE));
        } else {
            for (UserCommentRow comment : comments) {
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
            final UserReviewRow review) {
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
            final UserCommentRow comment) {
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
            final UserReviewRow review) {
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
        /** The review id. */
        private final String reviewId;

        private SelectReviewListener(final String inputReviewId) {
            this.reviewId = inputReviewId;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final UserReviewsState state = userReviewsViewModel.getState();
            state.setSelectedReviewId(reviewId);
            if (userReviewsController != null
                    && !isBlank(state.getUsername())) {
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
                userReviewsController.loadUserReviews(state.getUsername());
            }
            userReviewsViewModel.firePropertyChanged();
        }
    }
}
