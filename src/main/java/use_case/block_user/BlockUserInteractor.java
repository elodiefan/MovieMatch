package use_case.block_user;

/**
 * The Block User Interactor.
 */

public class BlockUserInteractor implements BlockUserInputBoundary {

    private final BlockUserUserDataAccessInterface userDataAccessObject;
    private final BlockUserOutputBoundary userPresenter;

    public BlockUserInteractor(BlockUserUserDataAccessInterface blockUserDataAccessInterface,
                               BlockUserOutputBoundary blockUserOutputBoundary) {
        this.userDataAccessObject = blockUserDataAccessInterface;
        this.userPresenter = blockUserOutputBoundary;
    }

    /**
     * Executes the Block User Use Case.
     */
    @Override
    public void execute(BlockUserInputData blockUserInputData) {
        final String otherUsername = blockUserInputData.getOtherUsername();
        if (!userDataAccessObject.alreadyBlocked(otherUsername)) {
            userDataAccessObject.addToBlockList(otherUsername);
            final BlockUserOutputData blockUserOutputData = new BlockUserOutputData(true, false);
            userPresenter.prepareBlockSuccessView(blockUserOutputData);
        }
        else {
            userDataAccessObject.removeFromBlockList(otherUsername);
            final BlockUserOutputData blockUserOutputData = new BlockUserOutputData(false, false);
            userPresenter.prepareBlockSuccessView(blockUserOutputData);
        }
    }
}
