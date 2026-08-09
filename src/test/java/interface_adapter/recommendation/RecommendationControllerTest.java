package interface_adapter.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInputData;

class RecommendationControllerTest {

    private static final class RecordingInteractor implements RecommendationInputBoundary {
        private RecommendationInputData input;

        @Override
        public void recommend(RecommendationInputData inputData) {
            input = inputData;
        }
    }

    @Test
    void homePageRequestUsesShortUngroupedInput() {
        final RecordingInteractor interactor = new RecordingInteractor();

        new RecommendationController(interactor).loadForHomePage("bob");

        assertEquals("bob", interactor.input.getUsername());
        assertEquals(RecommendationInputData.HOME_PAGE_LIMIT, interactor.input.getLimit());
        assertFalse(interactor.input.isGroupByGenre());
    }

    @Test
    void detailedRequestUsesLongGroupedInput() {
        final RecordingInteractor interactor = new RecordingInteractor();

        new RecommendationController(interactor).loadDetailed("bob");

        assertEquals("bob", interactor.input.getUsername());
        assertEquals(RecommendationInputData.DETAILED_LIMIT, interactor.input.getLimit());
        assertTrue(interactor.input.isGroupByGenre());
    }
}
