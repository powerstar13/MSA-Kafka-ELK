package study.kafka.consumer.infrastructure.exception.status;

import org.springframework.http.HttpStatus;
import study.kafka.consumer.infrastructure.exception.GlobalException;

public class RegistrationFailException extends GlobalException {

    public RegistrationFailException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public RegistrationFailException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public RegistrationFailException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
