package study.kafka.producer.domain.model.lecture;

import study.kafka.producer.domain.model.lecture.Lecture;

import java.util.List;

public interface LectureFactory {
    Lecture lectureBuilder(String lectureName);
    List<Lecture> adminSetupListBuilder();
}
