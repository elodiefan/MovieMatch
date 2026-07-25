package entity;

/**
 * Represents a premium user of the app. Includes bonus features exclusive to VIP.
 */

public class PremiumUser extends StandardUser implements Customizable {

    private String profileColour;

    public PremiumUser(String username, String displayName, String password,
                       String securityQuestion, String securityQuestionAnswer) {
        super(username, displayName, password, securityQuestion, securityQuestionAnswer);
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
