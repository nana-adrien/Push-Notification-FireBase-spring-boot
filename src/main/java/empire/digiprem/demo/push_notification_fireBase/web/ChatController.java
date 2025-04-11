package empire.digiprem.demo.push_notification_fireBase.web;

import com.google.api.client.json.Json;
import empire.digiprem.demo.push_notification_fireBase.model.ChatMessage;
import empire.digiprem.demo.push_notification_fireBase.model.SendMessageRequest;
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
                message.getType(),message.getContent(),principal.getName()
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
