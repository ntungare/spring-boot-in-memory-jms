package com.test.message.controllers;

import static com.test.message.constants.QueueConstants.COMPLEX_MESSAGE_QUEUE;
import static com.test.message.constants.QueueConstants.SIMPLE_MESSAGE_QUEUE;

import com.test.message.models.ComplexMessage;
import com.test.message.models.ImmutableComplexMessage;
import com.test.message.models.ImmutableSimpleMessage;
import java.util.stream.IntStream;
import javax.jms.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class IndexController {
    final Queue simpleMessageQueue;
    final Queue complexMessageQueue;
    final JmsTemplate jmsTemplate;

    public IndexController(
            @Qualifier(SIMPLE_MESSAGE_QUEUE) final Queue simpleMessageQueue,
            @Qualifier(COMPLEX_MESSAGE_QUEUE) final Queue complexMessageQueue,
            final JmsTemplate jmsTemplate) {
        this.simpleMessageQueue = simpleMessageQueue;
        this.complexMessageQueue = complexMessageQueue;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/")
    @ResponseBody
    ComplexMessage index(
            @RequestHeader("int-message") final String message,
            @RequestHeader("int-version") final Double version,
            @RequestHeader("int-length") final Long length) {
        log.info("message: {}, version: {}, length: {}", message, version, length);
        final ImmutableSimpleMessage simpleMessage =
                ImmutableSimpleMessage.builder()
                        .message(message)
                        .version(version)
                        .length(length)
                        .build();

        final ImmutableComplexMessage complexMessage =
                ImmutableComplexMessage.builder()
                        .message(message)
                        .version(version)
                        .length(length)
                        .simpleMessage(simpleMessage)
                        .build();

        log.info("sending simple message");
        IntStream.range(0, 10)
                .forEach(
                        index ->
                                jmsTemplate.convertAndSend(
                                        simpleMessageQueue,
                                        simpleMessage.withVersion(
                                                version + ((double) index / 10))));

        log.info("sending complex message");
        IntStream.range(0, 10)
                .forEach(
                        index ->
                                jmsTemplate.convertAndSend(
                                        complexMessageQueue,
                                        complexMessage.withVersion(
                                                version + ((double) index / 10))));

        log.info("done sending messages");
        return complexMessage;
    }
}
