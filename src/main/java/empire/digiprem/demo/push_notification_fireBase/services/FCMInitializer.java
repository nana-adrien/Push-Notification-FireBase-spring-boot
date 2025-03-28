package empire.digiprem.demo.push_notification_fireBase.services;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;


@Service
public class FCMInitializer {

    @Value("${app.firebase-configuration-file}")
    private  String firebaseConfigPath;

    Logger logger= LoggerFactory.getLogger(FCMInitializer.class);

    /*
    * L' annotation @PostConstruct permet d'appeler la méthode initialize() au démarrage de l'application.
     * */
    @PostConstruct
    public void initialize() {
        try{
          FirebaseOptions option = new FirebaseOptions.Builder()
                  .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
          if (FirebaseApp.getApps().isEmpty()){
              FirebaseApp.initializeApp(option);
              logger.info("Firebase App initialized");
          }

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
