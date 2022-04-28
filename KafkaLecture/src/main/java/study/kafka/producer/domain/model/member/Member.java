package study.kafka.producer.domain.model.member;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private int memberId; // 회원 고유번호

    private String memberName; // 회원 이름

    private String memberPassword; // 회원 비밀번호

    private MemberType memberType; // 회원 유형

    private Timestamp insertDt; // 회원 생성일

    private Timestamp updateDt; // 회원 수정일
}