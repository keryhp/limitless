package org.uk.ubs.limitless.service.controllers;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uk.ubs.limitless.api.DataService;
import org.uk.ubs.limitless.api.StoreService;
import org.uk.ubs.limitless.domain.Items;
import org.uk.ubs.limitless.util.JsonUtil;

import java.util.Collection;

@RestController
public class DataServiceImpl implements DataService {

    @Override
    @RequestMapping("/fetchItems")
    public JSONObject fetchItemsInfo() {
        String source = "D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\country-info.json";
        return JsonUtil.readFromJson(source);
    }

    @Override
    @RequestMapping("/fetchAllFeeds")
    public Collection<Items> fetchAllFeeds() {
        return StoreService.inMemoryStore.values();
    }

    @Override
    @RequestMapping("/fetchSpecificFeed")
    public Items fetchSpecificFeed(String key) {
        Items items = StoreService.inMemoryStore.get(key);
        return items;
    }
}
