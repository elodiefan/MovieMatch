package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class MovieTest {

    @Test
    void fullConstructorExposesEveryMovieProperty() {
        final List<Genre> genres = List.of(
                new Genre(878, "Science Fiction"),
                new Genre(18, "Drama")
        );
        final List<String> cast = List.of("Amy Adams", "Jeremy Renner");
        final Movie movie = new Movie(329865, "Arrival", 2016, 7.9,
                genres, "en", cast, 116,
                "A linguist meets visitors.", "/arrival.jpg");

        assertEquals(329865, movie.getID());
        assertEquals("Arrival", movie.getTitle());
        assertEquals(2016, movie.getReleaseYear());
        assertEquals(7.9, movie.getAverageRating());
        assertEquals(genres, movie.getGenres());
        assertEquals("en", movie.getLanguage());
        assertEquals(cast, movie.getCast());
        assertEquals(116, movie.getRuntime());
        assertEquals("A linguist meets visitors.", movie.getOverview());
        assertEquals("/arrival.jpg", movie.getPosterPath());
        assertEquals(MediaType.MOVIE, movie.getMediaType());
    }

    @Test
    void shortConstructorUsesEmptyOptionalMetadata() {
        final Movie movie = new Movie(1, "Movie", 2000, 5.0,
                List.of(), "fr", List.of(), 90);

        assertEquals("", movie.getOverview());
        assertEquals("", movie.getPosterPath());
    }

    @Test
    void ratingCanBeUpdated() {
        final Movie movie = new Movie(1, "Movie", 2000, 5.0,
                List.of(), "en", List.of(), 90);

        movie.updateRating(8.25);

        assertEquals(8.25, movie.getAverageRating());
    }
}
