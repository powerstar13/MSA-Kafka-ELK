package study.kafka.consumer.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final String TOPIC = "member-registration-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 회원 등록 topic 메시지 보내기
     * @param message : 발행될 메시지
     */
    public void sendMessage(String message) {
        log.info(String.format("===== Producing message >>> %s =====", message));
        // topic에 message 발행
        kafkaTemplate.send(TOPIC, message);
    }
}
