package empire.digiprem.demo.push_notification_fireBase.dto;

import java.util.List;

public record RegisterReqDto(
        String username,
        String password,
        String email,
        List<String> roles){}
