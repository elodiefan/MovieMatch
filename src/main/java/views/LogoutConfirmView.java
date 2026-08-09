package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.ViewManagerModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;

/**
 * The View for confirming logout.
 */
public class LogoutConfirmView extends JPanel implements ActionListener {

    private final String viewName = "logout confirm";

    private final LogoutViewModel logoutViewModel;
    private final ViewManagerModel viewManagerModel;
    private final String personalAccountViewName;

    private final JButton confirm;
    private final JButton cancel;

    private LogoutController logoutController;

    public LogoutConfirmView(LogoutViewModel logoutViewModel,
                             ViewManagerModel viewManagerModel,
                             String personalAccountViewName) {

        this.logoutViewModel = logoutViewModel;
        this.viewManagerModel = viewManagerModel;
        this.personalAccountViewName = personalAccountViewName;

        final JLabel message = new JLabel(
                "confirming the log out"
        );

        confirm = new JButton("confirm");
        cancel = new JButton("cancel");

        confirm.addActionListener(this);
        cancel.addActionListener(this);

        this.add(message);
        this.add(confirm);
        this.add(cancel);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        if (evt.getSource().equals(confirm)) {

            final LogoutState currentState =
                    logoutViewModel.getState();

            logoutController.execute(
                    currentState.getUsername()
            );
        }
        else if (evt.getSource().equals(cancel)) {
            // Changing your mind has to lead somewhere, otherwise this view is a dead end.
            viewManagerModel.switchView(personalAccountViewName);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}
