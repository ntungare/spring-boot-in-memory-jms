package com.test.message.consumers;

import static com.test.message.constants.QueueConstants.QUEUE_NAME;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.message.models.SimpleMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    final ObjectMapper objectMapper;

    public Consumer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = QUEUE_NAME)
    public void listener(@NonNull final SimpleMessage simpleMessage) {
        log.info("received: {}", toJson(simpleMessage));
    }

    String toJson(final SimpleMessage simpleMessage) {
        try {
            return objectMapper.writeValueAsString(simpleMessage);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }

        return "";
    }
}
