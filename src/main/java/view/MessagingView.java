package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.messaging.MessagingController;
import interface_adapter.messaging.MessagingViewModel;
import interface_adapter.other_account.OtherAccountController;
import interface_adapter.security_question.SecurityQuestionViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

    private final JTextField textInputField = new JTextField(30);

    private final JLabel title;

    private final JTextArea chatTextArea;

    private final JButton back;
    private final JButton refresh;

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

        final JPanel chatFrame = new JPanel();
        chatTextArea = new JTextArea();
        final JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        add(chatScrollPane, BorderLayout.CENTER);
        chatFrame.add(chatScrollPane);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(topOfScreen);
        this.add(chatFrame);
        this.add(textInputField);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        return;
    }

    public String getViewName() {
        return viewName;
    }

    public void setMessagingController(MessagingController messagingController) {
        this.messagingController = messagingController;
    }
}
