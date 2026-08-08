package views;

import java.awt.Image;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
 * Fetches poster thumbnails and puts them on a label once they arrive.
 *
 * The path comes from the data layer as plain text; how big to draw it is a
 * display decision, so the address is assembled here rather than further in.
 *
 * Every poster is fetched off the UI thread and kept, so scrolling a list of
 * suggestions never stalls the window and the same artwork is only downloaded
 * once per run.
 */
public final class PosterLoader {

    /** Width to request. Small enough to stay quick, large enough to read. */
    public static final String THUMBNAIL_SIZE = "w154";

    /** How tall a poster slot is, so rows line up before the image lands. */
    public static final int POSTER_WIDTH = 77;
    public static final int POSTER_HEIGHT = 115;

    private static final String IMAGE_BASE = "https://image.tmdb.org/t/p/";

    /** Already fetched artwork, shared across every screen. */
    private static final Map<String, ImageIcon> CACHE = new ConcurrentHashMap<>();

    private PosterLoader() {
    }

    /**
     * Puts the poster for a path onto a label when it has downloaded.
     *
     * Does nothing at all when the title has no artwork, which leaves the
     * placeholder in place rather than showing a broken image.
     *
     * @param label the label to draw the poster on
     * @param posterPath the path supplied with the title
     */
    public static void loadInto(JLabel label, String posterPath) {
        if (posterPath == null || posterPath.isBlank()) {
            return;
        }

        final ImageIcon cached = CACHE.get(posterPath);
        if (cached != null) {
            label.setIcon(cached);
            return;
        }

        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                final Image image = ImageIO.read(
                        URI.create(IMAGE_BASE + THUMBNAIL_SIZE + posterPath).toURL());
                return new ImageIcon(image.getScaledInstance(
                        POSTER_WIDTH, POSTER_HEIGHT, Image.SCALE_SMOOTH));
            }

            @Override
            protected void done() {
                try {
                    final ImageIcon icon = get();
                    CACHE.put(posterPath, icon);
                    label.setIcon(icon);
                }
                catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
                catch (Exception exception) {
                    // A missing or unreadable poster is not worth surfacing;
                    // the row still reads fine with its placeholder.
                    label.setIcon(null);
                }
            }
        }.execute();
    }
}
