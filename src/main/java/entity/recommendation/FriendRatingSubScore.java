package entity.recommendation;

import java.util.List;

import entity.Media;

/**
 * Scores a candidate on what the user's friends made of it.
 *
 * The social half of the algorithm: a title several friends rated five stars is
 * worth surfacing even when its genres are an imperfect match. Computed as the
 * friends' mean rating normalised from the 1-5 star scale.
 *
 * When no friend has rated the candidate the score is 0, exactly as the
 * algorithm specifies for the empty case. That is also the situation today,
 * since the app has no concept of friends yet — so this factor contributes
 * nothing until someone builds one, and every score is capped at 1.0 minus this
 * factor's weight in the meantime.
 */
public class FriendRatingSubScore implements SubScore {

    /** Friends rate on a 1-5 star scale. */
    private static final double MAX_STAR_RATING = 5.0;

    private static final String NAME = "friends";
    private static final double NO_FRIEND_RATINGS = 0.0;

    /**
     * Creates the friends' rating factor.
     */
    public FriendRatingSubScore() {
        // Stateless: everything needed arrives through scoreFor.
    }

    @Override
    public double scoreFor(final Media candidate, final ScoringContext context) {
        final List<Double> ratings = context.getFriendRatings();
        final double score;
        if (ratings.isEmpty()) {
            score = NO_FRIEND_RATINGS;
        }
        else {
            final double total = ratings.stream().mapToDouble(Double::doubleValue).sum();
            score = total / (ratings.size() * MAX_STAR_RATING);
        }
        return score;
    }

    @Override
    public double weightFrom(final ScoringWeights weights) {
        return weights.getFriend();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
