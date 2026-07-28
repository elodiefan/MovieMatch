package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
    private final JLabel password;
    private final JLabel premiumUpgrade;
    private final Jlabel customizeProfile;
    private final JLabel listsAvailable;

//    private final JLabel securityQuestionErrorField = new JLabel();
//

    public AccountView(AccountViewModel accountViewModel) {
        this.accountViewModel = accountViewModel;
        this.accountViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(AccountViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel noticePanel = new JPanel();
        accountNotice = new JLabel(accountViewModel.DESCRIPTION);
        username = new JLabel();
        noticePanel.add(accountNotice);
        noticePanel.add(username);

//        final JLabel securityQuestionTitle = new JLabel(AccountViewModel.SECURITY_TITLE);
//        securityQuestion = new JLabel();
//        final LabelTextPanel securityQuestionInfo = new LabelTextPanel(securityQuestion, securityQuestionInputField);
//
//        final JPanel buttons = new JPanel();
//        accountButton = new JButton(DeleteAccountViewModel.DELETE_ACCOUNT_BUTTON);
//        cancelButton = new JButton(DeleteAccountViewModel.CANCEL_BUTTON);
//        buttons.add(deleteAccountButton);

        deleteAccountButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(deleteAccountButton)) {
                        final DeleteAccountState currentState = deleteAccountViewModel.getState();

                        this.deleteAccountController.execute(
                                currentState.getUsername(),
                                currentState.getDisplayName(),
                                currentState.getPassword(),
                                currentState.getSecurityQuestion(),
                                currentState.getSecurityAnswer()
                        );
                    }
                }
        );

        cancelButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        deleteAccountController.switchToSignupView();
                    }
                }
        );

        addSecurityQuestionListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(noticePanel);
        this.add(securityQuestionTitle);
        this.add(securityQuestionInfo);
        this.add(securityQuestionErrorField);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final accountState state = (accountState) evt.getNewValue();
            username.setText(state.getUsername());
            securityQuestion.setText(state.getSecurityQuestion());
        }
        else if (evt.getPropertyName().equals("password")) {
            final AccountState state = (DeleteAccountState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "deleted account for " + state.getUsername());
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setAccountController(AccountController accountController) {
        this.accountController = accountController;
    }
}
