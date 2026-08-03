package entity;

import java.util.ArrayList;

/**
 * Represents a premium user of the app. Includes bonus features exclusive to VIP.
 */

public class PremiumUser extends StandardUser implements Customizable {

    private String profileColour;

    public PremiumUser(String username, String displayName, String password,
                       String securityQuestion, String answer) {
        super(username, displayName, password, securityQuestion, answer);
        final UserLists userLists = new UserLists(username, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        setUserLists(userLists);
        profileColour = "#FFFFFF";
    }

    public PremiumUser(String username, String displayName, String password,
                       String securityQuestion, String answer, UserLists userLists) {
        super(username, displayName, password, securityQuestion, answer, userLists);
        profileColour = "#FFFFFF";
    }

    public void setProfileColour(String hexCode) {
        profileColour = hexCode;
    }

    @Override
    public void changeProfileColour(String hexCode) {
        setProfileColour(hexCode);
    }
}
