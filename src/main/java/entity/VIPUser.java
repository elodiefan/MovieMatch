package entity;

/**
 * Represents a VIP user of the app. Includes bonus features exclusive to VIP.
 */

public class VIPUser extends StandardUser implements Customizable {

    private String profileColour;

    public VIPUser(String username, String displayName, String password) {
        super(username, displayName, password);
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
