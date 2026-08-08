package views;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.security_question.SecurityQuestionController;
import interface_adapter.security_question.SecurityQuestionState;
import interface_adapter.security_question.SecurityQuestionViewModel;

/** The View for recovering an account by answering a security question. */
public class SecurityQuestionView extends JPanel implements PropertyChangeListener {

    private final String viewName = "security question";
    private final SecurityQuestionViewModel securityQuestionViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JTextField answerInputField = new JTextField(15);
    private final JLabel questionLabel = new JLabel(" ");
    private final JLabel messageLabel = new JLabel(" ");

    private final JButton showQuestion;
    private final JButton verify;
    private final JButton back;

    private SecurityQuestionController securityQuestionController;
    private Runnable backHandler;

    public SecurityQuestionView(SecurityQuestionViewModel securityQuestionViewModel) {
        this.securityQuestionViewModel = securityQuestionViewModel;
        this.securityQuestionViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Recover Password");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        final LabelTextPanel answerInfo = new LabelTextPanel(
                new JLabel("Answer"), answerInputField);

        final JPanel buttons = new JPanel();
        showQuestion = new JButton("show question");
        buttons.add(showQuestion);
        verify = new JButton("verify");
        buttons.add(verify);
        back = new JButton(SecurityQuestionViewModel.BACK_BUTTON);
        buttons.add(back);

        // "Show question": look up the account and display its security question.
        showQuestion.addActionListener(evt -> {
            final SecurityQuestionState currentState = securityQuestionViewModel.getState();
            securityQuestionController.loadQuestion(currentState.getUsername());
        });

        // "Verify": submit the typed answer for checking.
        verify.addActionListener(evt -> {
            final SecurityQuestionState currentState = securityQuestionViewModel.getState();
            securityQuestionController.verify(currentState.getUsername(), currentState.getAnswer());
        });

        // "Back": abandon the recovery attempt. Clear the half-filled form first,
        // so returning later does not show the previous user's question.
        back.addActionListener(evt -> {
            securityQuestionViewModel.setState(new SecurityQuestionState());
            securityQuestionViewModel.firePropertyChanged();
            if (backHandler != null) {
                backHandler.run();
            }
        });

        // Keep the state's username in sync with the text field.
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final SecurityQuestionState state = securityQuestionViewModel.getState();
                state.setUsername(usernameInputField.getText());
                securityQuestionViewModel.setState(state);
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

        // Keep the state's answer in sync with the text field.
        answerInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final SecurityQuestionState state = securityQuestionViewModel.getState();
                state.setAnswer(answerInputField.getText());
                securityQuestionViewModel.setState(state);
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
        this.add(usernameInfo);
        this.add(buttons);
        this.add(questionLabel);
        this.add(answerInfo);
        this.add(messageLabel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SecurityQuestionState state = (SecurityQuestionState) evt.getNewValue();

        // Keep the text fields in step with the state, so a reset clears them.
        if (!usernameInputField.getText().equals(state.getUsername())) {
            usernameInputField.setText(state.getUsername());
        }
        if (!answerInputField.getText().equals(state.getAnswer())) {
            answerInputField.setText(state.getAnswer());
        }

        questionLabel.setText(state.getSecurityQuestion().isEmpty()
                ? " " : "Q: " + state.getSecurityQuestion());

        // Show the error if there is one, otherwise the (success) message.
        if (state.getError() != null && !state.getError().isEmpty()) {
            messageLabel.setText(state.getError());
        }
        else {
            messageLabel.setText(state.getMessage());
        }

        // While locked out, block further answering.
        answerInputField.setEnabled(!state.isLockedOut());
        verify.setEnabled(!state.isLockedOut());
    }

    public String getViewName() {
        return viewName;
    }

    public void setSecurityQuestionController(SecurityQuestionController securityQuestionController) {
        this.securityQuestionController = securityQuestionController;
    }

    public void setBackHandler(Runnable backHandler) {
        this.backHandler = backHandler;
    }
}
