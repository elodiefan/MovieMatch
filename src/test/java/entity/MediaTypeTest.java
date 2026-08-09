package entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MediaTypeTest {

    @Test
    void enumContainsOnlySupportedMediaTypesInExpectedOrder() {
        assertArrayEquals(
                new MediaType[]{MediaType.MOVIE, MediaType.TV_SHOW},
                MediaType.values()
        );
    }

    @Test
    void enumConstantsCanBeReadByStoredName() {
        assertEquals(MediaType.MOVIE, MediaType.valueOf("MOVIE"));
        assertEquals(MediaType.TV_SHOW, MediaType.valueOf("TV_SHOW"));
    }
}
