package views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interface_adapter.other_account.OtherAccountController;
import interface_adapter.other_account.OtherAccountState;
import interface_adapter.other_account.OtherAccountViewModel;

public class OtherAccountView extends JPanel implements PropertyChangeListener {

    private static final int ACTION_BUTTON_ROWS = 2;
    private static final int ACTION_BUTTON_COLUMNS = 2;
    private static final int ACTION_BUTTON_GAP = 8;

    private final String viewName = "other account";
    private final OtherAccountViewModel otherAccountViewModel;
    private OtherAccountController otherAccountController;

    private final JLabel title;
    private final JLabel username;
    private final JLabel displayName;
    private final JButton watchlistButton;
    private final JButton watchHistoryButton;
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

        final JPanel accountOptionsPanel = new JPanel(new GridLayout(
                ACTION_BUTTON_ROWS, ACTION_BUTTON_COLUMNS,
                ACTION_BUTTON_GAP, ACTION_BUTTON_GAP));
        accountOptionsPanel.setBorder(BorderFactory.createEmptyBorder(
                ACTION_BUTTON_GAP, ACTION_BUTTON_GAP,
                ACTION_BUTTON_GAP, ACTION_BUTTON_GAP));
        watchlistButton = new JButton(OtherAccountViewModel.WATCHLIST_BUTTON);
        watchHistoryButton = new JButton(OtherAccountViewModel.WATCH_HISTORY_BUTTON);
        messageButton = new JButton(OtherAccountViewModel.MESSAGE_BUTTON);
        backButton = new JButton(OtherAccountViewModel.BACK_BUTTON);
        accountOptionsPanel.add(watchlistButton);
        accountOptionsPanel.add(watchHistoryButton);
        accountOptionsPanel.add(messageButton);
        accountOptionsPanel.add(backButton);

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
                        final OtherAccountState currentState = otherAccountViewModel.getState();

                        this.otherAccountController.goToMessages(currentState.getUsername());
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(profilePanel);
        this.add(accountOptionsPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final OtherAccountState state = (OtherAccountState) evt.getNewValue();
            title.setText(state.getDisplayName() + otherAccountViewModel.TITLE_LABEL);
            username.setText(otherAccountViewModel.USERNAME_LABEL + state.getUsername());
            displayName.setText(otherAccountViewModel.DISPLAY_NAME_LABEL + state.getDisplayName());
            blockButton.setText(state.getBlockStatus());
            if (state.getViewMessageError() != null && state.getViewMessageError()
                    .equals("Cannot message this user.")) {
                JOptionPane.showMessageDialog(this, state.getViewMessageError());
            }
        }
        else if (evt.getPropertyName().equals("changed block state")) {
            final OtherAccountState state = otherAccountViewModel.getState();
            blockButton.setText(state.getBlockStatus());
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
