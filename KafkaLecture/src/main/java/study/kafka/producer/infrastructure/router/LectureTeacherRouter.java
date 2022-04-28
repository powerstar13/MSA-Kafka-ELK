package study.kafka.producer.infrastructure.router;

import study.kafka.producer.domain.model.lecture.LectureContent;
import study.kafka.producer.presentation.handler.TeacherHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class LectureTeacherRouter {

    @RouterOperations(
            {
                    @RouterOperation(path = "/lecture/content/exam", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = TeacherHandler.class, method = RequestMethod.POST, beanMethod = "updateNewTest",
                            operation = @Operation(operationId = "updateNewTest", responses = {
                                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LectureContent.class))),
                                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = LectureContent.class)))}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LectureContent.class))))
                    ),

                    @RouterOperation(path = "/lecture/content", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = TeacherHandler.class, method = RequestMethod.POST, beanMethod = "uploadContent",
                            operation = @Operation(operationId = "uploadContent", responses = {
                                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LectureContent.class))),
                                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = LectureContent.class)))}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LectureContent.class))))
                    ),

                    @RouterOperation(path = "/lecture/matching/{teacherId}", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = TeacherHandler.class, method = RequestMethod.GET, beanMethod = "getLectureOnTeacher",
                            operation = @Operation(operationId = "getLectureOnTeacher", responses = {
                                    @ApiResponse(responseCode = "200"),
                                    @ApiResponse(responseCode = "400")})
                    )
            }
    )
    @Bean
    public RouterFunction<ServerResponse> route2(TeacherHandler teacherHandler) {
        return RouterFunctions
                .route(POST("/lecture/content/exam").and(accept(MediaType.APPLICATION_JSON)), teacherHandler::updateNewTest)   // 시험 컨텐츠 생성
                .andRoute(POST("/lecture/content").and(accept(MediaType.APPLICATION_JSON)), teacherHandler::uploadContent) // 강의 컨텐츠 생성
                .andRoute(GET("/lecture/matching/{teacherId}").and(accept(MediaType.APPLICATION_JSON)), teacherHandler::getLectureOnTeacher) // 강사에 매칭된 강의목록 조회
                .andRoute(PATCH("/lecture/updateTestScore").and(accept(MediaType.APPLICATION_JSON)), teacherHandler::updateTestScore) // 강사가 시험점수를 반영
                ;
    }
}
