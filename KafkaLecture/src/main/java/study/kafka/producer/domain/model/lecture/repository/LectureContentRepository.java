package study.kafka.producer.domain.model.lecture.repository;

import study.kafka.producer.domain.model.lecture.LectureContent;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface LectureContentRepository extends ReactiveCrudRepository<LectureContent, Integer> {

    @Query("select * from lectureContent where lectureId=:lectureId")
    Mono<LectureContent> getContents(Integer lectureId);

}
