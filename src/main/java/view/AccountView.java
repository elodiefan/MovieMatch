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
import javax.swing.JTextField;

import interface_adapter.account.AccountController;
import interface_adapter.account.AccountState;
import interface_adapter.account.AccountViewModel;

/**
 * The View for when the user wants to delete their account.
 */
public class AccountView extends JPanel implements PropertyChangeListener {

    private final String viewName = "user account";
    private final AccountViewModel accountViewModel;
    private AccountController accountController;

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

    public AccountView(AccountViewModel accountViewModel) {
        this.accountViewModel = accountViewModel;
        this.accountViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(AccountViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel profilePanel = new JPanel();
        username = new JLabel();
        displayName = new JLabel();
        profilePanel.add(username);
        profilePanel.add(displayName);

        final JPanel listOptionsPanel = new JPanel();
        watchlistButton = new JButton(AccountViewModel.WATCHLIST_BUTTON);
        watchHistoryButton = new JButton(AccountViewModel.WATCH_HISTORY_BUTTON);
        reviewsButton = new JButton(AccountViewModel.REVIEWS_BUTTON);
        blockedUsersButton = new JButton(AccountViewModel.BLOCKED_USERS_BUTTON);
        listOptionsPanel.add(watchlistButton);
        listOptionsPanel.add(watchHistoryButton);
        listOptionsPanel.add(reviewsButton);
        listOptionsPanel.add(blockedUsersButton);

        final JPanel accountOptionsPanel = new JPanel();
        customizeButton = new JButton(AccountViewModel.CUSTOMIZE_BUTTON);
        logoutButton = new JButton(AccountViewModel.LOGOUT_BUTTON);
        resetPasswordButton = new JButton(AccountViewModel.RESET_PASSWORD_BUTTON);
        deleteAccountButton = new JButton(AccountViewModel.DELETE_ACCOUNT_BUTTON);
        accountOptionsPanel.add(customizeButton);
        accountOptionsPanel.add(logoutButton);
        accountOptionsPanel.add(resetPasswordButton);
        accountOptionsPanel.add(deleteAccountButton);

        watchlistButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // not implemented yet
                    }
                }
        );

        watchHistoryButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // not implemented yet
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
                        // not implemented yet
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

//        logoutButton.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        accountController.switchToLogOutConfirmView();
//                    }
//                }
//        );

        resetPasswordButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        accountController.switchToResetPasswordView();
                    }
                }
        );

        deleteAccountButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        accountController.switchToDeleteAccountView();
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
            final AccountState state = (AccountState) evt.getNewValue();
            username.setText(state.getUsername());
            displayName.setText(state.getDisplayName());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setAccountController(AccountController accountController) {
        this.accountController = accountController;
    }
}
