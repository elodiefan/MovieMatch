package use_case.block_user;

/**
 * Input boundary for actions related to blocking users.
 */
public interface BlockUserInputBoundary {

    /**
     * Executes the block user use case.
     * @param otherUsername the other user's username
     */
    void execute(String otherUsername);
}
