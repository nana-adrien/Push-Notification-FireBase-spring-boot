package empire.digiprem.demo.push_notification_fireBase.dto;

import empire.digiprem.demo.push_notification_fireBase.model.UserRole;

import java.util.Collection;

public record RegisterRespDto(Long id, String username, String email, Collection<UserRole> roles) {
}
