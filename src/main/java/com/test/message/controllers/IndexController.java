package com.test.message.controllers;

import com.test.message.models.ImmutableSimpleMessage;
import com.test.message.models.SimpleMessage;
import javax.jms.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class IndexController {
    final Queue queue;
    final JmsTemplate jmsTemplate;

    public IndexController(final Queue queue, final JmsTemplate jmsTemplate) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/")
    @ResponseBody
    SimpleMessage index(
            @RequestHeader("int-message") final String message,
            @RequestHeader("int-version") final Double version,
            @RequestHeader("int-length") final Long length) {
        log.info("message: {}, version: {}, length: {}", message, version, length);
        final SimpleMessage simpleMessage =
                ImmutableSimpleMessage.builder()
                        .message(message)
                        .version(version)
                        .length(length)
                        .build();

        jmsTemplate.convertAndSend(queue, simpleMessage);

        return simpleMessage;
    }
}
