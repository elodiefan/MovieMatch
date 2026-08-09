package use_case.block_user;

/**
 * Output Data for the Block User Use Case.
 */
public class BlockUserOutputData {

    private boolean isOnBlockList;
    private boolean useCaseFailed;

    public BlockUserOutputData(boolean isOnBlockList, boolean useCaseFailed) {
        this.isOnBlockList = isOnBlockList;
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isOnBlockList() {
        return isOnBlockList;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
