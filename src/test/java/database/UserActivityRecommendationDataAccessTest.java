package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import use_case.recommendation.UserRating;

class UserActivityRecommendationDataAccessTest {

    @Test
    void watchedItemsReceiveImpliedRatingsAndReviewsOverrideThem() {
        final Set<Integer> engaged = new LinkedHashSet<>(List.of(10, 20));
        final UserActivityRecommendationDataAccess dataAccess =
                new UserActivityRecommendationDataAccess(username -> engaged,
                        username -> List.of(new UserRating(20, 9.0), new UserRating(30, 7.5)));

        final List<UserRating> ratings = dataAccess.findRatingsByUser("bob");

        assertEquals(3, ratings.size());
        assertEquals(10, ratings.get(0).getMediaId());
        assertEquals(UserActivityRecommendationDataAccess.IMPLIED_RATING,
                ratings.get(0).getRating());
        assertEquals(20, ratings.get(1).getMediaId());
        assertEquals(9.0, ratings.get(1).getRating());
        assertEquals(30, ratings.get(2).getMediaId());
    }

    @Test
    void friendsAreEmptyBecauseTheApplicationHasNoFriendsFeature() {
        final UserActivityRecommendationDataAccess dataAccess =
                new UserActivityRecommendationDataAccess(username -> Set.of(),
                        username -> List.of());

        assertTrue(dataAccess.findFriendUsernames("bob").isEmpty());
        assertTrue(dataAccess.findFriendRatingsForMedia(List.of("friend"), 10).isEmpty());
    }

    @Test
    void engagedMediaIdsAreDelegatedWithoutCopying() {
        final Set<Integer> engaged = new LinkedHashSet<>(List.of(1, 2));
        final UserActivityRecommendationDataAccess dataAccess =
                new UserActivityRecommendationDataAccess(username -> engaged,
                        username -> List.of());

        assertSame(engaged, dataAccess.engagedMediaIds("bob"));
    }
}
