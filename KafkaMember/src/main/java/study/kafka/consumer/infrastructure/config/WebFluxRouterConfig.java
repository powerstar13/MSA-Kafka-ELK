package study.kafka.consumer.infrastructure.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import study.kafka.consumer.application.response.MemberInfoResponse;
import study.kafka.consumer.application.response.MemberLoginResponse;
import study.kafka.consumer.application.response.MemberRegistrationResponse;
import study.kafka.consumer.presentation.member.MemberHandler;
import study.kafka.consumer.presentation.member.request.MemberLoginRequest;
import study.kafka.consumer.presentation.member.request.MemberRegistrationRequest;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@EnableWebFlux // WebFlux 설정 활성화
public class WebFluxRouterConfig implements WebFluxConfigurer {

    @RouterOperations({
        @RouterOperation(
            path = "/member/admin/teacherRegistration",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "memberRegistration",
            operation = @Operation(
                description = "강사 등록 API",
                operationId = "teacherRegistration",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberRegistrationRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "201",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberRegistrationResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/studentRegistration",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "memberRegistration",
            operation = @Operation(
                description = "학생 회원 가입 API",
                operationId = "studentRegistration",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberRegistrationRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "201",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberRegistrationResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/login",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "login",
            operation = @Operation(
                description = "로그인 API",
                operationId = "login",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberLoginRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberLoginResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        )
    })
    @Bean
    public RouterFunction<ServerResponse> memberRouterBuilder(MemberHandler memberHandler) {

        return RouterFunctions.route()
            .path("/member", memberBuilder ->
                memberBuilder.nest(accept(MediaType.APPLICATION_JSON), builder ->
                    builder
                        .POST("/admin/teacherRegistration", memberHandler::memberRegistration) // 강사 등록 (관리자만)
                        .POST("/studentRegistration", memberHandler::memberRegistration) // 학생 회원 가입
                        .POST("/login", memberHandler::login) // 로그인
                )
            )
            .build();
    }

    @RouterOperations({
        @RouterOperation(
            path = "/member/findMemberInfo/${memberId}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = MemberHandler.class,
            method = RequestMethod.GET,
            beanMethod = "findMemberInfo",
            operation = @Operation(
                description = "회원 정보 조회 API",
                operationId = "findMemberInfo",
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberInfoResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        )
    })
    @Bean
    public RouterFunction<ServerResponse> memberRouterGETBuilder(MemberHandler memberHandler) {
        return RouterFunctions.route()
            .path("/member", builder -> builder
                .GET("/findMemberInfo/{memberId}", memberHandler::findMemberInfo)
            ).build();
    }

}
