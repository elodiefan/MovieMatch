package poc.chat;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Runs the chat proof of concept.
 * <p>
 * The question this answers is whether two separate copies of MovieMatch, on
 * two different laptops, can exchange messages without adding Spring Boot,
 * Docker or a WebSocket server. They can, because MongoDB Atlas is already a
 * server that every copy of the app is already connected to.
 * <p>
 * <b>Trying it on one machine.</b> Run twice. Answer "alice" then "bob" in the
 * first window and "bob" then "alice" in the second. Type in either.
 * <p>
 * <b>Trying it on two machines.</b> Same thing, one window each, as long as both
 * have a mongo.properties pointing at the same cluster. This is the run that
 * actually proves the point.
 * <p>
 * Pass {@code clean} as an argument to wipe the scratch collection afterwards.
 * <p>
 * Nothing here is Clean Architecture — there is no interactor, no boundary and
 * no presenter, and the window talks straight to the database. That is fine for
 * a throwaway spike whose only job is to answer one question, and it is exactly
 * what the real feature must not look like.
 */
public final class PocChat {

    private static final String PROPERTIES = "mongo.properties";

    private PocChat() {
    }

    /**
     * Entry point.
     *
     * @param args pass "clean" to delete the scratch messages and exit
     */
    public static void main(final String[] args) {
        final PocChatStore store = new PocChatStore(PROPERTIES);

        if (args.length > 0 && "clean".equals(args[0])) {
            store.deleteEverything();
            store.close();
            System.out.println("Scratch collection poc_messages emptied.");
        }
        else {
            final String me = ask("Your username");
            final String them = ask("Who are you messaging");
            if (me == null || them == null) {
                store.close();
            }
            else {
                SwingUtilities.invokeLater(() -> new PocChatWindow(store, me, them).setVisible(true));
            }
        }
    }

    private static String ask(final String prompt) {
        return JOptionPane.showInputDialog(null, prompt, "MovieMatch chat POC",
                JOptionPane.QUESTION_MESSAGE);
    }
}
