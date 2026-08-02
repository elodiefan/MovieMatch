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

import interface_adapter.delete_account.DeleteAccountController;
import interface_adapter.delete_account.DeleteAccountState;
import interface_adapter.delete_account.DeleteAccountViewModel;

/**
 * The View for when the user wants to delete their account.
 */
public class DeleteAccountView extends JPanel implements PropertyChangeListener {

    private final String viewName = "delete account";
    private final DeleteAccountViewModel deleteAccountViewModel;
    private DeleteAccountController deleteAccountController;

    private final JLabel username;

    private final JLabel deleteAccountNotice;

    private final JLabel securityQuestion;
    private final JTextField securityQuestionInputField = new JTextField(15);

    private final JButton deleteAccountButton;
    private final JButton cancelButton;

    public DeleteAccountView(DeleteAccountViewModel deleteAccountViewModel) {
        this.deleteAccountViewModel = deleteAccountViewModel;
        this.deleteAccountViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(DeleteAccountViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel noticePanel = new JPanel();
        deleteAccountNotice = new JLabel(DeleteAccountViewModel.DESCRIPTION);
        username = new JLabel();
        noticePanel.add(deleteAccountNotice);
        noticePanel.add(username);

        final JPanel securityQuestionPanel = new JPanel();
        final JLabel securityQuestionTitle = new JLabel(DeleteAccountViewModel.SECURITY_TITLE);
        securityQuestion = new JLabel();
        securityQuestionPanel.add(securityQuestionTitle);
        securityQuestionPanel.add(securityQuestion);
        securityQuestionPanel.add(securityQuestionInputField);

        final JPanel buttons = new JPanel();
        deleteAccountButton = new JButton(DeleteAccountViewModel.DELETE_ACCOUNT_BUTTON);
        cancelButton = new JButton(DeleteAccountViewModel.CANCEL_BUTTON);
        buttons.add(deleteAccountButton);
        buttons.add(cancelButton);

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
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deleteAccountController.switchToAccountView();
                    }
                }
        );

        addSecurityQuestionListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(noticePanel);
        this.add(securityQuestionPanel);
        this.add(buttons);
    }

    private void addSecurityQuestionListener() {
        securityQuestionInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final DeleteAccountState currentState = deleteAccountViewModel.getState();
                currentState.setSecurityAnswer(securityQuestionInputField.getText());
                deleteAccountViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final DeleteAccountState state = (DeleteAccountState) evt.getNewValue();
            username.setText(state.getUsername());
            securityQuestion.setText(state.getSecurityQuestion());
            if (state.getDeleteAccountError() != null) {
                JOptionPane.showMessageDialog(this, state.getDeleteAccountError());
            }
        }
        else if (evt.getPropertyName().equals("delete account")) {
            JOptionPane.showMessageDialog(this, "Successfully deleted account.");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setDeleteAccountController(DeleteAccountController deleteAccountController) {
        this.deleteAccountController = deleteAccountController;
    }
}