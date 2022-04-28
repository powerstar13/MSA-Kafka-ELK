package study.kafka.consumer.infrastructure.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

// DefaultErrorAttributes는 스프링이 자동으로 만들어내는 에러를 담고 있다.
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> map = super.getErrorAttributes(request, options);

        Throwable throwable = getError(request);
        if (throwable instanceof GlobalException) {
            // 사용자 정의 에러일 경우, GlobalException을 통해 따로 처리된다.
            GlobalException ex = (GlobalException) getError(request);
            map.put("rt", ex.getStatus().value());
            map.remove("status");
            map.put("rtMsg", ex.getReason());
            map.remove("error");
        }

        if (map.get("status") != null) { // status --> rt 로 치환
            map.put("rt", map.get("status"));
            map.remove("status");
        }
        if (map.get("error") != null) { // error --> rtMsg 로 치환
            map.put("rtMsg", map.get("error"));
            map.remove("error");
        }

        // 불필요 필드 제거
        if (map.get("message") != null) map.remove("message");
        if (map.get("timestamp") != null) map.remove("timestamp");
        if (map.get("requestId") != null) map.remove("requestId");

        return map;
    }
}
