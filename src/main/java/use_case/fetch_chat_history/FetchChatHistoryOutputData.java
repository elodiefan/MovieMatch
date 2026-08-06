package use_case.fetch_chat_history;

import java.util.ArrayList;

/**
 * Output Data for the fetch chat history use case.
 */
public class FetchChatHistoryOutputData {

    private ArrayList<String> displayText = new ArrayList<>();
    private boolean useCaseFailed;

    public FetchChatHistoryOutputData(ArrayList<String> displayText, boolean useCaseFailed) {
        this.displayText = displayText;
        this.useCaseFailed = useCaseFailed;
    }

    public ArrayList<String> getDisplayText() {
        return displayText;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
