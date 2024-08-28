package com.taskmanagement.demo.config;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taskmanagement.demo.dto.TaskDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Configuration
@EnableKafka
public class KafkaConfigPublisher {

    @Autowired
    private KafkaProperties kafkaProperties;


    @Bean
    public Map<String, Object> producerConfig(){
        Map<String,Object> mp = new HashMap<>();
        mp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        mp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerialize.class);
        return mp;
    }

    /*@Bean
    public void sendMessageToTopic(String message){
        CompletableFuture<SendResult<String, Object>> send = template.send("java-topic", message);
        send.whenComplete((result,ex)->{
            if(ex==null){
                System.out.println("Send Message=[ "+message+" ] with offset=[ "+ result.getRecordMetadata()+" ]");
            }
            else{
                System.out.println("Unable to send the message=[ "+message+" due to=["+ex.getMessage()+" ]");
            }
        });
    }*/

    @Bean
    public ProducerFactory<String, Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic createTopic(){
        return TopicBuilder.name("Java-Topic1").partitions(2).replicas(2).build();
    }

    /*@Bean
    public void sendEventToTopic(TaskDto customer){
        CompletableFuture<SendResult<String, Object>> send = template.send("java-topic", customer);
        send.whenComplete((result,ex)->{
            if(ex==null){
                System.out.println("Send Message=[ "+customer+" ] with offset=[ "+ result.getRecordMetadata()+" ]");
            }
            else{
                System.out.println("Unable to send the message=[ "+customer+" due to=["+ex.getMessage()+" ]");
            }
        });
    }*/
}
