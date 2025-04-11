package empire.digiprem.demo.push_notification_fireBase.config;

import empire.digiprem.demo.push_notification_fireBase.filters.JwtFilter;
import empire.digiprem.demo.push_notification_fireBase.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsService userDetailsService;
    private  PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /* Cette annotation indique à Spring que cette méthode retourne un bean à gérer dans le contexte de l'application.
         Ici, on retourne un objet SecurityFilterChain, qui configure la sécurité HTTP de l'application.
        */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                /*  Désactive la protection CSRF (Cross-Site Request Forgery).
                  ⚠️ Attention : c'est souvent utile pour les API REST ou WebSocket, mais en prod ou avec des formulaires, il faut être sûr de bien gérer les risques.
                */.csrf(AbstractHttpConfigurer::disable)
                /*  Désactive la configuration CORS (Cross-Origin Resource Sharing).
                 Cela permet des requêtes entre domaines différents sans restriction.
                 ⚠️ À utiliser avec précaution en production ! Sinon on risque des failles de sécurité (ex : des sites tiers accèdent à ton API).
               */.cors(AbstractHttpConfigurer::disable)
                /*Définition des règles d'autorisation pour les différentes requêtes HTTP.
                 */.authorizeHttpRequests(auth -> auth
                        /*Autorise toutes les requêtes vers les endpoints d’authentification (ex : login, register)
                          et vers les endpoints WebSocket (ex : /ws/ pour la négociation de connexion WebSocket).
                         */.requestMatchers("/api/v1/auth/**", "/ws/**" ).permitAll()
                        /*Toute autre requête devra être authentifiée.
                         * */.anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(userDetailsService, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        ex -> {
                            ex.authenticationEntryPoint((request, response, authException) -> response.sendError(401, "Unauthorized"));
                            ex .accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(403, "Forbidden")
                            );
                        }
                )
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return new ProviderManager(provider);
    }

}
