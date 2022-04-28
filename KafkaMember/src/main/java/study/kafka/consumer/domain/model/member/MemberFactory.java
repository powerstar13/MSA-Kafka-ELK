package study.kafka.consumer.domain.model.member;

import java.util.List;

public interface MemberFactory {

    Member memberBuilder(String memberName, String memberPassword, MemberType memberType); // 회원 정보 세팅
    List<Member> adminSetUpListBuilder(); // 초기 사이트 운영자 데이터 세팅
}
