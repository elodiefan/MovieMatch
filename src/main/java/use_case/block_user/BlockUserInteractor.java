package use_case.block_user;

import entity.UserFactory;
import use_case.block_user.BlockUserInputData;
import use_case.block_user.BlockUserOutputData;
import use_case.delete_account.DeleteAccountInputData;
import use_case.delete_account.DeleteAccountOutputBoundary;
import use_case.delete_account.DeleteAccountUserDataAccessInterface;

/**
 * The Block User Interactor.
 */

public class BlockUserInteractor {

    private final BlockUserUserDataAccessInterface userDataAccessObject;
    private final BlockUserOutputBoundary userPresenter;

    public BlockUserInteractor(BlockUserUserDataAccessInterface blockUserDataAccessInterface,
                               BlockUserOutputBoundary blockUserOutputBoundary) {
        this.userDataAccessObject = blockUserDataAccessInterface;
        this.userPresenter = blockUserOutputBoundary;
    }

    /**
     * Executes the Block User Use Case.
     * @param blockUserInputData the user's input info
     */
    @Override
    public void execute(BlockUserInputData blockUserInputData) {
        final String otherUsername = blockUserInputData.getOtherUsername();
        if (!userDataAccessObject.alreadyBlocked(otherUsername)) {
            userDataAccessObject.addToBlockList(otherUsername);
            final BlockUserOutputData blockUserOutputData = new BlockUserOutputData(true, false);
            userPresenter.prepareSuccessView(blockUserOutputData);
        }
        else {
            userDataAccessObject.removeFromBlockList(otherUsername);
            final BlockUserOutputData blockUserOutputData = new BlockUserOutputData(false, false);
            userPresenter.prepareSuccessView(blockUserOutputData);
        }
    }
}
