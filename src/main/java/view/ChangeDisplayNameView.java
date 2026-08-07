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
import interface_adapter.get_lists.GetListsState;
import interface_adapter.personal_account.PersonalAccountViewModel;

/**
 * The view for changing display name.
 */
public class ChangeDisplayNameView extends JPanel implements PropertyChangeListener {

    private final String viewName = "change display name";
    private final ChangeDisplayNameViewModel changeDisplayNameViewModel;
    private final PersonalAccountViewModel personalAccountViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JLabel forUserLabel = new JLabel(" ");
    private final JTextField newDisplayNameField = new JTextField(15);
    private final JLabel messageLabel = new JLabel(" ");

    private final JLabel title;
    private final JButton confirmChangesButton;
    private final JButton cancelButton;

    private ChangeDisplayNameController changeDisplayNameController;

    public ChangeDisplayNameView(ChangeDisplayNameViewModel changeDisplayNameViewModel,
                             PersonalAccountViewModel personalAccountViewModel,
                             ViewManagerModel viewManagerModel) {
        this.changeDisplayNameViewModel = changeDisplayNameViewModel;
        this.changeDisplayNameViewModel.addPropertyChangeListener(this);
        this.personalAccountViewModel = personalAccountViewModel;
        this.viewManagerModel = viewManagerModel;

        title = new JLabel(ChangeDisplayNameViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel newInfo = new LabelTextPanel(
                new JLabel(ChangeDisplayNameViewModel.NEW_DISPLAY_NAME_LABEL), newDisplayNameField);
        
        final JPanel buttons = new JPanel();
        confirmChangesButton = new JButton(ChangeDisplayNameViewModel.CONFIRM_BUTTON);
        buttons.add(confirmChangesButton);
        cancelButton = new JButton(ChangeDisplayNameViewModel.CANCEL_BUTTON);
        buttons.add(cancelButton);

        confirmChangesButton.addActionListener(evt -> {
            final ChangeDisplayNameState state = changeDisplayNameViewModel.getState();
            changeDisplayNameController.changeDisplayName(
                    state.getUsername(), newDisplayNameField.getText());
            newDisplayNameField.setText("");
        });
        
        cancelButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        changeDisplayNameViewModel.setState(new ChangeDisplayNameState());
                        newDisplayNameField.setText("");
                        viewManagerModel.switchView(personalAccountViewModel.getViewName());
                    }
                });

        // Keep state in sync with the "new display name" field.
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
