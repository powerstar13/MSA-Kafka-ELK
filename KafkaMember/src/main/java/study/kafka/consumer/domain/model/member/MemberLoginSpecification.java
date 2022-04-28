package study.kafka.consumer.domain.model.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import study.kafka.consumer.application.response.MemberLoginResponse;
import study.kafka.consumer.infrastructure.exception.status.ExceptionMessage;
import study.kafka.consumer.presentation.member.request.MemberLoginRequest;
import study.kafka.consumer.application.member.MemberSha256;
import study.kafka.consumer.infrastructure.exception.status.UnauthorizedException;
import study.kafka.consumer.infrastructure.jwt.JwtProvider;

@Component
@RequiredArgsConstructor
public class MemberLoginSpecification {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Value("${jwt.accessExpires}")
    private String accessExpiresString;

    @Value("${jwt.refreshExpires}")
    private String refreshExpiresString;

    public Mono<MemberLoginResponse> memberExistCheckAndLogin(MemberLoginRequest request) {

        Mono<Member> member = memberRepository.findByMemberNameAndMemberPassword(request.getMemberName(), MemberSha256.encrypt(request.getMemberPassword()));
        return member
                .hasElement()
                .flatMap(hasMember -> {
                    if (!hasMember) return Mono.error(new UnauthorizedException(ExceptionMessage.NotFoundLoginMember.getMessage()));

                    return MakeMemberLoginResponse(member);
                });
    }

    private Mono<MemberLoginResponse> MakeMemberLoginResponse(Mono<Member> member) {
        return member.flatMap(m -> {
            String accessToken = jwtProvider.createJwtToken(m, Long.parseLong(accessExpiresString));
            String refreshToken = jwtProvider.createJwtToken(m, Long.parseLong(refreshExpiresString));
            return Mono.just(new MemberLoginResponse(m.getMemberId(), accessToken, refreshToken));
        });
    }
}
