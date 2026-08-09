package use_case.get_lists.get_watchlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import entity.UserLists;

class GetWatchlistInteractorTest {

    @Test
    void executePresentsTheUsersWatchlist() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final GetWatchlistInteractor interactor = new GetWatchlistInteractor(
                new ListDataAccess("bob"), presenter);

        interactor.execute(new GetWatchlistInputData("bob", "Bob"));

        assertEquals("bob", presenter.output.getUsername());
        assertEquals("Bob", presenter.output.getDisplayName());
        assertEquals("Example Movie", presenter.output.getWatchlist());
        assertTrue(presenter.output.getWatchlistItems().isEmpty());
    }

    @Test
    void accountNavigationDependsOnTheCurrentUser() {
        final RecordingPresenter personalPresenter = new RecordingPresenter();
        new GetWatchlistInteractor(new ListDataAccess("bob"), personalPresenter)
                .switchToAccountView(new GetWatchlistInputData("bob", "Bob"));
        assertTrue(personalPresenter.personal);

        final RecordingPresenter otherPresenter = new RecordingPresenter();
        new GetWatchlistInteractor(new ListDataAccess("alice"), otherPresenter)
                .switchToAccountView(new GetWatchlistInputData("bob", "Bob"));
        assertTrue(otherPresenter.other);
    }

    private static final class ListDataAccess implements GetWatchlistUserDataAccessInterface {
        private final String currentUsername;

        private ListDataAccess(String currentUsername) {
            this.currentUsername = currentUsername;
        }

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        @Override
        public UserLists getLists(String username) {
            return new UserLists("bob", "Example Movie", "", "");
        }
    }

    private static final class RecordingPresenter implements GetWatchlistOutputBoundary {
        private GetWatchlistOutputData output;
        private boolean personal;
        private boolean other;

        @Override
        public void prepareSuccessView(GetWatchlistOutputData response) {
            output = response;
        }

        @Override
        public void switchToPersonalAccountView() {
            personal = true;
        }

        @Override
        public void switchToOtherAccountView() {
            other = true;
        }
    }
}
