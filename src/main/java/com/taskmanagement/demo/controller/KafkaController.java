package com.taskmanagement.demo.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.internals.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class KafkaController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);
    private final KafkaTemplate<String, Object> template;
    private final String topicName;
    private final int messagePerRequest;
    private CountDownLatch countDownLatch1;

    public KafkaController(KafkaTemplate<String, Object> template,
                           @Value("${spring.kafka.consumer.topic-name}") String topic,
                           @Value("${spring.kafka.consumer.message-per-request}") int messagePerRequest){
        this.template = template;
        this.topicName = topic;
        this.messagePerRequest = messagePerRequest;
    }


    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        countDownLatch1 = new CountDownLatch(messagePerRequest);
        IntStream.range(0,countDownLatch1)
                .forEach(i->this.template.send(topicName, String.valueOf(i)),
                        new PracticalAdvice("A Practical Advice", i));
        countDownLatch1.await(60, TimeUnit.SECONDS);
        logger.info("All message received");
        return "hello kafka";
    }

    @KafkaListener(topics = "advice-topic",clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory",topicPartitions = {@TopicPartition(topic = "",
    partitions = {"2"},partitionOffsets = {@PartitionOffset(partition = "2",initialOffset = "3")})})
    public void listenAsObject(ConsumerRecord<String, PracticalAdvice> consumerRecord,
                               @Payload PracticalAdvice payload){
        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", consumerRecord.key(),
                typeIdHeader(consumerRecord.headers()), payload, consumerRecord.toString());
        countDownLatch1.countDown();
    }

    @KafkaListener(topics = "advice-topic", clientIdPrefix = "string",
            containerFactory = "kafkaListenerStringContainerFactory")
    public void listenasString(ConsumerRecord<String, String> cr,
                               @Payload String payload) {
        logger.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
    }




}
