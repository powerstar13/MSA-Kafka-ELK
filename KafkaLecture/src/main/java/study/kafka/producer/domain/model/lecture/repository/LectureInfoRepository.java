package study.kafka.producer.domain.model.lecture.repository;

import study.kafka.producer.domain.model.lecture.LectureInfo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface LectureInfoRepository extends ReactiveCrudRepository<LectureInfo, Integer> {

    @Query("select * from lectureInfo where lectureId=:lectureId and memberId=:memberId")
    Mono<LectureInfo> findByLectureInfo(Integer lectureId, Integer memberId);

    @Query("select * from lectureInfo where lectureId=:lectureId")
    Flux<LectureInfo> findByLectureInfoList(Integer lectureId);

    // 강의정보 테이블에 test_score(시험점수) 값을 설정한다.
    // (현재) where 조건으로 memberId, lectureId를 주고, 데이터를 update
    @Query("update lectureInfo set testScore = :score where lectureId = :lectureId and memberId = :memberId")
    Mono<LectureInfo> updateTestScore(Integer score, Integer lectureId, Integer memberId);
}
