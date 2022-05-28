package com.example.testcontainersexample.integrationTest;

import com.example.testcontainersexample.dto.UserDTO;
import com.example.testcontainersexample.entity.User;
import com.example.testcontainersexample.repository.UserRepository;
import com.example.testcontainersexample.service.KafkaConsumerService;
import com.example.testcontainersexample.service.KafkaProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
//started in,container for
@SpringBootTest
public class ServicesTest extends TestContainerConfiguration{


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafkaConsumerService kafkaConsumer;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Value(value ="${spring.kafka.template.default-topic}")
    private  String topic;

    @Test
    void sendMassageProducerAndGettingMassagesConsumer() {
        Consumer<String,String> consumer = consumer();
        UserDTO userDTO = null;
        try {
            userDTO = kafkaProducer.sendMassages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.subscribe(Collections.singletonList(topic));
        consumer.poll(Duration.ofSeconds(5)).forEach(record -> kafkaConsumer.listener(record));
        consumer.close();
        User userProducer = new User(userDTO);
        userProducer.setId(1);
        boolean userDB = userRepository.existsById(1);
        assertEquals(true,userDB);
    }

    private KafkaConsumer<String,String> consumer(){
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"test-group");
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG,"true");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafka.getBootstrapServers());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new KafkaConsumer<>(props);
    }


}
