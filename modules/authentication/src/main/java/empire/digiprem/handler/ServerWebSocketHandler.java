package empire.digiprem.handler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class ServerWebSocketHandler extends TextWebSocketHandler {

    Collection<WebSocketSession> sessions=new ArrayList<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        if (!sessions.contains(session)) {
            sessions.add(session);
        }
        String request=message.getPayload();
        System.out.println("Server received: "+request);

        String response=String.format("response from server to '%s'"+session.getId(), HtmlUtils.htmlEscape(request));
       // session.sendMessage(new TextMessage(response));
        for(WebSocketSession session2:sessions){
            if(session2.isOpen() && !session.getId().equals(session2.getId())){
                // System.out.println("Server sends:{},"+maessage);
                session2.sendMessage(new TextMessage(response));
            }
        }
    }




    @Scheduled(fixedRate = 5000)
    void sendPeriodicMessages()throws IOException{
        sessionMessage("Server periodic message"+ LocalDateTime.now());
    }

    private void sessionMessage(String maessage) throws IOException {
        for(WebSocketSession session:sessions){
            if(session.isOpen()){
               // System.out.println("Server sends:{},"+maessage);
                session.sendMessage(new TextMessage(maessage));
            }
        }
    }

}


