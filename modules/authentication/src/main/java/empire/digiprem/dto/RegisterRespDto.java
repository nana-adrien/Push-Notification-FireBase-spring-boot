package empire.digiprem.dto;


import empire.digiprem.models.UserRole;
import java.util.Collection;

public record RegisterRespDto(Long id, String username, String email, Collection<UserRole> roles) {
}
