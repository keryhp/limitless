package org.uk.ubs.limitless.api;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.uk.ubs.limitless.domain.Items;

import java.util.Collection;

public interface DataService {

    JSONObject fetchItemsInfo();

    @RequestMapping("/fetchAllFeeds")
    Collection<Items> fetchAllFeeds();

    @RequestMapping("/fetchSpecificFeed")
    Items fetchSpecificFeed(String key);
}
