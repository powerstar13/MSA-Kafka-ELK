package study.kafka.producer.presentation.handler;

import study.kafka.producer.domain.model.lecture.LectureContent;
import study.kafka.producer.domain.model.lecture.LectureInfo;
import study.kafka.producer.application.lecture.LectureApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StudentHandler {

    private final LectureApplicationService lectureApplicationService;

    // 수강한 강의에 별점 남기기
    public Mono<ServerResponse> setLectureScore(ServerRequest request) {
        Mono<LectureInfo> lectureMono = request.bodyToMono(LectureInfo.class)
                .flatMap(lectureApplicationService::setLectureScore)
                .log()
                ;

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(lectureMono, LectureInfo.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                ;
    }

    // 강의를 선택해서 수강 신청 (수강정보 : LectureInfo 생성)
    public Mono<ServerResponse> enrolment(ServerRequest request) {
        Mono<LectureInfo> lectureInfoMono = request.bodyToMono(LectureInfo.class)
                .flatMap(lectureApplicationService::enrolment)
                .log()
                ;

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(lectureInfoMono, LectureInfo.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                ;
    }

    // 수강 신청한 강의 컨텐츠 열람
    public Mono<ServerResponse> getLectureContents(ServerRequest request) {
        Mono<LectureContent> LectureContentMono = request.bodyToMono(LectureInfo.class)
                .flatMap(lectureApplicationService::getLectureContents)
                .log()
                ;

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(LectureContentMono, LectureContent.class)
                .onErrorResume(error -> ServerResponse.badRequest().build())
                ;
    }
}
