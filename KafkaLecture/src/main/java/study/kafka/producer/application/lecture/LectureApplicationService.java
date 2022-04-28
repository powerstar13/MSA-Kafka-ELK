package study.kafka.producer.application.lecture;

import study.kafka.producer.domain.model.lecture.Lecture;
import study.kafka.producer.domain.model.lecture.LectureContent;
import study.kafka.producer.domain.model.lecture.LectureInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface LectureApplicationService {

    // 수강자 성적 입력
    Mono<Lecture> updateStudentScore(Lecture lecture);

    // 시험 컨텐츠 추가
    Mono<LectureContent> updateNewExam(LectureContent lectureContent);

    // 강의 컨텐츠 업로드
    Mono<LectureContent> uploadContent(LectureContent lectureContent);

    // 강사에 매칭된 강의 목록 조회
    Mono<Lecture> getLectureOnTeacher(Map<String, Object> param);

    // 강의 개설
    Mono<Lecture> createLecture(Lecture lecture);

    // 강의에 강사 매칭
    Mono<Lecture> matchingLecture(Lecture lecture);

    // 강의 아이디로 강의 조회 (테스트)
    Mono<Lecture> getLecture(Integer lectureId);

    // 강의를 학생 회원에게 노출 및 종료
    Mono<Lecture> changeLectureShowYn(Lecture lecture);

    // 강의를 선택해서 수강 신청 (수강정보 : LectureInfo 생성)
    Mono<LectureInfo> enrolment(LectureInfo lectureInfo);

    // 수강한 강의의 목록 조회
    Flux<Lecture> getLectureAllList();

    // 수강 신청한 강의 컨텐츠 열람
    Mono<LectureContent> getLectureContents(LectureInfo lectureInfo);

    // 수강한 강의에 별점 남기기
    Mono<LectureInfo> setLectureScore(LectureInfo lectureInfo);

    // 학생 회원이 제출한 별점을 열람
    Mono<Lecture> getLectureTotalScore(String lectureId);

    // 강사가 시험점수값을 업데이트
    Mono<LectureInfo> updateTestScore(LectureInfo lectureInfo);

}