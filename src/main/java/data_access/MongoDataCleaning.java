package data_access;

import java.util.List;

import org.bson.Document;

/** Class used to clean data for packaging as entities. */
public class MongoDataCleaning {

    private static final String MEDIA_TITLE = "mediaTitle";
    private static final String ADDED_AT = "addedAt";
    private static final String WATCHED_AT = "watchedAt";
    private static final int INDEX_OF_DATE = 10;
    private static final String NEW_LINE = "\n";

    /** Takes in raw MongoDB watchlist data and converts it to a String for a UserList watchlist. */
    public static String convertWatchlistToString(List<Document> watchlist) {
        final StringBuilder userWatchlist = new StringBuilder();
        if (watchlist != null) {
            for (Document mediaToWatch : watchlist) {
                final String date = formatDate(mediaToWatch.get(ADDED_AT,
                        String.class));
                userWatchlist.append(mediaToWatch.get(MEDIA_TITLE,
                        String.class));
                userWatchlist.append(" -- ");
                userWatchlist.append(date);
                userWatchlist.append(NEW_LINE);
            }
        }
        return userWatchlist.toString();
    }

    /** Takes in raw MongoDB watch history data and converts it to a String for a UserList watchhistory. */
    public static String convertWatchHistoryToString(List<Document> watchHistory) {
        final StringBuilder userWatchHistory = new StringBuilder();
        if (watchHistory != null) {
            for (Document mediaWatched : watchHistory) {
                final String date = formatDate(mediaWatched.get(WATCHED_AT,
                        String.class));
                userWatchHistory.append(mediaWatched.get(MEDIA_TITLE,
                        String.class));
                userWatchHistory.append(" -- ");
                userWatchHistory.append(date);
                userWatchHistory.append(NEW_LINE);
            }
        }
        return userWatchHistory.toString();
    }

    /** Takes in raw MongoDB blocked users data and converts it to a String for a UserList watchlist. */
    public static String convertBlockedUsersToString(List<String> blockedUsers) {
        final StringBuilder userBlockedUsers = new StringBuilder();
        if (blockedUsers != null) {
            for (String blockedUser : blockedUsers) {
                userBlockedUsers.append(blockedUser);
                userBlockedUsers.append(NEW_LINE);
            }
        }
        return userBlockedUsers.toString();
    }

    /** Formats raw MongoDB date data as a shortened String for output. */
    public static String formatDate(String rawDateData) {
        // "2026-07-01T09:07:00-04:00"
        final String date;
        if (rawDateData == null || rawDateData.length() < INDEX_OF_DATE) {
            date = "";
        } else {
            date = rawDateData.substring(0, INDEX_OF_DATE);
        }
        return date;

    }
}
