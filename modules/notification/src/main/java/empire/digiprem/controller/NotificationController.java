package empire.digiprem.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import empire.digiprem.dto.NotificationRequest;
import empire.digiprem.dto.NotificationResponse;
import empire.digiprem.services.FCMService;
import empire.digiprem.services.FCMService2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping
public class NotificationController {

    private FCMService fcmService;
    private FCMService2 fcmService2;

    public NotificationController(FCMService fcmService,FCMService2 fcmService2) {
        this.fcmService = fcmService;
        this.fcmService2 = fcmService2;
    }

    /*
    * Nous avons exposé un point de terminaison /notification qui acceptera un objet NotificationRequest et appellera la méthode sendMessageToToken() de notre classe de service pour envoyer la notification push.
     * */
    @PostMapping("/notification")
    public ResponseEntity<NotificationResponse> sendNotification(@RequestBody NotificationRequest request) throws ExecutionException, InterruptedException {
        fcmService.sendMessageToken(request);
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/notification2")
    public ResponseEntity<NotificationResponse> sendNotification2(@RequestBody NotificationRequest request) throws ExecutionException, InterruptedException, FirebaseMessagingException {
        fcmService2.sendNotification(request.token(),request.titre(),request.body());
        return new ResponseEntity<NotificationResponse>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
}
