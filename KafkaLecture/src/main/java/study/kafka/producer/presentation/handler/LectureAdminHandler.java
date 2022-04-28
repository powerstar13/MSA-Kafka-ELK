package study.kafka.producer.presentation.handler;

import study.kafka.producer.domain.model.lecture.Lecture;
import study.kafka.producer.infrastructure.kafka.KafkaProducerService;
import study.kafka.producer.application.lecture.LectureApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LectureAdminHandler {

    private final LectureApplicationService lectureApplicationService;
    private final KafkaProducerService kafkaProducerService;

    // lectureShowYn에 따른 강의 노출 여부 변경
    public Mono<ServerResponse> changeLectureShowYn(ServerRequest serverRequest) {

        Mono<Lecture> lectureMono = serverRequest.bodyToMono(Lecture.class)
            .flatMap(request -> {
                return lectureApplicationService.changeLectureShowYn(request);
            })
            .doOnSuccess(lecture -> {
                // Kafka Message 발행
                kafkaProducerService.sendMessage(
                    String.format("%s Event >>> %s 강의 %s",
                        serverRequest.path(),
                        lecture.getLectureName(),
                        lecture.getLectureShowYn() ? "오픈" : "종료"
                    )
                );
            });

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(lectureMono, Lecture.class)
            .onErrorResume(error -> ServerResponse.badRequest().build())
            ;
    }

    // 강의개설
    public Mono<ServerResponse> createLecture(ServerRequest request) {
        String jwt = request.headers().firstHeader("Authorization");
        System.out.println(jwt);
        Mono<Lecture> lectureMono = request.bodyToMono(Lecture.class)
                .flatMap(lectureApplicationService::createLecture)
                .log()
                ;

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(lectureMono, Lecture.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                ;
    }

    // 강사 매칭
    public Mono<ServerResponse> matchingLecture(ServerRequest request) {

        Mono<Lecture> lectureMono = request.bodyToMono(Lecture.class)
                .flatMap(lectureApplicationService::matchingLecture)
                .log()
                ;

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(lectureMono, Lecture.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                ;
    }

    // 강의id로 강의조회
    public Mono<ServerResponse> getLecture(ServerRequest request) {

        Mono<Lecture> lectureMono = lectureApplicationService.getLecture(Integer.valueOf(request.queryParam("lectureId").get()));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(lectureMono, Lecture.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                ;
    }

    // 학생 회원이 제출한 별점을 열람 API (특정 강의 ID에 해당하는 총 평점 조회)
    public Mono<ServerResponse> getLectureTotalScore(ServerRequest request) {
        Mono<Lecture> lectureMono = lectureApplicationService.getLectureTotalScore(request.queryParam("lectureId").get());

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(lectureMono, Lecture.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                ;
    }
}
