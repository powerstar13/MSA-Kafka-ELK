package study.kafka.consumer.application.member;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import study.kafka.consumer.domain.model.member.MemberType;
import study.kafka.consumer.application.response.MemberInfoResponse;
import study.kafka.consumer.application.response.MemberLoginResponse;
import study.kafka.consumer.application.response.MemberRegistrationResponse;

public interface MemberApplicationService {

    Mono<MemberRegistrationResponse> memberRegistration(ServerRequest request, MemberType memberType); // 회원 계정 생성

    Mono<MemberLoginResponse> login(ServerRequest request); // 로그인

    Mono<MemberInfoResponse> findMemberInfo(ServerRequest request); // 회원 정보 조회

    void studentToLectureShowInfoAlimtalk(String lectureName); // 강의 노출 여부 학생에게 알림톡 발송
}