package empire.digiprem.controller;

import empire.digiprem.dto.SendMessageRequest;
import empire.digiprem.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Objects;

@Controller
public class ChatController {
    private SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public ChatMessage sendMessage(@Payload SendMessageRequest message, Principal principal) {
        ChatMessage chatMessage=new ChatMessage(
                message.type(),message.content(),principal.getName()
        );
        System.out.println("message = "+chatMessage.getType());
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
        return chatMessage ;
    }
    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
        return message;
    }

}
