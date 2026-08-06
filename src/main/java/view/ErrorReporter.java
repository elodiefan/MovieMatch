package view;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Turns a thrown exception into something a user can read.
 * <p>
 * Swing swallows exceptions thrown inside an event handler: it prints a stack
 * trace to the console and the window simply does nothing. To anyone using the
 * app a failed database call is indistinguishable from a dead button, so this
 * installs a handler that reports the failure on screen instead.
 */
public final class ErrorReporter {

    private static final String CONNECTION_TITLE = "Cannot reach the server";
    private static final String GENERAL_TITLE = "Something went wrong";

    private ErrorReporter() {
    }

    /**
     * Makes uncaught errors on the Swing thread show a dialog.
     */
    public static void install() {
        Thread.setDefaultUncaughtExceptionHandler(
                (thread, throwable) -> SwingUtilities.invokeLater(
                        () -> show(null, throwable)));
    }

    /**
     * Shows a readable message for a failure.
     * @param parent the component to centre the dialog on, may be null
     * @param throwable what went wrong
     */
    public static void show(Component parent, Throwable throwable) {
        final boolean connection = isConnectionProblem(throwable);
        final String title;
        if (connection) {
            title = CONNECTION_TITLE;
        }
        else {
            title = GENERAL_TITLE;
        }

        JOptionPane.showMessageDialog(parent, messageFor(throwable, connection),
                title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Builds the wording shown to the user.
     * @param throwable what went wrong
     * @param connection whether this looks like a connectivity failure
     * @return the message to display
     */
    private static String messageFor(Throwable throwable, boolean connection) {
        final String result;
        if (connection) {
            result = "MovieMatch could not reach the database.\n\n"
                    + "Check that you are connected to the internet, that mongo.properties\n"
                    + "is present in the project folder, and that your IP is allowed in Atlas.\n\n"
                    + "Details: " + shortMessage(throwable);
        }
        else {
            result = "MovieMatch hit an unexpected problem and could not finish that action.\n\n"
                    + "Details: " + shortMessage(throwable);
        }
        return result;
    }

    /**
     * Decides whether a failure is about reaching the network.
     * @param throwable what went wrong
     * @return true if it looks like a connection problem
     */
    private static boolean isConnectionProblem(Throwable throwable) {
        boolean result = false;
        Throwable current = throwable;
        while (current != null && !result) {
            final String type = current.getClass().getName().toLowerCase();
            if (type.contains("mongo") || type.contains("socket") || type.contains("unknownhost")
                    || type.contains("connect") || type.contains("ssl") || type.contains("timeout")) {
                result = true;
            }
            current = current.getCause();
        }
        return result;
    }

    /**
     * Trims an exception message to something that fits in a dialog.
     * @param throwable what went wrong
     * @return a short description
     */
    private static String shortMessage(Throwable throwable) {
        String message = throwable.getMessage();
        if (message == null || message.isEmpty()) {
            message = throwable.getClass().getSimpleName();
        }
        final int limit = 300;
        if (message.length() > limit) {
            message = message.substring(0, limit) + "...";
        }
        return message;
    }
}
