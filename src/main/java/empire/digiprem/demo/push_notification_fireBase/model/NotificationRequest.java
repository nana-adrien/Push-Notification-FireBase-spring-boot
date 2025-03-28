package empire.digiprem.demo.push_notification_fireBase.model;

public class NotificationRequest {
    private String titre ;
    private String body ;
    private String topic ;
    private String token ;

    public NotificationRequest() {
    }

    public NotificationRequest(String titre, String body, String topic, String token) {
        this.titre = titre;
        this.body = body;
        this.topic = topic;
        this.token = token;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
