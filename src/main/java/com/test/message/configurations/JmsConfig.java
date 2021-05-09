package com.test.message.configurations;

import static com.test.message.constants.QueueConstants.COMPLEX_MESSAGE_QUEUE;
import static com.test.message.constants.QueueConstants.SIMPLE_MESSAGE_QUEUE;

import javax.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {
    @Bean(SIMPLE_MESSAGE_QUEUE)
    public Queue simpleMessageQueue() {
        return new ActiveMQQueue(SIMPLE_MESSAGE_QUEUE);
    }

    @Bean(COMPLEX_MESSAGE_QUEUE)
    public Queue complexMessageQueue() {
        return new ActiveMQQueue(COMPLEX_MESSAGE_QUEUE);
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
