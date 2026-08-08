package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TVShowTest {

    @Test
    void fullConstructorExposesEveryTvShowProperty() {
        final List<Genre> genres = List.of(
                new Genre(18, "Drama"), new Genre(9648, "Mystery"));
        final List<String> cast = List.of("Louis Hofmann", "Lisa Vicari");
        final TVShow show = new TVShow(70523, "Dark", 2017, 8.7,
                genres, "de", cast, 3, 26,
                "Families uncover a time-travel conspiracy.", "/dark.jpg");

        assertEquals(70523, show.getID());
        assertEquals("Dark", show.getTitle());
        assertEquals(2017, show.getReleaseYear());
        assertEquals(8.7, show.getAverageRating());
        assertEquals(genres, show.getGenres());
        assertEquals("de", show.getLanguage());
        assertEquals(cast, show.getCast());
        assertEquals(3, show.numberOfSeasons());
        assertEquals(26, show.numberOfEpisodes());
        assertEquals("Families uncover a time-travel conspiracy.",
                show.getOverview());
        assertEquals("/dark.jpg", show.getPosterPath());
        assertEquals(MediaType.TV_SHOW, show.getMediaType());
    }

    @Test
    void shortConstructorUsesEmptyOptionalMetadata() {
        final TVShow show = new TVShow(1, "Show", 2000, 5.0,
                List.of(), "ko", List.of(), 1, 8);

        assertEquals("", show.getOverview());
        assertEquals("", show.getPosterPath());
    }

    @Test
    void ratingCanBeUpdated() {
        final TVShow show = new TVShow(1, "Show", 2000, 5.0,
                List.of(), "en", List.of(), 1, 8);

        show.updateRating(9.0);

        assertEquals(9.0, show.getAverageRating());
    }
}
