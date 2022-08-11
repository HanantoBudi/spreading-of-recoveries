package id.co.askrindo.spreadingofrecoveries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import id.co.askrindo.spreadingofrecoveries.kafka.KafkaProducer;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    
    @Autowired
    KafkaProducer kafkaProducer;

    @PostMapping(value="/post")
    public void sendMessage() {
        kafkaProducer.publishToTopic("try");
    }
}
