package study.kafka.consumer.domain.model.member;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "member")
public class Member {

    @Id // ID로 엔티티를 식별한다.
    @Column(value = "memberId")
    private int memberId; // 회원 고유번호

    @Column(value = "memberName")
    private String memberName; // 회원 이름

    @Column(value = "memberPassword")
    private String memberPassword; // 회원 비밀번호

    @Column(value = "memberType")
    private MemberType memberType; // 회원 유형

    @Column(value = "memberPhone")
    private String memberPhone; // 회원 연락처

    @Column(value = "insertDt")
    private Timestamp insertDt; // 회원 생성일

    @Column(value = "updateDt")
    private Timestamp updateDt; // 회원 수정일
}
