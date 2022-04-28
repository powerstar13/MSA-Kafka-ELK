package study.kafka.producer.infrastructure.router;

import study.kafka.producer.domain.model.lecture.Lecture;
import study.kafka.producer.presentation.handler.LectureAdminHandler;
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
public class LectureAdminRouter {

    @RouterOperations(
            {
                    @RouterOperation(path = "/lecture/admin/create", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = LectureAdminHandler.class, method = RequestMethod.POST, beanMethod = "createLecture",
                            operation = @Operation(operationId = "createLecture", responses = {
                                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Lecture.class))),
                                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Lecture.class)))}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Lecture.class))))
                    ),

                    @RouterOperation(path = "/lecture/admin/matching", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = LectureAdminHandler.class, method = RequestMethod.POST, beanMethod = "matchingLecture",
                            operation = @Operation(operationId = "matchingLecture", responses = {
                                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Lecture.class))),
                                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Lecture.class)))}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Lecture.class))))
                    ),

                    @RouterOperation(path = "/lecture/getLecture", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = LectureAdminHandler.class, method = RequestMethod.GET, beanMethod = "getLecture",
                            operation = @Operation(operationId = "getLecture", responses = {
                                    @ApiResponse(responseCode = "200"),
                                    @ApiResponse(responseCode = "400")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Lecture.class))))
                    ),

                    @RouterOperation(path = "/lecture/getTotalScore", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = LectureAdminHandler.class, method = RequestMethod.GET, beanMethod = "getLectureTotalScore",
                            operation = @Operation(operationId = "getLectureTotalScore", responses = {
                                    @ApiResponse(responseCode = "200"),
                                    @ApiResponse(responseCode = "400")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Lecture.class))))
                    ),

                    @RouterOperation(path = "/admin/lecture/show", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = LectureAdminHandler.class, method = RequestMethod.PATCH, beanMethod = "changeLectureShowYn",
                            operation = @Operation(operationId = "changeLectureShowYn", responses = {
                                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Lecture.class))),
                                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Lecture.class)))}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Lecture.class))))
                    )
            }
    )

    @Bean
    public RouterFunction<ServerResponse> route(LectureAdminHandler lectureAdminHandler) {
        return RouterFunctions
                .route(POST("/lecture/admin/create"), lectureAdminHandler::createLecture)              // 강의개설
                .andRoute(POST("/lecture/admin/matching"), lectureAdminHandler::matchingLecture)       // 강사 매칭
                .andRoute(GET("/lecture/getLecture"), lectureAdminHandler::getLecture)                 // 강의 조회 (테스트)
                .andRoute(GET("/lecture/getTotalScore"), lectureAdminHandler::getLectureTotalScore)                 // 강의의 총 별점 조회
                .andRoute(PATCH("/lecture/admin/show"), lectureAdminHandler::changeLectureShowYn)        // 강의 노출, 강의 노출 종료(PATCH)
                ;
    }
}
