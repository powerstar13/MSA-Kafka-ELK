package study.kafka.producer.domain.model.lecture;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(value = "lectureContent")
public class LectureContent {

    @Id
    @Column(value ="id")
    Integer id;

    @Column(value ="contentName")
    String contentName;

    @Column(value ="contentBody")
    String contentBody;

    @Column(value ="lectureId")
    Integer lectureId;

    @Column(value ="contentType")
    String contentType;

    @Column(value ="insertDt")
    private LocalDateTime insertDt;

    @Column(value ="updateDt")
    private LocalDateTime updateDt;
}
