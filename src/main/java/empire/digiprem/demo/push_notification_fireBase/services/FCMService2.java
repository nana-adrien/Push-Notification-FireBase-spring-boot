package empire.digiprem.demo.push_notification_fireBase.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FCMService2 {
    public void sendNotification(String token, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).build();
        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        Message fcmMessage = Message.builder()
                .putData("title",title)
                .putData("body", body)
                //.putData("message", message)
                .setToken(token)
                .build();

        try {
            FirebaseMessaging.getInstance().send(fcmMessage);
            System.out.println("Notification envoyée !");
        } catch (Exception e) {
            e.printStackTrace();
        }
       // FirebaseMessaging.getInstance().send(message);
    }
}
