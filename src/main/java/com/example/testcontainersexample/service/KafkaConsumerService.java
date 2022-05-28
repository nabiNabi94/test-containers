package com.example.testcontainersexample.service;

import com.example.testcontainersexample.dto.UserDTO;
import com.example.testcontainersexample.entity.User;
import com.example.testcontainersexample.repository.UserRepository;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class KafkaConsumerService {

    private UserRepository userRepository;
    private Gson gson;
    public KafkaConsumerService(UserRepository userRepository, Gson gson) {
        this.userRepository = userRepository;
        this.gson = gson;
    }

    @KafkaListener(topics ="${spring.kafka.template.default-topic}")
    public void listener(ConsumerRecord<String,String> record){
        UserDTO userDTO = gson.fromJson(record.value(), UserDTO.class);
        User user = new User()
                .setFirstName(userDTO.getFirstName())
                .setLastName(userDTO.getLastName());
        userRepository.save(user);
    }
}
