package study.kafka.consumer.presentation.member.request;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import study.kafka.consumer.infrastructure.exception.status.BadRequestException;
import study.kafka.consumer.infrastructure.exception.status.ExceptionMessage;
import study.kafka.consumer.presentation.shared.request.RequestVerify;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegistrationRequest implements RequestVerify {

    private String memberName; // 회원 이름

    private String memberPassword; // 회원 비밀번호

    @Override
    public void verify() {

        if (StringUtils.isBlank(memberName)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberName.getMessage());
        if (StringUtils.isBlank(memberPassword)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberPassword.getMessage());
    }
}
