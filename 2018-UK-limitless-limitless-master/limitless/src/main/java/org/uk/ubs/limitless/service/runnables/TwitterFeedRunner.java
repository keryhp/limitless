package org.uk.ubs.limitless.service.runnables;

import org.apache.log4j.Logger;
import org.uk.ubs.limitless.api.KeywordService;
import org.uk.ubs.limitless.api.StoreService;
import org.uk.ubs.limitless.api.TwitterFeedService;
import org.uk.ubs.limitless.messaging.MessagePublisher;
import org.uk.ubs.limitless.service.feeds.TwitterFeedServiceImpl;
import org.uk.ubs.limitless.service.nlg.KeywordServiceImpl;
import org.uk.ubs.limitless.service.store.StoreServiceImpl;

import java.io.IOException;
import java.util.List;

public class TwitterFeedRunner implements Runnable {

    private static Logger LOG = Logger.getLogger(TwitterFeedRunner.class);
    private String keywords;
    private MessagePublisher messagePublisher;
    private StoreService storeService = new StoreServiceImpl();
    private KeywordService keywordService = new KeywordServiceImpl();
    private TwitterFeedService twitterFeedService = new TwitterFeedServiceImpl(messagePublisher, storeService);

    public TwitterFeedRunner(MessagePublisher messagePublisher, StoreService storeService) {
        this.messagePublisher = messagePublisher;
        this.storeService = storeService;
    }

    @Override
    public void run() {
        List<String> keywords = null;
        try {
            LOG.info("Building Keywords..");
            keywords = keywordService.buildKeywords();
            for (String keyword : keywords) {
                LOG.info("Searching Feed on Twitter");
                twitterFeedService.searchFeed(keyword);
            }
        } catch (IOException e) {
            LOG.error("Error fetching keywords" + e.getMessage());
            e.printStackTrace();
        }
    }
}
