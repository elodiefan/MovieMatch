package poc.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Runs the chat proof of concept.
 * <p>
 * The question this answers is whether several separate copies of MovieMatch,
 * on different laptops, can exchange messages without adding Spring Boot,
 * Docker or a WebSocket server. They can, because MongoDB Atlas is already a
 * server that every copy of the app is already connected to.
 * <p>
 * <b>Trying it on one machine.</b> Run it once per person you want to simulate,
 * giving each run a different username. Three runs is three windows, one per
 * user, and each window's dropdown picks who that person is talking to.
 * <p>
 * <b>Trying it on several machines.</b> One run each, as yourself, as long as
 * everyone's mongo.properties points at the same cluster. This is the run that
 * actually proves the point.
 * <p>
 * Arguments: pass a username to skip the prompt ({@code PocChat kiersten}), or
 * pass {@code clean} to empty the scratch collection and exit.
 * <p>
 * Nothing here is Clean Architecture — there is no interactor, no boundary and
 * no presenter, and the window talks straight to the database. That is fine for
 * a throwaway spike whose only job is to answer one question, and it is exactly
 * what the real feature must not look like.
 */
public final class PocChat {

    private static final String PROPERTIES = "mongo.properties";

    /** The team, so nobody has to type a recipient list. */
    private static final List<String> ROSTER =
            Arrays.asList("enzo", "kiersten", "lily", "elodie", "yidan");

    private PocChat() {
    }

    /**
     * Entry point.
     *
     * @param args optionally a username, or "clean" to wipe the scratch messages
     */
    public static void main(final String[] args) {
        final PocChatStore store = new PocChatStore(PROPERTIES);

        if (args.length > 0 && "clean".equals(args[0])) {
            store.deleteEverything();
            store.close();
            System.out.println("Scratch collection poc_messages emptied.");
        }
        else {
            final String me = whoAmI(args);
            if (me == null || me.trim().isEmpty()) {
                store.close();
            }
            else {
                final List<String> others = everyoneExcept(me.trim());
                SwingUtilities.invokeLater(() ->
                        new PocChatWindow(store, me.trim(), others).setVisible(true));
            }
        }
    }

    private static String whoAmI(final String[] args) {
        final String me;
        if (args.length > 0) {
            me = args[0];
        }
        else {
            me = JOptionPane.showInputDialog(null, "Your username", "MovieMatch chat POC",
                    JOptionPane.QUESTION_MESSAGE);
        }
        return me;
    }

    /**
     * Everyone on the roster apart from you, so you cannot message yourself.
     * A username that isn't on the roster simply gets the whole roster back.
     */
    private static List<String> everyoneExcept(final String me) {
        final List<String> others = new ArrayList<>();
        for (String person : ROSTER) {
            if (!person.equalsIgnoreCase(me)) {
                others.add(person);
            }
        }
        return others;
    }
}
