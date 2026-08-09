package use_case.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RecommendationInputDataTest {

    @Test
    void inputDataReturnsRequestInformation() {
        final RecommendationInputData data = new RecommendationInputData("bob", 5, true);

        assertEquals("bob", data.getUsername());
        assertEquals(5, data.getLimit());
        assertTrue(data.isGroupByGenre());
    }
}
