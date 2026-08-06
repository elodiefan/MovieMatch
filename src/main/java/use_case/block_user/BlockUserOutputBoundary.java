package use_case.block_user;

/**
 * The output boundary for the Block User Use Case.
 */

public interface BlockUserOutputBoundary {
    /**
     * Prepares the view for successfully blocking other user.
     * @param outputData the output data
     */
    void prepareBlockSuccessView(BlockUserOutputData outputData);

    /**
     * Prepares the view for successfully unblocking other user.
     * @param outputData the output data
     */
    void prepareUnblockSuccessView(BlockUserOutputData outputData);
}