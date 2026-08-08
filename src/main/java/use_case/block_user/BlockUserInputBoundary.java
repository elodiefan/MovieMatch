package use_case.block_user;

/**
 * Input boundary for actions related to blocking users.
 */
public interface BlockUserInputBoundary {

    /**
     * Executes the block user use case.
     * @param blockUserInputData the input data
     */
    void execute(BlockUserInputData blockUserInputData);
}
