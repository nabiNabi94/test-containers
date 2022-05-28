package com.example.testcontainersexample.service;

import com.example.testcontainersexample.dto.UserDTO;
import com.example.testcontainersexample.entity.User;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;


@Service
@EnableScheduling
public class KafkaProducer {

    @Value(value ="${spring.kafka.template.default-topic}")
    private  String topic;
    private KafkaTemplate kafkaTemplate;

    public KafkaProducer(KafkaTemplate kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public UserDTO sendMassages() throws ExecutionException, InterruptedException {
        UserDTO userDTO = new UserDTO().setFirstName("Gamer").setLastName("Simpson");
        ProducerRecord<String,UserDTO> producerRecord = new ProducerRecord<>(topic,"test",userDTO);
        ListenableFuture<SendResult<String, UserDTO>> future = kafkaTemplate.send(producerRecord);
        return future.get().getProducerRecord().value();

    }
}
