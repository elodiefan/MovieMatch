package use_case.log_media;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.OffsetDateTime;

import data_access.InMemoryUserDataAccessObject;
import entity.StandardUser;
import org.junit.jupiter.api.Test;

/**
 * Tests for the log media interactor.
 */
class LogMediaInteractorTest {

    @Test
    void successAddsMediaToWatchlist() {
        final InMemoryUserDataAccessObject dao = makeLoggedInDao();
        final LogMediaInputData inputData = new LogMediaInputData(550,
                " movie ", " Fight Club ");
        final LogMediaOutputBoundary presenter = new LogMediaOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMediaOutputData outputData) {
                assertEquals("Added to watchlist.", outputData.getMessage());
                assertEquals(" Fight Club ", outputData.getMediaTitle());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Logging to watchlist should have succeeded.");
            }
        };

        final LogMediaInputBoundary interactor =
                new LogMediaInteractor(dao, presenter);
        interactor.addToWatchlist(inputData);

        final String watchlist = dao.getLists("elodie").getWatchlist();
        assertTrue(watchlist.startsWith("Fight Club -- "));
        assertFalse(watchlist.contains("  Fight Club  "));
        OffsetDateTime.parse(watchlist.substring("Fight Club -- ".length())
                .trim());
    }

    @Test
    void successAddsMediaToWatchHistory() {
        final InMemoryUserDataAccessObject dao = makeLoggedInDao();
        final LogMediaInputData inputData = new LogMediaInputData(550,
                "movie", "Fight Club");
        final LogMediaOutputBoundary presenter = new LogMediaOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMediaOutputData outputData) {
                assertEquals("Added to watch history.",
                        outputData.getMessage());
                assertEquals("Fight Club", outputData.getMediaTitle());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Logging to watch history should have succeeded.");
            }
        };

        final LogMediaInputBoundary interactor =
                new LogMediaInteractor(dao, presenter);
        interactor.addToWatchHistory(inputData);

        final String watchHistory = dao.getLists("elodie").getWatchHistory();
        assertTrue(watchHistory.startsWith("Fight Club -- "));
        OffsetDateTime.parse(watchHistory.substring("Fight Club -- ".length())
                .trim());
    }

    @Test
    void failureWhenNoUserIsLoggedIn() {
        final InMemoryUserDataAccessObject dao =
                new InMemoryUserDataAccessObject();
        final LogMediaOutputBoundary presenter = new LogMediaOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMediaOutputData outputData) {
                fail("Logging should have failed.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Please log in before saving media.", error);
            }
        };

        final LogMediaInputBoundary interactor =
                new LogMediaInteractor(dao, presenter);
        interactor.addToWatchlist(new LogMediaInputData(550, "movie",
                "Fight Club"));
    }

    @Test
    void failureWhenMediaTitleIsMissing() {
        final InMemoryUserDataAccessObject dao = makeLoggedInDao();
        final LogMediaOutputBoundary presenter = new LogMediaOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMediaOutputData outputData) {
                fail("Logging should have failed.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Media title is missing.", error);
            }
        };

        final LogMediaInputBoundary interactor =
                new LogMediaInteractor(dao, presenter);
        interactor.addToWatchlist(new LogMediaInputData(550, "movie", " "));
    }

    @Test
    void failureWhenNoMediaIsSelected() {
        final InMemoryUserDataAccessObject dao = makeLoggedInDao();
        final LogMediaOutputBoundary presenter = new LogMediaOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMediaOutputData outputData) {
                fail("Logging should have failed.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("No media selected.", error);
            }
        };

        final LogMediaInputBoundary interactor =
                new LogMediaInteractor(dao, presenter);
        interactor.addToWatchHistory(new LogMediaInputData(-1, "movie",
                "Fight Club"));
    }

    private InMemoryUserDataAccessObject makeLoggedInDao() {
        final InMemoryUserDataAccessObject dao =
                new InMemoryUserDataAccessObject();
        dao.save(new StandardUser("elodie", "Elodie", "password",
                "Question?", "Answer"));
        dao.setCurrentUsername("elodie");
        return dao;
    }
}
