package use_case.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Media;
import entity.Movie;

class MediaPageTest {

    @Test
    void constructorStoresMediaAndPaginationMetadata() {
        final Movie movie = movie(1, "Arrival");
        final MediaPage page = new MediaPage(List.of(movie), 5, 87);

        assertEquals(List.of(movie), page.getMedia());
        assertEquals(5, page.getTotalPages());
        assertEquals(87, page.getTotalResults());
    }

    @Test
    void constructorAndGetterDefensivelyCopyMediaList() {
        final List<Media> source = new ArrayList<>();
        source.add(movie(1, "Arrival"));
        final MediaPage page = new MediaPage(source, 1, 1);
        source.clear();

        final List<Media> firstRead = page.getMedia();
        firstRead.clear();

        assertEquals(1, page.getMedia().size());
        assertNotSame(firstRead, page.getMedia());
    }

    @Test
    void emptyPageIsRepresentedWithoutInventingResults() {
        final MediaPage page = new MediaPage(List.of(), 0, 0);

        assertTrue(page.getMedia().isEmpty());
        assertEquals(0, page.getTotalPages());
        assertEquals(0, page.getTotalResults());
    }

    private static Movie movie(int id, String title) {
        return new Movie(id, title, 2016, 8.0, new ArrayList<>(),
                "en", new ArrayList<>(), 116);
    }
}
