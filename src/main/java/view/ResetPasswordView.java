package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.reset_password.ResetPasswordController;
import interface_adapter.reset_password.ResetPasswordState;
import interface_adapter.reset_password.ResetPasswordViewModel;

/**
 * The View for choosing a new password, shown after the user has answered their
 * security question correctly. It displays which account is being changed, takes
 * the new password twice, and reports success or a validation error.
 */
public class ResetPasswordView extends JPanel implements PropertyChangeListener {

    private final String viewName = "reset password";
    private final ResetPasswordViewModel resetPasswordViewModel;

    private final JLabel forUserLabel = new JLabel(" ");
    private final JPasswordField newPasswordField = new JPasswordField(15);
    private final JPasswordField confirmPasswordField = new JPasswordField(15);
    private final JLabel messageLabel = new JLabel(" ");

    private final JButton submit;
    private final JButton back;

    private ResetPasswordController resetPasswordController;

    /** Used for the "back to login" jump, which carries no data of its own. */
    private final ViewManagerModel viewManagerModel;

    public ResetPasswordView(ResetPasswordViewModel resetPasswordViewModel,
                             ViewManagerModel viewManagerModel) {
        this.resetPasswordViewModel = resetPasswordViewModel;
        this.resetPasswordViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        final JLabel title = new JLabel("Set a New Password");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel newInfo = new LabelTextPanel(
                new JLabel("New password"), newPasswordField);
        final LabelTextPanel confirmInfo = new LabelTextPanel(
                new JLabel("Confirm password"), confirmPasswordField);

        final JPanel buttons = new JPanel();
        submit = new JButton("change password");
        buttons.add(submit);
        back = new JButton(ResetPasswordViewModel.BACK_BUTTON);
        buttons.add(back);

        // Submit: hand the two typed passwords to the controller for validation + save.
        submit.addActionListener(evt -> {
            final ResetPasswordState state = resetPasswordViewModel.getState();
            resetPasswordController.changePassword(
                    state.getUsername(),
                    new String(newPasswordField.getPassword()),
                    new String(confirmPasswordField.getPassword()));
        });

        // "Back": abandon the reset. Clear the typed passwords so they do not
        // linger in the form if the user returns.
        back.addActionListener(evt -> {
            resetPasswordViewModel.setState(new ResetPasswordState());
            resetPasswordViewModel.firePropertyChanged();
            viewManagerModel.setState(LoginViewModel.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        // Keep state in sync with the "new password" field.
        newPasswordField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final ResetPasswordState state = resetPasswordViewModel.getState();
                state.setNewPassword(new String(newPasswordField.getPassword()));
                resetPasswordViewModel.setState(state);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });

        // Keep state in sync with the "confirm password" field.
        confirmPasswordField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final ResetPasswordState state = resetPasswordViewModel.getState();
                state.setConfirmPassword(new String(confirmPasswordField.getPassword()));
                resetPasswordViewModel.setState(state);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(forUserLabel);
        this.add(newInfo);
        this.add(confirmInfo);
        this.add(buttons);
        this.add(messageLabel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ResetPasswordState state = (ResetPasswordState) evt.getNewValue();

forUserLabel.setText("Changing password for: " + state.getUsername());

        // Reflect the (possibly cleared) fields from state.
        newPasswordField.setText(state.getNewPassword());
        confirmPasswordField.setText(state.getConfirmPassword());

        // Show error if present, otherwise the success message.
        if (state.getError() != null && !state.getError().isEmpty()) {
            messageLabel.setText(state.getError());
        }
        else {
            messageLabel.setText(state.getMessage());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setResetPasswordController(ResetPasswordController resetPasswordController) {
        this.resetPasswordController = resetPasswordController;
    }
}
