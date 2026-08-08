package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GenreTest {

    @Test
    void constructorValuesAreReturnedByGetters() {
        final Genre genre = new Genre(18, "Drama");

        assertEquals(18, genre.getId());
        assertEquals("Drama", genre.getName());
    }

    @Test
    void equalGenresUseIdAndNameAndHaveEqualHashes() {
        final Genre first = new Genre(18, "Drama");
        final Genre second = new Genre(18, "Drama");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void equalityHandlesIdentityNullOtherTypesAndDifferentValues() {
        final Genre genre = new Genre(18, "Drama");

        assertTrue(genre.equals(genre));
        assertFalse(genre.equals(null));
        assertFalse(genre.equals("Drama"));
        assertNotEquals(genre, new Genre(35, "Drama"));
        assertNotEquals(genre, new Genre(18, "Comedy"));
    }

    @Test
    void nullNamesAreComparedSafely() {
        assertEquals(new Genre(0, null), new Genre(0, null));
        assertNotEquals(new Genre(0, null), new Genre(0, "Unknown"));
    }
}
