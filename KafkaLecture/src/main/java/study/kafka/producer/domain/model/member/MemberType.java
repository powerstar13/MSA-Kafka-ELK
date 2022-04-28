package study.kafka.producer.domain.model.member;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MemberType {
    ADMIN("ADMIN", "사이트 운영자"),
    TEACHER("TEACHER", "강사"),
    STUDENT("STUDENT", "학생");

    private String name;
    private String description;
}
