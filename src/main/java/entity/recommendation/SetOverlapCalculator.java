package entity.recommendation;

import java.util.Collection;
import java.util.Set;

/**
 * Works out what fraction of a candidate's attributes the user already likes.
 * <p>
 * Both the genre and the cast factors compute the same ratio — the size of the
 * intersection divided by the size of the candidate's own set — so that
 * arithmetic lives here once rather than being written twice.
 * <p>
 * The denominator is the candidate's set, not the profile's. A candidate whose
 * every genre appears in the profile scores 1.0 no matter how broad the user's
 * taste is.
 */
public final class SetOverlapCalculator {

    /** Score used when the candidate lists nothing to compare. */
    private static final double NO_OVERLAP = 0.0;

    private SetOverlapCalculator() {
        // Utility class: all behaviour is in the static method below.
    }

    /**
     * Returns the share of the candidate's values that also appear in the profile's.
     * <p>
     * Guards the division: a candidate with no genres or no listed cast yields 0
     * instead of dividing by zero, which is why every caller can pass real data
     * straight through without checking it first.
     *
     * @param candidateValues the candidate's genres or cast
     * @param profileValues the values the user is known to like
     * @param <T> the type being compared
     * @return a value in the range [0, 1]
     */
    public static <T> double overlapRatio(final Collection<T> candidateValues,
                                          final Set<T> profileValues) {
        final double ratio;
        if (candidateValues == null || candidateValues.isEmpty()) {
            ratio = NO_OVERLAP;
        }
        else {
            final long shared = candidateValues.stream()
                    .distinct()
                    .filter(profileValues::contains)
                    .count();
            ratio = (double) shared / candidateValues.stream().distinct().count();
        }
        return ratio;
    }
}
