package study.kafka.consumer.application.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import study.kafka.consumer.application.response.MemberInfoResponse;
import study.kafka.consumer.application.response.MemberLoginResponse;
import study.kafka.consumer.application.response.MemberRegistrationResponse;
import study.kafka.consumer.domain.model.member.MemberLoginSpecification;
import study.kafka.consumer.domain.model.member.MemberSaveSpecification;
import study.kafka.consumer.domain.model.member.MemberSearchSpecification;
import study.kafka.consumer.domain.model.member.MemberType;
import study.kafka.consumer.infrastructure.exception.status.BadRequestException;
import study.kafka.consumer.infrastructure.exception.status.ExceptionMessage;
import study.kafka.consumer.presentation.member.request.MemberLoginRequest;
import study.kafka.consumer.presentation.member.request.MemberRegistrationRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberApplicationServiceImpl implements MemberApplicationService {

    private final MemberSaveSpecification memberSaveSpecification;
    private final MemberSearchSpecification memberSearchSpecification;

    private final MemberLoginSpecification memberLoginSpecification;

    /**
     * 회원 계정 생성
     * @param serverRequest : 전달된 Request
     * @param memberType : 회원 유형
     * @return Mono<MemberRegistrationResponse> : 저장된 회원 정보
     */
    @Override
    public Mono<MemberRegistrationResponse> memberRegistration(ServerRequest serverRequest, MemberType memberType) {

        return serverRequest.bodyToMono(MemberRegistrationRequest.class).flatMap(
            request -> {
                request.verify(); // Request 유효성 검사

                return memberSaveSpecification.memberExistCheckAndRegistration(request, memberType); // 회원 계정 생성
            }
        ).switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())));
    }

    @Override
    public Mono<MemberLoginResponse> login(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(MemberLoginRequest.class).flatMap(
                request -> {
                    request.verify(); // Request 유효성 검사
                    return memberLoginSpecification.memberExistCheckAndLogin(request);
                }
        ).switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())));
    }

    @Override
    public Mono<MemberInfoResponse> findMemberInfo(ServerRequest request) {

        String memberIdStr = request.pathVariable("memberId");
        if (StringUtils.isBlank(memberIdStr)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberId.getMessage());

        int memberId = Integer.parseInt(memberIdStr);

        return memberSearchSpecification.getMemberInfo(memberId);
    }

    @Override
    public void studentToLectureShowInfoAlimtalk(String lectureName) {
        log.info("===== 학생에게 강의 노출 여부 알림톡 발송 완료 =====");
    }
}
