package data_access;

import java.util.List;

import org.bson.Document;

/**
 * Class used to clean data for packaging as entities.
 */
public class MongoDataCleaning {

    private static final String MEDIA_TITLE = "mediaTitle";
    private static final String ADDED_AT = "addedAt";
    private static final int INDEX_OF_DATE = 10;
    private static final String NEW_LINE = "\n";

    private static final String SENDER = "sender";
    private static final String BODY = "body";
    private static final String TIMESTAMP = "timestamp";
    private static final int INDEX_OF_START_TIME = 11;
    private static final int INDEX_OF_END_TIME = 19;

    /**
     * Takes in raw MongoDB watchlist data and converts it to a String for a UserList watchlist.
     * @param watchlist the list of MongoDb Documents with watchlist data.
     * @return the watchlist as a String.
     */
    public static String convertWatchlistToString(List<Document> watchlist) {
        final StringBuilder userWatchlist = new StringBuilder();
        for (Document mediaToWatch : watchlist) {
            final String date = formatDate(mediaToWatch.get(ADDED_AT, String.class), 0, INDEX_OF_DATE);
            userWatchlist.append(mediaToWatch.get(MEDIA_TITLE, String.class));
            userWatchlist.append("-- ");
            userWatchlist.append(date);
            userWatchlist.append(NEW_LINE);
        }
        return userWatchlist.toString();
    }

    /**
     * Takes in raw MongoDB watch history data and converts it to a String for a UserList watchhistory.
     * @param watchHistory the list of MongoDb Documents with watch history data.
     * @return the watch history as a String.
     */
    public static String convertWatchHistoryToString(List<Document> watchHistory) {
        final StringBuilder userWatchHistory = new StringBuilder();
        for (Document mediaWatched: watchHistory) {
            final String date = formatDate(mediaWatched.get(ADDED_AT, String.class), 0, INDEX_OF_DATE);
            userWatchHistory.append(mediaWatched.get(MEDIA_TITLE, String.class));
            userWatchHistory.append("-- ");
            userWatchHistory.append(date);
            userWatchHistory.append(NEW_LINE);
        }
        return watchHistory.toString();
    }

    /**
     * Takes in raw MongoDB blocked users data and converts it to a String for a UserList watchlist.
     * @param blockedUsers the list of MongoDb Documents with blocked users data.
     * @return the blocked users as a String.
     */
    public static String convertBlockedUsersToString(List<String> blockedUsers) {
        final StringBuilder userBlockedUsers = new StringBuilder();
        for (String blockedUser: blockedUsers) {
            userBlockedUsers.append(blockedUser);
            userBlockedUsers.append(NEW_LINE);
        }
        return userBlockedUsers.toString();
    }

    /**
     * Formats raw MongoDB date data as a shortened String for output.
     * @param rawDateData data in raw form
     * @param start start index
     * @param end end index
     * @return the data as a shortened String.
     */
    public static String formatDate(String rawDateData, int start, int end) {
        // "2026-07-01T09:07:00-04:00"
        return rawDateData.substring(start, end);
    }

    public static String formatChat(List<Document> chatHistory) {
        final StringBuilder formattedChat = new StringBuilder();
        for (Document message: chatHistory) {
            final String date = formatDate(message.get(ADDED_AT, String.class), 0, INDEX_OF_DATE);
            final String time = formatDate(message.get(ADDED_AT, String.class), INDEX_OF_START_TIME, INDEX_OF_END_TIME);
            formattedChat.append(message.get(SENDER, String.class));
            formattedChat.append(" - ");
            formattedChat.append(message.get(BODY, String.class));
            formattedChat.append("(" + date + ", " + time + ")");
            formattedChat.append(NEW_LINE);
        }
        return formattedChat.toString();
    }
}
