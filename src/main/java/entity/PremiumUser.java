package entity;

import java.util.List;

/**
 * Represents a premium user of the app. Includes bonus features exclusive to VIP.
 */

public class PremiumUser extends StandardUser implements Customizable {

    private String profileColour;

    public PremiumUser(String username, String displayName, String password,
                       String securityQuestion, String securityQuestionAnswer, UserLists userLists) {
        super(username, displayName, password, securityQuestion, securityQuestionAnswer, userLists);
        profileColour = "#FFFFFF";
    }


    @Override
    public UserLists getUserLists() {
        return userLists;
    }

    @Override
    public List<Integer> getWatchlist() {
        return userLists.getWatchlist();
    }

    @Override
    public List<Integer> getWatchHistory() {
        return userLists.getWatchHistory();
    }

    @Override
    public List<Integer> getReviews() {
        return userLists.getReviews();
    }

    @Override
    public List<String> getBlockedUsers() {
        return userLists.getBlockedUsers();
    }

    public void setProfileColour(String hexCode) {
        profileColour = hexCode;
    }

    @Override
    public void changeProfileColour(String hexCode) {
        setProfileColour(hexCode);
    }
}
