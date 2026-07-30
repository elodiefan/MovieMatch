package view;

import interface_adapter.home_page.HomePageController;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the homepage.
 */

public class HomePageView extends JPanel implements PropertyChangeListener {

    private final String viewName = "homepage";
    private final HomePageViewModel homePageViewModel;
    private HomePageController homePageController;

    private final JList listOfRecommendations;
    private final JLabel recommendationsHeader;

    private final JButton searchButton;
    private final JButton accountButton;

    public HomePageView(HomePageViewModel homePageViewModel) {
        this.homePageViewModel = homePageViewModel;
        this.homePageViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(HomePageViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel recommendationsPanel = new JPanel();
        recommendationsHeader = new JLabel(HomePageViewModel.RECOMMENDATIONS_LABEL);
        // TODO: Populate list with recommendations and maybe when click on media brings you to media's page.
        listOfRecommendations = new JList();
        recommendationsPanel.add(recommendationsHeader);
        recommendationsPanel.add(listOfRecommendations);

        final JPanel buttons = new JPanel();
        searchButton = new JButton(HomePageViewModel.SEARCH_BUTTON_LABEL);
        accountButton = new JButton(HomePageViewModel.ACCOUNT_BUTTON_LABEL);
        buttons.add(searchButton);
        buttons.add(accountButton);

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
        }
        else if (evt.getPropertyName().equals("password")) {
            final DeleteAccountState state = (DeleteAccountState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "deleted account for " + state.getUsername());
        }

    }

    public String getViewName() {
        return viewName;
    }
}

/**
 * need search button that brings to search page, popup that shows list (unclickable just text) of recommendations,
 * button that brings to user acc,
 */