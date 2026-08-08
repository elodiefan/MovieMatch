package entity;

/**
 * Represents user accounts that support profile customization.
 */

public interface Customizable {

    /**
     * Changes the colour of VIP user's profile.
     * @param hexCode the chosen colour for the profile.
     */
    void changeProfileColour(String hexCode);
}
