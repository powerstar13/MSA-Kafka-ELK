package study.kafka.consumer.application.response;

import lombok.*;
import study.kafka.consumer.domain.model.member.MemberType;
import study.kafka.consumer.presentation.shared.response.SuccessResponse;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse extends SuccessResponse {

    private int memberId; // 회원 고유번호

    private MemberType memberType;
}
