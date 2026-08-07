package entity;

/**
 * Represents the genre of a piece of media.
 */

public class Genre {

    private final int id;
    private final String name;

    /**
     * Gets a new genre.
     */
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the id of this genre.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of this genre.
     */
    public String getName() {
        return name;
    }
}
