package study.kafka.producer.domain.model.lecture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum LectureInfoState {
    ENROLMENT("Enrolment", "수강신청"),
    TAKING("TAKING", "수강중"),
    END("END", "수강종료");

    private String name;
    private String description;
}
