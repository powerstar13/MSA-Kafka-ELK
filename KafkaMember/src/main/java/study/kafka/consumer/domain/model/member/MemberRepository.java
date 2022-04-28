package study.kafka.consumer.domain.model.member;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Integer> {

    Mono<Member> findByMemberNameAndMemberType(String memberName, MemberType memberType);
    Mono<Member> findByMemberNameAndMemberPassword(String memberName, String memberPassword);



}
