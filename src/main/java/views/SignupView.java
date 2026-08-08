package views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * Swing view for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int FIELD_COLUMNS = 20;
    private static final int BORDER_GAP = 12;
    private static final int ROW_GAP = 6;
    private static final int USERNAME_ROW = 0;
    private static final int DISPLAY_NAME_ROW = 1;
    private static final int PASSWORD_ROW = 2;
    private static final int REPEAT_PASSWORD_ROW = 3;
    private static final int SECURITY_QUESTION_ROW = 4;
    private static final int SECURITY_ANSWER_ROW = 5;

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(FIELD_COLUMNS);
    private final JTextField displayNameInputField = new JTextField(FIELD_COLUMNS);
    private final JPasswordField passwordInputField = new JPasswordField(FIELD_COLUMNS);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(FIELD_COLUMNS);
    private final JComboBox<String> securityQuestionComboBox;
    private final JTextField securityAnswerInputField = new JTextField(FIELD_COLUMNS);
    private final JButton signUpButton = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
    private final JButton cancelButton = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
    private final JButton toLoginButton = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
    private SignupController signupController;

    /**
     * Creates the signup view.
     *
     * @param signupViewModel the signup view model
     */
    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        this.signupViewModel.addPropertyChangeListener(this);
        this.securityQuestionComboBox = new JComboBox<>(signupViewModel.getSecurityQuestionOptions());
        setLayout(new BorderLayout(BORDER_GAP, BORDER_GAP));
        add(new JLabel(SignupViewModel.TITLE_LABEL), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Responds to signup view button clicks.
     *
     * @param event the button click event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        final Object eventSource = event.getSource();
        if (eventSource == signUpButton) {
            submitSignup();
        }
        else if (eventSource == cancelButton) {
            clearForm();
        }
        else if (eventSource == toLoginButton && signupController != null) {
            signupController.switchToLoginView();
        }
    }

    /**
     * Responds to changes in the signup view model.
     *
     * @param event the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        final SignupState state = (SignupState) event.getNewValue();
        setFields(state);
        if (state.getSignupError() != null) {
            JOptionPane.showMessageDialog(this, state.getSignupError());
        }
    }

    /**
     * Returns the name used to identify this view.
     *
     * @return the signup view name
     */
    public String getViewName() {
        return signupViewModel.getViewName();
    }

    /**
     * Sets the controller used by this signup view.
     *
     * @param signupController the signup controller
     */
    public void setSignupController(SignupController signupController) {
        this.signupController = signupController;
    }

    /**
     * Creates the signup form panel.
     *
     * @return the form panel
     */
    private JPanel createFormPanel() {
        final JPanel formPanel = new JPanel(new GridBagLayout());
        addFormRow(formPanel, SignupViewModel.USERNAME_LABEL, usernameInputField, USERNAME_ROW);
        addFormRow(formPanel, SignupViewModel.DISPLAY_NAME_LABEL, displayNameInputField, DISPLAY_NAME_ROW);
        addFormRow(formPanel, SignupViewModel.PASSWORD_LABEL, passwordInputField, PASSWORD_ROW);
        addFormRow(formPanel, SignupViewModel.REPEAT_PASSWORD_LABEL, repeatPasswordInputField,
                REPEAT_PASSWORD_ROW);
        addFormRow(formPanel, SignupViewModel.SECURITY_QUESTION_LABEL, securityQuestionComboBox,
                SECURITY_QUESTION_ROW);
        addFormRow(formPanel, SignupViewModel.SECURITY_ANSWER_LABEL, securityAnswerInputField,
                SECURITY_ANSWER_ROW);
        return formPanel;
    }

    /**
     * Creates the signup button panel.
     *
     * @return the button panel
     */
    private JPanel createButtonPanel() {
        final JPanel buttonPanel = new JPanel();
        signUpButton.addActionListener(this);
        cancelButton.addActionListener(this);
        toLoginButton.addActionListener(this);
        buttonPanel.add(toLoginButton);
        buttonPanel.add(signUpButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    /**
     * Adds a labeled input row to the form panel.
     *
     * @param formPanel the panel receiving the row
     * @param labelText the row label text
     * @param inputComponent the input component
     * @param row the row number
     */
    private void addFormRow(JPanel formPanel, String labelText, java.awt.Component inputComponent, int row) {
        final GridBagConstraints labelConstraints = createLabelConstraints(row);
        final GridBagConstraints inputConstraints = createInputConstraints(row);
        formPanel.add(new JLabel(labelText), labelConstraints);
        formPanel.add(inputComponent, inputConstraints);
    }

    /**
     * Creates constraints for a form label.
     *
     * @param row the row number
     * @return label constraints
     */
    private GridBagConstraints createLabelConstraints(int row) {
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(ROW_GAP, BORDER_GAP, ROW_GAP, BORDER_GAP);
        return constraints;
    }

    /**
     * Creates constraints for a form input.
     *
     * @param row the row number
     * @return input constraints
     */
    private GridBagConstraints createInputConstraints(int row) {
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = row;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(ROW_GAP, BORDER_GAP, ROW_GAP, BORDER_GAP);
        constraints.weightx = 1.0;
        return constraints;
    }

    /**
     * Submits the current signup form values to the controller.
     */
    private void submitSignup() {
        updateStateFromFields();
        if (signupController != null) {
            final SignupState state = signupViewModel.getState();
            signupController.execute(state.getUsername(), state.getDisplayName(), state.getPassword(),
                    state.getRepeatPassword(), state.getSecurityQuestion(), state.getSecurityAnswer());
        }
    }

    /**
     * Copies the current form field values into the signup state.
     */
    private void updateStateFromFields() {
        final SignupState state = signupViewModel.getState();
        state.setUsername(usernameInputField.getText());
        state.setDisplayName(displayNameInputField.getText());
        state.setPassword(new String(passwordInputField.getPassword()));
        state.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
        state.setSecurityQuestion(getSelectedSecurityQuestion());
        state.setSecurityAnswer(securityAnswerInputField.getText());
        state.setSignupError(null);
        signupViewModel.setState(state);
    }

    /**
     * Returns the selected security question.
     *
     * @return the selected security question
     */
    private String getSelectedSecurityQuestion() {
        final Object selectedQuestion = securityQuestionComboBox.getSelectedItem();
        final String question;
        if (selectedQuestion == null) {
            question = "";
        }
        else {
            question = selectedQuestion.toString();
        }
        return question;
    }

    /**
     * Updates the visible signup fields from the current signup state.
     *
     * @param state the current signup state
     */
    private void setFields(SignupState state) {
        usernameInputField.setText(state.getUsername());
        displayNameInputField.setText(state.getDisplayName());
        passwordInputField.setText(state.getPassword());
        repeatPasswordInputField.setText(state.getRepeatPassword());
        securityQuestionComboBox.setSelectedItem(state.getSecurityQuestion());
        securityAnswerInputField.setText(state.getSecurityAnswer());
    }

    /**
     * Clears all editable signup fields.
     */
    private void clearForm() {
        usernameInputField.setText("");
        displayNameInputField.setText("");
        passwordInputField.setText("");
        repeatPasswordInputField.setText("");
        securityAnswerInputField.setText("");
        signupViewModel.setState(new SignupState());
        signupViewModel.firePropertyChanged();
    }
}
