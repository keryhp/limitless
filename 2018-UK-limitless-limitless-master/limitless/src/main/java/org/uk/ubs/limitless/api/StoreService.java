package org.uk.ubs.limitless.api;

import org.uk.ubs.limitless.domain.Items;
import org.uk.ubs.limitless.domain.RSSFeed;

import java.util.HashMap;

public interface StoreService {

    HashMap<String, Items> inMemoryStore = new HashMap<>();

    void storeRSSFeedData(RSSFeed rssFeed);
}
