package empire.digiprem.demo.push_notification_fireBase.dto;

import lombok.Builder;

import java.io.Serializable;
@Builder
public record AuthRespDto(String token,String username){}
