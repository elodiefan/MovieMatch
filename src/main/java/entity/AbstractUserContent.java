package entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents content created by a user, such as a review or comment.
 */
public abstract class AbstractUserContent {

    private static final ZoneId TORONTO_ZONE = ZoneId.of("America/Toronto");
    private final String contentId;
    private final String authorUsername;
    private final String authorDisplayName;
    private final ZonedDateTime createdAt;
    private final Set<String> likedByUsernames;

    /**
     * Creates user-generated content.
     * @param contentId the unique identifier for this content
     * @param authorUsername the username of the content author
     * @param authorDisplayName the display name of the content author
     * @param createdAt the time this content was created
     * @param likedByUsernames the usernames of users who liked this content
     */
    public AbstractUserContent(String contentId, String authorUsername, String authorDisplayName,
                               ZonedDateTime createdAt, Set<String> likedByUsernames) {
        this.contentId = contentId;
        this.authorUsername = authorUsername;
        this.authorDisplayName = authorDisplayName;
        this.createdAt = createdAt;
        this.likedByUsernames = new HashSet<>(likedByUsernames);
    }

    /**
     * Returns the current date and time in Toronto.
     * @return the current Toronto date and time
     */
    public static ZonedDateTime getCurrentTorontoTime() {
        return ZonedDateTime.now(TORONTO_ZONE);
    }

    /**
     * Returns the unique identifier for this content.
     * @return the content id
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * Returns the username of the content author.
     * @return the author's username
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Returns the display name of the content author.
     * @return the author's display name
     */
    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    /**
     * Returns the time this content was created.
     * @return the creation time
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the usernames of users who liked this content.
     * @return a copy of the liked-by usernames
     */
    public Set<String> getLikedByUsernames() {
        return new HashSet<>(likedByUsernames);
    }

    /**
     * Returns the number of likes on this content.
     * @return the like count
     */
    public int getLikeCount() {
        return likedByUsernames.size();
    }

    /**
     * Adds a like from a user.
     * @param username the username liking this content
     */
    public void like(String username) {
        likedByUsernames.add(username);
    }

    /**
     * Removes a like from a user.
     * @param username the username unliking this content
     */
    public void unlike(String username) {
        likedByUsernames.remove(username);
    }
}
