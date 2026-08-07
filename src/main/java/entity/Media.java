package entity;

import java.util.List;

/**
 * Interface representing all types of media (ie. movie, TV show).
 */

public interface Media {

    /**
     * Gets the unique identifier of this media.
     */
    int getID();

    /**
     * Gets the title of this media.
     */
    String getTitle();

    /**
     * Gets the release year of this media.
     */
    int getReleaseYear();

    /**
     * Gets the rating of this media.
     */
    double getAverageRating();

    /**
     * Gets the genre of this media.
     */
    List<Genre> getGenres();

    /**
     * Gets the language of this media.
     */
    String getLanguage();

    /**
     * Gets the cast and crew of this media.
     *
     * @return the media cast
     */
    List<String> getCast();

    /**
     * Updates the rating by the reviews of this media.
     */
    void updateRating(double rating);

    /**
     * Gets the type of this media.
     */
    MediaType getMediaType();
}
