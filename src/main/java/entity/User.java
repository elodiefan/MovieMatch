package entity;

/**
 * Interface representing a user of the app.
 */

public interface User {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getUsername();

    /**
     * Returns the display name of the user.
     * @return the display name of the user.
     */
    String getDisplayName();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the chosen security question of the user.
     * @return the chosen security question of the user.
     */
    String getSecurityQuestion();

    /**
     * Returns the answer to the user's chosen security question.
     * @return the answer to the user's chosen security question.
     */
    String getSecurityQuestionAnswer();
}
