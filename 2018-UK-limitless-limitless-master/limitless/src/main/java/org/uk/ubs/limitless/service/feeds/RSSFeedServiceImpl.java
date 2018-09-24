package org.uk.ubs.limitless.service.feeds;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.json.simple.JSONObject;
import org.uk.ubs.limitless.api.RSSFeedService;
import org.uk.ubs.limitless.api.StoreService;
import org.uk.ubs.limitless.domain.Items;
import org.uk.ubs.limitless.domain.RSSFeed;
import org.uk.ubs.limitless.util.FeedUtil;
import org.uk.ubs.limitless.util.JsonUtil;
import org.xml.sax.InputSource;
import twitter4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class RSSFeedServiceImpl implements RSSFeedService {

    private static Logger LOG = Logger.getLogger(RSSFeedServiceImpl.class);
    private boolean islocalFeedEnabled = true;

    public void searchFeed(String url) throws IOException, FeedException, InterruptedException {
        while (true) {
            LOG.info("Grab RSS Feeds=" + url);
            if (islocalFeedEnabled) {
                searchLocalData();
            } else {
                searchRSSFeed(url);
            }
            Thread.sleep(600000);
        }
    }

    private void searchRSSFeed(String url) throws IOException, FeedException {
        SyndFeed feed = null;
        InputStream is = null;

        try {
            URLConnection openConnection = new URL(url).openConnection();
            is = new URL(url).openConnection().getInputStream();
            if ("gzip".equals(openConnection.getContentEncoding())) {
                is = new GZIPInputStream(is);
            }
            InputSource source = new InputSource(is);
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(source);
            List<JSONObject> items = new ArrayList<>();
            if (feed.getEntries() != null && feed.getEntries().size() > 0) {
                List<JSONObject> ims = FeedUtil.toJson(feed.getEntries(), new Date());
                if (ims != null) {
                    items.addAll(ims);
                }
            } else {
                JSONObject im = FeedUtil.toJson(feed);
                items.add(im);
            }
            storeRSSFeedObjects(items);

        } catch (Exception e) {
            LOG.error("Exception occurred when building the feed object out of the url", e);
            e.printStackTrace();
        } finally {
            if (is != null) is.close();
        }
    }

    private RSSFeed searchLocalData() throws IOException {
        RSSFeed aggFeed = new RSSFeed();
        RSSFeed rssFeed = (RSSFeed) JsonUtil.convertFromJson("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\rss1.json", RSSFeed.class);
        aggFeed.setItems(rssFeed.getItems());
        rssFeed = (RSSFeed) JsonUtil.convertFromJson("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\rss2.json", RSSFeed.class);
        aggFeed.getItems().addAll(rssFeed.getItems());
        rssFeed = (RSSFeed) JsonUtil.convertFromJson("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\rss3.json", RSSFeed.class);
        aggFeed.getItems().addAll(rssFeed.getItems());
        for (Items items : aggFeed.getItems()) {
            StoreService.inMemoryStore.put(FeedUtil.getRandomCategory(), items);
        }
        LOG.info(aggFeed.getItems().toString());
        return aggFeed;
    }

    private void storeRSSFeedObjects(List<org.json.simple.JSONObject> objs) throws IOException {
        RSSFeed aggFeed = new RSSFeed();
        for (org.json.simple.JSONObject obj : objs) {
            Items item = (Items) JsonUtil.convertFromJsonObject(obj, Items.class);
            StoreService.inMemoryStore.put(FeedUtil.getRandomCategory(), item);
        }
    }

    private List<String> searchLocalFuturesData() throws IOException {

        File file = new File("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\futures-dataset.json");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> datasets = new ArrayList<String>();
        while ((st = br.readLine()) != null) {
            datasets.add(st);
            LOG.info("@" + st);
        }
        return datasets;
    }
}
