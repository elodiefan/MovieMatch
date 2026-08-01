package view;

import java.awt.Component;
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
import javax.swing.JTabbedPane;

import interface_adapter.account.ReviewsState;
import interface_adapter.account.ReviewsViewModel;

/**
 * The view for a user's past reviews and comments.
 */
public class ReviewsView extends JPanel implements PropertyChangeListener {
    private static final int CARD_GAP = 10;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a z");

    private final String viewName = "reviews";
    private final ReviewsViewModel reviewsViewModel;
    private final JPanel reviewsPanel;
    private final JPanel commentsPanel;
    private final JButton backButton;

    public ReviewsView(ReviewsViewModel reviewsViewModel) {
        this.reviewsViewModel = reviewsViewModel;
        this.reviewsViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(ReviewsViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton(ReviewsViewModel.BACK_BUTTON_LABEL);

        reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));

        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(ReviewsViewModel.REVIEWS_TAB_LABEL, new JScrollPane(reviewsPanel));
        tabbedPane.addTab(ReviewsViewModel.COMMENTS_TAB_LABEL, new JScrollPane(commentsPanel));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(backButton);
        this.add(tabbedPane);

        updateView(reviewsViewModel.getState());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ReviewsState state = (ReviewsState) evt.getNewValue();
            updateView(state);
        }
    }

    public String getViewName() {
        return viewName;
    }

    private void updateView(ReviewsState state) {
        setReviews(state.getReviews());
        setComments(state.getComments());
    }

    private void setReviews(List<ReviewsState.ReviewRow> reviews) {
        reviewsPanel.removeAll();

        if (reviews.isEmpty()) {
            reviewsPanel.add(new JLabel("No reviews yet."));
        }
        else {
            for (ReviewsState.ReviewRow review : reviews) {
                reviewsPanel.add(createReviewCard(review));
                reviewsPanel.add(Box.createVerticalStrut(CARD_GAP));
            }
        }

        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }

    private void setComments(List<ReviewsState.CommentRow> comments) {
        commentsPanel.removeAll();

        if (comments.isEmpty()) {
            commentsPanel.add(new JLabel("No comments yet."));
        }
        else {
            for (ReviewsState.CommentRow comment : comments) {
                commentsPanel.add(createCommentCard(comment));
                commentsPanel.add(Box.createVerticalStrut(CARD_GAP));
            }
        }

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    private Component createReviewCard(ReviewsState.ReviewRow review) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.add(new JLabel(review.getMediaTitle()));
        card.add(new JLabel("Rating: " + review.getRating() + "%"));
        card.add(new JLabel(formatTime(review.getCreatedAt())));
        card.add(new JLabel(review.getReviewText()));
        return card;
    }

    private Component createCommentCard(ReviewsState.CommentRow comment) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.add(new JLabel(comment.getMediaTitle()));
        card.add(new JLabel("On review: " + comment.getReviewText()));
        card.add(new JLabel(formatTime(comment.getCreatedAt())));
        card.add(new JLabel(comment.getCommentText()));
        return card;
    }

    private String formatTime(ZonedDateTime dateTime) {
        return dateTime.format(TIME_FORMATTER);
    }
}
