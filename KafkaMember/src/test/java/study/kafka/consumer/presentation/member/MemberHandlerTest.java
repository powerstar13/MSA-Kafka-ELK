package study.kafka.consumer.presentation.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import study.kafka.consumer.application.response.MemberLoginResponse;
import study.kafka.consumer.infrastructure.config.WebFluxRouterConfig;
import study.kafka.consumer.application.response.MemberInfoResponse;
import study.kafka.consumer.application.response.MemberRegistrationResponse;
import study.kafka.consumer.presentation.member.request.MemberRegistrationRequest;

@SpringBootTest
class MemberHandlerTest {

    private WebTestClient webTestClientPostMethod;
    private WebTestClient webTestClientGetMethod;

    @Autowired
    private WebFluxRouterConfig webFluxRouterConfig;
    @Autowired
    private MemberHandler memberHandler;

    @BeforeEach
    void setUp() {
        webTestClientPostMethod = WebTestClient
            .bindToRouterFunction( // WebFluxConfig에서 작성한 router를 WebTestClient에 바인딩해준다.
                webFluxRouterConfig.memberRouterBuilder(memberHandler)
            )
            .build();

        webTestClientGetMethod = WebTestClient
            .bindToRouterFunction( // WebFluxConfig에서 작성한 router를 WebTestClient에 바인딩해준다.
                webFluxRouterConfig.memberRouterGETBuilder(memberHandler)
            )
            .build();
    }

    /**
     * 강사 등록
     */
    @Test
    void teacherRegistration() {

        MemberRegistrationRequest request = MemberRegistrationRequest.builder()
            .memberName("홍강사")
            .memberPassword("1234")
            .build();

        webTestClientPostMethod
            .post()
            .uri("/member/admin/teacherRegistration")
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MemberRegistrationResponse.class)
            .value(memberRegistrationResponse -> {
                Assertions.assertInstanceOf(Integer.class, memberRegistrationResponse.getMemberId());
            });
    }

    /**
     * 학생 회원 등록
     */
    @Test
    void studentRegistration() {

        MemberRegistrationRequest request = MemberRegistrationRequest.builder()
            .memberName("홍학생")
            .memberPassword("1234")
            .build();

        webTestClientPostMethod
            .post()
            .uri("/member/studentRegistration")
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MemberRegistrationResponse.class)
            .value(memberRegistrationResponse -> {
                Assertions.assertInstanceOf(Integer.class, memberRegistrationResponse.getMemberId());
            });
    }

    /**
     * 로그인
     */
    @Test
    void login() {

        webTestClientPostMethod
            .post()
            .uri("/member/login")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MemberLoginResponse.class);
    }

    /**
     * 회원 정보 조회
     */
    @Test
    void findMemberInfo() {

        webTestClientGetMethod
            .get()
            .uri("/member/findMemberInfo/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(MemberInfoResponse.class);
    }
}