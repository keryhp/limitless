package org.uk.ubs.limitless.service.store;

import org.uk.ubs.limitless.api.StoreService;
import org.uk.ubs.limitless.domain.Items;
import org.uk.ubs.limitless.domain.RSSFeed;

public class StoreServiceImpl implements StoreService {

    @Override
    public void storeRSSFeedData(RSSFeed rssFeed) {
        for (Items item : rssFeed.getItems()) {
            inMemoryStore.put(item.getGuid(), item);
        }
    }
}
