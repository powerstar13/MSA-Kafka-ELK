package study.kafka.consumer.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import study.kafka.consumer.application.member.MemberApplicationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final MemberApplicationService memberApplicationService;

    /**
     * 강의 오픈 토픽 구독
     * @param message : 받은 메시지
     */
    @KafkaListener(topics = "lecture-open-topic", groupId = "lecture-consumer-group")
    public void lectureOpenConsume(String message) {
        log.info(String.format("===== Consumed message >>> %s =====", message));

        memberApplicationService.studentToLectureShowInfoAlimtalk(message); // 학생에게 강의 노출 여부 알림톡 발송
    }
}
