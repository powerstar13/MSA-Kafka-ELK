package study.kafka.producer.domain.model.lecture;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LectureFactoryImpl implements LectureFactory {
    @Override
    public Lecture lectureBuilder(String lectureName) {
        return Lecture.builder().lectureName(lectureName).build();
    }

    @Override
    public List<Lecture> adminSetupListBuilder() {
        return Arrays.asList(
                this.lectureBuilder("스프링 강의"),
                this.lectureBuilder("마이크로 서비스 강의"),
                this.lectureBuilder("자바 강의"),
                this.lectureBuilder("데이터베이스 강의"),
                this.lectureBuilder("네트워크 강의"),
                this.lectureBuilder("알고리즘 강의"),
                this.lectureBuilder("자료구조 강의")
        );
    }
}
