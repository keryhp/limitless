package org.uk.ubs.limitless.service.runnables;

import org.uk.ubs.limitless.api.RSSFeedService;
import org.uk.ubs.limitless.service.feeds.RSSFeedServiceImpl;
import twitter4j.Logger;

public class RSSFeedRunner implements Runnable {

    private static Logger LOG = Logger.getLogger(RSSFeedServiceImpl.class);
    RSSFeedService rssFeedService = new RSSFeedServiceImpl();
    private String url;

    public RSSFeedRunner(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            rssFeedService.searchFeed(url);
        } catch (Exception e) {
            LOG.error("Unable to read from RSS" + e.getMessage());
            e.printStackTrace();
        }
    }
}
