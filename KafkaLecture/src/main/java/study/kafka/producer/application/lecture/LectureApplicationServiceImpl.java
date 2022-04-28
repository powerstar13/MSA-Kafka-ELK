package study.kafka.producer.application.lecture;

import study.kafka.producer.domain.model.lecture.Lecture;
import study.kafka.producer.domain.model.lecture.LectureContent;
import study.kafka.producer.domain.model.lecture.LectureInfo;
import study.kafka.producer.domain.model.lecture.LectureInfoState;
import study.kafka.producer.domain.model.lecture.repository.LectureContentRepository;
import study.kafka.producer.domain.model.lecture.repository.LectureInfoRepository;
import study.kafka.producer.domain.model.lecture.repository.LectureRepository;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LectureApplicationServiceImpl implements LectureApplicationService {

    private final LectureRepository lectureRepository;
    private final LectureContentRepository lectureContentRepository;
    private final LectureInfoRepository lectureInfoRepository;

    public LectureApplicationServiceImpl(LectureRepository lectureRepository, LectureContentRepository lectureContentRepository, LectureInfoRepository lectureInfoRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureContentRepository = lectureContentRepository;
        this.lectureInfoRepository = lectureInfoRepository;
    }

    // 강의를 학생 회원에게 노출 및 종료
    @Override
    public Mono<Lecture> changeLectureShowYn(Lecture lecture) {
        return this.getLecture(lecture.getLectureId())
            .doOnNext(selectedLecture -> selectedLecture.setLectureShowYn(lecture.getLectureShowYn()))
            .flatMap(lectureRepository::save);
    }

    // 수강자 성적 입력
    @Override
    public Mono<Lecture> updateStudentScore(Lecture lecture) {
        return null;
    }

    // 시험 컨텐츠 추가
    @Override
    public Mono<LectureContent> updateNewExam(LectureContent lectureContent) {
        lectureContent.setContentType("exam");
        return lectureContentRepository.save(lectureContent);
    }

    // 강의 컨텐츠 업로드
    @Override
    public Mono<LectureContent> uploadContent(LectureContent lectureContent) {
        lectureContent.setContentType("lecture");
        return lectureContentRepository.save(lectureContent);
    }

    // 강사에 매칭된 강의 목록 조회
    @Override
    public Mono<Lecture> getLectureOnTeacher(Map<String, Object> param) {
        String teacherId = (String) param.get("teacherId");
        return lectureRepository.findByMemberId(teacherId);
    }

    @Override
    public Flux<Lecture> getLectureAllList() {
        return lectureRepository.findAll();
    }

    // 수강한 강의에 별점 남기기
    @Override
    public Mono<LectureInfo> setLectureScore(LectureInfo lectureInfo) {
        return lectureInfoRepository.findByLectureInfo(lectureInfo.getLectureId(), lectureInfo.getMemberId())
                .doOnNext(data-> data.setLectureScore(lectureInfo.getLectureScore()))
                .doOnNext(data-> data.setUpdateDt(LocalDateTime.now()))
                .flatMap(lectureInfoRepository::save)
                .log();
    }

    // 학생 회원이 제출한 별점을 열람
    @Override
    public Mono<Lecture> getLectureTotalScore(String lectureId) {
        return lectureRepository.getTotalScore(lectureId);
    }

    // 강사가 강의정보 테이블에 있는 testScore(시험점수) 값을 업데이트한다.
    @Override
    public Mono<LectureInfo> updateTestScore(LectureInfo lectureInfo) {
        return lectureInfoRepository.updateTestScore(lectureInfo.getTestScore(), lectureInfo.getLectureId(), lectureInfo.getMemberId());
    }

    // 강의 개설
    @Override
    public Mono<Lecture> createLecture(Lecture lecture) {
        String lectureName = lecture.getLectureName();
        String memberName = lecture.getMemberName();
        return lectureRepository.save(new Lecture(null, lectureName, 0, memberName, false, 0, LocalDateTime.now(), LocalDateTime.now()));
    }

    // 강의에 강사 매칭
    @Override
    public Mono<Lecture> matchingLecture(Lecture lecture) {
        return lectureRepository.findById(lecture.getLectureId())
                .doOnNext(data-> data.setMemberName(lecture.getMemberName()))
                .doOnNext(data-> data.setUpdateDt(LocalDateTime.now()))
                .flatMap(lectureRepository::save)
                .log()
                ;
    }

    // 강의 아이디로 강의 조회 (테스트)
    @Override
    public Mono<Lecture> getLecture(Integer lectureId) {
        return lectureRepository.findById(lectureId);
    }

    // 강의를 선택해서 수강 신청 (수강정보 : LectureInfo 생성)
    @Override
    public Mono<LectureInfo> enrolment(LectureInfo lectureInfo){
        Integer lectureId = lectureInfo.getLectureId();
        Integer memberId = lectureInfo.getMemberId();
        return lectureInfoRepository.findByLectureInfo(lectureId, memberId)
                .hasElement()
                .flatMap(data -> {
                            if (data){
                                return Mono.just(lectureInfo); // 중복시 빈값.. 처리필요
                            }
                            else{
                                return this.lectureInfoRepository.save(new LectureInfo(null, lectureId, memberId, 0, LectureInfoState.ENROLMENT.getName(), 0, LocalDateTime.now(), LocalDateTime.now()));
                            }
                        }
                );
    }

    // 수강 신청한 강의 컨텐츠 열람
    public Mono<LectureContent> getLectureContents(LectureInfo lectureInfo){
        return lectureContentRepository.getContents(lectureInfo.getLectureId());
    };

}