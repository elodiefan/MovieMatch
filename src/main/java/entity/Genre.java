package entity;

/**
 * Represents the genre of a piece of media.
 */

public class Genre {

    private final int id;
    private final String name;

    /**
     * Gets a new genre.
     *
     * @param id the unique identifier of the genre
     * @param name the name of the genre
     */
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the id of this genre.
     *
     * @return the genre id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of this genre.
     *
     * @return the genre name
     */
    public String getName() {
        return name;
    }
}
