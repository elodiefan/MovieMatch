package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * A centred "working on it" panel, shown while a screen waits on the network.
 *
 * Pure presentation with no controller and no state of its own, so any screen
 * can drop one in without pulling anything else along.
 *
 * The bar is indeterminate on purpose. Recommendations take a couple of seconds
 * and there is no honest way to report progress through them, so a bar that
 * pretends to know a percentage would be lying. A moving one says "still
 * working" without claiming more than that.
 */
public class LoadingPanel extends JPanel {

    private static final int BAR_WIDTH = 260;
    private static final int BAR_HEIGHT = 8;
    private static final int GAP = 14;
    private static final int MESSAGE_SIZE = 15;

    private final JLabel message;
    private final JLabel detail;
    private final JProgressBar bar;

    public LoadingPanel(String initialMessage) {
        message = new JLabel(initialMessage, SwingConstants.CENTER);
        message.setFont(UiTheme.baseFont(Font.BOLD, MESSAGE_SIZE));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        detail = new JLabel(" ", SwingConstants.CENTER);
        detail.setForeground(UiTheme.MUTED_TEXT);
        detail.setAlignmentX(Component.CENTER_ALIGNMENT);

        bar = new JProgressBar();
        bar.setIndeterminate(true);
        bar.setPreferredSize(new Dimension(BAR_WIDTH, BAR_HEIGHT));
        bar.setMaximumSize(new Dimension(BAR_WIDTH, BAR_HEIGHT));
        bar.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(Box.createVerticalGlue());
        column.add(message);
        column.add(Box.createRigidArea(new Dimension(0, GAP)));
        column.add(bar);
        column.add(Box.createRigidArea(new Dimension(0, GAP)));
        column.add(detail);
        column.add(Box.createVerticalGlue());
        column.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

        this.setLayout(new BorderLayout());
        this.add(column, BorderLayout.CENTER);
    }

    /**
     * Changes the headline while still loading.
     */
    public void setMessage(String text) {
        message.setText(text);
    }

    /**
     * Sets the smaller line under the bar, for saying what is happening.
     */
    public void setDetail(String text) {
        if (text == null || text.isBlank()) {
            detail.setText(" ");
        }
        else {
            detail.setText(text);
        }
    }

    /**
     * Stops the animation when hidden, so an off-screen panel is not repainting
     * forever in the background.
     */
    public void setAnimating(boolean animating) {
        bar.setIndeterminate(animating);
    }
}
