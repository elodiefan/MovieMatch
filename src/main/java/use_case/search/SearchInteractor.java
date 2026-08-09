package use_case.search;

import java.util.ArrayList;
import java.util.List;

import entity.Genre;
import entity.Media;

/**
 * The Search Interactor.
 * How much of a result set to fetch at once is a decision about what a user is
 * willing to wait for, so it lives here rather than in the data access. TMDB
 * reports up to 500 pages for a common word and every result costs a further
 * request for its details, so asking for everything is not an option.
 */
public class SearchInteractor implements SearchInputBoundary {

    /**
     * How many pages one request fetches.
     */
    public static final int PAGES_PER_REQUEST = 3;

    private static final String EMPTY_KEYWORD_ERROR = "You have to enter at least one word";

    private final SearchMediaDataAccess searchDataAccess;
    private final SearchOutputBoundary searchPresenter;

    public SearchInteractor(SearchMediaDataAccess searchDataAccess,
                            SearchOutputBoundary searchPresenter) {
        this.searchDataAccess = searchDataAccess;
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void execute(SearchInputData inputData) {
        search(inputData, false);
    }

    @Override
    public void loadMore(SearchInputData inputData) {
        search(inputData, true);
    }

    /**
     * Fetches one block of pages and reports it.
     *
     * @param inputData the input data
     * @param appending the appending
     */
    private void search(SearchInputData inputData, boolean appending) {
        final String keyword = inputData.getKeyword();

        if (keyword == null || keyword.trim().isEmpty()) {
            searchPresenter.prepareFailView(EMPTY_KEYWORD_ERROR);
        }
        else {
            final int startPage = Math.max(1, inputData.getStartPage());
            final List<Media> results = new ArrayList<>();

            int totalPages = startPage;
            int totalResults = 0;
            int page = startPage;
            final int lastWanted = startPage + PAGES_PER_REQUEST - 1;

            while (page <= lastWanted && page <= totalPages) {
                final MediaPage mediaPage = searchDataAccess.searchPage(keyword, page);
                results.addAll(mediaPage.getMedia());
                totalPages = mediaPage.getTotalPages();
                totalResults = mediaPage.getTotalResults();
                page++;
            }

            searchPresenter.prepareSuccessView(new SearchOutputData(
                    toMediaResultData(results), keyword, page,
                    page <= totalPages, appending, totalResults));
        }
    }

    private List<MediaResultData> toMediaResultData(List<Media> mediaResults) {
        final List<MediaResultData> rows = new ArrayList<>();
        for (Media media : mediaResults) {
            rows.add(new MediaResultData(media.getID(),
                    media.getMediaType().name().toLowerCase(),
                    media.getTitle(), media.getReleaseYear(),
                    media.getAverageRating(),
                    toGenreNames(media.getGenres()),
                    toGenreIds(media.getGenres()), media.getLanguage(),
                    media.getOverview(), media.getPosterPath()));
        }
        return rows;
    }

    private List<String> toGenreNames(List<Genre> genres) {
        final List<String> names = new ArrayList<>();
        for (Genre genre : genres) {
            names.add(genre.getName());
        }
        return names;
    }

    private List<Integer> toGenreIds(List<Genre> genres) {
        final List<Integer> ids = new ArrayList<>();
        for (Genre genre : genres) {
            ids.add(genre.getId());
        }
        return ids;
    }
}
