package com.ebs.marketplace.jwt;

        import io.jsonwebtoken.Claims;
        import io.jsonwebtoken.Jwts;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.stereotype.Component;

        import java.util.Date;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.function.Function;

@Component
public class JwtUtil {

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).compact();
    }
}
