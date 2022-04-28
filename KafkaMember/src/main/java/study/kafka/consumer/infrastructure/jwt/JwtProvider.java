package study.kafka.consumer.infrastructure.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import study.kafka.consumer.domain.model.member.Member;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String createJwtToken(Member member, long interval) {
        Date expiration = new Date(System.currentTimeMillis() + interval);
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return Jwts.builder()
                .claim("memberId", member.getMemberId()) // MemberId
                .claim("memberType", member.getMemberType()) // MemberType
                .signWith(key, SignatureAlgorithm.HS256) // 해시값
                .setExpiration(expiration) // 만료시간
                .compact();
    }
}
