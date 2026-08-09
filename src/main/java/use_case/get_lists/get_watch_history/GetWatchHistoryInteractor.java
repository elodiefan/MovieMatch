package use_case.get_lists.get_watch_history;

import java.util.ArrayList;
import java.util.List;

import entity.MediaListItem;
import entity.UserLists;

/**
 * The Watch History View Interactor.
 */

public class GetWatchHistoryInteractor implements GetWatchHistoryInputBoundary {

    private final GetWatchHistoryUserDataAccessInterface userDataAccessObject;
    private final GetWatchHistoryOutputBoundary getListsPresenter;

    public GetWatchHistoryInteractor(GetWatchHistoryUserDataAccessInterface userDataAccessInterface,
                                  GetWatchHistoryOutputBoundary getWatchHistoryOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.getListsPresenter = getWatchHistoryOutputBoundary;
    }

    @Override
    public void execute(GetWatchHistoryInputData getListsInputData) {
        final String username = getListsInputData.getUsername();
        final String displayName = getListsInputData.getDisplayName();
        final UserLists userLists = userDataAccessObject.getLists(username);
        final String watchHistory = userLists.getWatchHistory();
        final GetWatchHistoryOutputData getWatchHistoryOutputData = new GetWatchHistoryOutputData(username,
                displayName, watchHistory, toWatchHistoryItemData(
                        userLists.getWatchHistoryItems()));
        getListsPresenter.prepareSuccessView(getWatchHistoryOutputData);
    }

    private List<WatchHistoryItemData> toWatchHistoryItemData(
            List<MediaListItem> mediaListItems) {
        final List<WatchHistoryItemData> itemData = new ArrayList<>();
        for (MediaListItem item : mediaListItems) {
            itemData.add(new WatchHistoryItemData(item.getMediaId(),
                    item.getMediaType(), item.getMediaTitle(),
                    item.getLoggedAt(), item.getPosterPath()));
        }
        return itemData;
    }

    /**
     * Switches from list view to account view.
     *
     * @param getListsInputData the get lists input data
     */
    @Override
    public void switchToAccountView(GetWatchHistoryInputData getListsInputData) {
        if (userDataAccessObject.getCurrentUsername().equals(getListsInputData.getUsername())) {
            getListsPresenter.switchToPersonalAccountView();
        }
        else {
            getListsPresenter.switchToOtherAccountView();
        }
    }
}
