package use_case.block_user;

/**
 * Output Data for the Block User Use Case.
 */
public class BlockUserOutputData {

    private boolean addedToBlockList;
    private boolean useCaseFailed;

    public BlockUserOutputData(boolean addedToBlockList, boolean useCaseFailed) {
        this.addedToBlockList = addedToBlockList;
        this.useCaseFailed = useCaseFailed;
    }

    public boolean addedToBlockList() {
        return addedToBlockList;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
