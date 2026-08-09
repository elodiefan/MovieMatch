package views;

import java.util.concurrent.Executor;

import javax.swing.SwingUtilities;

/**
 * Runs an update on Swing's event dispatch thread.
 * Presenters need their updates to land on the UI thread, because work that
 * reaches the network runs off it. Taking a plain Executor there and keeping
 * the Swing part here means the interface adapter layer never names a UI
 * framework, so a presenter can still be tested with a same-thread executor.
 */
public final class SwingUiExecutor implements Executor {

    /**
     * Runs the update now if already on the UI thread, or queues it if not.
     *
     * @param command the update to run
     */
    @Override
    public void execute(Runnable command) {
        if (SwingUtilities.isEventDispatchThread()) {
            command.run();
        }
        else {
            SwingUtilities.invokeLater(command);
        }
    }
}
