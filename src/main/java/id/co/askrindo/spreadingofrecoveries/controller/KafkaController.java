package id.co.askrindo.spreadingofrecoveries.controller;

import id.co.askrindo.spreadingofrecoveries.model.CreateSor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import id.co.askrindo.spreadingofrecoveries.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @PostMapping(value="/publish")
    public ResponseEntity<String>  sendMessage(@RequestBody CreateSor createSor) {
        kafkaProducer.sendMessage(createSor);
        return ResponseEntity.ok("Message has been sent to kafka topic. ");
    }

}
