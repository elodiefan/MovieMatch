package entity;

import java.util.Objects;

/**
 * Represents the genre of a piece of media.
 */

public class Genre {

    private final int id;
    private final String name;

    /**
     * Gets a new genre.
     *
     * @param id the id
     * @param name the name
     */
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the id of this genre.
     *
     * @return the get id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of this genre.
     *
     * @return the get name
     */
    public String getName() {
        return name;
    }

    /**
     * Compares genres by value rather than by identity.
     * Two genres loaded separately — say from a user's taste profile and from a
     * candidate title — describe the same genre when their ids match. Without
     * this, putting genres in a {@code Set} would treat those as different
     * members and every overlap calculation would come out as zero.
     *
     * @param other the object to compare against
     * @return true if other is a genre with the same id and name
     */
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

    /**
     * Returns a hash consistent with {@link #equals}.
     *
     * @return the hash code for this genre
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}
