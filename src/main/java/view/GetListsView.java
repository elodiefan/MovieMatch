package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import interface_adapter.get_lists.GetListsController;
import interface_adapter.get_lists.GetListsState;
import interface_adapter.get_lists.GetListsViewModel;

/**
 * The View for a user's personal account lists.
 */

public class GetListsView extends JPanel implements PropertyChangeListener {

    private static final int TEXT_PADDING = 10;

    private final String viewName = "view lists";
    private GetListsViewModel getListsViewModel;
    private GetListsController getListsController;

    private final JLabel viewMessage;
    private final JTextArea userList;

    public GetListsView(GetListsViewModel getListsViewModel) {
        this.getListsViewModel = getListsViewModel;
        this.getListsViewModel.addPropertyChangeListener(this);

        viewMessage = new JLabel("", SwingConstants.CENTER);
        UiTheme.asTitle(viewMessage);
        final JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(viewMessage, BorderLayout.CENTER);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, TEXT_PADDING, 0));

        userList = new JTextArea();
        // This is a read-out of the user's list, not somewhere to type.
        userList.setEditable(false);
        userList.setLineWrap(true);
        userList.setWrapStyleWord(true);
        userList.setBorder(BorderFactory.createEmptyBorder(
                TEXT_PADDING, TEXT_PADDING, TEXT_PADDING, TEXT_PADDING));
        final JScrollPane scrollPane = new JScrollPane(userList);

        final JPanel returnPanel = new JPanel();
        final JButton returnButton = new JButton(GetListsViewModel.RETURN_BUTTON);
        returnPanel.add(returnButton);

        returnButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final GetListsState state = getListsViewModel.getState();
                        getListsController.switchToAccountView(state.getUsername(), state.getDisplayName());
                    }
                }
        );

        // BorderLayout so the list itself takes all the spare room when the
        // window is resized, rather than the heading and button drifting apart.
        this.setLayout(new BorderLayout());
        this.add(labelPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(returnPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final GetListsState state = (GetListsState) evt.getNewValue();
            viewMessage.setText(state.getDisplayName() + state.getListLabel());
            userList.setText(state.getDisplayText());
            // A freshly loaded list should start at the top.
            userList.setCaretPosition(0);
        }
    }

    public void setGetListsController(GetListsController getListsController) {
        this.getListsController = getListsController;
    }

    public String getViewName() {
        return viewName;
    }
}
