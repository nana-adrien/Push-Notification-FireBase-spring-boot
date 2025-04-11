package empire.digiprem.demo.push_notification_fireBase.model;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

    public ChatMessage() {
    }

    public ChatMessage(MessageType type, String content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
    }
   public static class Builder {
        private MessageType type;
        private String content;
        private String sender;

       public Builder() {
       }
       public Builder setType(MessageType type) {
            this.type = type;
            return this;
        }
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }
        public Builder setSender(String sender) {
            this.sender = sender;
            return this;
        }
        public ChatMessage build() {
            return new ChatMessage(type, content, sender);
        }
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
