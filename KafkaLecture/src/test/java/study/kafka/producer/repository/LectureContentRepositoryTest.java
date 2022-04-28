package study.kafka.producer.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import study.kafka.producer.domain.model.lecture.repository.LectureContentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class LectureContentRepositoryTest {

    @Autowired
    LectureContentRepository lectureContentRepository;

    @Test
    void readsAllContentsCorrectly() {
        StepVerifier.create(lectureContentRepository.findAll())
                .assertNext(o -> assertEquals("test1", o.getContentName()))
                .assertNext(o -> assertEquals("test2", o.getContentName()))
                .assertNext(o -> assertEquals("test2", o.getContentName()))
                .verifyComplete();
    }

}