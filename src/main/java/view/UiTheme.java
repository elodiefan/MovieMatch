package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The application's look and feel.
 * <p>
 * Swing bakes colours and fonts into a component when it is constructed, so
 * everything here has to be set before the first view exists. {@link #install()}
 * is therefore called at the very top of {@code Main}, ahead of the AppBuilder.
 * <p>
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

    /** Padding inside a screen, so content is not flush against the window. */
    public static final int PAGE_PADDING = 18;

    private static final String PREFERRED_FONT = "Segoe UI";
    private static final int BASE_FONT_SIZE = 14;
    private static final int TITLE_FONT_SIZE = 22;

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

        // Nimbus derives most of its chrome from these few keys.
        UIManager.put("nimbusBase", ACCENT);
        UIManager.put("nimbusBlueGrey", new Color(0xC7, 0xCC, 0xD6));
        UIManager.put("control", BACKGROUND);
        UIManager.put("nimbusLightBackground", SURFACE);
        UIManager.put("text", TEXT);
        UIManager.put("nimbusFocus", new Color(0x6C, 0x8B, 0xC7));
        UIManager.put("nimbusSelectionBackground", new Color(0x4F, 0x6D, 0xA8));
        UIManager.put("nimbusSelection", new Color(0x4F, 0x6D, 0xA8));

        // Buttons ship with almost no padding, which is most of why the UI
        // looks cramped.
        UIManager.put("Button.contentMargins", new Insets(8, 16, 8, 16));
        UIManager.put("ToggleButton.contentMargins", new Insets(8, 16, 8, 16));
        UIManager.put("TextField.contentMargins", new Insets(6, 8, 6, 8));
        UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(BORDER));

        final Font base = baseFont(Font.PLAIN, BASE_FONT_SIZE);
        for (String key : new String[] {"Button.font", "Label.font", "TextField.font",
            "PasswordField.font", "CheckBox.font", "RadioButton.font", "ComboBox.font",
            "List.font", "Table.font", "TextArea.font", "TitledBorder.font", "Slider.font"}) {
            UIManager.put(key, base);
        }
    }

    /**
     * Builds a font, falling back to the platform default if the preferred
     * family is not installed.
     * @param style a {@link Font} style constant
     * @param size the point size
     * @return the font to use
     */
    public static Font baseFont(int style, int size) {
        return new Font(PREFERRED_FONT, style, size);
    }

    /**
     * Styles a label as a screen heading.
     * @param label the label to restyle
     * @return the same label, for chaining
     */
    public static JLabel asTitle(JLabel label) {
        label.setFont(baseFont(Font.BOLD, TITLE_FONT_SIZE));
        label.setForeground(TEXT);
        return label;
    }

    /**
     * Gives a screen breathing room and a consistent background.
     * <p>
     * Applied to each registered view rather than inside the views themselves,
     * so no individual screen has to know about it.
     * @param view the screen to pad
     */
    public static void padScreen(JComponent view) {
        view.setBorder(BorderFactory.createEmptyBorder(
                PAGE_PADDING, PAGE_PADDING, PAGE_PADDING, PAGE_PADDING));
        view.setBackground(BACKGROUND);
    }

    /**
     * Stops a vertical screen from spreading its content into the whole window.
     * <p>
     * A {@code BoxLayout} on the Y axis shares surplus height between its
     * children, and a {@code JPanel}'s maximum height is unbounded by default,
     * so on a large window every row drifts apart into bands of empty space.
     * Pinning each row to its preferred height and absorbing the remainder in a
     * single glue at the bottom keeps the screen together as the window grows.
     * <p>
     * Scroll panes are deliberately left unbounded: those are the parts that
     * should take the extra room.
     * @param view the screen to tidy
     */
    public static void tidyVerticalScreen(JPanel view) {
        if (!(view.getLayout() instanceof BoxLayout)) {
            return;
        }

        boolean hasStretchingChild = false;
        for (Component child : view.getComponents()) {
            if (child instanceof JScrollPane) {
                hasStretchingChild = true;
            }
            else if (child instanceof JComponent) {
                final JComponent row = (JComponent) child;
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, row.getPreferredSize().height));
                row.setAlignmentX(Component.CENTER_ALIGNMENT);

                // A label stretched to full width draws its text at the left,
                // while a FlowLayout row centres its contents. Without this the
                // same screen ends up with both alignments at once.
                if (row instanceof JLabel) {
                    ((JLabel) row).setHorizontalAlignment(SwingConstants.CENTER);
                }
            }
        }

        // With nothing that stretches, the surplus needs somewhere to go, or
        // BoxLayout hands it back to the rows we just pinned.
        if (!hasStretchingChild) {
            view.add(Box.createVerticalGlue());
        }
    }

    /**
     * Styles the first label on a screen as its heading.
     * <p>
     * Every screen here opens with a title label, but they were all left at the
     * default body size, so nothing read as a heading.
     * @param view the screen whose title should stand out
     */
    public static void styleFirstLabelAsTitle(Container view) {
        final JLabel first = findFirstLabel(view);
        if (first != null) {
            asTitle(first);
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
     * <p>
     * Views built before {@link #install()} took effect, or built by hand with
     * explicit colours, would otherwise keep the old defaults.
     * @param root the top of the tree to restyle
     */
    public static void applyTo(Component root) {
        if (root instanceof JPanel) {
            root.setBackground(BACKGROUND);
        }
        else if (root instanceof JLabel) {
            root.setForeground(TEXT);
        }
        else if (root instanceof JTextField) {
            ((JTextField) root).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        }
        else if (root instanceof JScrollPane) {
            ((JScrollPane) root).getViewport().setBackground(SURFACE);
            ((JScrollPane) root).setBorder(BorderFactory.createLineBorder(BORDER));
        }

        if (root instanceof JButton) {
            ((JButton) root).setFocusPainted(false);
        }

        if (root instanceof Container) {
            for (Component child : ((Container) root).getComponents()) {
                applyTo(child);
            }
        }
    }
}
