package data_access;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Review;

import use_case.review.GetMediaReviewsDataAccessInterface;

/**
 * Loads read-only review data from TMDB.
 */
public final class TmdbReviewDataAccessObject
        implements GetMediaReviewsDataAccessInterface {
    /** Movie media type. */
    private static final String MOVIE_TYPE = "movie";
    /** TV media type. */
    private static final String TV_TYPE = "tv";
    /** TMDB source label. */
    private static final String TMDB_SOURCE = "tmdb";
    /** TMDB review id prefix. */
    private static final String TMDB_REVIEW_PREFIX = "tmdb-";
    /** Default title for TMDB reviews. */
    private static final String UNKNOWN_MEDIA_TITLE = "";
    /** Maximum rating scale used by TMDB author details. */
    private static final double TMDB_MAX_RATING = 10.0;
    /** Percentage multiplier. */
    private static final double PERCENT_MULTIPLIER = 10.0;

    /** TMDB API client. */
    private final TmdbApiClient tmdbApiClient;
    /** JSON mapper. */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Creates a TMDB review data access object.
     *
     * @param inputTmdbApiClient the TMDB API client
     */
    public TmdbReviewDataAccessObject(
            final TmdbApiClient inputTmdbApiClient) {
        this.tmdbApiClient = inputTmdbApiClient;
    }

    @Override
    public List<Review> getReviewsByMedia(final int mediaId,
                                          final String mediaType) {
        final List<Review> reviews = new ArrayList<>();
        if (MOVIE_TYPE.equals(mediaType) || TV_TYPE.equals(mediaType)) {
            try {
                reviews.addAll(parseReviews(mediaId, mediaType,
                        getReviewsJson(mediaId, mediaType)));
            } catch (IOException exception) {
                throw new IllegalStateException(
                        "Unable to load TMDB reviews.", exception);
            }
        }
        return reviews;
    }

    /**
     * Requests review JSON from TMDB.
     *
     * <p>This calls methods that Yidan is adding to {@link TmdbApiClient}:
     * getMovieReviews(int) and getTvShowReviews(int).</p>
     *
     * @param mediaId the TMDB media id
     * @param mediaType the TMDB media type
     * @return the raw TMDB reviews JSON
     * @throws IOException if the request fails
     */
    private String getReviewsJson(final int mediaId, final String mediaType)
            throws IOException {
        final String methodName;
        if (MOVIE_TYPE.equals(mediaType)) {
            methodName = "getMovieReviews";
        } else {
            methodName = "getTvShowReviews";
        }
        return invokeReviewMethod(methodName, mediaId);
    }

    /**
     * Invokes the review method once it exists on the shared TMDB client.
     *
     * @param methodName the review method name
     * @param mediaId the TMDB media id
     * @return the raw TMDB reviews JSON
     * @throws IOException if the client request fails
     */
    private String invokeReviewMethod(final String methodName,
                                      final int mediaId) throws IOException {
        String response;
        try {
            final Method method = TmdbApiClient.class.getMethod(methodName,
                    int.class);
            response = (String) method.invoke(tmdbApiClient, mediaId);
        } catch (NoSuchMethodException exception) {
            throw new IOException("TMDB review endpoint is not wired yet.",
                    exception);
        } catch (IllegalAccessException exception) {
            throw new IOException("TMDB review endpoint cannot be accessed.",
                    exception);
        } catch (InvocationTargetException exception) {
            response = handleInvocationFailure(exception);
        }
        return response;
    }

    /**
     * Converts a reflected TMDB client failure into an IOException.
     *
     * @param exception the reflected invocation exception
     * @return never normally returns
     * @throws IOException always thrown with the underlying cause
     */
    private String handleInvocationFailure(
            final InvocationTargetException exception) throws IOException {
        final Throwable cause = exception.getCause();
        if (cause instanceof IOException) {
            throw (IOException) cause;
        }
        throw new IOException("TMDB review endpoint failed.", cause);
    }

    /**
     * Parses TMDB reviews into Review entities.
     *
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     * @param reviewsJson the raw TMDB reviews JSON
     * @return parsed reviews
     * @throws IOException if the JSON cannot be parsed
     */
    private List<Review> parseReviews(final int mediaId,
                                      final String mediaType,
                                      final String reviewsJson)
            throws IOException {
        final List<Review> reviews = new ArrayList<>();
        final JsonNode root = objectMapper.readTree(reviewsJson);
        for (JsonNode reviewNode : root.path("results")) {
            reviews.add(toReview(mediaId, mediaType, reviewNode));
        }
        return reviews;
    }

    /**
     * Converts one TMDB review JSON object into a Review.
     *
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     * @param reviewNode the TMDB review node
     * @return the converted review
     */
    private Review toReview(final int mediaId, final String mediaType,
                            final JsonNode reviewNode) {
        final JsonNode authorDetails = reviewNode.path("author_details");
        final String tmdbReviewId = reviewNode.path("id").asText();
        final String author = reviewNode.path("author").asText();
        final String username = firstNonBlank(authorDetails.path("username")
                .asText(), author, "tmdb-user");
        final String displayName = firstNonBlank(authorDetails.path("name")
                .asText(), author, username);
        final ZonedDateTime createdAt = parseDateTime(reviewNode
                .path("created_at").asText());
        final ZonedDateTime updatedAt = parseDateTime(reviewNode
                .path("updated_at").asText());

        return new Review(TMDB_REVIEW_PREFIX + tmdbReviewId, mediaId,
                mediaType, UNKNOWN_MEDIA_TITLE, "tmdb:" + username,
                displayName, parseRating(authorDetails.path("rating")),
                reviewNode.path("content").asText(), createdAt, updatedAt,
                TMDB_SOURCE, new HashSet<>());
    }

    /**
     * Converts a TMDB rating into a percentage.
     *
     * @param ratingNode the TMDB rating node
     * @return rating percentage
     */
    private double parseRating(final JsonNode ratingNode) {
        final double rating;
        if (ratingNode == null || ratingNode.isMissingNode()
                || ratingNode.isNull()) {
            rating = 0.0;
        } else if (ratingNode.asDouble() <= TMDB_MAX_RATING) {
            rating = ratingNode.asDouble() * PERCENT_MULTIPLIER;
        } else {
            rating = ratingNode.asDouble();
        }
        return rating;
    }

    /**
     * Parses a TMDB ISO timestamp.
     *
     * @param value the timestamp value
     * @return parsed time, or the current time when missing
     */
    private ZonedDateTime parseDateTime(final String value) {
        final ZonedDateTime parsedTime;
        if (isBlank(value)) {
            parsedTime = ZonedDateTime.now();
        } else {
            parsedTime = ZonedDateTime.parse(value);
        }
        return parsedTime;
    }

    /**
     * Returns the first non-blank value.
     *
     * @param first the first value
     * @param second the second value
     * @param fallback the fallback value
     * @return the first usable value
     */
    private String firstNonBlank(final String first, final String second,
                                 final String fallback) {
        final String value;
        if (!isBlank(first)) {
            value = first.trim();
        } else if (!isBlank(second)) {
            value = second.trim();
        } else {
            value = fallback;
        }
        return value;
    }

    /**
     * Checks whether a value is blank.
     *
     * @param value the value to check
     * @return true if the value is blank
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }
}
