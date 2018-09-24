package org.uk.ubs.limitless.api;

import com.sun.syndication.io.FeedException;

import java.io.IOException;

public interface RSSFeedService {
    void searchFeed(String Keyword) throws IOException, FeedException, InterruptedException;
}
