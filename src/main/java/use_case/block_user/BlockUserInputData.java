package use_case.block_user;

/** The input data for the Block User Use Case. */

public class BlockUserInputData {

    private final String otherUsername;

    public BlockUserInputData(String otherUsername) {
        this.otherUsername = otherUsername;
    }

    String getOtherUsername() {
        return otherUsername;
    }
}
