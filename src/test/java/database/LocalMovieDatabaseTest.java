package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Movie;

class LocalMovieDatabaseTest {

    private static Movie movie(int id, String title) {
        return new Movie(id, title, 2000, 7.0, new ArrayList<>(),
                "en", new ArrayList<>(), 120);
    }

    @Test
    void searchIgnoresCaseAndSurroundingWhitespace() {
        final LocalMovieDatabase database = new LocalMovieDatabase(List.of(
                movie(1, "The Dark Knight"), movie(2, "Inception")));

        final List<Movie> results = database.search("  DARK knight  ");

        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getID());
    }

    @Test
    void nullBlankAndUnknownKeywordsReturnNoMovies() {
        final LocalMovieDatabase database =
                new LocalMovieDatabase(List.of(movie(1, "Inception")));

        assertTrue(database.search(null).isEmpty());
        assertTrue(database.search("   ").isEmpty());
        assertTrue(database.search("Arrival").isEmpty());
    }

    @Test
    void constructorAndGetterDefensivelyCopyTheList() {
        final List<Movie> source = new ArrayList<>();
        source.add(movie(1, "Inception"));
        final LocalMovieDatabase database = new LocalMovieDatabase(source);
        source.clear();

        final List<Movie> firstRead = database.getMovies();
        firstRead.clear();

        assertEquals(1, database.getMovies().size());
        assertNotSame(firstRead, database.getMovies());
    }

    @Test
    void defaultConstructorLoadsMoviesResource() {
        assertTrue(new LocalMovieDatabase().getMovies().size() > 0);
    }
}
