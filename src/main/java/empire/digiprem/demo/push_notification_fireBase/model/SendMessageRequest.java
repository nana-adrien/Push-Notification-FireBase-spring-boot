package empire.digiprem.demo.push_notification_fireBase.model;

public class SendMessageRequest {
    private MessageType type;
    private String content;

    public SendMessageRequest() {
    }

    public SendMessageRequest(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
