package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.comments.CommentsState;
import interface_adapter.comments.CommentsViewModel;
import interface_adapter.media_detail.MediaDetailController;
import interface_adapter.media_reviews.MediaReviewsState;
import interface_adapter.media_reviews.MediaReviewsViewModel;
import interface_adapter.user_reviews.UserCommentRow;
import interface_adapter.user_reviews.UserReviewRow;
import interface_adapter.user_reviews.UserReviewsController;
import interface_adapter.user_reviews.UserReviewsState;
import interface_adapter.user_reviews.UserReviewsViewModel;

/**
 * Swing view for a user's reviews.
 */
public final class MyReviewsView extends JPanel
        implements PropertyChangeListener {

    private static final String UNAVAILABLE_POSTER_TEXT = "Poster unavailable";
    /**
     * The card gap.
     */
    private static final int CARD_GAP = 10;
    /**
     * The poster width.
     */
    private static final int POSTER_WIDTH = 92;
    /**
     * The poster height.
     */
    private static final int POSTER_HEIGHT = 138;
    /**
     * TMDB poster base URL.
     */
    private static final String POSTER_BASE_URL =
            "https://image.tmdb.org/t/p/w185";
    /**
     * Smallest valid rating percentage.
     */
    private static final double MIN_RATING = 0.0;
    /**
     * Largest valid rating percentage.
     */
    private static final double MAX_RATING = 100.0;
    /**
     * Rating validation message.
     */
    private static final String RATING_ERROR =
            "Rating needs to be between 0 and 100 inclusive.";

    /**
     * The time_formatter.
     */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a z");

    /**
     * The view name.
     */
    private final String viewName = UserReviewsViewModel.VIEW_NAME;
    /**
     * The user reviews view model.
     */
    private final UserReviewsViewModel userReviewsViewModel;
    /**
     * The reviews panel.
     */
    private final JPanel reviewsPanel = new JPanel();
    /**
     * The comments panel.
     */
    private final JPanel commentsPanel = new JPanel();
    /**
     * The error label.
     */
    private final JLabel errorLabel = new JLabel();
    /**
     * The back button.
     */
    private final JButton backButton =
            new JButton(UserReviewsViewModel.BACK_BUTTON_LABEL);
    /**
     * The user reviews controller.
     */
    private UserReviewsController userReviewsController;
    /**
     * The media detail controller.
     */
    private MediaDetailController mediaDetailController;
    /**
     * The media reviews view model.
     */
    private MediaReviewsViewModel mediaReviewsViewModel;
    /**
     * The comments view model.
     */
    private CommentsViewModel commentsViewModel;
    /**
     * The loading content.
     */
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
        reviewsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(UserReviewsViewModel.REVIEWS_TAB_LABEL,
                new JScrollPane(reviewsPanel));
        tabbedPane.addTab(UserReviewsViewModel.COMMENTS_TAB_LABEL,
                new JScrollPane(commentsPanel));
        tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);

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
     * Sets the controller for opening media detail pages.
     * @param inputMediaDetailController the media detail controller
     */
    public void setMediaDetailController(
            final MediaDetailController inputMediaDetailController) {
        this.mediaDetailController = inputMediaDetailController;
    }

    /**
     * Sets destination view models used to select review/comment targets.
     * @param inputMediaReviewsViewModel the media reviews view model
     * @param inputCommentsViewModel the comments view model
     */
    public void setMediaReviewTargets(
            final MediaReviewsViewModel inputMediaReviewsViewModel,
            final CommentsViewModel inputCommentsViewModel) {
        this.mediaReviewsViewModel = inputMediaReviewsViewModel;
        this.commentsViewModel = inputCommentsViewModel;
    }

    /**
     * Refreshes all visible content from state.
     * @param state the user reviews state
     */
    private void updateView(final UserReviewsState state) {
        if (state != null) {
            errorLabel.setText(state.getUserReviewsError());
            if (!loadContent(state)) {
                setReviews(state.getReviews());
                setComments(state.getComments());
            }
        }
    }

    /**
     * Loads persisted user reviews and comments into state.
     * @param state the user reviews state
     * @return true if fresh content was requested
     */
    private boolean loadContent(final UserReviewsState state) {
        final boolean contentRequested;
        if (!loadingContent && userReviewsController != null
                && !isBlank(state.getUsername())) {
            loadingContent = true;
            try {
                userReviewsController.loadUserReviews(state.getUsername());
                userReviewsController.loadUserComments(state.getUsername());
            }
            finally {
                loadingContent = false;
            }
            contentRequested = true;
        }
        else {
            contentRequested = false;
        }
        return contentRequested;
    }

    /**
     * Displays the given review rows.
     * @param reviews the review rows to display
     */
    private void setReviews(final List<UserReviewRow> reviews) {
        reviewsPanel.removeAll();

        if (reviews.isEmpty()) {
            reviewsPanel.add(new JLabel(
                    UserReviewsViewModel.EMPTY_REVIEWS_MESSAGE));
        }
        else {
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
        }
        else {
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
    private Component createReviewCard(final UserReviewRow review) {
        final JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, POSTER_HEIGHT));

        final JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JLabel titleLabel = createMediaTitleLabel(review.getMediaTitle(),
                review.getReleaseYear());
        addReviewNavigation(titleLabel, review);
        detailPanel.add(titleLabel);
        detailPanel.add(new JLabel("Rating: " + review.getRating() + "%"));
        detailPanel.add(new JLabel("Created: "
                + formatTime(review.getCreatedAt())));
        detailPanel.add(new JLabel("Updated: "
                + formatTime(review.getUpdatedAt())));
        detailPanel.add(new JLabel("Likes: " + review.getLikeCount()));
        detailPanel.add(new JLabel(review.getReviewText()));
        detailPanel.add(createButtonPanel(review));

        final JLabel posterLabel = createPosterLabel(review.getPosterPath());
        addReviewNavigation(posterLabel, review);
        card.add(posterLabel, BorderLayout.WEST);
        card.add(detailPanel, BorderLayout.CENTER);

        return card;
    }

    /**
     * Creates the display card for one comment.
     * @param comment the comment row to display
     * @return the comment card
     */
    private Component createCommentCard(final UserCommentRow comment) {
        final JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, POSTER_HEIGHT));

        final JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JLabel titleLabel = createMediaTitleLabel(comment.getMediaTitle(),
                comment.getReleaseYear());
        addCommentNavigation(titleLabel, comment);
        detailPanel.add(titleLabel);
        detailPanel.add(new JLabel("On review: " + comment.getReviewText()));
        detailPanel.add(new JLabel("Created: "
                + formatTime(comment.getCreatedAt())));
        detailPanel.add(new JLabel("Likes: " + comment.getLikeCount()));
        detailPanel.add(new JLabel(comment.getCommentText()));
        detailPanel.add(createCommentButtonPanel(comment));

        final JLabel posterLabel = createPosterLabel(comment.getPosterPath());
        addCommentNavigation(posterLabel, comment);
        card.add(posterLabel, BorderLayout.WEST);
        card.add(detailPanel, BorderLayout.CENTER);

        return card;
    }

    /**
     * Creates the action buttons for one comment row.
     * @param comment the comment row
     * @return the button panel
     */
    private Component createCommentButtonPanel(final UserCommentRow comment) {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JButton editButton =
                new JButton(UserReviewsViewModel.EDIT_BUTTON_LABEL);
        final JButton deleteButton =
                new JButton(UserReviewsViewModel.DELETE_BUTTON_LABEL);

        editButton.addActionListener(new SelectCommentListener(
                comment.getCommentId()));
        deleteButton.addActionListener(new SelectCommentListener(
                comment.getCommentId()));

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        return buttonPanel;
    }

    /**
     * Creates the action buttons for one review row.
     * @param review the review row
     * @return the button panel
     */
    private Component createButtonPanel(final UserReviewRow review) {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
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
     * Creates a title label with release year.
     * @param mediaTitle the media title
     * @param releaseYear the release year
     * @return the title label
     */
    private JLabel createMediaTitleLabel(final String mediaTitle,
                                         final int releaseYear) {
        final String yearText;
        if (releaseYear > 0) {
            yearText = " (" + releaseYear + ")";
        }
        else {
            yearText = "";
        }
        final JLabel titleLabel = new JLabel(mediaTitle + yearText);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return titleLabel;
    }

    /**
     * Creates a poster label and starts loading its image.
     * @param posterPath the poster path
     * @return the poster label
     */
    private JLabel createPosterLabel(final String posterPath) {
        final JLabel posterLabel = new JLabel();
        posterLabel.setPreferredSize(new Dimension(POSTER_WIDTH,
                POSTER_HEIGHT));
        posterLabel.setHorizontalAlignment(JLabel.CENTER);
        posterLabel.setVerticalAlignment(JLabel.CENTER);
        posterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        updatePoster(posterLabel, posterPath);
        return posterLabel;
    }

    /**
     * Adds click navigation to a review media label or poster.
     * @param component the clickable component
     * @param review the review row
     */
    private void addReviewNavigation(final Component component,
                                     final UserReviewRow review) {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent event) {
                openMediaDetail(review);
            }
        });
    }

    /**
     * Adds click navigation to a comment media label or poster.
     * @param component the clickable component
     * @param comment the comment row
     */
    private void addCommentNavigation(final Component component,
                                      final UserCommentRow comment) {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent event) {
                openMediaDetail(comment);
            }
        });
    }

    /**
     * Opens the media detail page focused on the selected review.
     * @param review the review row
     */
    private void openMediaDetail(final UserReviewRow review) {
        selectTarget(review.getReviewId(), "");
        if (mediaDetailController != null) {
            mediaDetailController.execute(review.getMediaId(),
                    review.getMediaType(), review.getMediaTitle(),
                    review.getReleaseYear(), review.getPosterPath());
        }
    }

    /**
     * Opens the media detail page focused on the selected comment.
     * @param comment the comment row
     */
    private void openMediaDetail(final UserCommentRow comment) {
        selectTarget(comment.getReviewId(), comment.getCommentId());
        if (mediaDetailController != null) {
            mediaDetailController.execute(comment.getMediaId(),
                    comment.getMediaType(), comment.getMediaTitle(),
                    comment.getReleaseYear(), comment.getPosterPath());
        }
    }

    /**
     * Stores the review/comment that should be highlighted after navigation.
     * @param reviewId the selected review id
     * @param commentId the selected comment id
     */
    private void selectTarget(final String reviewId, final String commentId) {
        if (mediaReviewsViewModel != null) {
            final MediaReviewsState reviewsState =
                    mediaReviewsViewModel.getState();
            reviewsState.setSelectedReviewId(reviewId);
            mediaReviewsViewModel.setState(reviewsState);
        }
        if (commentsViewModel != null) {
            final CommentsState commentsState = commentsViewModel.getState();
            commentsState.setReviewId(reviewId);
            commentsState.setSelectedCommentId(commentId);
            commentsViewModel.setState(commentsState);
        }
    }

    /**
     * Updates a poster label.
     * @param posterLabel the poster label
     * @param posterPath the poster path
     */
    private void updatePoster(final JLabel posterLabel,
                              final String posterPath) {
        if (posterPath == null || posterPath.isEmpty()) {
            posterLabel.setText(UNAVAILABLE_POSTER_TEXT);
        }
        else {
            posterLabel.setText("Loading...");
            loadPosterInBackground(posterLabel, posterPath);
        }
    }

    private void loadPosterInBackground(final JLabel posterLabel,
                                        final String posterPath) {
        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() {
                return loadPoster(posterPath);
            }

            @Override
            protected void done() {
                setLoadedPoster(posterLabel, this);
            }
        }.execute();
    }

    private ImageIcon loadPoster(final String posterPath) {
        ImageIcon poster = null;
        try {
            final ImageIcon original = new ImageIcon(URI.create(
                    POSTER_BASE_URL + posterPath).toURL());
            if (original.getIconWidth() > 0) {
                final Image scaled = original.getImage().getScaledInstance(
                        POSTER_WIDTH, POSTER_HEIGHT, Image.SCALE_SMOOTH);
                poster = new ImageIcon(scaled);
            }
        }
        catch (MalformedURLException | IllegalArgumentException exception) {
            poster = null;
        }
        return poster;
    }

    private void setLoadedPoster(final JLabel posterLabel,
                                 final SwingWorker<ImageIcon, Void> worker) {
        try {
            final ImageIcon poster = worker.get();
            posterLabel.setIcon(poster);
            if (poster == null) {
                posterLabel.setText(UNAVAILABLE_POSTER_TEXT);
            }
            else {
                posterLabel.setText("");
            }
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            posterLabel.setText(UNAVAILABLE_POSTER_TEXT);
        }
        catch (ExecutionException exception) {
            posterLabel.setText(UNAVAILABLE_POSTER_TEXT);
        }
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
        }
        else {
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
        /**
         * The review id.
         */
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
                    final Double rating =
                            promptForRating("New rating percentage:");
                    if (rating != null) {
                        final String reviewText =
                                JOptionPane.showInputDialog(
                                        MyReviewsView.this,
                                        "New review text:");
                        userReviewsController.editReview(reviewId,
                                state.getUsername(), rating, reviewText);
                    }
                }
                userReviewsController.loadUserReviews(state.getUsername());
            }
            userReviewsViewModel.firePropertyChanged();
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

        private SelectCommentListener(final String inputCommentId) {
            this.commentId = inputCommentId;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final UserReviewsState state = userReviewsViewModel.getState();
            if (userReviewsController != null
                    && !isBlank(state.getUsername())) {
                final String command = ((JButton) event.getSource()).getText();
                if (UserReviewsViewModel.DELETE_BUTTON_LABEL.equals(command)) {
                    userReviewsController.deleteComment(commentId,
                            state.getUsername());
                } else {
                    final String commentText = JOptionPane.showInputDialog(
                            MyReviewsView.this, "New comment text:");
                    if (!isBlank(commentText)) {
                        userReviewsController.editComment(commentId,
                                state.getUsername(), commentText);
                    }
                }
                userReviewsController.loadUserComments(state.getUsername());
            }
            userReviewsViewModel.firePropertyChanged();
        }
    }

    /**
     * Opens a rating dialog that cannot be submitted until valid.
     * @param title the dialog title
     * @return the rating, or null if cancelled
     */
    private Double promptForRating(final String title) {
        final JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(this), title,
                Dialog.ModalityType.APPLICATION_MODAL);
        final JTextField ratingField = new JTextField(12);
        final JLabel validationLabel = new JLabel(" ");
        validationLabel.setForeground(Color.RED);
        final JButton submitButton = new JButton("Submit");
        submitButton.setEnabled(false);
        final JButton cancelButton = new JButton("Cancel");
        final Double[] rating = new Double[1];

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel(title));
        inputPanel.add(ratingField);
        inputPanel.add(validationLabel);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        ratingField.getDocument().addDocumentListener(
                new RatingValidationListener(ratingField, validationLabel,
                        submitButton));
        submitButton.addActionListener(event -> {
            rating[0] = parseRating(ratingField.getText());
            dialog.dispose();
        });
        cancelButton.addActionListener(event -> dialog.dispose());

        dialog.add(inputPanel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        return rating[0];
    }

    private Double parseRating(final String ratingText) {
        Double rating = null;
        try {
            final double parsedRating = Double.parseDouble(ratingText);
            if (parsedRating >= MIN_RATING && parsedRating <= MAX_RATING) {
                rating = parsedRating;
            }
        }
        catch (NumberFormatException exception) {
            rating = null;
        }
        return rating;
    }

    private final class RatingValidationListener implements DocumentListener {
        private final JTextField ratingField;
        private final JLabel validationLabel;
        private final JButton submitButton;

        private RatingValidationListener(final JTextField inputRatingField,
                                         final JLabel inputValidationLabel,
                                         final JButton inputSubmitButton) {
            this.ratingField = inputRatingField;
            this.validationLabel = inputValidationLabel;
            this.submitButton = inputSubmitButton;
        }

        @Override
        public void insertUpdate(final DocumentEvent event) {
            updateValidation();
        }

        @Override
        public void removeUpdate(final DocumentEvent event) {
            updateValidation();
        }

        @Override
        public void changedUpdate(final DocumentEvent event) {
            updateValidation();
        }

        private void updateValidation() {
            final boolean validRating = parseRating(ratingField.getText())
                    != null;
            submitButton.setEnabled(validRating);
            if (isBlank(ratingField.getText()) || validRating) {
                validationLabel.setText(" ");
            }
            else {
                validationLabel.setText(RATING_ERROR);
            }
        }
    }
}
