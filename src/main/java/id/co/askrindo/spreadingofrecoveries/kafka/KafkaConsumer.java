package id.co.askrindo.spreadingofrecoveries.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    
    @KafkaListener(topics="mytopic", groupId="mygroup")
    public void consumeFromTopic(String message) {
        System.out.println("Consumed message = "+message);
    }
    
}
