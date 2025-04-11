package empire.digiprem.demo.push_notification_fireBase.listener;

import empire.digiprem.demo.push_notification_fireBase.model.ChatMessage;
import empire.digiprem.demo.push_notification_fireBase.model.MessageType;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component

public class WebSocketListener {

    private  final SimpMessageSendingOperations messagingTemplate;

    public WebSocketListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    private void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.printf("j'ai recu une nouvelle connexion de socket web");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username=(String) accessor.getSessionAttributes().get("username");

        if (username!=null) {
            System.out.printf("user disconnected:{}"+username);
        }
        ChatMessage chatMessage=new ChatMessage.Builder()
                .setType(MessageType.LEAVE)
                .setSender(username).build();

        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
}
