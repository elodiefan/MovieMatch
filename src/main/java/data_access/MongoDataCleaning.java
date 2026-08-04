package data_access;

import entity.UserLists;
import org.bson.Document;

import java.util.List;

/**
 * Class used to clean data for packaging as entities.
 */
public class MongoDataCleaning {

    private static final String MEDIA_TITLE = "mediaTitle";
    private static final String ADDED_AT = "addedAt";
    private static final int INDEX_OF_DATE = 10;
    private static final String NEW_LINE = "\n";

    /**
     * Takes in raw MongoDB watchlist and converts it to a String for a UserList watchlist.
     * @param watchlist the list of MongoDb Documents with watchlist data.
     * @return the watchlist as a String.
     */
    public String convertWatchlistToString(List<Document> watchlist) {
        final StringBuilder userWatchlist = new StringBuilder();
        // assuming the database stores from oldest to newest, i will reverse it so it outputs newest to oldest??
        for (Document mediaToWatch : watchlist.reversed()) {
            final String date = formatDate(mediaToWatch.get(ADDED_AT, String.class));
            userWatchlist.append(mediaToWatch.get(MEDIA_TITLE, String.class));
            userWatchlist.append("-- ");
            userWatchlist.append(date);
            userWatchlist.append(NEW_LINE);
        }
        return userWatchlist.toString();
    }

    public String convertWatchHistoryToString(List<Document> watchHistory) {
        final StringBuilder userWatchHistory = new StringBuilder();
        for (Document mediaWatched: watchHistory.reversed()) {
            final String date = formatDate(mediaWatched.get(ADDED_AT, String.class));
            userWatchHistory.append(mediaWatched.get(MEDIA_TITLE, String.class));
            userWatchHistory.append("-- ");
            userWatchHistory.append(date);
            userWatchHistory.append(NEW_LINE);
        }
        return watchHistory.toString();
    }

    public String convertBlockedUsersToString(List<String> blockedUsers) {
        final StringBuilder userBlockedUsers = new StringBuilder();
        for (String blockedUser: blockedUsers) {
            userBlockedUsers.append(blockedUser);
            userBlockedUsers.append(NEW_LINE);
        }
        return userBlockedUsers.toString());
    }

    private String formatDate(String rawDateData) {
        // "2026-07-01T09:07:00-04:00"
        return rawDateData.substring(0, INDEX_OF_DATE);

    }
}
