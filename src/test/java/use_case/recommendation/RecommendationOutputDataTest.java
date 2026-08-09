package use_case.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the indexed view a presenter reads results through.
 */
class RecommendationOutputDataTest {

    private static final String USER = "enzo";

    private static RecommendedMedia media(int id, String title, String posterPath) {
        return new RecommendedMedia(id, title, 2024, 0.75, "Drama",
                "because you like drama", posterPath);
    }

    private static RecommendationOutputData flat() {
        return new RecommendationOutputData(USER,
                List.of(media(1, "First", "/one.jpg"), media(2, "Second", "/two.jpg")),
                new ArrayList<>());
    }

    private static RecommendationOutputData grouped() {
        return new RecommendationOutputData(USER,
                List.of(media(1, "First", "/one.jpg")),
                List.of(new GenreSection("Drama",
                                List.of(media(3, "Third", "/three.jpg"))),
                        new GenreSection("Comedy",
                                List.of(media(4, "Fourth", "/four.jpg")))));
    }

    @Test
    @DisplayName("each recommendation can be read by position")
    void indexedGettersReadTheRightRow() {
        final RecommendationOutputData data = flat();

        assertEquals(2, data.getRecommendationCount());
        assertEquals(1, data.getRecommendationMediaId(0));
        assertEquals("Second", data.getRecommendationTitle(1));
        assertEquals(2024, data.getRecommendationReleaseYear(0));
        assertEquals(0.75, data.getRecommendationScore(0), 0.0001);
        assertEquals("Drama", data.getRecommendationPrimaryGenre(0));
        assertEquals("because you like drama", data.getRecommendationExplanation(0));
    }

    @Test
    @DisplayName("the poster path is readable by position")
    void posterPathIsReadable() {
        final RecommendationOutputData data = flat();

        assertEquals("/one.jpg", data.getRecommendationPosterPath(0));
        assertEquals("/two.jpg", data.getRecommendationPosterPath(1));
    }

    @Test
    @DisplayName("a section's rows carry their poster too")
    void sectionPosterPathIsReadable() {
        // The full screen reads through the section getters, so missing this
        // would leave artwork on the home strip but not on the full list.
        final RecommendationOutputData data = grouped();

        assertEquals("/three.jpg", data.getSectionRecommendationPosterPath(0, 0));
        assertEquals("/four.jpg", data.getSectionRecommendationPosterPath(1, 0));
    }

    @Test
    @DisplayName("sections can be read by position")
    void sectionGettersReadTheRightRow() {
        final RecommendationOutputData data = grouped();

        assertEquals(2, data.getSectionCount());
        assertEquals(1, data.getSectionRecommendationCount(0));
        assertEquals(3, data.getSectionRecommendationMediaId(0, 0));
        assertEquals("Third", data.getSectionRecommendationTitle(0, 0));
        assertEquals(2024, data.getSectionRecommendationReleaseYear(0, 0));
        assertEquals(0.75, data.getSectionRecommendationScore(0, 0), 0.0001);
        assertEquals("Drama", data.getSectionRecommendationPrimaryGenre(0, 0));
        assertEquals("because you like drama", data.getSectionRecommendationExplanation(0, 0));
    }

    @Test
    @DisplayName("a section heading reads as a reason")
    void sectionHeadingExplainsItself() {
        assertTrue(grouped().getSectionHeading(0).startsWith("Because you like "));
    }

    @Test
    @DisplayName("nothing to suggest is reported as empty")
    void emptyIsReported() {
        final RecommendationOutputData nothing =
                new RecommendationOutputData(USER, new ArrayList<>(), new ArrayList<>());

        assertTrue(nothing.isEmpty());
        assertEquals(0, nothing.getRecommendationCount());
        assertEquals(0, nothing.getSectionCount());
    }

    @Test
    @DisplayName("having results means not empty")
    void resultsMeanNotEmpty() {
        assertFalse(flat().isEmpty());
    }

    @Test
    @DisplayName("the username comes back so the screen knows who it is for")
    void usernameIsCarried() {
        assertEquals(USER, flat().getUsername());
    }

    @Test
    @DisplayName("a flat request produces no sections")
    void flatHasNoSections() {
        assertEquals(0, flat().getSectionCount());
    }
}
