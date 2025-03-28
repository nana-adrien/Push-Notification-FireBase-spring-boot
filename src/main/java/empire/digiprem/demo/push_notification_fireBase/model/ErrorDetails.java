package empire.digiprem.demo.push_notification_fireBase.model;

import java.time.LocalDateTime;

public class ErrorDetails {
    LocalDateTime timestamp;
    String message;
    String details;

    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}


