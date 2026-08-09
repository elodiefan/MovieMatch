package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MediaListItemTest {

    @Test
    void gettersReturnTheSavedMediaInformation() {
        final MediaListItem item = new MediaListItem(
                101, "movie", "Arrival", "2026-08-08", "/arrival.jpg");

        assertEquals(101, item.getMediaId());
        assertEquals("movie", item.getMediaType());
        assertEquals("Arrival", item.getMediaTitle());
        assertEquals("2026-08-08", item.getLoggedAt());
        assertEquals("/arrival.jpg", item.getPosterPath());
    }
}
