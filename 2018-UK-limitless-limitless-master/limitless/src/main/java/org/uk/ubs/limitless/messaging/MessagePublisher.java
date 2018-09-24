package org.uk.ubs.limitless.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;
import org.uk.ubs.limitless.domain.Categories;
import org.uk.ubs.limitless.domain.Feed;
import org.uk.ubs.limitless.domain.MessageContent;
import org.uk.ubs.limitless.util.JsonUtil;


@Component
public class MessagePublisher {

    @Autowired
    private JmsTemplate jmsTempalte;

    public void sendMessage(Feed feed) {
        MessagePostProcessor messagePostProcessor = message -> {
            Integer integer = Categories.getFeedCategoryByUser(feed.getPublisher());
            message.setIntProperty("Category", integer);
            return message;
        };
        for (MessageContent mc : feed.getMessageContent()) {
            jmsTempalte.convertAndSend("processor_queue", JsonUtil.convertToJson(mc), messagePostProcessor);
        }
    }
}