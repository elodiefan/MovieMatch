package use_case.get_lists.get_watch_history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import entity.UserLists;

class GetWatchHistoryInteractorTest {

    @Test
    void executePresentsTheUsersWatchHistory() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final GetWatchHistoryInteractor interactor = new GetWatchHistoryInteractor(
                new ListDataAccess("bob"), presenter);

        interactor.execute(new GetWatchHistoryInputData("bob", "Bob"));

        assertEquals("bob", presenter.output.getUsername());
        assertEquals("Bob", presenter.output.getDisplayName());
        assertEquals("Example Movie", presenter.output.getWatchHistory());
        assertTrue(presenter.output.getWatchHistoryItems().isEmpty());
    }

    @Test
    void accountNavigationDependsOnTheCurrentUser() {
        final RecordingPresenter personalPresenter = new RecordingPresenter();
        new GetWatchHistoryInteractor(new ListDataAccess("bob"), personalPresenter)
                .switchToAccountView(new GetWatchHistoryInputData("bob", "Bob"));
        assertTrue(personalPresenter.personal);

        final RecordingPresenter otherPresenter = new RecordingPresenter();
        new GetWatchHistoryInteractor(new ListDataAccess("alice"), otherPresenter)
                .switchToAccountView(new GetWatchHistoryInputData("bob", "Bob"));
        assertTrue(otherPresenter.other);
    }

    private static final class ListDataAccess implements GetWatchHistoryUserDataAccessInterface {
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
            return new UserLists("bob", "", "Example Movie", "");
        }
    }

    private static final class RecordingPresenter implements GetWatchHistoryOutputBoundary {
        private GetWatchHistoryOutputData output;
        private boolean personal;
        private boolean other;

        @Override
        public void prepareSuccessView(GetWatchHistoryOutputData response) {
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
