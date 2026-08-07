package use_case.get_lists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import data_access.InMemoryUserDataAccessObject;
import entity.StandardUser;
import entity.User;
import entity.UserLists;
import org.junit.jupiter.api.Test;
import use_case.get_lists.get_watch_history.GetWatchHistoryInputBoundary;
import use_case.get_lists.get_watch_history.GetWatchHistoryInteractor;
import use_case.get_lists.get_watch_history.GetWatchHistoryOutputBoundary;
import use_case.get_lists.get_watch_history.GetWatchHistoryOutputData;

/**
 * Tests for the get watch history interactor.
 */
class GetWatchHistoryInteractorTest {

    @Test
    void successLoadsWatchHistory() {
        final InMemoryUserDataAccessObject dao = makeDao();
        final GetWatchHistoryOutputBoundary presenter =
                new GetWatchHistoryOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetWatchHistoryOutputData response) {
                        assertEquals("elodie", response.getUsername());
                        assertEquals("Elodie", response.getDisplayName());
                        assertEquals("Dune -- yesterday\n",
                                response.getWatchHistory());
                    }

                    @Override
                    public void switchToPersonalAccountView() {
                        fail("Should not switch views while loading.");
                    }

                    @Override
                    public void switchToOtherAccountView() {
                        fail("Should not switch views while loading.");
                    }
                };

        final GetWatchHistoryInputBoundary interactor =
                new GetWatchHistoryInteractor(dao, presenter);
        interactor.execute(new GetListsInputData("elodie", "Elodie"));
    }

    @Test
    void switchToAccountViewUsesPersonalAccountForCurrentUser() {
        final InMemoryUserDataAccessObject dao = makeDao();
        final GetWatchHistoryOutputBoundary presenter =
                new TrackingWatchHistoryPresenter();
        final GetWatchHistoryInputBoundary interactor =
                new GetWatchHistoryInteractor(dao, presenter);

        interactor.switchToAccountView(new GetListsInputData("elodie",
                "Elodie"));

        assertEquals("personal",
                ((TrackingWatchHistoryPresenter) presenter).switchedTo);
    }

    @Test
    void switchToAccountViewUsesOtherAccountForDifferentUser() {
        final InMemoryUserDataAccessObject dao = makeDao();
        final GetWatchHistoryOutputBoundary presenter =
                new TrackingWatchHistoryPresenter();
        final GetWatchHistoryInputBoundary interactor =
                new GetWatchHistoryInteractor(dao, presenter);

        interactor.switchToAccountView(new GetListsInputData("lily",
                "Lily"));

        assertEquals("other",
                ((TrackingWatchHistoryPresenter) presenter).switchedTo);
    }

    private InMemoryUserDataAccessObject makeDao() {
        final InMemoryUserDataAccessObject dao =
                new InMemoryUserDataAccessObject();
        final User user = new StandardUser("elodie", "Elodie", "password",
                "Question?", "Answer", new UserLists("elodie",
                "Fight Club -- today\n", "Dune -- yesterday\n", ""));
        dao.save(user);
        dao.setCurrentUsername("elodie");
        return dao;
    }

    private static final class TrackingWatchHistoryPresenter
            implements GetWatchHistoryOutputBoundary {
        private String switchedTo = "";

        @Override
        public void prepareSuccessView(GetWatchHistoryOutputData response) {
            fail("Should not load list while switching views.");
        }

        @Override
        public void switchToPersonalAccountView() {
            switchedTo = "personal";
        }

        @Override
        public void switchToOtherAccountView() {
            switchedTo = "other";
        }
    }
}
