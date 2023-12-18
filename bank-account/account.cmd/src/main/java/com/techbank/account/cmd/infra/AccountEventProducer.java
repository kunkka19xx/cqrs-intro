package com.techbank.account.cmd.infra;

import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Override
    public void produce(String topicName, BaseEvent event) {
        this.kafkaTemplate.send(topicName, event);
    }
}
