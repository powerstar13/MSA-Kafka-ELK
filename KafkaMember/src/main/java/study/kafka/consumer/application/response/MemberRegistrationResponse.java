package study.kafka.consumer.application.response;

import lombok.*;
import study.kafka.consumer.presentation.shared.response.CreatedSuccessResponse;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegistrationResponse extends CreatedSuccessResponse {

    private int memberId; // 회원 고유번호
}
