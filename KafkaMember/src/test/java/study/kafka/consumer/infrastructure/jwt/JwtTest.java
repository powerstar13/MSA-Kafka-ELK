package study.kafka.consumer.infrastructure.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import study.kafka.consumer.domain.model.member.Member;
import study.kafka.consumer.domain.model.member.MemberType;

@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${jwt.refreshExpires}")
    private String refreshExpiresString;

    @Test
    void generateToken() {
        Member member = Member.builder()
                .memberId(1)
                .memberName("memberName")
                .memberPassword("memberPassword")
                .memberType(MemberType.ADMIN)
                .build();
        long refreshExpires = Long.parseLong(refreshExpiresString);

        String token = jwtProvider.createJwtToken(member, refreshExpires);
        System.out.println(token);

    }


}
