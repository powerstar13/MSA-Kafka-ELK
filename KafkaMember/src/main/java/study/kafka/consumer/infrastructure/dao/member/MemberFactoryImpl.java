package study.kafka.consumer.infrastructure.dao.member;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import study.kafka.consumer.domain.model.member.Member;
import study.kafka.consumer.domain.model.member.MemberFactory;
import study.kafka.consumer.domain.model.member.MemberType;
import study.kafka.consumer.infrastructure.exception.status.ExceptionMessage;
import study.kafka.consumer.application.member.MemberSha256;
import study.kafka.consumer.infrastructure.exception.status.BadRequestException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberFactoryImpl implements MemberFactory {

    /**
     * 회원 정보 세팅
     * @param memberName : 이름
     * @param memberPassword : 비밀번호
     * @param memberType : 회원 유형
     * @return Member : 회원 정보
     */
    @Override
    public Member memberBuilder(String memberName, String memberPassword, MemberType memberType) {

        if (StringUtils.isBlank(memberName)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberName.getMessage());
        if (StringUtils.isBlank(memberPassword)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberPassword.getMessage());
        if (memberType == null) throw new BadRequestException(ExceptionMessage.IsRequiredMemberType.getMessage());

        return Member.builder()
            .memberName(memberName)
            .memberPassword(MemberSha256.encrypt(memberPassword))
            .memberType(memberType)
            .build();
    }

    /**
     * 초기 사이트 운영자 데이터 세팅
     * @return List<Member> : 운영자 목록
     */
    @Override
    public List<Member> adminSetUpListBuilder() {

        String[] memberNameArray = new String[] { "홍준성", "유하얀", "이휘수", "배소현", "박철훈", "윤해서", "심재현", "김영수", "지경희", "이성화", "정승훈", "설동찬" };

        return Arrays.stream(memberNameArray)
            .map(s -> this.memberBuilder(s, "msa", MemberType.ADMIN))
            .collect(Collectors.toList());
    }
}
