package empire.digiprem.demo.push_notification_fireBase.interceptor;

import empire.digiprem.demo.push_notification_fireBase.services.UserDetailServiceImpl;
import empire.digiprem.demo.push_notification_fireBase.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class WebSocketAuthenticationInterceptor implements ChannelInterceptor {

    private JwtTokenUtil jwtTokenUtil;
    private UserDetailServiceImpl userDetailService;

    public WebSocketAuthenticationInterceptor(JwtTokenUtil jwtTokenUtil, UserDetailServiceImpl userDetailService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailService = userDetailService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        /* Récupère les entêtes du message STOMP, ici on accède à l'objet StompHeaderAccessor
         */
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        log.info("Headers: {}", accessor);
        /*  Vérification que l'accessor n'est pas null (protection contre un possible NullPointerException)
         */
        assert accessor != null;
        /*  Si la commande STOMP est CONNECT (cela signifie qu'un client tente de se connecter au serveur)
         */
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            /*  Récupère l'en-tête "Authorization" dans les entêtes du message STOMP
             */
            String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
            /* Vérifie que l'en-tête "Authorization" n'est pas null
             */
            assert authorizationHeader != null;
            /* On enlève le préfixe "Bearer " du token pour obtenir seulement le token JWT
             */
            String token = authorizationHeader.substring(7); /* Le "7" correspond à la longueur du mot "Bearer " pour couper le préfixe*/
            /* Utilisation de l'utilitaire JWT pour extraire le nom d'utilisateur à partir du token JWT
             */
            String username = jwtTokenUtil.getUsername(token);
            /*  Chargement des détails de l'utilisateur à partir du service de gestion des utilisateurs
             */
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            /*Crée un objet UsernamePasswordAuthenticationToken avec les détails de l'utilisateur et ses autorités
             */
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            /* Définit l'objet Authentication dans le contexte de sécurité Spring, pour marquer que l'utilisateur est authentifié
             */
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            /* Définit l'utilisateur dans le header STOMP pour qu'il soit accessible dans les futures communications
             */
            accessor.setUser(usernamePasswordAuthenticationToken);
        }
        /* Retourne le message original, modifié ou non, selon si une connexion a eu lieu
         */
        return message;
    }

}
