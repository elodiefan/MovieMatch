package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interface_adapter.recommendation.RecommendationRow;

/**
 * One suggested title: its poster, name, genre and the reason it was picked.
 * Shared by the home page strip and the full recommendation screen so both
 * stay identical, and so a change to how a suggestion reads happens once.
 */
public class RecommendationRowPanel extends JPanel {

    private static final int GAP = 10;

    /**
     * Roughly how many characters of explanation belong on a line.
     * A wrapped label needs a width in pixels, so it is worked out from the
     * text size rather than fixed, or the line length changes every time the
     * slider does.
     */
    private static final int EXPLANATION_CHARACTERS = 26;

    public RecommendationRowPanel(RecommendationRow media) {
        final JLabel poster = new JLabel("", SwingConstants.CENTER);
        // Reserved up front so rows do not jump about as artwork arrives.
        poster.setPreferredSize(new Dimension(
                PosterLoader.POSTER_WIDTH, PosterLoader.POSTER_HEIGHT));
        poster.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        PosterLoader.loadInto(poster, media.getPosterPath());

        // Held at the top, so a row whose text runs longer than the poster
        // leaves the gap underneath rather than floating the artwork.
        final JPanel posterHolder = new JPanel(new BorderLayout());
        posterHolder.add(poster, BorderLayout.NORTH);

        final JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBorder(BorderFactory.createEmptyBorder(0, GAP, 0, 0));

        final JLabel title = new JLabel(media.getTitle()
                + " (" + media.getReleaseYear() + ")");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        text.add(title);

        final JLabel genre = new JLabel(media.getPrimaryGenre());
        genre.setForeground(UITheme.MUTED_TEXT);
        genre.setAlignmentX(Component.LEFT_ALIGNMENT);
        text.add(genre);

        // Only present once Gemini has had a look; the deterministic ranking
        // alone leaves it blank, which is a valid result rather than a fault.
        if (media.getExplanation() != null && !media.getExplanation().isBlank()) {
            final int wrapWidth = UITheme.baseFontSize() * EXPLANATION_CHARACTERS;
            final JLabel why = new JLabel("<html><body style='width:" + wrapWidth + "px'>"
                    + media.getExplanation() + "</body></html>");
            why.setForeground(UITheme.MUTED_TEXT);
            why.setAlignmentX(Component.LEFT_ALIGNMENT);
            text.add(why);
        }

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(GAP / 2, 0, GAP / 2, 0));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(posterHolder, BorderLayout.WEST);
        this.add(text, BorderLayout.CENTER);
    }

    /**
     * Keeps the row the height it actually needs.
     * The lists these sit in hand any spare height to whichever rows will
     * accept it. Left alone that stretches every suggestion down a tall window
     * and strands the poster in an over-sized box. Width still fills, so the
     * explanation has the room it needs.
     *
     * @return the maximum size
     */
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height);
    }
}
