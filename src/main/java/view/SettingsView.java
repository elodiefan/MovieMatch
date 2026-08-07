package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import use_case.settings.SettingsInteractor;

/** The View for changing display settings. */
public class SettingsView extends JPanel implements PropertyChangeListener {

    private static final int SLIDER_TICK_SPACING = 4;

    private final String viewName = SettingsViewModel.VIEW_NAME;
    private final SettingsViewModel settingsViewModel;
    private SettingsController settingsController;

    private final JCheckBox darkModeToggle;
    private final JSlider textSizeSlider;
    private final JLabel textSizeValue;

    /** The panel holding every screen, so a change reaches all of them. */
    private Component appearanceRoot;

    public SettingsView(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;
        this.settingsViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SettingsViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        darkModeToggle = new JCheckBox(SettingsViewModel.DARK_MODE_LABEL);
        final JPanel darkModePanel = new JPanel();
        darkModePanel.add(darkModeToggle);

        textSizeSlider = new JSlider(SettingsInteractor.MIN_TEXT_SIZE,
                SettingsInteractor.MAX_TEXT_SIZE,
                SettingsInteractor.DEFAULT_TEXT_SIZE);
        textSizeSlider.setMajorTickSpacing(SLIDER_TICK_SPACING);
        textSizeSlider.setPaintTicks(true);
        textSizeSlider.setPaintLabels(true);
        textSizeValue = new JLabel(String.valueOf(SettingsInteractor.DEFAULT_TEXT_SIZE));

        final JPanel textSizePanel = new JPanel();
        textSizePanel.add(new JLabel(SettingsViewModel.TEXT_SIZE_LABEL));
        textSizePanel.add(textSizeSlider);
        textSizePanel.add(textSizeValue);

        final JButton backButton = new JButton(SettingsViewModel.BACK_BUTTON_LABEL);
        final JPanel backPanel = new JPanel();
        backPanel.add(backButton);

        darkModeToggle.addActionListener(event -> applyChoices());
        // Only act once the drag finishes, since restyling every screen on each
        // intermediate value makes the slider crawl.
        textSizeSlider.addChangeListener(event -> {
            if (!textSizeSlider.getValueIsAdjusting()) {
                applyChoices();
            }
            textSizeValue.setText(String.valueOf(textSizeSlider.getValue()));
        });
        backButton.addActionListener(event -> settingsController.switchToHomePageView());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(darkModePanel);
        this.add(textSizePanel);
        this.add(backPanel);
    }

    private void applyChoices() {
        settingsController.execute(darkModeToggle.isSelected(), textSizeSlider.getValue());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SettingsState state = (SettingsState) evt.getNewValue();

        textSizeValue.setText(String.valueOf(state.getTextSize()));

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

    /**
     * Sets the component a settings change should restyle, which is the panel holding every screen rather than this one.
     */
    public void setAppearanceRoot(Component appearanceRoot) {
        this.appearanceRoot = appearanceRoot;
    }
}
