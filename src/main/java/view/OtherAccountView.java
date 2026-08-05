package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.other_account.OtherAccountController;
import interface_adapter.other_account.OtherAccountState;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountState;

public class OtherAccountView extends JPanel implements PropertyChangeListener {

    private final String viewName = "other account";
    private final OtherAccountViewModel otherAccountViewModel;
    private OtherAccountController otherAccountController;

    private final JLabel title;
    private final JLabel username;
    private final JLabel displayName;
    private final JButton watchlistButton;
    private final JButton watchHistoryButton;
    private final JButton reviewsButton;
    private final JButton blockButton;
    private final JButton messageButton;
    private final JButton backButton;

    public OtherAccountView(OtherAccountViewModel otherAccountViewModel) {
        this.otherAccountViewModel = otherAccountViewModel;
        this.otherAccountViewModel.addPropertyChangeListener(this);

        title = new JLabel();

        final JPanel profilePanel = new JPanel();
        username = new JLabel();
        displayName = new JLabel();
        profilePanel.add(username);
        profilePanel.add(displayName);

        final JPanel listOptionsPanel = new JPanel();
        watchlistButton = new JButton(OtherAccountViewModel.WATCHLIST_BUTTON);
        watchHistoryButton = new JButton(OtherAccountViewModel.WATCH_HISTORY_BUTTON);
        reviewsButton = new JButton(OtherAccountViewModel.REVIEWS_BUTTON);
        listOptionsPanel.add(watchlistButton);
        listOptionsPanel.add(watchHistoryButton);
        listOptionsPanel.add(reviewsButton);

        final JPanel accountOptionsPanel = new JPanel();
        backButton = new JButton(OtherAccountViewModel.BACK_BUTTON);
        messageButton = new JButton(OtherAccountViewModel.MESSAGE_BUTTON);
        blockButton = new JButton();
        listOptionsPanel.add(backButton);
        listOptionsPanel.add(messageButton);
        listOptionsPanel.add(blockButton);

        watchlistButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final OtherAccountState state = otherAccountViewModel.getState();
                        otherAccountController.switchToWatchlistView(state.getUsername(), state.getDisplayName());
                    }
                }
        );

        watchHistoryButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final OtherAccountState state = otherAccountViewModel.getState();
                        otherAccountController.switchToWatchHistoryView(state.getUsername(), state.getDisplayName());
                    }
                }
        );

//        reviewsButton.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        accountController.switchToReviewsView();
//                    }
//                }
//        );

        backButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        otherAccountController.switchToSearchView();
                    }
                }
        );

        messageButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(messageButton)) {
                        final OtherAccountState otherAccountState = new OtherAccountState();

                        this.otherAccountController.executeMessaging();
                    }
                }
        );

        blockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(blockButton)) {
                        final OtherAccountState currentState = otherAccountViewModel.getState();

                        this.otherAccountController.executeBlockUser(
                                currentState.getUsername()
                        );
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(profilePanel);
        this.add(listOptionsPanel);
        this.add(accountOptionsPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final OtherAccountState state = (OtherAccountState) evt.getNewValue();
            title.setText(state.getDisplayName() + "'s Account");
            username.setText(state.getUsername());
            displayName.setText(state.getDisplayName());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setOtherAccountController(OtherAccountController otherAccountController) {
        this.otherAccountController = otherAccountController;
    }
}
