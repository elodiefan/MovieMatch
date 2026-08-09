package database;

import entity.StandardUserFactory;
import entity.User;

/**
 * Runnable check that the MongoDB connection and the change-password code work.
 * <p>
 * <strong>How to run:</strong> right-click this file in IntelliJ and choose
 * "Run MongoConnectionCheck.main()". You need a {@code mongo.properties} file in
 * the project root first â€” see the MongoDB guide for what goes in it.
 * <p>
 * It uses its own throwaway account ({@value #TEST_USERNAME}) so it never
 * touches real user data, and deletes nothing: run it as often as you like.
 */
public final class MongoConnectionCheck {

    private static final String TEST_USERNAME = "test-user";
    private static final String ORIGINAL_PASSWORD = "originalPass";
    private static final String NEW_PASSWORD = "changedPass";

    private MongoConnectionCheck() {
        // Utility class: not meant to be instantiated.
    }

    /**
     * Runs the check.
     * @param args ignored
     */
    public static void main(String[] args) {
        System.out.println("Connecting to MongoDB Atlas...");

        // Declared as the interface, so swapping to InMemoryUserDataAccessObject
        // below would be the only change needed to run this without a network.
        final UserDataAccessObject dao = new MongoUserDataAccessObject();

        try {
            System.out.println("Connected.\n");

            // 1. Make sure the test account exists, with a known password.
            dao.save(new StandardUserFactory().create(TEST_USERNAME, "Test User",
                    ORIGINAL_PASSWORD, "What is your pet's name?", "Fido"));
            System.out.println("1. saved '" + TEST_USERNAME + "' with password: " + ORIGINAL_PASSWORD);

            // 2. Read it back out of the database.
            final User saved = dao.get(TEST_USERNAME);
            System.out.println("2. read back from Atlas:");
            System.out.println("      username         = " + saved.getUsername());
            System.out.println("      displayName      = " + saved.getDisplayName());
            System.out.println("      password         = " + saved.getPassword());
            System.out.println("      securityQuestion = " + saved.getSecurityQuestion());
            System.out.println("      answer           = " + saved.getAnswer());

            // 3. Change the password â€” the part this branch is really testing.
            dao.changePassword(TEST_USERNAME, NEW_PASSWORD);
            System.out.println("3. changed password to: " + NEW_PASSWORD);

            // 4. Read it again to prove the change reached the database.
            final String stored = dao.get(TEST_USERNAME).getPassword();
            System.out.println("4. password now in Atlas: " + stored);

            if (NEW_PASSWORD.equals(stored)) {
                System.out.println("\nPASS - the password change was saved to MongoDB.");
            }
            else {
                System.out.println("\nFAIL - expected '" + NEW_PASSWORD + "' but found '" + stored + "'.");
            }

            // 5. Check a username that does not exist.
            System.out.println("\nextra checks:");
            System.out.println("   existsByName('" + TEST_USERNAME + "') = " + dao.existsByName(TEST_USERNAME));
            System.out.println("   existsByName('no-such-user')  = " + dao.existsByName("no-such-user"));
            System.out.println("   get('no-such-user')           = " + dao.get("no-such-user"));
        }
        catch (RuntimeException ex) {
            System.out.println("\nSomething went wrong: " + ex.getMessage());
            System.out.println("If this is an SSLException, you are probably on a network that "
                    + "blocks MongoDB. Try a phone hotspot - see the troubleshooting section of "
                    + "the MongoDB guide.");
        }
        finally {
            dao.close();
        }
    }
}
