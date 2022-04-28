package study.kafka.consumer.infrastructure.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.core.scheduler.Schedulers;
import study.kafka.consumer.domain.model.member.MemberFactory;
import study.kafka.consumer.domain.model.member.MemberRepository;

/**
 * Spring Data R2DBC를 사용하는 방법에는 Spring Data에서 지원하는 Repository를 이용하는 방법과 R2dbcEntityTemplate을 이용하는 방법이 있다.
 * 이번 예제에서는 Repository를 이용할 것이기 때문에 @EnableR2dbcRepositories 어노테이션을 추가한다.
 */
@Slf4j
@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
@RequiredArgsConstructor
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final MemberFactory memberFactory;

    //localhost9093접속->jdbc:h2:mem:msa 유저 sa 연결(embeded h2)
    @Override
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder()
                .inMemory("msa")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1") // DB연결이 닫혀도 유지되도록 설정
                .username("sa")
                .build());
    }

    @Bean
    public ConnectionFactoryInitializer h2DbInitializer() {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();

        initializer.setConnectionFactory(connectionFactory());
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }

    /**
     * 초기 사이트 운영자 데이터 세팅
     *
     * @param memberRepository : member 레포티토리
     * @return CommandLineRunner : 명령 실행
     */
    @Bean
    public CommandLineRunner setUp(MemberRepository memberRepository) {

        return args -> {
            // Member 정보 저장
            memberRepository.saveAll(memberFactory.adminSetUpListBuilder())
                .doOnSubscribe(subscription -> log.info("===== Member Data setUp START ====="))
                .doOnNext(member -> log.info(member.toString()))
                .doOnComplete(() -> log.info("===== Member Data setUp END ====="))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
        };
    }

}