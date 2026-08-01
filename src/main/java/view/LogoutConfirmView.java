package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.logout.LogoutController;

/**
 * The View for confirming logout.
 */
public class LogoutConfirmView extends JPanel implements ActionListener {

    private final String username;

    private final String viewName = "logout confirm";

    private final JButton confirm;
    private final JButton cancel;

    private LogoutController logoutController;

    public LogoutConfirmView(String username) {

        this.username = username;

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
            logoutController.execute(username);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}
