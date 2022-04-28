package study.kafka.producer.infrastructure.util;

import study.kafka.producer.domain.shared.token.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtManager {
    @Value("${jwt.secret}")
    private String secret;

    public TokenInfo getTokenInfo(String accessToken) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Claims tokenClaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        TokenInfo tokenInfo = TokenInfo.create(tokenClaims);
        return tokenInfo;
    }
}
