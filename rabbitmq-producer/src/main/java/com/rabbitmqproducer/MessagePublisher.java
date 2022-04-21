package com.rabbitmqproducer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("Producer")
public class MessagePublisher {

    private RabbitTemplate template;

    @Autowired
    public MessagePublisher(RabbitTemplate template){
        this.template = template;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody CustomMessage customMessage){
        customMessage.setMessageId(UUID.randomUUID().toString());
        customMessage.setMessageDate(new Date());
        template.convertAndSend(MQConfig.Exchange,
                MQConfig.ROUTING_KEY, customMessage);
        return "Message published!";
    }
}
