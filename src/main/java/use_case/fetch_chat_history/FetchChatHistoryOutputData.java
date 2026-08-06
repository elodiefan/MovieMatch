package use_case.fetch_chat_history;

/**
 * Output Data for the fetch chat history use case.
 */
public class FetchChatHistoryOutputData {

    private String displayText;
    private boolean useCaseFailed;

    public FetchChatHistoryOutputData(String displayText, boolean useCaseFailed) {
        this.displayText = displayText;
        this.useCaseFailed = useCaseFailed;
    }

    public String getDisplayText() {
        return displayText;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
