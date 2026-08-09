package views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.JLabel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests how poster artwork is sized and when it is fetched at all.
 *
 * Nothing here downloads anything: the paths used are the ones the loader is
 * supposed to ignore, and the scaling is checked directly on an image made here.
 */
class PosterLoaderTest {

    /**
     * Calls the private scaling helper.
     *
     * Reached by reflection rather than by widening it, because the scaling is
     * the part that actually went wrong and it is worth pinning without
     * changing the class's shape to suit a test.
     */
    private static Image fitToSlot(Image image) throws Exception {
        final Method method =
                PosterLoader.class.getDeclaredMethod("fitToSlot", Image.class);
        method.setAccessible(true);
        return (Image) method.invoke(null, image);
    }

    private static BufferedImage image(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    @DisplayName("a poster of the usual shape fills the slot exactly")
    void twoByThreePosterFillsTheSlot() throws Exception {
        // What TMDB returns at w154.
        final Image scaled = fitToSlot(image(154, 231));

        assertEquals(PosterLoader.POSTER_WIDTH, scaled.getWidth(null));
        assertEquals(PosterLoader.POSTER_HEIGHT, scaled.getHeight(null));
    }

    @Test
    @DisplayName("an unusually shaped poster keeps its proportions")
    void oddShapedPosterIsNotStretched() throws Exception {
        // Not every poster is two by three. Forcing them all into the same
        // rectangle is what squashed the odd ones.
        final Image scaled = fitToSlot(image(154, 218));

        final double sourceRatio = 154.0 / 218.0;
        final double scaledRatio = (double) scaled.getWidth(null) / scaled.getHeight(null);
        assertEquals(sourceRatio, scaledRatio, 0.02, "the shape must survive the scaling");
    }

    @Test
    @DisplayName("nothing is scaled beyond the slot in either direction")
    void nothingOverflowsTheSlot() throws Exception {
        for (int[] size : new int[][] {{154, 231}, {154, 218}, {300, 100}, {100, 300}, {50, 50}}) {
            final Image scaled = fitToSlot(image(size[0], size[1]));

            assertTrue(scaled.getWidth(null) <= PosterLoader.POSTER_WIDTH,
                    "a " + size[0] + "x" + size[1] + " poster overflowed the slot's width");
            assertTrue(scaled.getHeight(null) <= PosterLoader.POSTER_HEIGHT,
                    "a " + size[0] + "x" + size[1] + " poster overflowed the slot's height");
        }
    }

    @Test
    @DisplayName("a very wide image is limited by the width, a tall one by the height")
    void theTighterSideIsWhatLimits() throws Exception {
        final Image wide = fitToSlot(image(300, 100));
        assertEquals(PosterLoader.POSTER_WIDTH, wide.getWidth(null),
                "a wide image runs out of width first");

        final Image tall = fitToSlot(image(100, 300));
        assertEquals(PosterLoader.POSTER_HEIGHT, tall.getHeight(null),
                "a tall image runs out of height first");
    }

    @Test
    @DisplayName("scaling never collapses an image to nothing")
    void extremeShapesStayVisible() throws Exception {
        final Image sliver = fitToSlot(image(1000, 1));

        assertTrue(sliver.getWidth(null) >= 1);
        assertTrue(sliver.getHeight(null) >= 1, "a rounded-down height must not become zero");
    }

    @Test
    @DisplayName("a title with no artwork is left alone rather than fetched")
    void blankPathsAreIgnored() {
        // Doing nothing leaves the placeholder in place, which reads better
        // than a broken image, and avoids a pointless request.
        final JLabel label = new JLabel();

        PosterLoader.loadInto(label, null);
        assertNull(label.getIcon());

        PosterLoader.loadInto(label, "");
        assertNull(label.getIcon());

        PosterLoader.loadInto(label, "   ");
        assertNull(label.getIcon());
    }

    @Test
    @DisplayName("the slot is the two-by-three shape posters actually come in")
    void slotMatchesAPosterShape() {
        assertEquals(PosterLoader.POSTER_HEIGHT,
                Math.round(PosterLoader.POSTER_WIDTH * 1.5),
                "a slot that is not 2:3 would letterbox every poster");
    }

    @Test
    @DisplayName("the size requested from the source is a small one")
    void asksForAThumbnailNotAFullPoster() {
        // A list of suggestions downloads one of these per row, so the full
        // size original would be a lot of bytes for a 92 pixel wide slot.
        assertEquals("w154", PosterLoader.THUMBNAIL_SIZE);
    }

    @Test
    @DisplayName("the loader is a utility, not something to instantiate")
    void isAUtilityClass() {
        assertTrue(Modifier.isFinal(PosterLoader.class.getModifiers()));
        assertEquals(1, PosterLoader.class.getDeclaredConstructors().length);
        assertTrue(Modifier.isPrivate(
                PosterLoader.class.getDeclaredConstructors()[0].getModifiers()));
    }
}
