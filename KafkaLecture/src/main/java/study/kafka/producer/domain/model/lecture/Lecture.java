package study.kafka.producer.domain.model.lecture;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "lecture")
public class Lecture {
    @Id
    @Column(value = "lectureId")
    private Integer lectureId;

    @Column(value ="lectureName")
    private String lectureName;

    @Column(value ="memberId")
    private Integer memberId;

    @Column(value="memberName")
    private String memberName;

    @Column(value ="lectureShowYn")
    private Boolean lectureShowYn;

    @Column(value ="lectureTotalScore")
    private Integer lectureTotalScore;

    @Column(value ="insertDt")
    private LocalDateTime insertDt;

    @Column(value ="updateDt")
    private LocalDateTime updateDt;

}
