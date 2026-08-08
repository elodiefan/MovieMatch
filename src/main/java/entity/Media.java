package entity;

import java.util.List;

/**
 * Interface representing all types of media (ie. movie, TV show).
 */

public interface Media {

    /**
     * Gets the unique identifier of this media.
     *
     * @return the get i d
     */
    int getID();

    /**
     * Gets the title of this media.
     *
     * @return the get title
     */
    String getTitle();

    /**
     * Gets the release year of this media.
     *
     * @return the get release year
     */
    int getReleaseYear();

    /**
     * Gets the rating of this media.
     *
     * @return the get average rating
     */
    double getAverageRating();

    /**
     * Gets the genre of this media.
     *
     * @return the get genres
     */
    List<Genre> getGenres();

    /**
     * Gets the language of this media.
     *
     * @return the get language
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
     *
     * @param rating the rating
     */
    void updateRating(double rating);

    /**
     * Gets the type of this media.
     *
     * @return the get media type
     */
    MediaType getMediaType();
}
