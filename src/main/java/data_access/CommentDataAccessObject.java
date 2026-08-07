package data_access;

import use_case.comment.CommentDataAccessInterface;
import use_case.comment.create_comment.CreateCommentDataAccessInterface;
import use_case.comment.delete_comment.DeleteCommentDataAccessInterface;
import use_case.comment.get_review_comments.GetReviewCommentsDataAccessInterface;
import use_case.comment.get_user_comments.GetUserCommentsDataAccessInterface;
import use_case.comment.like_comment.LikeCommentDataAccessInterface;
import use_case.comment.unlike_comment.UnlikeCommentDataAccessInterface;

public interface CommentDataAccessObject extends
        CommentDataAccessInterface,
        CreateCommentDataAccessInterface,
        DeleteCommentDataAccessInterface,
        GetReviewCommentsDataAccessInterface,
        GetUserCommentsDataAccessInterface,
        LikeCommentDataAccessInterface,
        UnlikeCommentDataAccessInterface {

    /** Releases any resources held by this data store, such as an open database connection. */
    void close();
}

