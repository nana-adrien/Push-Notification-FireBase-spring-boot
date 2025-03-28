package empire.digiprem.demo.push_notification_fireBase.web;

import empire.digiprem.demo.push_notification_fireBase.model.NotificationRequest;
import empire.digiprem.demo.push_notification_fireBase.model.NotificationResponse;
import empire.digiprem.demo.push_notification_fireBase.services.FCMService;
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

    public NotificationController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    /*
    * Nous avons exposé un point de terminaison /notification qui acceptera un objet NotificationRequest et appellera la méthode sendMessageToToken() de notre classe de service pour envoyer la notification push.
     * */
    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody NotificationRequest request) throws ExecutionException, InterruptedException {
        fcmService.sendMessageToken(request);
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
}
