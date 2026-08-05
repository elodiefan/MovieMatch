package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import interface_adapter.get_lists.*;

/**
 * The View for a user's personal account lists.
 */

public class GetListsView extends JPanel implements PropertyChangeListener {
    private final String viewName = "view lists";
//    private GetWatchlistViewModel getWatchlistViewModel;
//    private GetWatchHistoryViewModel getWatchHistoryViewModel;
//    private GetBlockedUsersViewModel getBlockedUsersViewModel;
    private GetListsViewModel getListsViewModel;
    private GetListsController getListsController;

    // private final JLabel username;
    private final JLabel viewMessage;
    private final JTextArea userList;

    public GetListsView(GetListsViewModel getListsViewModel) {
        this.getListsViewModel = getListsViewModel;
        this.getListsViewModel.addPropertyChangeListener(this);

        final JPanel labelPanel = new JPanel();
        viewMessage = new JLabel();
        labelPanel.add(viewMessage);

        final JPanel listPanel = new JPanel();
        userList = new JTextArea();
        final JScrollPane scrollPane = new JScrollPane(userList);
        add(scrollPane, BorderLayout.CENTER);
        listPanel.add(scrollPane);

        final JPanel returnPanel = new JPanel();
        final JButton returnButton = new JButton(GetListsViewModel.RETURN_BUTTON);
        returnPanel.add(returnButton);

        returnButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getListsController.switchToAccountView();
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(labelPanel);
        this.add(scrollPane);
        this.add(returnPanel);
    }

//    public GetListsView(GetWatchlistViewModel getWatchlistViewModel) {
//        this.getWatchlistViewModel = getWatchlistViewModel;
//        this.getWatchlistViewModel.addPropertyChangeListener(this);
//        viewMessage = new JLabel();
//        userList = new JTextArea();
//        setUpView();
//    }
//
//    public GetListsView(GetWatchHistoryViewModel getWatchHistoryViewModel) {
//        this.getWatchHistoryViewModel = getWatchHistoryViewModel;
//        this.getWatchHistoryViewModel.addPropertyChangeListener(this);
//        viewMessage = new JLabel();
//        userList = new JTextArea();
//        setUpView();
//    }
//
//    public GetListsView(GetBlockedUsersViewModel getBlockedUsersViewModel) {
//        this.getBlockedUsersViewModel = getBlockedUsersViewModel;
//        this.getBlockedUsersViewModel.addPropertyChangeListener(this);
//        viewMessage = new JLabel();
//        userList = new JTextArea();
//        setUpView();
//    }

//    /**
//     * Sets up list view template for user.
//     */
//    public void setUpView() {
//        final JPanel labelPanel = new JPanel();
//        labelPanel.add(viewMessage);
//
//        final JPanel listPanel = new JPanel();
//        final JScrollPane scrollPane = new JScrollPane(listPanel);
//        add(scrollPane, BorderLayout.CENTER);
//
//        final JPanel returnPanel = new JPanel();
//        final JButton returnButton = new JButton("Return to account");
//        returnPanel.add(returnButton);
//
//        returnButton.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        getListsController.switchToAccountView();
//                    }
//                }
//        );
//
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
//        this.add(labelPanel);
//        this.add(scrollPane);
//        this.add(returnPanel);
//    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final GetListsState state = (GetListsState) evt.getNewValue();
            viewMessage.setText(state.getUsername() + state.getDisplayText());
        }
    }

    public void setGetListsController(GetListsController getListsController) {
        this.getListsController = getListsController;
    }

    public String getViewName() {
        return viewName;
    }
}
