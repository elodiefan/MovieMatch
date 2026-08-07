package use_case.filter;

import java.util.ArrayList;
import java.util.List;

import entity.Genre;
import entity.Media;

/** The Filter Interactor. */
public class FilterInteractor implements FilterInputBoundary {

    private static final double MINIMUM_ALLOWED_RATING = 0.0;
    private static final double MAXIMUM_ALLOWED_RATING = 10.0;

    private final FilterOutputBoundary filterPresenter;

    public FilterInteractor(FilterOutputBoundary filterPresenter) {
        this.filterPresenter = filterPresenter;
    }

    private List<Media> filterMedia(
            List<Media> originalResults,
            FilterCriteria criteria) {

        final List<Media> filteredResults = new ArrayList<>();

        for (Media media : originalResults) {
            if (matchesLanguages(media, criteria)
                    && matchesMinimumRating(media, criteria)
                    && matchesGenres(media, criteria)
                    && matchesYearRange(media, criteria)) {
                filteredResults.add(media);
            }
        }

        return filteredResults;
    }

    @Override
    public void execute(FilterInputData inputData) {
        if (inputData == null) {
            filterPresenter.prepareFailView(
                    "Filter input cannot be null"
            );
        }
        else {
            final List<Media> originalResults =
                    inputData.getOriginalResults();
            final FilterCriteria criteria =
                    inputData.getCriteria();

            if (originalResults == null) {
                filterPresenter.prepareFailView(
                        "Original search results cannot be null"
                );
            }
            else if (criteria == null) {
                filterPresenter.prepareFailView(
                        "Filter criteria cannot be null"
                );
            }
            else if (!hasValidRating(criteria)) {
                filterPresenter.prepareFailView(
                        "Minimum rating must be between 0 and 10"
                );
            }
            else if (!hasValidYearRange(criteria)) {
                filterPresenter.prepareFailView(
                        "Earliest year cannot be later than latest year"
                );
            }
            else {
                final List<Media> filteredResults =
                        filterMedia(originalResults, criteria);

                final FilterOutputData outputData =
                        new FilterOutputData(filteredResults);

                filterPresenter.prepareSuccessView(outputData);
            }
        }
    }

    private boolean matchesLanguages(
            Media media,
            FilterCriteria criteria) {

        final List<String> languages = criteria.getLanguages();
        boolean matches = languages == null || languages.isEmpty();

        if (!matches) {
            for (String language : languages) {
                if (language != null
                        && language.equalsIgnoreCase(media.getLanguage())) {
                    matches = true;
                }
            }
        }

        return matches;
    }

    private boolean matchesMinimumRating(
            Media media,
            FilterCriteria criteria) {

        final Double minimumRating = criteria.getMinimumRating();

        return minimumRating == null
                || media.getAverageRating() >= minimumRating;
    }

    private boolean matchesGenres(
            Media media,
            FilterCriteria criteria) {

        final List<Integer> selectedGenreIds =
                criteria.getGenreIds();
        boolean matches = selectedGenreIds == null
                || selectedGenreIds.isEmpty();

        if (!matches) {
            final List<Genre> mediaGenres = media.getGenres();

            if (mediaGenres != null) {
                for (Genre genre : mediaGenres) {
                    if (genre != null
                            && selectedGenreIds.contains(genre.getId())) {
                        matches = true;
                    }
                }
            }
        }

        return matches;
    }

    private boolean matchesYearRange(
            Media media,
            FilterCriteria criteria) {

        final Integer earliestYear = criteria.getEarliestYear();
        final Integer latestYear = criteria.getLatestYear();
        final int releaseYear = media.getReleaseYear();

        final boolean meetsEarliestYear =
                earliestYear == null || releaseYear >= earliestYear;
        final boolean meetsLatestYear =
                latestYear == null || releaseYear <= latestYear;

        return meetsEarliestYear && meetsLatestYear;
    }

    private boolean hasValidRating(FilterCriteria criteria) {
        final Double minimumRating = criteria.getMinimumRating();

        return minimumRating == null
                || !Double.isNaN(minimumRating)
                && minimumRating >= MINIMUM_ALLOWED_RATING
                && minimumRating <= MAXIMUM_ALLOWED_RATING;
    }

    private boolean hasValidYearRange(FilterCriteria criteria) {
        final Integer earliestYear = criteria.getEarliestYear();
        final Integer latestYear = criteria.getLatestYear();

        return earliestYear == null
                || latestYear == null
                || earliestYear <= latestYear;
    }
}
