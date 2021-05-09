package com.test.message.consumers;

import static com.test.message.constants.QueueConstants.COMPLEX_MESSAGE_QUEUE;
import static com.test.message.constants.QueueConstants.SIMPLE_MESSAGE_QUEUE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.message.models.ComplexMessage;
import com.test.message.models.SimpleMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    final ObjectMapper objectMapper;

    public Consumer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Async
    @JmsListener(destination = SIMPLE_MESSAGE_QUEUE)
    public void listener(@NonNull final SimpleMessage simpleMessage) {
        log.info("received simpleMessage on {}: {}", SIMPLE_MESSAGE_QUEUE, toJson(simpleMessage));
    }

    @Async
    @JmsListener(destination = COMPLEX_MESSAGE_QUEUE)
    public void listener(@NonNull final ComplexMessage complexMessage) {
        log.info(
                "received complexMessage on {}: {}", COMPLEX_MESSAGE_QUEUE, toJson(complexMessage));
    }

    String toJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }

        return "";
    }
}
