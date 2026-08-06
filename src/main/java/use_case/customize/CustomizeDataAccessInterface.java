package use_case.customize;

/**
 * The DAO for the customize use case.
 */
public interface CustomizeDataAccessInterface {

    /**
     * Checks if customizable unlock code is valid.
     * @param code the code inputted by the user.
     * @return true if code is found in database; false otherwise.
     */
    boolean checkValidCode(int code);
}
