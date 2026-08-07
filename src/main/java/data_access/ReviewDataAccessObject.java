package data_access;

import use_case.comment.GetUserCommentsReviewDataAccessInterface;
import use_case.review.ReviewDataAccessInterface;
import use_case.review.create_review.CreateReviewDataAccessInterface;
import use_case.review.delete_review.DeleteReviewDataAccessInterface;
import use_case.review.edit_review.EditReviewDataAccessInterface;
import use_case.review.get_media_reviews.GetMediaReviewsDataAccessInterface;
import use_case.review.get_user_reviews.GetUserReviewsDataAccessInterface;
import use_case.review.like_review.LikeReviewDataAccessInterface;
import use_case.review.unlike_review.UnlikeReviewDataAccessInterface;

public interface ReviewDataAccessObject extends
        ReviewDataAccessInterface,
        CreateReviewDataAccessInterface,
        DeleteReviewDataAccessInterface,
        EditReviewDataAccessInterface,
        GetMediaReviewsDataAccessInterface,
        GetUserReviewsDataAccessInterface,
        GetUserCommentsReviewDataAccessInterface,
        LikeReviewDataAccessInterface,
        UnlikeReviewDataAccessInterface {

    /** Releases any resources held by this data store, such as an open database connection. */
    void close();
}

