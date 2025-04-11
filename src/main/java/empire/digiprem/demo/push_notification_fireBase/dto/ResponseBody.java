package empire.digiprem.demo.push_notification_fireBase.dto;

import lombok.Builder;

@Builder
public class ResponseBody <T>{
        boolean hasSusses ;
        ErrorMessage errorMessage;
        T payload;

}
