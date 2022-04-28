package study.kafka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Eureka Client 역할을 수행한다. YAML에 명시한 유레카 서버에 해당 애플리케이션을 등록한다.
public class KafkaMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaMemberApplication.class, args);
	}

}
