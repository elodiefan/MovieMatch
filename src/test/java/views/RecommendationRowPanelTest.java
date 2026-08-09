package views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import interface_adapter.recommendation.RecommendationRow;

/**
 * Tests the shared row both recommendation screens draw.
 *
 * Every row here is built without a poster path. A real path would send the
 * loader off to fetch artwork over the network, which would make these tests
 * slow, and failing without an internet connection. What is being checked is
 * the row's shape, and that is the same either way.
 */
class RecommendationRowPanelTest {

    private static final String NO_POSTER = "";

    private static RecommendationRow row(String posterPath, String explanation) {
        return new RecommendationRow(1, "Arrival", 2016, 0.82, "Science Fiction",
                explanation, posterPath);
    }

    private static RecommendationRow row() {
        return row(NO_POSTER, "because you like science fiction");
    }

    private static String allText(Component root) {
        final StringBuilder text = new StringBuilder();
        if (root instanceof JLabel) {
            final String label = ((JLabel) root).getText();
            if (label != null) {
                text.append(label).append('\n');
            }
        }
        if (root instanceof Container) {
            for (Component child : ((Container) root).getComponents()) {
                text.append(allText(child));
            }
        }
        return text.toString();
    }

    private static boolean hasPosterSizedSlot(Component root) {
        boolean found = false;
        if (root instanceof JLabel) {
            final Dimension size = root.getPreferredSize();
            found = size.width == PosterLoader.POSTER_WIDTH
                    && size.height == PosterLoader.POSTER_HEIGHT;
        }
        if (!found && root instanceof Container) {
            for (Component child : ((Container) root).getComponents()) {
                found = hasPosterSizedSlot(child);
                if (found) {
                    break;
                }
            }
        }
        return found;
    }

    @Test
    @DisplayName("the row never grows taller than it needs")
    void heightIsPinnedToWhatTheRowNeeds() {
        // The lists these sit in hand spare height to any row that accepts it.
        // Left unpinned that stretched every suggestion down a tall window and
        // left the poster stranded in an oversized box.
        final RecommendationRowPanel panel = new RecommendationRowPanel(row());

        assertEquals(panel.getPreferredSize().height, panel.getMaximumSize().height,
                "a row must not accept more height than it needs");
    }

    @Test
    @DisplayName("the row still stretches sideways to use the width")
    void widthIsStillFlexible() {
        final RecommendationRowPanel panel = new RecommendationRowPanel(row());

        assertTrue(panel.getMaximumSize().width > panel.getPreferredSize().width,
                "the explanation needs the full width even though the height is fixed");
    }

    @Test
    @DisplayName("the poster slot is reserved before any artwork arrives")
    void posterSlotIsReservedUpFront() {
        // Without this the rows jump about as each image lands.
        assertTrue(hasPosterSizedSlot(new RecommendationRowPanel(row())),
                "a slot the size of a poster should already exist");
    }

    @Test
    @DisplayName("the title, year and genre are all shown")
    void theRowReadsProperly() {
        final String text = allText(new RecommendationRowPanel(row()));

        assertTrue(text.contains("Arrival"));
        assertTrue(text.contains("2016"));
        assertTrue(text.contains("Science Fiction"));
    }

    @Test
    @DisplayName("an explanation is shown when there is one")
    void explanationIsShown() {
        final String text = allText(new RecommendationRowPanel(
                row(NO_POSTER, "because you like science fiction")));

        assertTrue(text.contains("because you like science fiction"));
    }

    @Test
    @DisplayName("a row without an explanation still builds")
    void noExplanationIsFine() {
        // The deterministic ranking leaves it blank, which is a valid result
        // rather than a fault, so the row has to cope with all three of these.
        assertNotNull(new RecommendationRowPanel(row(NO_POSTER, null)));
        assertNotNull(new RecommendationRowPanel(row(NO_POSTER, "")));
        assertNotNull(new RecommendationRowPanel(row(NO_POSTER, "   ")));
    }

    @Test
    @DisplayName("a title with no artwork keeps its shape")
    void noPosterKeepsTheLayout() {
        final RecommendationRowPanel panel = new RecommendationRowPanel(row(NO_POSTER, "why"));

        assertTrue(hasPosterSizedSlot(panel));
        assertEquals(panel.getPreferredSize().height, panel.getMaximumSize().height);
    }

    @Test
    @DisplayName("a null poster path does not break the row")
    void nullPosterIsFine() {
        assertNotNull(new RecommendationRowPanel(row(null, "why")));
    }

    @Test
    @DisplayName("a long explanation makes the row taller, not wider")
    void longExplanationGrowsDownwards() {
        final RecommendationRowPanel shortOne =
                new RecommendationRowPanel(row(NO_POSTER, "short"));
        final RecommendationRowPanel longOne = new RecommendationRowPanel(row(NO_POSTER,
                "a considerably longer explanation that has to wrap onto several "
                        + "lines before it is finished, which is what the wrapped "
                        + "label is there to do in the first place"));

        assertTrue(longOne.getPreferredSize().height >= shortOne.getPreferredSize().height);
        assertEquals(longOne.getPreferredSize().height, longOne.getMaximumSize().height,
                "even a tall row must still be pinned to what it needs");
    }
}
