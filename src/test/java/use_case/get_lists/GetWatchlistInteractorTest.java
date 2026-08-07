package use_case.get_lists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import data_access.InMemoryUserDataAccessObject;
import entity.StandardUser;
import entity.User;
import entity.UserLists;
import org.junit.jupiter.api.Test;
import use_case.get_lists.get_watchlist.GetWatchlistInputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInteractor;
import use_case.get_lists.get_watchlist.GetWatchlistOutputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistOutputData;

/**
 * Tests for the get watchlist interactor.
 */
class GetWatchlistInteractorTest {

    @Test
    void successLoadsWatchlist() {
        final InMemoryUserDataAccessObject dao = makeDao();
        final GetWatchlistOutputBoundary presenter =
                new GetWatchlistOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetWatchlistOutputData response) {
                        assertEquals("elodie", response.getUsername());
                        assertEquals("Elodie", response.getDisplayName());
                        assertEquals("Fight Club -- today\n",
                                response.getWatchlist());
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

        final GetWatchlistInputBoundary interactor =
                new GetWatchlistInteractor(dao, presenter);
        interactor.execute(new GetListsInputData("elodie", "Elodie"));
    }

    @Test
    void switchToAccountViewUsesPersonalAccountForCurrentUser() {
        final InMemoryUserDataAccessObject dao = makeDao();
        final GetWatchlistOutputBoundary presenter =
                new TrackingWatchlistPresenter();
        final GetWatchlistInputBoundary interactor =
                new GetWatchlistInteractor(dao, presenter);

        interactor.switchToAccountView(new GetListsInputData("elodie",
                "Elodie"));

        assertEquals("personal",
                ((TrackingWatchlistPresenter) presenter).switchedTo);
    }

    @Test
    void switchToAccountViewUsesOtherAccountForDifferentUser() {
        final InMemoryUserDataAccessObject dao = makeDao();
        final GetWatchlistOutputBoundary presenter =
                new TrackingWatchlistPresenter();
        final GetWatchlistInputBoundary interactor =
                new GetWatchlistInteractor(dao, presenter);

        interactor.switchToAccountView(new GetListsInputData("lily",
                "Lily"));

        assertEquals("other",
                ((TrackingWatchlistPresenter) presenter).switchedTo);
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

    private static final class TrackingWatchlistPresenter
            implements GetWatchlistOutputBoundary {
        private String switchedTo = "";

        @Override
        public void prepareSuccessView(GetWatchlistOutputData response) {
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
