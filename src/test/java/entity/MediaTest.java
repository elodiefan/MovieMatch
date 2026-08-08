package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.List;

import org.junit.jupiter.api.Test;

class MediaTest {

    @Test
    void movieCanBeUsedThroughMediaContract() {
        final Media media = new Movie(1, "Arrival", 2016, 7.9,
                List.of(new Genre(18, "Drama")), "en",
                List.of("Amy Adams"), 116, "First contact", "/arrival.jpg");

        assertInstanceOf(Movie.class, media);
        assertEquals(1, media.getID());
        assertEquals("Arrival", media.getTitle());
        assertEquals(MediaType.MOVIE, media.getMediaType());
        assertEquals("Amy Adams", media.getCast().get(0));
    }

    @Test
    void tvShowCanBeUsedThroughMediaContract() {
        final Media media = new TVShow(2, "Dark", 2017, 8.7,
                List.of(new Genre(18, "Drama")), "de",
                List.of("Louis Hofmann"), 3, 26,
                "Time travel", "/dark.jpg");

        assertInstanceOf(TVShow.class, media);
        assertEquals(2017, media.getReleaseYear());
        assertEquals("de", media.getLanguage());
        assertEquals(MediaType.TV_SHOW, media.getMediaType());
        assertEquals("/dark.jpg", media.getPosterPath());
    }

    @Test
    void updateRatingIsAvailableThroughMediaContract() {
        final Media media = new Movie(1, "Arrival", 2016, 7.9,
                List.of(), "en", List.of(), 116);

        media.updateRating(9.1);

        assertEquals(9.1, media.getAverageRating());
    }
}
