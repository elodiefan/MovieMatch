package use_case.get_lists.get_watchlist;

import java.util.ArrayList;
import java.util.List;

import entity.MediaListItem;
import entity.UserLists;

/**
 * The Watchlist View Interactor.
 */

public class GetWatchlistInteractor implements GetWatchlistInputBoundary {

    private final GetWatchlistUserDataAccessInterface userDataAccessObject;
    private final GetWatchlistOutputBoundary getListsPresenter;

    public GetWatchlistInteractor(GetWatchlistUserDataAccessInterface userDataAccessInterface,
                                  GetWatchlistOutputBoundary getWatchListOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.getListsPresenter = getWatchListOutputBoundary;
    }

    @Override
    public void execute(GetWatchlistInputData getWatchlistInputData) {
        final String username = getWatchlistInputData.getUsername();
        final String displayName = getWatchlistInputData.getDisplayName();
        final UserLists userLists = userDataAccessObject.getLists(username);
        final String watchlist = userLists.getWatchlist();
        final GetWatchlistOutputData getWatchlistOutputData = new GetWatchlistOutputData(username,
                displayName, watchlist, toWatchlistItemData(userLists.getWatchlistItems()));
        getListsPresenter.prepareSuccessView(getWatchlistOutputData);
    }

    private List<WatchlistItemData> toWatchlistItemData(
            List<MediaListItem> mediaListItems) {
        final List<WatchlistItemData> itemData = new ArrayList<>();
        for (MediaListItem item : mediaListItems) {
            itemData.add(new WatchlistItemData(item.getMediaId(),
                    item.getMediaType(), item.getMediaTitle(),
                    item.getLoggedAt(), item.getPosterPath()));
        }
        return itemData;
    }

    /**
     * Switches from list view to account view.
     *
     * @param getWatchlistInputData the get lists input data
     */
    @Override
    public void switchToAccountView(GetWatchlistInputData getWatchlistInputData) {
        if (userDataAccessObject.getCurrentUsername().equals(getWatchlistInputData.getUsername())) {
            getListsPresenter.switchToPersonalAccountView();
        }
        else {
            getListsPresenter.switchToOtherAccountView();
        }
    }
}
