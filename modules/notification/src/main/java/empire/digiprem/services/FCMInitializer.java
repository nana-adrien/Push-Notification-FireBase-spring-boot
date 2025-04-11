package empire.digiprem.services;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;


@Service
public class FCMInitializer {

    @Value("${app.firebase-configuration-file}") // Injection du chemin du fichier
    private String firebaseConfigPath;

    Logger logger= LoggerFactory.getLogger(FCMInitializer.class);

    /*
    * L' annotation @PostConstruct permet d'appeler la méthode initialize() au démarrage de l'application.
     * */
    @PostConstruct
    public void initialize() {
        try{
            System.out.println("📂 Chemin du fichier Firebase : " + firebaseConfigPath);

            InputStream inputStream = FCMInitializer.class.getClassLoader().getResourceAsStream("java-firebase-sdk-firebase-adminsdk.json");

            FirebaseOptions option = new FirebaseOptions.Builder()
                  .setCredentials(GoogleCredentials.fromStream(inputStream/*new ClassPathResource(firebaseConfigPath).getInputStream())*/)).build();
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
