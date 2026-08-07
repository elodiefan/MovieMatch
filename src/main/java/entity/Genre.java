package entity;

import java.util.Objects;

/** Represents the genre of a piece of media. */

public class Genre {

    private final int id;
    private final String name;

    /** Gets a new genre. */
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Returns the id of this genre. */
    public int getId() {
        return id;
    }

    /** Returns the name of this genre. */
    public String getName() {
        return name;
    }

    /** Compares genres by value rather than by identity. */
    @Override
    public boolean equals(final Object other) {
        final boolean result;
        if (this == other) {
            result = true;
        }
        else if (other == null || this.getClass() != other.getClass()) {
            result = false;
        }
        else {
            final Genre genre = (Genre) other;
            result = this.id == genre.id && Objects.equals(this.name, genre.name);
        }
        return result;
    }

    /** Returns a hash consistent with {@link #equals}. */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}
