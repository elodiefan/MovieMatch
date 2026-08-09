package views;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Turns a thrown exception into something a user can read.
 */
public final class ErrorReporter {

    private static final String CONNECTION_TITLE = "Cannot reach the server";
    private static final String GENERAL_TITLE = "Something went wrong";
    private static final String TMDB_TITLE = "Movie search is not set up";

    private ErrorReporter() {
    }

    /**
     * Makes uncaught errors on the Swing thread show a dialog.
     */
    public static void install() {
        Thread.setDefaultUncaughtExceptionHandler(
                (thread, throwable) -> {
                    SwingUtilities.invokeLater(
                            () -> show(null, throwable));
                });
    }

    /**
     * Shows a readable message for a failure.
     *
     * @param parent the parent
     * @param throwable the throwable
     */
    public static void show(Component parent, Throwable throwable) {
        final String title;
        final String message;

        if (isTmdbProblem(throwable)) {
            title = TMDB_TITLE;
            message = "Movie and TV search needs a TMDB access token, which is not set on\n"
                    + "this computer.\n\n"
                    + "Set the environment variable Tmdb_Read_Access to your TMDB read access\n"
                    + "token, then restart MovieMatch. The name is case sensitive on macOS\n"
                    + "and Linux.\n\n"
                    + "Everything else, including finding users, works without it.";
        }
        else if (isConnectionProblem(throwable)) {
            title = CONNECTION_TITLE;
            message = messageFor(throwable, true);
        }
        else {
            title = GENERAL_TITLE;
            message = messageFor(throwable, false);
        }

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Decides whether a failure is the missing TMDB token rather than a fault.
     *
     * @param throwable the throwable
     * @return the is tmdb problem
     */
    private static boolean isTmdbProblem(Throwable throwable) {
        boolean result = false;
        Throwable current = throwable;
        while (current != null && !result) {
            final String message = current.getMessage();
            if (message != null) {
                final String lower = message.toLowerCase();
                if (lower.contains("tmdb") || lower.contains("tmdb_read_access")) {
                    result = true;
                }
            }
            current = current.getCause();
        }
        return result;
    }

    /**
     * Builds the wording shown to the user.
     *
     * @param throwable the throwable
     * @param connection the connection
     * @return the message for
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
     *
     * @param throwable the throwable
     * @return the is connection problem
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
     *
     * @param throwable the throwable
     * @return the short message
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
