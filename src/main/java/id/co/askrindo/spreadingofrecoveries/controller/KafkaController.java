package id.co.askrindo.spreadingofrecoveries.controller;

import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.askrindo.spreadingofrecoveries.kafka.KafkaProducer;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    
    @Autowired
    KafkaProducer producer;

    @PostMapping(value="/post")
    public void sendMessage(@RequestParam("msg") String msg) {
        producer.publishToTopic(msg);
    }
}
