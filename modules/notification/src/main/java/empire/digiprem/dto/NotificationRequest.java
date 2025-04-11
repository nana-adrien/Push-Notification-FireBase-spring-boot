package empire.digiprem.dto;

public record NotificationRequest(
     String titre,
     String body,
     String topic,
     String token)
    {}