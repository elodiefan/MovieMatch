package use_case.delete_account;

public interface DeleteAccountCommentDataAccessInterface {

    /**
     * Deletes all comments made by authorUsername.
     * @param authorUsername the author's username
     */
    void deleteAllComments(String authorUsername);
}
