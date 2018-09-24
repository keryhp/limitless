package org.uk.ubs.limitless.domain;

import java.util.ArrayList;
import java.util.List;

public class RSSFeed {
    private List<Items> items = new ArrayList<>();

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}

