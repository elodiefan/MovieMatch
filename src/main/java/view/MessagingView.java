package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.messaging.MessagingController;
import interface_adapter.messaging.MessagingState;
import interface_adapter.messaging.MessagingViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The View for messaging.
 */

public class MessagingView extends JPanel implements PropertyChangeListener {
    private final String viewName = "chat";
    private MessagingViewModel messagingViewModel;
    private MessagingController messagingController;
    /** Used for the "Forgot Password" jump, which carries no data of its own. */
    private final ViewManagerModel viewManagerModel;

    private final JTextField textInputField = new JTextField(15);

    private final JLabel title;

    private final JTextArea chatTextArea = new JTextArea(5, 20);

    private final JButton back;
    private final JButton refresh;
    private final JButton send;

    public MessagingView(MessagingViewModel messagingViewModel, ViewManagerModel viewManagerModel) {
        this.messagingViewModel = messagingViewModel;
        this.messagingViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        final JPanel topOfScreen = new JPanel();
        back = new JButton(messagingViewModel.BACK_BUTTON_LABEL);
        title = new JLabel();
        title.setAlignmentY(Component.TOP_ALIGNMENT);
        refresh = new JButton(messagingViewModel.REFRESH);
        topOfScreen.add(back);
        topOfScreen.add(title);
        topOfScreen.add(refresh);

        final JPanel chatPanel = new JPanel();
        chatTextArea.setEditable(false);
        final JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(chatScrollPane);

        final JPanel textPanel = new JPanel();
        textPanel.add(textInputField);
        send = new JButton(messagingViewModel.SEND_BUTTON_LABEL);
        textPanel.add(send);

        back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        messagingController.switchToOtherAccountView();
                        textInputField.setText("");
                    }
                }
        );

        refresh.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(refresh)) {
                        final MessagingState state = messagingViewModel.getState();

                        this.messagingController.executeFetchUpdateChatHistory(
                                state.getUsername(),
                                state.getOtherUsername(),
                                chatTextArea.getText()
                        );
                    }
                }
        );

        send.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(send)) {
                            final MessagingState state = messagingViewModel.getState();

                            messagingController.executeSendMessage(state.getUsername(), state.getOtherUsername(),
                                    textInputField.getText(), LocalDateTime.now());
                        }
                    }
                }
        );

        addTextInputFieldListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(topOfScreen);
        this.add(chatPanel);
        this.add(textPanel);
    }

    private void addTextInputFieldListener() {
        textInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MessagingState state = messagingViewModel.getState();
                state.appendDisplayText(new String(textInputField.getText()));
                messagingViewModel.setState(state);
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
            final MessagingState state = (MessagingState) evt.getNewValue();
            title.setText("Chat with " + state.getOtherUsername());
            chatTextArea.setText(state.getDisplayText());
        }
        else if (evt.getPropertyName().equals("sent message")) {
            textInputField.setText("");
        }
        else if (evt.getPropertyName().equals("fetched chat history")) {
            final MessagingState state = messagingViewModel.getState();
            chatTextArea.setText(state.getDisplayText());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setMessagingController(MessagingController messagingController) {
        this.messagingController = messagingController;
    }
}
