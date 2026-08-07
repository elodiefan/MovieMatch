package use_case.search_user;

import java.util.ArrayList;
import java.util.List;

import entity.User;

/** The Search User Interactor. */
public class SearchUserInteractor implements SearchUserInputBoundary {

    private static final String BLANK_KEYWORD_ERROR = "Enter a username or display name to search for.";

    private final SearchUserDataAccess searchUserDataAccess;
    private final SearchUserOutputBoundary userPresenter;

    public SearchUserInteractor(SearchUserDataAccess searchUserDataAccess,
                                SearchUserOutputBoundary searchUserOutputBoundary) {
        this.searchUserDataAccess = searchUserDataAccess;
        this.userPresenter = searchUserOutputBoundary;
    }

    /** Executes the Search User Use Case. */
    @Override
    public void execute(SearchUserInputData searchUserInputData) {
        final String keyword = searchUserInputData.getKeyword();

        if (keyword == null || keyword.trim().isEmpty()) {
            userPresenter.prepareFailView(BLANK_KEYWORD_ERROR);
        }
        else {
            final String trimmed = keyword.trim();
            final List<User> found = searchUserDataAccess.search(trimmed);
            userPresenter.prepareSuccessView(new SearchUserOutputData(toSummaries(found), trimmed));
        }
    }

    /** Drops everything about each user except the two fields a result row shows. */
    private List<UserSummary> toSummaries(List<User> users) {
        final List<UserSummary> summaries = new ArrayList<>();
        for (User user : users) {
            summaries.add(new UserSummary(user.getUsername(), user.getDisplayName()));
        }
        return summaries;
    }
}
