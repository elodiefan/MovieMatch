package use_case.send_message;

/**
 * Output Data for the Send Message Use Case.
 */
public class SendMessageOutputData {

    private String sender;
    private String body;
    private boolean useCaseFailed;

    public SendMessageOutputData(String sender, String body, boolean useCaseFailed) {
        this.sender = sender;
        this.body = body;
        this.useCaseFailed = useCaseFailed;
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
