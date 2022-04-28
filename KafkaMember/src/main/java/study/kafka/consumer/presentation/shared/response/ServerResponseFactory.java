package study.kafka.consumer.presentation.shared.response;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class ServerResponseFactory {

    /**
     * Success response without body
     * @return Mono<ServerResponse> : body 없이 성공만 반환
     */
    public static Mono<ServerResponse> successOnly() {
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .build();
    }

    /**
     * Success response with body (shortcut)
     * @param response : 반환할 값
     * @return Mono<ServerResponse> : body 정보 반환
     */
    public static Mono<ServerResponse> successBodyValue(Object response) {

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(response);
    }
}
