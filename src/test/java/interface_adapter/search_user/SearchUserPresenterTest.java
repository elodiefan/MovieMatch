package interface_adapter.search_user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.InMemoryUserDataAccessObject;
import entity.StandardUser;
import use_case.search_user.SearchUserInputBoundary;
import use_case.search_user.SearchUserInputData;
import use_case.search_user.SearchUserInteractor;

/**
 * Drives the real interactor, presenter and view model together over the
 * in-memory store, so everything between a typed keyword and the state the view
 * draws is covered without a database.
 */
class SearchUserPresenterTest {

    private SearchUserViewModel viewModel;
    private SearchUserInputBoundary interactor;

    @BeforeEach
    void setUp() {
        final InMemoryUserDataAccessObject dataAccess = new InMemoryUserDataAccessObject();
        dataAccess.save(new StandardUser("kiersten", "Kiersten", "pw", "q", "a"));
        dataAccess.save(new StandardUser("lily", "Lily Fan", "pw", "q", "a"));

        viewModel = new SearchUserViewModel();
        interactor = new SearchUserInteractor(dataAccess, new SearchUserPresenter(viewModel));
    }

    @Test
    void aMatchLandsInTheStateTheViewReads() {
        interactor.execute(new SearchUserInputData("kier"));

        final SearchUserState state = viewModel.getState();
        assertEquals(1, state.getResults().size());
        assertEquals("Kiersten", state.getResults().get(0).getDisplayName());
        assertNull(state.getSearchError());
        assertTrue(state.isSearched());
    }

    @Test
    void findingNobodyRecordsTheKeywordSoTheViewCanNameIt() {
        interactor.execute(new SearchUserInputData("nobody"));

        final SearchUserState state = viewModel.getState();
        assertTrue(state.getResults().isEmpty());
        assertEquals("nobody", state.getKeyword());
        assertTrue(state.isSearched(), "searching and finding nothing still counts as having searched");
        assertNull(state.getSearchError(), "no results is not an error");
    }

    @Test
    void aBlankKeywordSetsAnErrorAndLeavesSearchedFalse() {
        interactor.execute(new SearchUserInputData(""));

        final SearchUserState state = viewModel.getState();
        assertTrue(state.getSearchError() != null);
        assertFalse(state.isSearched(), "a refused search never ran");
    }

    @Test
    void aGoodSearchClearsAnEarlierError() {
        interactor.execute(new SearchUserInputData(""));
        assertTrue(viewModel.getState().getSearchError() != null);

        interactor.execute(new SearchUserInputData("lily"));
        assertNull(viewModel.getState().getSearchError(),
                "the stale error must not stay on screen next to good results");
    }
}
