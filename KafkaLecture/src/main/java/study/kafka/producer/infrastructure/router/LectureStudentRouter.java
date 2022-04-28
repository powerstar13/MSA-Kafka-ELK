package study.kafka.producer.infrastructure.router;

import study.kafka.producer.domain.model.lecture.LectureContent;
import study.kafka.producer.domain.model.lecture.LectureInfo;
import study.kafka.producer.presentation.handler.StudentHandler;
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
public class LectureStudentRouter {

    @RouterOperations(
            {
                    @RouterOperation(path = "/lecture/student/setScore", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = StudentHandler.class, method = RequestMethod.POST, beanMethod = "setLectureScore",
                            operation = @Operation(operationId = "setLectureScore", responses = {
                                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LectureInfo.class))),
                                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = LectureInfo.class)))}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LectureInfo.class))))
                    ),

                    @RouterOperation(path = "/lecture/student/enrolment", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = StudentHandler.class, method = RequestMethod.POST, beanMethod = "enrolment",
                            operation = @Operation(operationId = "enrolment", responses = {
                                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LectureInfo.class))),
                                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = LectureInfo.class)))}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LectureInfo.class))))
                    ),

                    @RouterOperation(path = "/lecture/student/getContents", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = StudentHandler.class, method = RequestMethod.POST, beanMethod = "getContents",
                            operation = @Operation(operationId = "getContents", responses = {
                                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LectureContent.class))),
                                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = LectureContent.class)))}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LectureContent.class))))
                    )
            }
    )

    @Bean
    public RouterFunction<ServerResponse> studentRouter(StudentHandler studentHandler) {
        return RouterFunctions
                .route(POST("/lecture/student/setScore"), studentHandler::setLectureScore)              // 수강한 강의에 별점 남기기
                .andRoute(POST("/lecture/student/enrolment"), studentHandler::enrolment)                // 강의를 선택해서 수강 신청 (수강정보 : LectureInfo 생성)
                .andRoute(POST("/lecture/student/getContents"), studentHandler::getLectureContents)
                ;
    }
}
