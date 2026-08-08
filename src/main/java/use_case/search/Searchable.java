package use_case.search;

import java.util.List;

/**
 * Interface for objects that can be searched.
 *
 * @param <T> The class that is aimed to search.
 */
public interface Searchable<T> {

    /**
     * Searches items using a keyword.
     *
     * @param keyword keyword entered by user
     * @return matching results
     */
    List<T> search(String keyword);
}
