package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_display_name.ChangeDisplayNameController;
import interface_adapter.change_display_name.ChangeDisplayNameState;
import interface_adapter.change_display_name.ChangeDisplayNameViewModel;
import interface_adapter.personal_account.PersonalAccountViewModel;

/**
 * The view for changing display name.
 */
public class ChangeDisplayNameView extends JPanel implements PropertyChangeListener {

    private final String viewName = "change display name";
    private final ChangeDisplayNameViewModel changeDisplayNameViewModel;

    private final JLabel forUserLabel;
    private final JTextField newDisplayNameField;
    private final JLabel messageLabel;

    private final JButton confirmChangesButton;
    private final JButton backButton;

    private ChangeDisplayNameController changeDisplayNameController;

    public ChangeDisplayNameView(ChangeDisplayNameViewModel changeDisplayNameViewModel,
                             PersonalAccountViewModel personalAccountViewModel,
                             ViewManagerModel viewManagerModel) {
        this.changeDisplayNameViewModel = changeDisplayNameViewModel;
        this.changeDisplayNameViewModel.addPropertyChangeListener(this);

        forUserLabel = new JLabel(" ");
        forUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newDisplayNameField = new JTextField(ChangeDisplayNameViewModel.COLUMN_SIZE);
        messageLabel = new JLabel(" ");

        final LabelTextPanel newInfo = new LabelTextPanel(
                new JLabel(ChangeDisplayNameViewModel.NEW_DISPLAY_NAME_LABEL), newDisplayNameField);
        
        final JPanel buttons = new JPanel();
        confirmChangesButton = new JButton(ChangeDisplayNameViewModel.CONFIRM_BUTTON);
        buttons.add(confirmChangesButton);
        backButton = new JButton(ChangeDisplayNameViewModel.BACK_BUTTON);
        buttons.add(backButton);

        confirmChangesButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final ChangeDisplayNameState state = changeDisplayNameViewModel.getState();
                        changeDisplayNameController.changeDisplayName(
                                state.getUsername(), newDisplayNameField.getText());
                        newDisplayNameField.setText("");
                    }
                });
        
        backButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final ChangeDisplayNameState state = changeDisplayNameViewModel.getState();
                        changeDisplayNameController.switchToPersonalAccountView(state.getUsername(),
                                state.getNewDisplayName());
                        newDisplayNameField.setText("");
                    }
                });

//         Keep state in sync with the "new display name" field.
        newDisplayNameField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final ChangeDisplayNameState state = changeDisplayNameViewModel.getState();
                state.setNewDisplayName(newDisplayNameField.getText());
                changeDisplayNameViewModel.setState(state);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               // update();
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
        final ChangeDisplayNameState state = (ChangeDisplayNameState) evt.getNewValue();

        forUserLabel.setText("Changing display name for: " + state.getUsername());

        newDisplayNameField.setText(state.getNewDisplayName());

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

    public void setChangeDisplayNameController(ChangeDisplayNameController changeDisplayNameController) {
        this.changeDisplayNameController = changeDisplayNameController;
    }
}
