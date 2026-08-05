package poc.chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 * One person's chat window: who they're talking to, the transcript, a box to
 * type in, and a timer that keeps checking Atlas for anything new.
 * <p>
 * The dropdown at the top is what makes this work for more than two people.
 * Every conversation is still strictly one to one — the database query filters
 * on a sender/recipient pair — but one window can switch between several of
 * them, so three users need three windows rather than six. The real
 * {@code ChatView} should replace the dropdown with a {@code JList} of
 * conversations down the left-hand side; the logic underneath is the same.
 * <p>
 * Two things here are worth copying into the real feature.
 * <p>
 * First, <b>the poll is what replaces the WebSocket.</b> Every two seconds the
 * timer asks the store for messages newer than the last one on screen. That is
 * all "real time" needs to mean for this project. If it ever feels sluggish,
 * {@code MongoCollection.watch()} pushes changes instead of polling, using the
 * same driver and no new dependency — but it needs a background thread, so
 * polling is the sane thing to build first.
 * <p>
 * Second, <b>your own messages are not echoed locally.</b> Pressing send writes
 * to Atlas and nothing else; the text only appears once the next poll reads it
 * back. That is deliberate — if you can see your own message, the full round
 * trip through the database provably works.
 * <p>
 * The Mongo calls run inside {@link SwingWorker} rather than directly in the
 * timer, because a Swing {@code Timer} fires on the event dispatch thread and a
 * network call there would freeze the window for as long as it takes.
 */
public class PocChatWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int REFRESH_MILLIS = 2000;
    private static final int WINDOW_WIDTH = 440;
    private static final int WINDOW_HEIGHT = 520;
    private static final int TRANSCRIPT_ROWS = 18;
    private static final int INPUT_COLUMNS = 22;

    private static final DateTimeFormatter CLOCK =
            DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

    private final transient PocChatStore store;
    private final String me;

    private final JTextArea transcript = new JTextArea();
    private final JTextField input = new JTextField(INPUT_COLUMNS);
    private final JComboBox<String> recipientPicker;

    /** Who this window is currently talking to. Changes with the dropdown. */
    private String them;

    /** Timestamp of the newest message already on screen, for this conversation. */
    private long lastSeen;

    /**
     * Builds and shows a chat window.
     *
     * @param store where messages live
     * @param me the username this window is signed in as
     * @param others everyone this window can message
     */
    public PocChatWindow(final PocChatStore store, final String me, final List<String> others) {
        super("MovieMatch chat POC — you are " + me);
        this.store = store;
        this.me = me;
        this.them = others.get(0);

        this.transcript.setEditable(false);
        this.transcript.setLineWrap(true);
        this.transcript.setWrapStyleWord(true);
        this.transcript.setRows(TRANSCRIPT_ROWS);

        this.recipientPicker = new JComboBox<>(others.toArray(new String[0]));
        this.recipientPicker.addActionListener(event -> this.switchConversation());

        final JButton send = new JButton("Send");
        send.addActionListener(event -> this.sendWhateverIsTyped());
        this.input.addActionListener(event -> this.sendWhateverIsTyped());

        final JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.add(new JLabel("Talking to "));
        top.add(this.recipientPicker);

        final JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        bottom.add(this.input);
        bottom.add(send);

        final JPanel root = new JPanel(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        root.add(top, BorderLayout.NORTH);
        root.add(new JScrollPane(this.transcript), BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        this.setContentPane(root);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.pack();

        // Load the whole history on open, so an existing conversation is visible
        // rather than the window starting blank. The real view should cap this
        // at the most recent N messages.
        this.switchConversation();

        final Timer poller = new Timer(REFRESH_MILLIS, event -> this.pullNewMessages());
        poller.start();
    }

    /**
     * Points the window at whoever is selected in the dropdown and reloads that
     * conversation from the start.
     */
    private void switchConversation() {
        this.them = (String) this.recipientPicker.getSelectedItem();
        this.transcript.setText("");
        this.lastSeen = 0L;
        this.pullNewMessages();
    }

    private void sendWhateverIsTyped() {
        final String body = this.input.getText().trim();
        if (!body.isEmpty()) {
            this.input.setText("");
            final String recipient = this.them;
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    PocChatWindow.this.store.send(PocChatWindow.this.me, recipient, body);
                    return null;
                }

                @Override
                protected void done() {
                    // Don't wait up to two seconds to see your own message.
                    PocChatWindow.this.pullNewMessages();
                }
            }.execute();
        }
    }

    private void pullNewMessages() {
        final String recipient = this.them;
        final long since = this.lastSeen;
        new SwingWorker<List<PocMessage>, Void>() {
            @Override
            protected List<PocMessage> doInBackground() {
                return PocChatWindow.this.store.conversationSince(
                        PocChatWindow.this.me, recipient, since);
            }

            @Override
            protected void done() {
                try {
                    // Ignore a reply that arrives after the user switched away.
                    if (recipient.equals(PocChatWindow.this.them)) {
                        PocChatWindow.this.show(this.get());
                    }
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                catch (ExecutionException ex) {
                    PocChatWindow.this.transcript.append(
                            "[could not reach Atlas: " + ex.getCause().getMessage() + "]\n");
                }
            }
        }.execute();
    }

    private void show(final List<PocMessage> arrivals) {
        for (PocMessage message : arrivals) {
            this.transcript.append(CLOCK.format(Instant.ofEpochMilli(message.getSentAt()))
                    + "  " + message.getSender() + ": " + message.getBody() + "\n");
            this.lastSeen = Math.max(this.lastSeen, message.getSentAt());
        }
        this.transcript.setCaretPosition(this.transcript.getDocument().getLength());
    }
}
