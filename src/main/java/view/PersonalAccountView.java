package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.personal_account.PersonalAccountController;
import interface_adapter.personal_account.PersonalAccountState;
import interface_adapter.personal_account.PersonalAccountViewModel;

/**
 * The View for when the user wants to delete their account.
 */
public class PersonalAccountView extends JPanel implements PropertyChangeListener {

    private final String viewName = "personal account";
    private final PersonalAccountViewModel personalAccountViewModel;
    private PersonalAccountController personalAccountController;

    private final JLabel welcomeLabel;
    private final JLabel username;
    private final JLabel displayName;
    private final JButton customizeButton;
    private final JButton logoutButton;
    private final JButton resetPasswordButton;
    private final JButton deleteAccountButton;
    private final JButton watchlistButton;
    private final JButton watchHistoryButton;
    private final JButton reviewsButton;
    private final JButton blockedUsersButton;
    private final JButton backButton;

    public PersonalAccountView(PersonalAccountViewModel personalAccountViewModel) {
        this.personalAccountViewModel = personalAccountViewModel;
        this.personalAccountViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(PersonalAccountViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel profilePanel = new JPanel();
        welcomeLabel = new JLabel();
        username = new JLabel();
        displayName = new JLabel();
        profilePanel.add(username);
        profilePanel.add(displayName);

        final JPanel listOptionsPanel = new JPanel();
        watchlistButton = new JButton(PersonalAccountViewModel.WATCHLIST_BUTTON);
        watchHistoryButton = new JButton(PersonalAccountViewModel.WATCH_HISTORY_BUTTON);
        reviewsButton = new JButton(PersonalAccountViewModel.REVIEWS_BUTTON);
        blockedUsersButton = new JButton(PersonalAccountViewModel.BLOCKED_USERS_BUTTON);
        listOptionsPanel.add(watchlistButton);
        listOptionsPanel.add(watchHistoryButton);
        listOptionsPanel.add(reviewsButton);
        listOptionsPanel.add(blockedUsersButton);

        final JPanel accountOptionsPanel = new JPanel();
        backButton = new JButton(PersonalAccountViewModel.BACK_BUTTON);
        customizeButton = new JButton(PersonalAccountViewModel.CUSTOMIZE_BUTTON);
        logoutButton = new JButton(PersonalAccountViewModel.LOGOUT_BUTTON);
        resetPasswordButton = new JButton(PersonalAccountViewModel.RESET_PASSWORD_BUTTON);
        deleteAccountButton = new JButton(PersonalAccountViewModel.DELETE_ACCOUNT_BUTTON);
        accountOptionsPanel.add(backButton);
        accountOptionsPanel.add(customizeButton);
        accountOptionsPanel.add(logoutButton);
        accountOptionsPanel.add(resetPasswordButton);
        accountOptionsPanel.add(deleteAccountButton);

        watchlistButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final PersonalAccountState state = personalAccountViewModel.getState();
                        personalAccountController.switchToWatchlistView(state.getUsername(), state.getDisplayName());
                    }
                }
        );

        watchHistoryButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final PersonalAccountState state = personalAccountViewModel.getState();
                        personalAccountController.switchToWatchHistoryView(state.getUsername(), state.getDisplayName());
                    }
                }
        );

//        reviewsButton.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        accountController.switchToReviewsView();
//                    }
//                }
//        );

        blockedUsersButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final PersonalAccountState state = personalAccountViewModel.getState();
                        personalAccountController.switchToBlockedUsersView(state.getUsername(), state.getDisplayName());
                    }
                }
        );

        backButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        personalAccountController.switchToHomePageView();
                    }
                }
        );

        customizeButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // not implemented yet
                    }
                }
        );

        logoutButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final PersonalAccountState state = personalAccountViewModel.getState();
                        personalAccountController.switchToLogoutConfirmView(state.getUsername());
                    }
                }
        );

        resetPasswordButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        personalAccountController.switchToResetPasswordView();
                    }
                }
        );

        deleteAccountButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        personalAccountController.switchToDeleteAccountView();
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(profilePanel);
        this.add(listOptionsPanel);
        this.add(accountOptionsPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final PersonalAccountState state = (PersonalAccountState) evt.getNewValue();
            welcomeLabel.setText(personalAccountViewModel.TITLE_LABEL);
            username.setText(personalAccountViewModel.USERNAME_LABEL + state.getUsername());
            displayName.setText(personalAccountViewModel.DISPLAY_NAME_LABEL + state.getDisplayName());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setPersonalAccountController(PersonalAccountController personalAccountController) {
        this.personalAccountController = personalAccountController;
    }
}
