package study.kafka.producer.infrastructure.webClient;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
public class LectureWebClient {

    private final WebClient client;
    public LectureWebClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8083").build();
    }

    public Mono getMemberInfo(Integer memId) {

        return client.get()
                .uri("/member/findMemberInfo/"+memId)
                .retrieve()
                .bodyToMono(HashMap.class);

    }
}
