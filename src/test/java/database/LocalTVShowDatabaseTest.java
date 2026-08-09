package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.TVShow;

class LocalTVShowDatabaseTest {

    private static TVShow show(int id, String title) {
        return new TVShow(id, title, 2010, 8.0, new ArrayList<>(),
                "en", new ArrayList<>(), 3, 24);
    }

    @Test
    void searchIgnoresCaseAndSurroundingWhitespace() {
        final LocalTVShowDatabase database = new LocalTVShowDatabase(List.of(
                show(1, "Breaking Bad"), show(2, "The Bear")));

        final List<TVShow> results = database.search("  BREAKING bad ");

        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getID());
    }

    @Test
    void nullBlankAndUnknownKeywordsReturnNoShows() {
        final LocalTVShowDatabase database =
                new LocalTVShowDatabase(List.of(show(1, "The Bear")));

        assertTrue(database.search(null).isEmpty());
        assertTrue(database.search(" ").isEmpty());
        assertTrue(database.search("Severance").isEmpty());
    }

    @Test
    void constructorAndGetterDefensivelyCopyTheList() {
        final List<TVShow> source = new ArrayList<>();
        source.add(show(1, "The Bear"));
        final LocalTVShowDatabase database = new LocalTVShowDatabase(source);
        source.clear();

        final List<TVShow> firstRead = database.getTvShows();
        firstRead.clear();

        assertEquals(1, database.getTvShows().size());
        assertNotSame(firstRead, database.getTvShows());
    }

    @Test
    void defaultConstructorLoadsTvShowsResource() {
        assertTrue(new LocalTVShowDatabase().getTvShows().size() > 0);
    }
}
