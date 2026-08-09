package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_username.ChangeUsernameController;
import interface_adapter.change_username.ChangeUsernameState;
import interface_adapter.change_username.ChangeUsernameViewModel;
import interface_adapter.personal_account.PersonalAccountState;
import interface_adapter.personal_account.PersonalAccountViewModel;

/**
 * The view for changing username.
 */
public class ChangeUsernameView extends JPanel implements PropertyChangeListener {

    private final String viewName = "change username";
    private final ChangeUsernameViewModel changeUsernameViewModel;

    private final JLabel forUserLabel;
    private final JTextField newUsernameField;
    private final JLabel messageLabel;

    private final JButton confirmChangesButton;
    private final JButton backButton;

    private ChangeUsernameController changeUsernameController;

    public ChangeUsernameView(ChangeUsernameViewModel changeUsernameViewModel,
                              PersonalAccountViewModel personalAccountViewModel,
                              ViewManagerModel viewManagerModel) {
        this.changeUsernameViewModel = changeUsernameViewModel;
        this.changeUsernameViewModel.addPropertyChangeListener(this);

        forUserLabel = new JLabel(" ");
        forUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newUsernameField = new JTextField(ChangeUsernameViewModel.COLUMN_SIZE);
        messageLabel = new JLabel(" ");

        final LabelTextPanel newInfo = new LabelTextPanel(
                new JLabel(ChangeUsernameViewModel.NEW_USERNAME_LABEL), newUsernameField);

        final JPanel buttons = new JPanel();
        confirmChangesButton = new JButton(ChangeUsernameViewModel.CONFIRM_BUTTON);
        buttons.add(confirmChangesButton);
        backButton = new JButton(ChangeUsernameViewModel.BACK_BUTTON);
        buttons.add(backButton);

        confirmChangesButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final ChangeUsernameState state = changeUsernameViewModel.getState();
                        changeUsernameController.changeUsername(
                                state.getUsername(), newUsernameField.getText(), state.getDisplayName());
                        newUsernameField.setText("");
                    }
                });

        backButton.addActionListener(evt -> {
            changeUsernameViewModel.firePropertyChanged();
            final PersonalAccountState updatedPersonalAccountState = new PersonalAccountState();
            updatedPersonalAccountState.setUsername(changeUsernameViewModel.getState().getUsername());
            updatedPersonalAccountState.setDisplayName(changeUsernameViewModel.getState().getDisplayName());
            personalAccountViewModel.setState(updatedPersonalAccountState);
            viewManagerModel.setState(PersonalAccountViewModel.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
            personalAccountViewModel.firePropertyChanged();
        });

        // Keep state in sync with the "new username" field.
        newUsernameField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final ChangeUsernameState state = changeUsernameViewModel.getState();
                state.setNewUsername(newUsernameField.getText());
                changeUsernameViewModel.setState(state);
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
        this.add(forUserLabel);
        this.add(newInfo);
        this.add(buttons);
        this.add(messageLabel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ChangeUsernameState state = (ChangeUsernameState) evt.getNewValue();
        forUserLabel.setText("Changing username for: " + state.getUsername());
        newUsernameField.setText(state.getNewUsername());

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

    public void setChangeUsernameController(ChangeUsernameController changeUsernameController) {
        this.changeUsernameController = changeUsernameController;
    }
}
