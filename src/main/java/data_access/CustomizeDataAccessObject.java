package data_access;

import use_case.customize.CustomizeDataAccessInterface;

/**
 * Data access object for the customize data access interface.
 */
public interface CustomizeDataAccessObject extends CustomizeDataAccessInterface {

    /**
     * Releases any resources held by this data store, such as an open database
     * connection. Call once when the app shuts down. Implementations with
     * nothing to release may do nothing.
     */
    void close();
}
