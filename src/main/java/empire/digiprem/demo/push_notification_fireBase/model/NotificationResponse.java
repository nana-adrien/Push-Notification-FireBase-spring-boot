package empire.digiprem.demo.push_notification_fireBase.model;

public class NotificationResponse {
    private int status;
    private String message;

    public NotificationResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
