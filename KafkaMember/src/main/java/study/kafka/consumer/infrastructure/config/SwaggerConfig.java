package study.kafka.consumer.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
    info = @Info(
        title = "회원 Domain Server",
        version = "v1.0.0",
        description = "회원 서비스 API 문서"
    )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi memberApi() {

        return GroupedOpenApi.builder()
            .group("member")
            .pathsToMatch("/member/**")
            .build();
    }
}
