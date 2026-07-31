package entity;

import java.util.List;

/**
 * Interface representing all types of media (ie. movie, TV show).
 */

public interface Media {

    /**
     * Gets the unique identifier of this media.
     *
     * @return the media ID
     */
    int getID();

    /**
     * Gets the title of this media.
     *
     * @return the media Title
     */
    String getTitle();

    /**
     * Gets the release year of this media.
     *
     * @return the media ReleaseYear
     */
    int getReleaseYear();

    /**
     * Gets the rating of this media.
     *
     * @return the media Rating
     */
    double getAverageRating();

    /**
     * Gets the genre of this media.
     *
     * @return the media genre
     */
    List<Genre> getGenres();

    /**
     * Gets the language of this media.
     *
     * @return the media language
     */
    String getLanguage();

    /**
     * Updates the rating by the reviews of this media.
     *
     * @param rating updated rating value
     */
    void updateRating(double rating);

}
