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
@Table(value = "lectureInfo")
public class LectureInfo {
    @Id
    @Column(value = "lectureInfoId")
    private Integer lectureInfoId;

    @Column(value = "lectureId")
    private Integer lectureId;

    @Column(value ="memberId")
    private Integer memberId;

    @Column(value ="testScore")
    private Integer testScore;

    @Column(value="lectureState")
    private String LectureState;

    @Column(value ="lectureScore")
    private Integer lectureScore;

    @Column(value ="insertDt")
    private LocalDateTime insertDt;

    @Column(value ="updateDt")
    private LocalDateTime updateDt;
}
