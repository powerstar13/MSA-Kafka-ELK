package study.kafka.producer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@OpenAPIDefinition(info = @Info(title = "Lecture Swagger", version = "1.0", description = "Documentation APIs v1.0"))
@SpringBootApplication
@EnableDiscoveryClient
public class KafkaLectureApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaLectureApplication.class, args);
	}

}
