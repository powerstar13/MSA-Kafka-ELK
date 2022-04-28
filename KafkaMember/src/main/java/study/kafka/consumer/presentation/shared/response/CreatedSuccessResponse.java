package study.kafka.consumer.presentation.shared.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CreatedSuccessResponse {

    private final int rt = HttpStatus.CREATED.value();

    private final String rtMsg = HttpStatus.CREATED.getReasonPhrase();
}
