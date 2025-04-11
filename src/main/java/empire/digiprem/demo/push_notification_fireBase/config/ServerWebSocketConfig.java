package empire.digiprem.demo.push_notification_fireBase.config;

import empire.digiprem.demo.push_notification_fireBase.handler.ServerWebSocketHandler;
import empire.digiprem.demo.push_notification_fireBase.services.UserDetailServiceImpl;
import empire.digiprem.demo.push_notification_fireBase.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class ServerWebSocketConfig implements WebSocketConfigurer {



    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(),"/websocket").withSockJS();
        registry.addHandler(webSocketHandler(),"/websocket");
    }
    @Bean
    public WebSocketHandler webSocketHandler() {
        return  new ServerWebSocketHandler();
    }
}
