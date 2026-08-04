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

import interface_adapter.account.AccountState;
import interface_adapter.view_lists.GetWatchlistViewModel;
import interface_adapter.view_lists.ViewListsController;
import interface_adapter.view_lists.ViewListsState;
import interface_adapter.view_lists.ViewListsViewModel;
/**
 * The View for a user's personal account lists.
 */

public class ViewListsView extends JPanel implements PropertyChangeListener {
    private final String viewName = "view lists";
    private final GetWatchlistViewModel getWatchlistViewModel;
    private ViewListsController viewListsController;

    // private final JLabel username;
    private final JLabel viewMessage;

    public ViewListsView(GetWatchlistViewModel getWatchlistViewModel) {
        this.getWatchlistViewModel = getWatchlistViewModel;
        this.getWatchlistViewModel.addPropertyChangeListener(this);
        viewMessage = new JLabel();
        setUpView(viewMessage);
    }

    public void setUpView(JLabel viewMessage) {
        final JPanel labelPanel = new JPanel();
        labelPanel.add(viewMessage);

        final JPanel listPanel = new JPanel();
        final JTextArea userList = new JTextArea();
        final JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);

        final JPanel returnPanel = new JPanel();
        final JButton returnButton = new JButton(ViewListsViewModel.RETURN_BUTTON);

        returnButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        viewListsController.switchToAccountView();
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(labelPanel);
        this.add(scrollPane);
        this.add(returnPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ViewListsState state = (ViewListsState) evt.getNewValue();
            viewMessage.setText(state.getUsername() + ViewListsViewModel.);
        }
    }
}
