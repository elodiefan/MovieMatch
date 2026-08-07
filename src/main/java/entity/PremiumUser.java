package entity;

/** Represents a premium user of the app. */

public class PremiumUser extends StandardUser implements Customizable {

    private String profileColour;

    public PremiumUser(String username, String displayName, String password,
                       String securityQuestion, String answer) {
        super(username, displayName, password, securityQuestion, answer);
        final UserLists userLists = new UserLists(username, "", "", "");
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
