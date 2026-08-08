package entity.recommendation;

import java.util.Collection;
import java.util.Set;

/**
 * Works out what fraction of a candidate's attributes the user already likes.
 */
public final class SetOverlapCalculator {

    /**
     * Score used when the candidate lists nothing to compare.
     */
    private static final double NO_OVERLAP = 0.0;

    private SetOverlapCalculator() {
        // Utility class: all behaviour is in the static method below.
    }

    /**
     * Returns the share of the candidate's values that also appear in the profile's.
     *
     * @param candidateValues the candidate values
     * @param profileValues the profile values
     * @return the overlap ratio
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
