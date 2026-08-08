package views;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interface_adapter.delete_account.DeleteAccountState;
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
        listOptionsPanel.add(backButton);
        listOptionsPanel.add(messageButton);

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
                        final OtherAccountState currentState = new OtherAccountState();

                        this.otherAccountController.goToMessages(currentState.getUsername());
                    }
                }
        );

        // A block button used to sit here, but it was built with no label at
        // all and nothing ever gave it one, so it drew as a blank box next to
        // Message. Blocking still exists on the controller and can go back on
        // this screen once it has a label and something showing whether the
        // account is already blocked.

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
            title.setText(state.getDisplayName() + otherAccountViewModel.TITLE_LABEL);
            username.setText(otherAccountViewModel.USERNAME_LABEL + state.getUsername());
            displayName.setText(otherAccountViewModel.DISPLAY_NAME_LABEL + state.getDisplayName());
            if (state.getViewMessageError() != null) {
                JOptionPane.showMessageDialog(this, state.getViewMessageError());
            }
        }
        else if (evt.getPropertyName().equals("cannot message")) {
            JOptionPane.showMessageDialog(this, "Cannot message this user.");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setOtherAccountController(OtherAccountController otherAccountController) {
        this.otherAccountController = otherAccountController;
    }
}
