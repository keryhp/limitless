package org.uk.ubs.limitless.domain;

import java.util.List;

public class Feed {

    private String publisher;
    private List<MessageContent> messageContent;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<MessageContent> getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(List<MessageContent> messageContent) {
        this.messageContent = messageContent;
    }
}
