package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import entity.Genre;
import entity.Media;
import entity.Movie;
import entity.TVShow;
import use_case.recommendation.MediaCatalogueDataAccessInterface;

/** A small fixed catalogue, so the recommendation feature can run before the real one exists. */
public class SeedMediaCatalogue implements MediaCatalogueDataAccessInterface {

    private static final Genre ACTION = new Genre(28, "Action");
    private static final Genre ADVENTURE = new Genre(12, "Adventure");
    private static final Genre COMEDY = new Genre(35, "Comedy");
    private static final Genre DRAMA = new Genre(18, "Drama");
    private static final Genre HORROR = new Genre(27, "Horror");
    private static final Genre ROMANCE = new Genre(10749, "Romance");
    private static final Genre SCI_FI = new Genre(878, "Science Fiction");
    private static final Genre THRILLER = new Genre(53, "Thriller");

    private static final String ENGLISH = "en";
    private static final String KOREAN = "ko";

    private final List<Media> catalogue;

    /** Builds the seed catalogue. */
    public SeedMediaCatalogue() {
        this.catalogue = createSeedData();
    }

    /** Returns every title in the catalogue. */
    public List<Media> getAll() {
        return Collections.unmodifiableList(this.catalogue);
    }

    @Override
    public List<Media> findCandidates(final Set<Genre> genres, final Set<Integer> excludeMediaIds) {
        final List<Media> matches = new ArrayList<>();
        for (final Media media : this.catalogue) {
            if (!excludeMediaIds.contains(media.getID()) && sharesGenre(media, genres)) {
                matches.add(media);
            }
        }
        return matches;
    }

    @Override
    public Media findById(final int mediaId) {
        Media found = null;
        for (final Media media : this.catalogue) {
            if (media.getID() == mediaId) {
                found = media;
                break;
            }
        }
        return found;
    }

    /** Reports whether a title is worth offering for the given genres. */
    private static boolean sharesGenre(final Media media, final Set<Genre> genres) {
        return genres.isEmpty() || !Collections.disjoint(media.getGenres(), genres);
    }

    /** Creates the fixed set of titles. */
    private static List<Media> createSeedData() {
        final List<Media> seed = new ArrayList<>();

        seed.add(new Movie(101, "Dune: Part Two", 2024, 8.2,
                Arrays.asList(SCI_FI, ADVENTURE), ENGLISH,
                Arrays.asList("Denis Villeneuve", "Timothee Chalamet", "Zendaya"), 166));
        seed.add(new Movie(102, "Arrival", 2016, 7.6,
                Arrays.asList(SCI_FI, DRAMA), ENGLISH,
                Arrays.asList("Denis Villeneuve", "Amy Adams", "Jeremy Renner"), 116));
        seed.add(new Movie(103, "Sicario", 2015, 7.6,
                Arrays.asList(THRILLER, ACTION), ENGLISH,
                Arrays.asList("Denis Villeneuve", "Emily Blunt", "Benicio del Toro"), 121));
        seed.add(new Movie(104, "Everything Everywhere All at Once", 2022, 7.8,
                Arrays.asList(SCI_FI, COMEDY, ADVENTURE), ENGLISH,
                Arrays.asList("Michelle Yeoh", "Ke Huy Quan", "Stephanie Hsu"), 139));
        seed.add(new Movie(105, "Parasite", 2019, 8.5,
                Arrays.asList(THRILLER, DRAMA), KOREAN,
                Arrays.asList("Bong Joon-ho", "Song Kang-ho", "Choi Woo-shik"), 132));
        seed.add(new Movie(106, "Get Out", 2017, 7.7,
                Arrays.asList(HORROR, THRILLER), ENGLISH,
                Arrays.asList("Jordan Peele", "Daniel Kaluuya", "Allison Williams"), 104));
        seed.add(new Movie(107, "Past Lives", 2023, 7.8,
                Arrays.asList(ROMANCE, DRAMA), ENGLISH,
                Arrays.asList("Celine Song", "Greta Lee", "Teo Yoo"), 105));
        seed.add(new Movie(108, "Mad Max: Fury Road", 2015, 7.6,
                Arrays.asList(ACTION, ADVENTURE, SCI_FI), ENGLISH,
                Arrays.asList("George Miller", "Tom Hardy", "Charlize Theron"), 120));
        seed.add(new Movie(109, "The Grand Budapest Hotel", 2014, 8.1,
                Arrays.asList(COMEDY, DRAMA), ENGLISH,
                Arrays.asList("Wes Anderson", "Ralph Fiennes", "Tony Revolori"), 99));
        seed.add(new Movie(110, "Blade Runner 2049", 2017, 7.5,
                Arrays.asList(SCI_FI, THRILLER), ENGLISH,
                Arrays.asList("Denis Villeneuve", "Ryan Gosling", "Harrison Ford"), 164));
        // Deliberately has no cast listed, the way sparse catalogue entries do.
        // Proves the cast factor scores zero rather than dividing by zero.
        seed.add(new Movie(111, "Uncatalogued Indie", 2021, 6.4,
                Collections.singletonList(DRAMA), ENGLISH, new ArrayList<>(), 92));

        seed.add(new TVShow(201, "Severance", 2022, 8.7,
                Arrays.asList(SCI_FI, THRILLER, DRAMA), ENGLISH,
                Arrays.asList("Ben Stiller", "Adam Scott", "Britt Lower"), 2, 19));
        seed.add(new TVShow(202, "The Bear", 2022, 8.4,
                Arrays.asList(DRAMA, COMEDY), ENGLISH,
                Arrays.asList("Christopher Storer", "Jeremy Allen White", "Ayo Edebiri"), 3, 28));
        seed.add(new TVShow(203, "Squid Game", 2021, 7.8,
                Arrays.asList(THRILLER, DRAMA, ACTION), KOREAN,
                Arrays.asList("Hwang Dong-hyuk", "Lee Jung-jae", "Park Hae-soo"), 2, 16));
        seed.add(new TVShow(204, "The Last of Us", 2023, 8.6,
                Arrays.asList(DRAMA, HORROR, SCI_FI), ENGLISH,
                Arrays.asList("Craig Mazin", "Pedro Pascal", "Bella Ramsey"), 1, 9));

        return seed;
    }
}
