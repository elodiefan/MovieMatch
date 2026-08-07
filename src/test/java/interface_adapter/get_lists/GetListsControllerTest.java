package interface_adapter.get_lists;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import use_case.get_lists.GetListsInputData;
import use_case.get_lists.get_blocked_users.GetBlockedUsersInputBoundary;
import use_case.get_lists.get_watch_history.GetWatchHistoryInputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInputBoundary;

/**
 * Tests for the get lists controller.
 */
class GetListsControllerTest {

    @Test
    void executeWatchlistUseCasePackagesInputData() {
        final CapturingWatchlistBoundary watchlistBoundary =
                new CapturingWatchlistBoundary();
        final GetListsController controller = new GetListsController(
                watchlistBoundary);

        controller.executeWatchlistUseCase("elodie", "Elodie");

        assertEquals("elodie", watchlistBoundary.inputData.getUsername());
        assertEquals("Elodie", watchlistBoundary.inputData.getDislayName());
    }

    @Test
    void executeWatchHistoryUseCasePackagesInputData() {
        final CapturingWatchHistoryBoundary historyBoundary =
                new CapturingWatchHistoryBoundary();
        final GetListsController controller = new GetListsController(
                historyBoundary);

        controller.executeWatchHistoryUseCase("elodie", "Elodie");

        assertEquals("elodie", historyBoundary.inputData.getUsername());
        assertEquals("Elodie", historyBoundary.inputData.getDislayName());
    }

    @Test
    void switchToAccountViewUsesConfiguredWatchlistInteractorFirst() {
        final CapturingWatchlistBoundary watchlistBoundary =
                new CapturingWatchlistBoundary();
        final GetListsController controller = new GetListsController(
                watchlistBoundary);

        controller.switchToAccountView("elodie", "Elodie");

        assertEquals("elodie",
                watchlistBoundary.switchInputData.getUsername());
    }

    private static final class CapturingWatchlistBoundary
            implements GetWatchlistInputBoundary {
        private GetListsInputData inputData;
        private GetListsInputData switchInputData;

        @Override
        public void execute(GetListsInputData getListsInputData) {
            this.inputData = getListsInputData;
        }

        @Override
        public void switchToAccountView(GetListsInputData getListsInputData) {
            this.switchInputData = getListsInputData;
        }
    }

    private static final class CapturingWatchHistoryBoundary
            implements GetWatchHistoryInputBoundary {
        private GetListsInputData inputData;

        @Override
        public void execute(GetListsInputData getListsInputData) {
            this.inputData = getListsInputData;
        }

        @Override
        public void switchToAccountView(GetListsInputData getListsInputData) {
            this.inputData = getListsInputData;
        }
    }

    private static final class CapturingBlockedUsersBoundary
            implements GetBlockedUsersInputBoundary {
        @Override
        public void execute(GetListsInputData getListsInputData) {

        }

        @Override
        public void switchToAccountView(GetListsInputData getListsInputData) {

        }
    }
}
