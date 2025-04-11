package empire.digiprem.services;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import empire.digiprem.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;


@Service
public class FCMService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());



        /*
    * La méthode sendMessageToToken() est notre principale méthode d'envoi de notification. Elle utilise les méthodes d'assistance getPreconfiguredMessageToToken() pour créer l'objet Message et sendAndGetResponse() pour envoyer le message au service FCM pour distribution.
     * */
    public  void sendMessageToken(NotificationRequest request)throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request).build();
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput=gson.toJson(message);
        String response=sendAndGetResponse(message);
        logger.info("Sent message to token. Device token:"+request.token()+", "+response+" msg "+jsonOutput);
    }


    private  String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(topic) .build())
                .build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return  ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).build()).build();
    }
    private  Message.Builder getPreconfiguredMessageToToken(NotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.topic());
        ApnsConfig apnsConfig = getApnsConfig(request.topic());
        Notification notification=Notification.builder().setTitle(request.titre()).setBody(request.body()).build();

        return Message.builder().setToken(request.token()).setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(notification);

    }

}
