package interface_adapter.get_lists;

import static org.junit.jupiter.api.Assertions.assertEquals;

import interface_adapter.ViewManagerModel;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountViewModel;
import org.junit.jupiter.api.Test;
import use_case.get_lists.get_watch_history.GetWatchHistoryOutputData;
import use_case.get_lists.get_watchlist.GetWatchlistOutputData;

/**
 * Tests for the get lists presenter.
 */
class GetListsPresenterTest {

    @Test
    void prepareSuccessViewForWatchlistUpdatesListStateAndSwitchesView() {
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final GetListsViewModel getListsViewModel = new GetListsViewModel();
        final GetListsPresenter presenter = new GetListsPresenter(
                viewManagerModel, getListsViewModel,
                new PersonalAccountViewModel(), new OtherAccountViewModel());

        presenter.prepareSuccessView(new GetWatchlistOutputData("elodie",
                "Elodie", "Fight Club -- today\n"));

        assertEquals("elodie", getListsViewModel.getState().getUsername());
        assertEquals("Elodie", getListsViewModel.getState().getDisplayName());
        assertEquals("Fight Club -- today\n",
                getListsViewModel.getState().getDisplayText());
        assertEquals(getListsViewModel.getViewName(),
                viewManagerModel.getState());
    }

    @Test
    void prepareSuccessViewForWatchHistoryUpdatesListStateAndSwitchesView() {
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final GetListsViewModel getListsViewModel = new GetListsViewModel();
        final GetListsPresenter presenter = new GetListsPresenter(
                viewManagerModel, getListsViewModel,
                new PersonalAccountViewModel(), new OtherAccountViewModel());

        presenter.prepareSuccessView(new GetWatchHistoryOutputData("elodie",
                "Elodie", "Dune -- yesterday\n"));

        assertEquals("elodie", getListsViewModel.getState().getUsername());
        assertEquals("Elodie", getListsViewModel.getState().getDisplayName());
        assertEquals("Dune -- yesterday\n",
                getListsViewModel.getState().getDisplayText());
        assertEquals(getListsViewModel.getViewName(),
                viewManagerModel.getState());
    }

    @Test
    void switchToPersonalAccountViewCopiesUsernameAndSwitchesView() {
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final GetListsViewModel getListsViewModel = new GetListsViewModel();
        final PersonalAccountViewModel personalAccountViewModel =
                new PersonalAccountViewModel();
        final GetListsState state = getListsViewModel.getState();
        state.setUsername("elodie");
        getListsViewModel.setState(state);
        final GetListsPresenter presenter = new GetListsPresenter(
                viewManagerModel, getListsViewModel,
                personalAccountViewModel, new OtherAccountViewModel());

        presenter.switchToPersonalAccountView();

        assertEquals("elodie", personalAccountViewModel.getState()
                .getUsername());
        assertEquals(personalAccountViewModel.getViewName(),
                viewManagerModel.getState());
    }
}
