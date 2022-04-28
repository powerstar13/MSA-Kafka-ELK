package study.kafka.producer.infrastructure.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static reactor.netty.http.HttpProtocol.H2C;

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Override
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder()
                .inMemory("lecture")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1") // DB연결이 닫혀도 유지되도록 설정
                .username("sa")
                .build());
    }

    @Bean
    public ConnectionFactoryInitializer h2DbInitializer() {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("lecture.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("lectureContent.sql"));

        initializer.setConnectionFactory(connectionFactory());
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }
}
