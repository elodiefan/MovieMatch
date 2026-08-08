package use_case.log_media;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Interactor for logging media to watchlist and watch history.
 */
public class LogMediaInteractor implements LogMediaInputBoundary {
    /**
     * Toronto time zone id.
     */
    private static final ZoneId TORONTO_ZONE =
            ZoneId.of("America/Toronto");
    /**
     * The minimum valid TMDB media id.
     */
    private static final int MIN_MEDIA_ID = 0;
    /**
     * Watchlist success message.
     */
    private static final String WATCHLIST_MESSAGE =
            "Added to watchlist.";
    /**
     * Watch history success message.
     */
    private static final String WATCH_HISTORY_MESSAGE =
            "Added to watch history.";

    /**
     * The data access object.
     */
    private final LogMediaDataAccessInterface dataAccessObject;
    /**
     * The output boundary.
     */
    private final LogMediaOutputBoundary presenter;

    /**
     * Creates an interactor for logging media.
     * @param inputDataAccessObject the data access object
     * @param inputPresenter the output boundary
     */
    public LogMediaInteractor(
            final LogMediaDataAccessInterface inputDataAccessObject,
            final LogMediaOutputBoundary inputPresenter) {
        this.dataAccessObject = inputDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void addToWatchlist(final LogMediaInputData inputData) {
        final String error = validateInput(inputData);
        if (error == null) {
            final String username = dataAccessObject.getCurrentUsername();
            dataAccessObject.addToWatchlist(username, inputData.getMediaId(),
                    trimToEmpty(inputData.getMediaType()),
                    trimToEmpty(inputData.getMediaTitle()),
                    getCurrentTorontoTimestamp());
            presenter.prepareSuccessView(new LogMediaOutputData(
                    inputData.getMediaTitle(), WATCHLIST_MESSAGE));
        } else {
            presenter.prepareFailView(error);
        }
    }

    @Override
    public void addToWatchHistory(final LogMediaInputData inputData) {
        final String error = validateInput(inputData);
        if (error == null) {
            final String username = dataAccessObject.getCurrentUsername();
            dataAccessObject.addToWatchHistory(username, inputData.getMediaId(),
                    trimToEmpty(inputData.getMediaType()),
                    trimToEmpty(inputData.getMediaTitle()),
                    getCurrentTorontoTimestamp());
            presenter.prepareSuccessView(new LogMediaOutputData(
                    inputData.getMediaTitle(), WATCH_HISTORY_MESSAGE));
        } else {
            presenter.prepareFailView(error);
        }
    }

    /**
     * Validates the media item and current user.
     * @param inputData the input data
     * @return an error message, or null when valid
     */
    private String validateInput(final LogMediaInputData inputData) {
        final String error;
        if (inputData == null) {
            error = "No media selected.";
        } else if (isBlank(dataAccessObject.getCurrentUsername())) {
            error = "Please log in before saving media.";
        } else {
            error = validateMedia(inputData);
        }
        return error;
    }

    /**
     * Validates the selected media item.
     * @param inputData the input data
     * @return an error message, or null when valid
     */
    private String validateMedia(final LogMediaInputData inputData) {
        final String error;
        if (inputData.getMediaId() < MIN_MEDIA_ID) {
            error = "No media selected.";
        } else if (isBlank(inputData.getMediaType())) {
            error = "Media type is missing.";
        } else if (isBlank(inputData.getMediaTitle())) {
            error = "Media title is missing.";
        } else {
            error = null;
        }
        return error;
    }

    /**
     * Returns the current Toronto timestamp.
     * @return the current timestamp with Toronto offset
     */
    private String getCurrentTorontoTimestamp() {
        return ZonedDateTime.now(TORONTO_ZONE).toOffsetDateTime().toString();
    }

    /**
     * Checks whether a string is blank.
     * @param value the value to check
     * @return true if the value is blank
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Trims a value or returns an empty string.
     * @param value the value to trim
     * @return the trimmed value
     */
    private String trimToEmpty(final String value) {
        final String trimmedValue;
        if (value == null) {
            trimmedValue = "";
        } else {
            trimmedValue = value.trim();
        }
        return trimmedValue;
    }
}
