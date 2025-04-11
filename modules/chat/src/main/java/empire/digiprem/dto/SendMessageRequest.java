package empire.digiprem.dto;

import empire.digiprem.model.MessageType;

public record SendMessageRequest (
     MessageType type,
     String content){}

