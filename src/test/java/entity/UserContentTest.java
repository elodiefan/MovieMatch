package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class UserContentTest {

    private static final class TestContentAbstract extends AbstractUserContent {
        TestContentAbstract(String contentId, String authorUsername, String authorDisplayName,
                            ZonedDateTime createdAt, Set<String> likedByUsernames) {
            super(contentId, authorUsername, authorDisplayName, createdAt, likedByUsernames);
        }
    }

    @Test
    void gettersReturnContentInformation() {
        final ZonedDateTime createdAt = ZonedDateTime.of(
                2026, 8, 8, 12, 0, 0, 0, ZoneId.of("America/Toronto"));
        final TestContentAbstract content = new TestContentAbstract(
                "content-1", "bob", "Bob", createdAt, Set.of("alice"));

        assertEquals("content-1", content.getContentId());
        assertEquals("bob", content.getAuthorUsername());
        assertEquals("Bob", content.getAuthorDisplayName());
        assertEquals(createdAt, content.getCreatedAt());
        assertEquals(Set.of("alice"), content.getLikedByUsernames());
        assertEquals(1, content.getLikeCount());
    }

    @Test
    void likesCanBeAddedAndRemovedWithoutDuplicates() {
        final TestContentAbstract content = contentWithNoLikes();

        content.like("alice");
        content.like("alice");
        assertEquals(1, content.getLikeCount());

        content.unlike("alice");
        assertEquals(0, content.getLikeCount());
    }

    @Test
    void likeCollectionsAreDefensivelyCopied() {
        final Set<String> original = new HashSet<>(Set.of("alice"));
        final TestContentAbstract content = new TestContentAbstract(
                "content-1", "bob", "Bob", AbstractUserContent.getCurrentTorontoTime(), original);

        original.add("charlie");
        final Set<String> returned = content.getLikedByUsernames();
        returned.add("david");

        assertEquals(Set.of("alice"), content.getLikedByUsernames());
    }

    @Test
    void currentTimeUsesTorontoZone() {
        final ZonedDateTime currentTime = AbstractUserContent.getCurrentTorontoTime();

        assertEquals(ZoneId.of("America/Toronto"), currentTime.getZone());
        assertTrue(currentTime.isBefore(ZonedDateTime.now(currentTime.getZone()).plusSeconds(1)));
        assertFalse(currentTime.isBefore(ZonedDateTime.now(currentTime.getZone()).minusSeconds(1)));
    }

    private static TestContentAbstract contentWithNoLikes() {
        return new TestContentAbstract("content-1", "bob", "Bob",
                AbstractUserContent.getCurrentTorontoTime(), Set.of());
    }
}
