package empire.digiprem.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import empire.digiprem.models.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenUtil {
    //@Value("${jwt.secret-key}")
    private String SECRET_KEY = "bonjou le monde";
    // @Value("${jwt.expiration}")
    private Long EXPIRATION = 5 * 60 * 1000L;
    private Algorithm algorithm;

    public JwtTokenUtil() {
        this.algorithm = Algorithm.HMAC256(SECRET_KEY);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, Map.of());
    }

    private String generateToken(UserDetails userDetails, Map<String, String> claims) {
        //  Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTCreator.Builder jwtBuilder = JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("")
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION));
        claims.forEach(jwtBuilder::withClaim);
        return jwtBuilder.sign(algorithm);
    }

    public DecodedJWT decodedJWT(String token) throws TokenExpiredException {
        //   Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public <T> T getClaims(String name, Function<Map<String, Claim>, T> claimsResolver) {
        Map<String, Claim> claim = getAllClaims(name);
        return claimsResolver.apply(claim);
    }

    public String getUsername(String token) {
        return getClaims(token, claims -> claims.get("sub").asString());
    }

    public Date getExpiration(String token) {
        return getClaims(token, claims -> claims.get("exp").asDate());
    }

    public Map<String, Claim> getAllClaims(String token) {
        return decodedJWT(token).getClaims();
    }

    public Collection<UserRole> getUserRoles(String token) {
        return getClaims(token, claims -> claims.get("roles").asList(UserRole.class));
    }

    public boolean isTokenExpired(String token) {
        try {
            return decodedJWT(token).getExpiresAt().before(new Date(System.currentTimeMillis()));
        } catch (TokenExpiredException e) {
            return true;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            return decodedJWT(token).getSubject().equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (TokenExpiredException e) {
            return true;
        }
    }
}
