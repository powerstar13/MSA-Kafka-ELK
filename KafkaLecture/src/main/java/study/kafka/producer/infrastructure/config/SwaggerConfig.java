package study.kafka.producer.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi lectureApi() {

        return GroupedOpenApi.builder()
                .group("lecture")
                .pathsToMatch("/lecture/**")
                .build();
    }
}