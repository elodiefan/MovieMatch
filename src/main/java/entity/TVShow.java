package entity;

import java.util.List;

/**
 * Represents a TV show.
 */

public class TVShow implements Media {

    private final int id;
    private final String title;
    private final int releaseYear;
    private double averageRating;
    private final List<Genre> genres;
    private final String language;
    private final List<String> cast;
    private final int numberOfSeasons;
    private final int numberOfEpisodes;

    /**
     * Creates a new movie.
     *
     * @param id the id of the movie
     * @param title the title of the movie
     * @param releaseYear the release year of the movie
     * @param averageRating the rating of the movie
     * @param genres the genres of the movie
     * @param language the language of the movie
     * @param cast the cast of the movie
     * @param numberOfSeasons the number of seasons of the movie
     * @param numberOfEpisodes the number of episodes of the movie
     */
    public TVShow(int id,
                 String title,
                 int releaseYear,
                 double averageRating,
                 List<Genre> genres,
                 String language,
                 List<String> cast,
                 int numberOfSeasons,
                 int numberOfEpisodes) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.genres = genres;
        this.language = language;
        this.cast = cast;
        this.numberOfSeasons = numberOfSeasons;
        this.numberOfEpisodes = numberOfEpisodes;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    public List<String> getCast() {
        return cast;
    }

    /**
     * Returns the amount of seasons of this show.
     *
     * @return the genre id
     */
    public int numberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     * Returns the amount of episodes of this show.
     *
     * @return the genre id
     */
    public int numberOfEpisodes() {
        return numberOfEpisodes;
    }

    @Override
    public void updateRating(double rating) {
        this.averageRating = rating;
    }
}
