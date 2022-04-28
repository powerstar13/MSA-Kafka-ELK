package study.kafka.producer.application.lecture;

import study.kafka.producer.domain.model.lecture.Lecture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LectureApplicationServiceTest {

    @Autowired
    LectureApplicationService lectureApplicationService;

    @Test
    void getLectureAllListTest() {
        Flux<Lecture> result = lectureApplicationService.getLectureAllList();
        StepVerifier.create(result)
                .assertNext(o -> assertEquals(o.getLectureName(), "testName"))
                .assertNext(o -> assertEquals(o.getLectureName(), "테스트강의"))
                .verifyComplete();
    }

}