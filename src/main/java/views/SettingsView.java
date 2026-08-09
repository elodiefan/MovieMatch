package views;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

import interface_adapter.settings.SettingsController;
import interface_adapter.settings.SettingsState;
import interface_adapter.settings.SettingsViewModel;

/**
 * The View for changing display settings.
 */
public class SettingsView extends JPanel implements PropertyChangeListener {

    private static final int SLIDER_TICK_SPACING = 5;
    private static final int HINT_INDENT = 26;

    private final String viewName = SettingsViewModel.VIEW_NAME;
    private final SettingsViewModel settingsViewModel;
    private SettingsController settingsController;

    private final JCheckBox darkModeToggle;
    private final JSlider textSizeSlider;
    private final JLabel textSizeValue;
    private final JCheckBox adultContentToggle;

    /**
     * The panel holding every screen, so a change reaches all of them.
     */
    private Component appearanceRoot;
    private Runnable backHandler;

    public SettingsView(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;
        this.settingsViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SettingsViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        darkModeToggle = new JCheckBox(SettingsViewModel.DARK_MODE_LABEL);
        final JPanel darkModePanel = new JPanel();
        darkModePanel.add(darkModeToggle);

        textSizeSlider = new JSlider(SettingsViewModel.MIN_TEXT_SIZE,
                SettingsViewModel.MAX_TEXT_SIZE,
                SettingsViewModel.DEFAULT_TEXT_SIZE);
        textSizeSlider.setMajorTickSpacing(SLIDER_TICK_SPACING);
        textSizeSlider.setMinorTickSpacing(1);
        textSizeSlider.setPaintTicks(true);
        textSizeSlider.setPaintLabels(true);
        textSizeValue = new JLabel(String.valueOf(SettingsViewModel.DEFAULT_TEXT_SIZE));

        final JPanel textSizePanel = new JPanel();
        textSizePanel.add(new JLabel(SettingsViewModel.TEXT_SIZE_LABEL));
        textSizePanel.add(textSizeSlider);
        textSizePanel.add(textSizeValue);

        // Unchecked to begin with, so nothing adult is asked for until it is
        // deliberately turned on.
        adultContentToggle = new JCheckBox(SettingsViewModel.ADULT_CONTENT_LABEL);
        adultContentToggle.setSelected(false);

        final JLabel adultContentHint = new JLabel(SettingsViewModel.ADULT_CONTENT_HINT);
        adultContentHint.setForeground(UiTheme.MUTED_TEXT);
        adultContentHint.setBorder(
                BorderFactory.createEmptyBorder(0, HINT_INDENT, 0, 0));

        final JPanel adultContentPanel = new JPanel();
        adultContentPanel.setLayout(new BoxLayout(adultContentPanel, BoxLayout.Y_AXIS));
        adultContentToggle.setAlignmentX(Component.LEFT_ALIGNMENT);
        adultContentHint.setAlignmentX(Component.LEFT_ALIGNMENT);
        adultContentPanel.add(adultContentToggle);
        adultContentPanel.add(adultContentHint);

        final JButton backButton = new JButton(SettingsViewModel.BACK_BUTTON_LABEL);
        final JPanel backPanel = new JPanel();
        backPanel.add(backButton);

        darkModeToggle.addActionListener(event -> applyChoices());
        adultContentToggle.addActionListener(event -> applyChoices());
        // Only act once the drag finishes, since restyling every screen on each
        // intermediate value makes the slider crawl.
        textSizeSlider.addChangeListener(event -> {
            if (!textSizeSlider.getValueIsAdjusting()) {
                applyChoices();
            }
            textSizeValue.setText(String.valueOf(textSizeSlider.getValue()));
        });
        backButton.addActionListener(event -> {
            if (backHandler != null) {
                backHandler.run();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(darkModePanel);
        this.add(textSizePanel);
        this.add(adultContentPanel);
        this.add(backPanel);
    }

    private void applyChoices() {
        settingsController.execute(darkModeToggle.isSelected(), textSizeSlider.getValue(),
                adultContentToggle.isSelected());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SettingsState state = (SettingsState) evt.getNewValue();

        textSizeValue.setText(String.valueOf(state.getTextSize()));
        adultContentToggle.setSelected(state.isAllowAdultContent());

        if (appearanceRoot != null) {
            // Restyling rebuilds every component's UI delegate, including the
            // slider and checkbox that started this. Doing that inside their own
            // event handler pulls the delegate out from under them and the
            // handler then fails on a null reference, so it waits for the
            // current event to finish first.
            final boolean darkMode = state.isDarkMode();
            final int textSize = state.getTextSize();
            SwingUtilities.invokeLater(
                    () -> UiTheme.applyAppearance(appearanceRoot, darkMode, textSize));
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public void setBackHandler(Runnable backHandler) {
        this.backHandler = backHandler;
    }

    /**
     * Sets the component a settings change should restyle,
     * which is the panel holding every screen rather than this one.
     *
     * @param appearanceRoot the appearance root
     */
    public void setAppearanceRoot(Component appearanceRoot) {
        this.appearanceRoot = appearanceRoot;
    }
}
