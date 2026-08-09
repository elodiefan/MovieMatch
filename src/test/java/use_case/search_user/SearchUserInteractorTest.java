package use_case.search_user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.StandardUserFactory;
import entity.User;

/**
 * Tests for the Search User Interactor.
 */
class SearchUserInteractorTest {

    /**
     * Records what the interactor searched for and returns whatever it was given.
     */
    private static class FakeDataAccess implements SearchUserDataAccess {
        private final List<User> toReturn;
        private String receivedKeyword;

        FakeDataAccess(List<User> toReturn) {
            this.toReturn = toReturn;
        }

        @Override
        public List<User> search(String keyword) {
            this.receivedKeyword = keyword;
            return toReturn;
        }
    }

    /**
     * Records which presenter method the interactor called, and with what.
     */
    private static class RecordingPresenter implements SearchUserOutputBoundary {
        private SearchUserOutputData successData;
        private String failError;

        @Override
        public void prepareSuccessView(SearchUserOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            this.failError = error;
        }
    }

    private static User user(String username, String displayName) {
        return new StandardUserFactory().create(
                username, displayName, "hunter2", "First pet?", "rex");
    }

    @Test
    void blankKeywordFailsAndNeverReachesTheDatabase() {
        final FakeDataAccess dataAccess = new FakeDataAccess(new ArrayList<>());
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchUserInteractor(dataAccess, presenter).execute(new SearchUserInputData("   "));

        assertTrue(presenter.failError != null, "a blank keyword should fail");
        assertTrue(presenter.successData == null, "a blank keyword should not succeed");
        assertTrue(dataAccess.receivedKeyword == null, "a blank keyword should not hit the store");
    }

    @Test
    void nullKeywordFails() {
        final FakeDataAccess dataAccess = new FakeDataAccess(new ArrayList<>());
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchUserInteractor(dataAccess, presenter).execute(new SearchUserInputData(null));

        assertTrue(presenter.failError != null, "a null keyword should fail rather than throw");
    }

    @Test
    void keywordIsTrimmedBeforeSearching() {
        final FakeDataAccess dataAccess = new FakeDataAccess(new ArrayList<>());
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchUserInteractor(dataAccess, presenter).execute(new SearchUserInputData("  kiersten  "));

        assertEquals("kiersten", dataAccess.receivedKeyword);
    }

    @Test
    void findingNobodyIsSuccessNotFailure() {
        final FakeDataAccess dataAccess = new FakeDataAccess(new ArrayList<>());
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchUserInteractor(dataAccess, presenter).execute(new SearchUserInputData("nobody"));

        assertTrue(presenter.failError == null, "an empty result is not an error");
        assertTrue(presenter.successData != null, "an empty result is still a successful search");
        assertTrue(presenter.successData.getResults().isEmpty());
        assertEquals("nobody", presenter.successData.getKeyword(),
                "the view needs the keyword to say what it found nothing for");
    }

    @Test
    void resultsAreMappedToSummariesKeepingOrder() {
        final FakeDataAccess dataAccess = new FakeDataAccess(Arrays.asList(
                user("kiersten", "Kiersten"),
                user("lily", "Lily")));
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchUserInteractor(dataAccess, presenter).execute(new SearchUserInputData("i"));

        final List<UserSummary> results = presenter.successData.getResults();
        assertEquals(2, results.size());
        assertEquals("kiersten", results.get(0).getUsername());
        assertEquals("Kiersten", results.get(0).getDisplayName());
        assertEquals("lily", results.get(1).getUsername());
    }

    /**
     * The point of {@link UserSummary}: whatever the data store hands back, only
     * the username and display name are allowed past the output boundary. If
     * someone later widens it to carry a whole {@code User}, this fails.
     */
    @Test
    void resultsCarryNoCredentials() {
        final FakeDataAccess dataAccess = new FakeDataAccess(
                Arrays.asList(user("enzo", "Enzo")));
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchUserInteractor(dataAccess, presenter).execute(new SearchUserInputData("enzo"));

        final Class<?> resultType = presenter.successData.getResults().get(0).getClass();
        final List<String> methodNames = new ArrayList<>();
        for (java.lang.reflect.Method method : resultType.getMethods()) {
            methodNames.add(method.getName());
        }
        assertFalse(methodNames.contains("getPassword"), "search results must not expose passwords");
        assertFalse(methodNames.contains("getAnswer"), "search results must not expose security answers");
        assertFalse(methodNames.contains("getSecurityQuestion"),
                "search results must not expose security questions");
    }
}
