package study.kafka.producer.infrastructure.util;

import study.kafka.producer.domain.shared.token.TokenInfo;
import study.kafka.producer.domain.model.member.Member;
import study.kafka.producer.domain.model.member.MemberType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtManagerTest {
    @Autowired
    JwtManager jwtManager;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.refreshExpires}")
    private String refreshExpiresString;

    String generateTestToken(Member member) {
        long refreshExpires = Long.parseLong(refreshExpiresString);
        Date expiration = new Date(System.currentTimeMillis() + refreshExpires);
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return Jwts.builder()
                .claim("memberId", member.getMemberId()) // MemberId
                .claim("memberType", member.getMemberType()) // MemberType
                .signWith(key, SignatureAlgorithm.HS256) // 해시값
                .setExpiration(expiration) // 만료시간
                .compact();

    }

    @Test
    void getTokenInfo() {
        Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());

            Member member1 = new Member(1, "memberName", "memberPassword", MemberType.ADMIN, nowTimestamp,
            nowTimestamp);
            String accessToken1 = generateTestToken(member1);

            // 헤더로부터 토큰을 받아서 tokenInfo객체를 생성합니다.
            TokenInfo tokenInfo = jwtManager.getTokenInfo(accessToken1);

            // tokenInfo는 memberId와 memberType, exp를 제공합니다.
            assertThat(tokenInfo.getMemberId()).isEqualTo(1);
            assertThat(tokenInfo.getMemberType()).isEqualTo(MemberType.ADMIN);

            Member member2 = new Member(11, "memberName", "memberPassword", MemberType.TEACHER, nowTimestamp,
            nowTimestamp);
            String accessToken2 = generateTestToken(member2);
            TokenInfo tokenInfo2 = jwtManager.getTokenInfo(accessToken2);
            assertThat(tokenInfo2.getMemberId()).isEqualTo(11);
            assertThat(tokenInfo2.getMemberType()).isEqualTo(MemberType.TEACHER);

    }

}