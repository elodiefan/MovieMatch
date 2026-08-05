package use_case.account;

import use_case.get_lists.GetListsInputData;

public class AccountViewManager {

    /**
     * Executes the get lists view use case.
     */
    public void switchToGetListsView() {
        String username ;
        GetListsInputData getListsInputData = new GetListsInputData();
        getWatchlistInteractor.execute(getListsInputData);
    }
}
