package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Media;
import entity.Movie;
import entity.TVShow;
import use_case.search.MediaPage;

class DatabaseSearchMediaDataAccessTest {

    private static Movie movie(int id, String title) {
        return new Movie(id, title, 2000, 7.0, new ArrayList<>(),
                "en", new ArrayList<>(), 100);
    }

    private static TVShow show(int id, String title) {
        return new TVShow(id, title, 2010, 8.0, new ArrayList<>(),
                "en", new ArrayList<>(), 2, 12);
    }

    @Test
    void searchFallsBackAndMatchesEveryWordInAnyOrder() {
        final DatabaseSearchMediaDataAccess dataAccess =
                new DatabaseSearchMediaDataAccess(
                        new LocalMovieDatabase(List.of(movie(1, "Dark Knight Rises"))),
                        new LocalTVShowDatabase(List.of(show(2, "Knight of the Dark"),
                                show(3, "Dark Matter"))));

        final List<Media> results = dataAccess.search("DARK knight");

        assertEquals(List.of(1, 2), List.of(
                results.get(0).getID(), results.get(1).getID()));
    }

    @Test
    void firstPageContainsAllLocalResultsAndLaterPagesAreEmpty() {
        final DatabaseSearchMediaDataAccess dataAccess =
                new DatabaseSearchMediaDataAccess(
                        new LocalMovieDatabase(List.of(movie(1, "Star Wars"))),
                        new LocalTVShowDatabase(List.of(show(2, "Star Trek"))));

        final MediaPage first = dataAccess.searchPage("star", 1);
        final MediaPage second = dataAccess.searchPage("star", 2);

        assertEquals(2, first.getMedia().size());
        assertEquals(1, first.getTotalPages());
        assertEquals(2, first.getTotalResults());
        assertTrue(second.getMedia().isEmpty());
        assertEquals(0, second.getTotalResults());
    }
}
