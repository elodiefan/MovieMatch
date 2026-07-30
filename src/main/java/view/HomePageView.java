package view;

import interface_adapter.home_page.HomePageController;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the homepage.
 */

public class HomePageView extends JPanel implements PropertyChangeListener {

    private final String viewName = "homepage";
    private final HomePageViewModel homePageViewModel;
    private HomePageController homePageController;

    private final JList listOfRecommendations;
    private final JLabel recommendationsHeader;

    private final JButton searchButton;
    private final JButton accountButton;

    public HomePageView(HomePageViewModel homePageViewModel) {
        this.homePageViewModel = homePageViewModel;
        this.homePageViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(HomePageViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

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
                    public void actionPerformed(ActionEvent e) {
                        homePageController.switchToSearchView();
                    }
                }
        );

        accountButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        homePageController.switchToAccountView();
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(recommendationsPanel);
        this.add(buttons);
    }

    // TODO: Implement if program allows for user to be brought to media page when click on recommendations???
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }

    public void setHomePageController(HomePageController homePageController) {
        this.homePageController = homePageController;
    }
}
