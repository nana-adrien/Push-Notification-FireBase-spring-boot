package empire.digiprem.dto;

import lombok.Builder;

import java.io.Serializable;
@Builder
public record AuthRespDto(String token,String username){}
