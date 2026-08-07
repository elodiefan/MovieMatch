package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import interface_adapter.home_page.HomePageController;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;

/**
 * The View for the homepage.
 */

public class HomePageView extends JPanel implements PropertyChangeListener {

    private static final int BUTTON_GAP = 10;
    private static final int HEADER_GAP = 6;

    private final String viewName = "home page";
    private final HomePageViewModel homePageViewModel;
    private HomePageController homePageController;

    private final JLabel greeting;

    private final JList listOfRecommendations;
    private final JLabel recommendationsHeader;
    private final JLabel recommendationsPlaceholder;
    private final JScrollPane recommendationsScroll;

    /** Swapped in for the placeholder once recommendations are wired up. */
    private HomeRecommendationsPanel recommendationsPanel;

    private final JButton searchButton;
    private final JButton findUsersButton;
    private final JButton accountButton;
    private final JButton settingsButton;

    public HomePageView(HomePageViewModel homePageViewModel) {
        this.homePageViewModel = homePageViewModel;
        this.homePageViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(HomePageViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // One line rather than "Hi," stacked above the name on its own row.
        greeting = new JLabel("", SwingConstants.CENTER);
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(title);
        header.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, HEADER_GAP)));
        header.add(greeting);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, UiTheme.ROW_GAP, 0));

        recommendationsHeader = new JLabel(HomePageViewModel.RECOMMENDATIONS_LABEL);
        // TODO: Populate list with recommendations and *maybe* when click on media brings you to media's page.
        listOfRecommendations = new JList();
        recommendationsPlaceholder = new JLabel(
                "Nothing here yet. Use Search to find something to watch.", SwingConstants.CENTER);

        // The list is empty until recommendations are implemented, so an empty
        // box in the middle of the page needs to say why it is empty.
        final JPanel recommendationsBody = new JPanel(new BorderLayout());
        recommendationsBody.add(recommendationsPlaceholder, BorderLayout.CENTER);

        recommendationsScroll = new JScrollPane(recommendationsBody);
        recommendationsScroll.setBorder(BorderFactory.createTitledBorder(
                HomePageViewModel.RECOMMENDATIONS_LABEL));

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, BUTTON_GAP, BUTTON_GAP));
        searchButton = new JButton(HomePageViewModel.SEARCH_BUTTON_LABEL);
        findUsersButton = new JButton(HomePageViewModel.FIND_USERS_BUTTON_LABEL);
        accountButton = new JButton(HomePageViewModel.ACCOUNT_BUTTON_LABEL);
        settingsButton = new JButton(HomePageViewModel.SETTINGS_BUTTON_LABEL);
        buttons.add(searchButton);
        buttons.add(findUsersButton);
        buttons.add(accountButton);
        buttons.add(settingsButton);

        settingsButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        homePageController.switchToSettingsView();
                    }
                }
        );

        // Search looks for media, Find Users looks for people. Separate views.
        searchButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        homePageController.switchToSearchView();
                    }
                }
        );

        findUsersButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        homePageController.switchToSearchUserView();
                    }
                }
        );

        accountButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(accountButton)) {
                            final HomePageState currentState = homePageViewModel.getState();

                            homePageController.switchToPersonalAccountView(currentState.getUsername(),
                                    currentState.getDisplayName());
                        }
                    }
                }
        );

        // BorderLayout so the recommendations area absorbs the window, instead
        // of the whole page collapsing to a cluster in the middle of the screen.
        this.setLayout(new BorderLayout());
        this.add(header, BorderLayout.NORTH);
        this.add(recommendationsScroll, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
    }

    // TODO: Implement if program allows for user to be brought to media page when click on recommendations???
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final HomePageState state = (HomePageState) evt.getNewValue();
        final String name = state.getUsername() == null ? "" : state.getUsername();
        greeting.setText("Hi, " + name);

        // Landing on the home page is what triggers a load; the panel itself
        // ignores repeat calls while one is already running.
        if (recommendationsPanel != null) {
            recommendationsPanel.loadFor(name);
        }
    }

    /**
     * Puts the live recommendation strip where the placeholder was.
     */
    public void setRecommendationsPanel(HomeRecommendationsPanel recommendationsPanel) {
        this.recommendationsPanel = recommendationsPanel;
        this.remove(recommendationsScroll);
        this.add(recommendationsPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomePageController(HomePageController homePageController) {
        this.homePageController = homePageController;
    }
}
