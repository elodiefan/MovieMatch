package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

/**
 * The application's look and feel.
 *
 * Swing bakes colours and fonts into a component when it is constructed, so
 * everything here has to be set before the first view exists. #install()
 * is therefore called at the very top of Main, ahead of the AppBuilder.
 *
 * The base is Nimbus, which ships with the JDK and looks considerably less dated
 * than the default Metal theme. The palette below then retints it.
 */
public final class UiTheme {

    /** Page background. */
    public static final Color BACKGROUND = new Color(0xF4, 0xF6, 0xF9);

    /** Panels and fields that sit on top of the background. */
    public static final Color SURFACE = new Color(0xFF, 0xFF, 0xFF);

    /** Body text. */
    public static final Color TEXT = new Color(0x1F, 0x24, 0x30);

    /** Secondary text, for hints and captions. */
    public static final Color MUTED_TEXT = new Color(0x66, 0x6E, 0x7D);

    /** The main accent, used for chrome and selection. */
    public static final Color ACCENT = new Color(0x3D, 0x4B, 0x66);

    /** Hairline separators and borders. */
    public static final Color BORDER = new Color(0xD8, 0xDD, 0xE5);

    /** Dark page background. */
    public static final Color DARK_BACKGROUND = new Color(0x24, 0x28, 0x30);

    /** Dark panels and fields. */
    public static final Color DARK_SURFACE = new Color(0x2E, 0x33, 0x3D);

    /** Text on a dark background. */
    public static final Color DARK_TEXT = new Color(0xE6, 0xE9, 0xEF);

    /** The accent used to tint dark chrome. */
    public static final Color DARK_ACCENT = new Color(0x54, 0x63, 0x82);

    /** Separators on a dark background. */
    public static final Color DARK_BORDER = new Color(0x3F, 0x46, 0x52);

    /** Padding inside a screen, so content is not flush against the window. */
    public static final int PAGE_PADDING = 18;

    /**
     * How wide the content column is allowed to get.
     *
     * Rows stretched to the full window look lost on a maximised screen, where
     * a few controls end up marooned in the middle of a very wide page. Holding
     * them to a column keeps a screen looking the same whatever the window does.
     */
    public static final int CONTENT_MAX_WIDTH = 720;

    /** Vertical breathing room between rows of a screen. */
    public static final int ROW_GAP = 12;

    /** Marks a row this class has already sized, so it can be resized later. */
    private static final String PINNED = "UiTheme.pinnedRow";

    private static final String PREFERRED_FONT = "Segoe UI";
    private static final int BASE_FONT_SIZE = 15;
    private static final int TITLE_FONT_SIZE = 28;

    /** Kept so a text size change can rescale the heading with everything else. */
    private static final String[] FONT_KEYS = {"Button.font", "Label.font", "TextField.font",
        "PasswordField.font", "CheckBox.font", "RadioButton.font", "ComboBox.font",
        "List.font", "Table.font", "TextArea.font", "TitledBorder.font", "Slider.font"};

    private UiTheme() {
    }

    /**
     * Installs the look and feel. Safe to call once, before any UI is built.
     */
    public static void install() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException
               | UnsupportedLookAndFeelException exception) {
            // Nimbus is optional; the default look and feel still works.
        }

        putPalette(false);
        putFontSize(BASE_FONT_SIZE);

        // Buttons ship with almost no padding, which is most of why the UI
        // looks cramped.
        UIManager.put("Button.contentMargins", new Insets(8, 16, 8, 16));
        UIManager.put("ToggleButton.contentMargins", new Insets(8, 16, 8, 16));
        UIManager.put("TextField.contentMargins", new Insets(6, 8, 6, 8));
    }

    /**
     * Switches the whole application between the light and dark palettes and
     * changes its text size.
     *
     * Nimbus paints components through its own delegates rather than from the
     * background colour of each component, so setting colours by hand only
     * reaches plain panels and labels. Replacing the palette keys and then
     * rebuilding the delegates with updateComponentTreeUI is what makes
     * buttons, fields and scroll bars change too.
     */
    public static void applyAppearance(Component root, boolean darkMode, int textSize) {
        putPalette(darkMode);
        putFontSize(textSize);

        SwingUtilities.updateComponentTreeUI(root);

        // updateComponentTreeUI only resizes fonts the look and feel installed
        // itself, so anything a screen set by hand keeps its old size. This
        // reaches the rest.
        applyFontSize(root, textSize);

        // Rebuilding the delegates drops the explicit colours and borders set
        // when the screens were first registered, so they go back on here.
        applyTo(root, darkMode);
        if (root instanceof Container) {
            for (Component card : ((Container) root).getComponents()) {
                if (card instanceof JComponent) {
                    padScreen((JComponent) card);
                }
                styleFirstLabelAsTitle((Container) card, textSize);
            }
        }
        root.setBackground(darkMode ? DARK_BACKGROUND : BACKGROUND);

        // Larger text means taller rows, and the layout pass pinned each row to
        // the height it had at the old size. Left alone, the content clips.
        repinRows(root);

        root.revalidate();
        root.repaint();
    }

    private static void putPalette(boolean darkMode) {
        if (darkMode) {
            UIManager.put("nimbusBase", DARK_ACCENT);
            UIManager.put("nimbusBlueGrey", new Color(0x44, 0x4B, 0x59));
            UIManager.put("control", DARK_BACKGROUND);
            UIManager.put("nimbusLightBackground", DARK_SURFACE);
            UIManager.put("text", DARK_TEXT);
            UIManager.put("info", DARK_SURFACE);
            UIManager.put("nimbusFocus", new Color(0x7E, 0x9A, 0xD0));
            UIManager.put("nimbusSelectionBackground", new Color(0x45, 0x5C, 0x8A));
            UIManager.put("nimbusSelection", new Color(0x45, 0x5C, 0x8A));
            UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(DARK_BORDER));
        }
        else {
            UIManager.put("nimbusBase", ACCENT);
            UIManager.put("nimbusBlueGrey", new Color(0xC7, 0xCC, 0xD6));
            UIManager.put("control", BACKGROUND);
            UIManager.put("nimbusLightBackground", SURFACE);
            UIManager.put("text", TEXT);
            UIManager.put("info", SURFACE);
            UIManager.put("nimbusFocus", new Color(0x6C, 0x8B, 0xC7));
            UIManager.put("nimbusSelectionBackground", new Color(0x4F, 0x6D, 0xA8));
            UIManager.put("nimbusSelection", new Color(0x4F, 0x6D, 0xA8));
            UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(BORDER));
        }
    }

    private static void putFontSize(int textSize) {
        // These have to be FontUIResource, not Font. Swing only replaces a
        // component's font when the existing one is a UIResource; a plain Font
        // reads as "the application chose this", so the second size change
        // would silently do nothing.
        final Font base = new FontUIResource(baseFont(Font.PLAIN, textSize));
        for (String key : FONT_KEYS) {
            UIManager.put(key, base);
        }
        // Nimbus builds its own fonts from this one key and largely ignores the
        // per-component keys above.
        UIManager.put("defaultFont", base);
    }

    /**
     * Sets the font size on a component and everything inside it.
     *
     * The look and feel only reaches components it created the font for, which
     * leaves anything built by hand at its original size. Walking the tree is
     * what makes a size change reach every screen rather than just some of them.
     */
    private static void applyFontSize(Component root, int textSize) {
        final Font current = root.getFont();
        if (current != null) {
            // deriveFont keeps bold and italic, so emphasis survives a resize.
            root.setFont(new FontUIResource(current.deriveFont((float) textSize)));
        }

        if (root instanceof JSlider) {
            // The tick labels live in a table rather than the component tree.
            final JSlider slider = (JSlider) root;
            if (slider.getLabelTable() != null) {
                final Enumeration<?> labels = slider.getLabelTable().elements();
                while (labels.hasMoreElements()) {
                    final Object value = labels.nextElement();
                    if (value instanceof Component) {
                        final Component label = (Component) value;
                        label.setFont(new FontUIResource(
                                label.getFont().deriveFont((float) textSize)));
                    }
                }
            }
        }

        if (root instanceof JComponent && ((JComponent) root).getBorder() instanceof TitledBorder) {
            final TitledBorder titled = (TitledBorder) ((JComponent) root).getBorder();
            if (titled.getTitleFont() != null) {
                titled.setTitleFont(titled.getTitleFont().deriveFont((float) textSize));
            }
        }

        if (root instanceof Container) {
            for (Component child : ((Container) root).getComponents()) {
                applyFontSize(child, textSize);
            }
        }
    }

    /**
     * Refreshes the pinned row heights after the text size has changed.
     */
    private static void repinRows(Component root) {
        if (root instanceof JComponent) {
            final JComponent component = (JComponent) root;
            if (Boolean.TRUE.equals(component.getClientProperty(PINNED))) {
                component.setMaximumSize(
                        new Dimension(CONTENT_MAX_WIDTH, component.getPreferredSize().height));
            }
        }
        if (root instanceof Container) {
            for (Component child : ((Container) root).getComponents()) {
                repinRows(child);
            }
        }
    }

    /**
     * Builds a font, falling back to the platform default if the preferred
     * family is not installed.
     */
    public static Font baseFont(int style, int size) {
        return new Font(PREFERRED_FONT, style, size);
    }

    /**
     * Styles a label as a screen heading.
     */
    public static JLabel asTitle(JLabel label) {
        return asTitle(label, BASE_FONT_SIZE);
    }

    /**
     * Styles a label as a screen heading, scaled to the current text size.
     */
    public static JLabel asTitle(JLabel label, int textSize) {
        final int headingSize = TITLE_FONT_SIZE + (textSize - BASE_FONT_SIZE);
        label.setFont(baseFont(Font.BOLD, headingSize));
        label.setForeground(darkModeActive() ? DARK_TEXT : TEXT);
        return label;
    }

    /**
     * Reports whether the dark palette is currently installed.
     */
    public static boolean darkModeActive() {
        return DARK_BACKGROUND.equals(UIManager.get("control"));
    }

    /**
     * Gives a screen breathing room and a consistent background.
     *
     * Applied to each registered view rather than inside the views themselves,
     * so no individual screen has to know about it.
     */
    public static void padScreen(JComponent view) {
        view.setBorder(BorderFactory.createEmptyBorder(
                PAGE_PADDING, PAGE_PADDING, PAGE_PADDING, PAGE_PADDING));
        view.setBackground(darkModeActive() ? DARK_BACKGROUND : BACKGROUND);
    }

    /**
     * Stops a vertical screen from spreading its content into the whole window.
     *
     * A BoxLayout on the Y axis shares surplus height between its
     * children, and a JPanel's maximum height is unbounded by default,
     * so on a large window every row drifts apart into bands of empty space.
     * Pinning each row to its preferred height and absorbing the remainder in a
     * single glue at the bottom keeps the screen together as the window grows.
     *
     * Scroll panes are deliberately left unbounded: those are the parts that
     * should take the extra room.
     */
    public static void tidyVerticalScreen(JPanel view) {
        if (!(view.getLayout() instanceof BoxLayout)) {
            return;
        }

        boolean hasStretchingChild = false;
        final List<Component> rows = new ArrayList<>();
        for (Component child : view.getComponents()) {
            rows.add(child);
            if (child instanceof JScrollPane) {
                hasStretchingChild = true;
            }
            else if (child instanceof JComponent) {
                final JComponent row = (JComponent) child;
                row.putClientProperty(PINNED, Boolean.TRUE);
                row.setMaximumSize(new Dimension(CONTENT_MAX_WIDTH, row.getPreferredSize().height));
                row.setAlignmentX(Component.CENTER_ALIGNMENT);

                // A row held to the content width draws its text at its own left
                // edge, while a FlowLayout row centres its contents. Without this
                // the same screen ends up with both alignments at once.
                if (row instanceof JLabel) {
                    ((JLabel) row).setHorizontalAlignment(SwingConstants.CENTER);
                }
            }
        }

        // Rebuild the screen with gaps between the rows. Adding struts as we go
        // would shift the indexes we are iterating over.
        view.removeAll();
        boolean first = true;
        for (Component row : rows) {
            if (!first) {
                view.add(Box.createRigidArea(new Dimension(0, ROW_GAP)));
            }
            view.add(row);
            first = false;
        }

        // With nothing that stretches, the surplus has to go somewhere or
        // BoxLayout hands it back to the rows we just pinned. A larger share
        // below than above sits the content a little high, which reads better
        // than dead centre on a tall window.
        if (!hasStretchingChild) {
            view.add(Box.createVerticalGlue(), 0);
            view.add(Box.createVerticalGlue());
            view.add(Box.createVerticalGlue());
        }
    }

    /**
     * Styles the first label on a screen as its heading.
     *
     * Every screen here opens with a title label, but they were all left at the
     * default body size, so nothing read as a heading.
     */
    public static void styleFirstLabelAsTitle(Container view) {
        styleFirstLabelAsTitle(view, BASE_FONT_SIZE);
    }

    /**
     * Styles the first label on a screen as its heading, at the current text size.
     */
    public static void styleFirstLabelAsTitle(Container view, int textSize) {
        final JLabel first = findFirstLabel(view);
        if (first != null) {
            asTitle(first, textSize);
        }
    }

    private static JLabel findFirstLabel(Container container) {
        JLabel result = null;
        for (Component child : container.getComponents()) {
            if (result == null && child instanceof JLabel
                    && !((JLabel) child).getText().isEmpty()) {
                result = (JLabel) child;
            }
            else if (result == null && child instanceof Container) {
                result = findFirstLabel((Container) child);
            }
        }
        return result;
    }

    /**
     * Applies the palette to a component and everything inside it.
     *
     * Views built before #install() took effect, or built by hand with
     * explicit colours, would otherwise keep the old defaults.
     */
    public static void applyTo(Component root) {
        applyTo(root, darkModeActive());
    }

    /**
     * Applies one of the two palettes to a component and everything inside it.
     */
    public static void applyTo(Component root, boolean darkMode) {
        final Color background = darkMode ? DARK_BACKGROUND : BACKGROUND;
        final Color surface = darkMode ? DARK_SURFACE : SURFACE;
        final Color foreground = darkMode ? DARK_TEXT : TEXT;
        final Color border = darkMode ? DARK_BORDER : BORDER;

        if (root instanceof JPanel) {
            root.setBackground(background);
        }
        else if (root instanceof JLabel) {
            root.setForeground(foreground);
        }
        else if (root instanceof JTextArea) {
            root.setBackground(surface);
            root.setForeground(foreground);
            // The caret is painted in the old foreground and vanishes otherwise.
            ((JTextArea) root).setCaretColor(foreground);
        }
        else if (root instanceof JTextField) {
            root.setBackground(surface);
            root.setForeground(foreground);
            ((JTextField) root).setCaretColor(foreground);
            ((JTextField) root).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(border),
                    BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        }
        else if (root instanceof JScrollPane) {
            final JScrollPane scrollPane = (JScrollPane) root;
            scrollPane.getViewport().setBackground(surface);
            // A titled border is the screen saying what the box contains, so
            // replacing it with a plain line would throw that caption away.
            if (scrollPane.getBorder() instanceof TitledBorder) {
                final TitledBorder titled = (TitledBorder) scrollPane.getBorder();
                titled.setTitleColor(foreground);
                titled.setBorder(BorderFactory.createLineBorder(border));
            }
            else {
                scrollPane.setBorder(BorderFactory.createLineBorder(border));
            }
        }

        if (root instanceof JCheckBox) {
            root.setBackground(background);
            root.setForeground(foreground);
        }

        if (root instanceof JComboBox) {
            // Nimbus keeps the combo box light, so dark text on it disappears.
            root.setBackground(surface);
            root.setForeground(foreground);
        }

        if (root instanceof JList) {
            root.setBackground(surface);
            root.setForeground(foreground);
        }

        if (root instanceof JButton) {
            ((JButton) root).setFocusPainted(false);
        }

        if (root instanceof Container) {
            for (Component child : ((Container) root).getComponents()) {
                applyTo(child, darkMode);
            }
        }
    }
}
