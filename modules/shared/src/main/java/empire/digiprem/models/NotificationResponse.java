package empire.digiprem.models;

public class NotificationResponse {
    private int status;
    private String message;

    public NotificationResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
