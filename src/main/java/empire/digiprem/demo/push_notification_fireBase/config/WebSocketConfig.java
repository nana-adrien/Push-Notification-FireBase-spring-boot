package empire.digiprem.demo.push_notification_fireBase.config;

import empire.digiprem.demo.push_notification_fireBase.interceptor.WebSocketAuthenticationInterceptor;
import empire.digiprem.demo.push_notification_fireBase.services.UserDetailServiceImpl;
import empire.digiprem.demo.push_notification_fireBase.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
    private JwtTokenUtil jwtTokenUtil;
    private UserDetailServiceImpl userDetailService;

    public WebSocketConfig(JwtTokenUtil jwtTokenUtil, UserDetailServiceImpl userDetailService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailService = userDetailService;
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /* Déclare l'endpoint WebSocket à l'URL "/ws"
         Avec SockJS pour la compatibilité avec les anciens navigateurs*/
        registry.addEndpoint("/ws").withSockJS();
        registry.addEndpoint("/ws");
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
     registration.interceptors(new WebSocketAuthenticationInterceptor(jwtTokenUtil,userDetailService));
    }
}
