package use_case.send_message;

/**
 * Output Data for the Send Message Use Case.
 */
public class SendMessageOutputData {

    private String sender;
    private String message;
    private boolean useCaseFailed;

    public SendMessageOutputData(String sender, String message,boolean useCaseFailed) {
        this.sender = sender;
        this.message = message;
        this.useCaseFailed = useCaseFailed;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
