package id.co.askrindo.spreadingofrecoveries.kafka;

import id.co.askrindo.spreadingofrecoveries.model.CreateSor;
import id.co.askrindo.spreadingofrecoveries.service.TSubrogationSorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private TSubrogationSorService tSubrogationSorService;

    @Value("${spring.kafka.topic-json.name}")
    private String topicName;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(CreateSor createSor){
        LOGGER.info(String.format("Json message recieved : %s [start] -> %s", topicName, createSor.toString()));
        ResponseEntity<?> tClaimSocCreate = tSubrogationSorService.recoveriesSorProsess(createSor);
        LOGGER.info(String.format("Json message recieved : %s [end] -> %s", topicName, createSor.toString()));
    }
}
