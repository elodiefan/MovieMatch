package use_case.block_user;

/**
 * The output boundary for the Block User Use Case.
 */

public interface BlockUserOutputBoundary {
    /**
     * Prepares the success view for the Block User Use Case.
     * @param outputData the output data
     */
    void prepareBlockSuccessView(BlockUserOutputData outputData);
}
