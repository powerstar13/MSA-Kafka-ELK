package study.kafka.producer.presentation.handler;

import study.kafka.producer.domain.model.lecture.Lecture;
import study.kafka.producer.domain.model.lecture.LectureContent;
import study.kafka.producer.domain.model.lecture.LectureInfo;
import study.kafka.producer.domain.model.lecture.repository.LectureRepository;
import study.kafka.producer.application.lecture.LectureApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class TeacherHandler {

    private final LectureApplicationService lectureApplicationService;
    private final LectureRepository lectureRepository;

    // 수강자 성적 입력
    public Mono<ServerResponse> updateStudentScore(ServerRequest request) {

        Mono<Lecture> lectureMono = request.bodyToMono(Lecture.class)
                .flatMap(lectureApplicationService::updateStudentScore);

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(lectureMono, Lecture.class)
                .onErrorResume(error -> ServerResponse.badRequest().build());
    }

    // 시험 컨텐츠 추가
    public Mono<ServerResponse> updateNewTest(ServerRequest request) {

        Mono<LectureContent> lectureMono = request.bodyToMono(LectureContent.class)
                .flatMap(lectureApplicationService::updateNewExam);

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(lectureMono, LectureContent.class)
                .onErrorResume(error -> ServerResponse.badRequest().build());
    }

    // 강의 컨텐츠 업로드
    public Mono<ServerResponse> uploadContent(ServerRequest request) {

        Mono<LectureContent> lectureMono = request.bodyToMono(LectureContent.class)
                .flatMap(lectureApplicationService::uploadContent);

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(lectureMono, LectureContent.class)
                .onErrorResume(error -> ServerResponse.badRequest().build());
    }

    // 강사에 매칭된 강의 목록 조회
    public Mono<ServerResponse> getLectureOnTeacher(ServerRequest request) {
        Integer teacherId = Integer.valueOf(request.pathVariable("teacherId"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return Mono.just(lectureRepository.findAllByMemberId(teacherId))
                .flatMap(lecture -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(lecture, Lecture.class)
                        .switchIfEmpty(notFound));
    }

    // 강사가 강의정보 테이블의 시험점수(testScore)를 반영한다.
    public Mono<ServerResponse> updateTestScore(ServerRequest request) {
        Mono<LectureInfo> lectureInfoMono = request.bodyToMono(LectureInfo.class)
                .flatMap(lectureApplicationService::updateTestScore);

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(lectureInfoMono, Lecture.class)
                .onErrorResume(error -> ServerResponse.badRequest().build());
    }
}
