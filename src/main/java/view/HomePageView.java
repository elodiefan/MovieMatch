package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import interface_adapter.home_page.HomePageController;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;

/**
 * The View for the homepage.
 */

public class HomePageView extends JPanel implements PropertyChangeListener {

    private final String viewName = "home page";
    private final HomePageViewModel homePageViewModel;
    private HomePageController homePageController;

    private final JLabel username;

    private final JList listOfRecommendations;
    private final JLabel recommendationsHeader;

    private final JButton searchButton;
    private final JButton accountButton;

    public HomePageView(HomePageViewModel homePageViewModel) {
        this.homePageViewModel = homePageViewModel;
        this.homePageViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(HomePageViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameInfo = new JLabel("Hi, ");
        username = new JLabel();

        final JPanel recommendationsPanel = new JPanel();
        recommendationsHeader = new JLabel(HomePageViewModel.RECOMMENDATIONS_LABEL);
        // TODO: Populate list with recommendations and *maybe* when click on media brings you to media's page.
        listOfRecommendations = new JList();
        recommendationsPanel.add(recommendationsHeader);
        recommendationsPanel.add(listOfRecommendations);

        final JPanel buttons = new JPanel();
        searchButton = new JButton(HomePageViewModel.SEARCH_BUTTON_LABEL);
        accountButton = new JButton(HomePageViewModel.ACCOUNT_BUTTON_LABEL);
        buttons.add(searchButton);
        buttons.add(accountButton);

        searchButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        homePageController.switchToSearchView();
                    }
                }
        );

        accountButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(accountButton)) {
                            final HomePageState currentState = homePageViewModel.getState();

                            homePageController.switchToAccountView(currentState.getUsername(), currentState.getDisplayName());
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(recommendationsPanel);
        this.add(buttons);
    }

    // TODO: Implement if program allows for user to be brought to media page when click on recommendations???
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final HomePageState state = (HomePageState) evt.getNewValue();
        username.setText(state.getUsername());
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomePageController(HomePageController homePageController) {
        this.homePageController = homePageController;
    }
}
