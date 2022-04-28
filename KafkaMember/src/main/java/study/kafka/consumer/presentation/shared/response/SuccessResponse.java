package study.kafka.consumer.presentation.shared.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class SuccessResponse {

    private final int rt = HttpStatus.OK.value();

    private final String rtMsg = HttpStatus.OK.getReasonPhrase();
}
