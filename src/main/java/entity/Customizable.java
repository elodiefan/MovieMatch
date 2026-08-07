package entity;

/** Represents user accounts that support profile customization. */

public interface Customizable {

    /** Changes the colour of VIP user's profile. */
    void changeProfileColour(String hexCode);
}
