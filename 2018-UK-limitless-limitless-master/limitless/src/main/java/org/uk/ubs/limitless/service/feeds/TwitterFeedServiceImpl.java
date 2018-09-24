package org.uk.ubs.limitless.service.feeds;

import org.uk.ubs.limitless.Application;
import org.uk.ubs.limitless.api.StoreService;
import org.uk.ubs.limitless.api.TwitterFeedService;
import org.uk.ubs.limitless.domain.Feed;
import org.uk.ubs.limitless.domain.Items;
import org.uk.ubs.limitless.domain.MessageContent;
import org.uk.ubs.limitless.domain.RSSFeed;
import org.uk.ubs.limitless.messaging.MessagePublisher;
import org.uk.ubs.limitless.util.FeedUtil;
import twitter4j.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TwitterFeedServiceImpl implements TwitterFeedService {

    private static Logger LOG = Logger.getLogger(Application.class);
    RSSFeed rssFeed = new RSSFeed();
    private int ctr = 0;
    private MessagePublisher messagePublisher;
    private StoreService storeService;
    private List<MessageContent> mcList;
    //As Twitter free API is limited purpose use only - use local extracted info for dev and test
    private boolean searchTwitterAPI = false;

    public TwitterFeedServiceImpl(MessagePublisher messagePublisher, StoreService storeService) {
        this.messagePublisher = messagePublisher;
        this.storeService = storeService;
    }

    public void searchFeed(String keyword) {
        LOG.info("Searching Twitter Feeds for keyword=" + keyword);
        Twitter twitter = TwitterFactory.getSingleton();

        while (true) {
            try {
                Query query = new Query(keyword);
                if (searchTwitterAPI) {
                    searchTwitter(twitter, query);
                } else {
                    searchLocalData();
                }
                Thread.sleep(600000);
            } catch (Exception e) {
                LOG.error("error searching Twitter Feed" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void prepareMessage(Status status) {
        MessageContent mc = new MessageContent();
        mc.setMsg(status.getText());
        mc.setUsername(status.getUser().getName());
        prepareFeed(status);
        convertToRSSFeed(status);
    }

    private void prepareFeed(Status status) {
        Feed feed = new Feed();
        feed.setPublisher(status.getUser().getName());
        feed.setMessageContent(mcList);
        storeFeedMessage(feed);
    }

    private void convertToRSSFeed(Status status) {
        Items item = new Items();
        item.setAuthor(status.getUser().getName());
        item.setDescription(status.getText());
        rssFeed.getItems().add(item);
    }

    private void storeFeedMessage(Feed feed) {
        messagePublisher.sendMessage(feed);
        //categorize info through NLP here
        storeService.storeRSSFeedData(rssFeed);
    }

    private List<String> searchLocalData() throws IOException {
        File file = new File("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\tweets.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> tweets = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            tweets.add(st);
            Items item = new Items();
            item.setAuthor(st);
            item.setDescription(st);
            ctr += 1;
            item.setGuid(FeedUtil.getRandomCategory());
            rssFeed.getItems().add(item);
            LOG.info("@" + st);
        }
        storeService.storeRSSFeedData(rssFeed);
        return tweets;
    }

    private void searchTwitter(Twitter twitter, Query query) throws TwitterException {
        QueryResult result = twitter.search(query);
        for (
                Status status : result.getTweets()) {
            LOG.info("@" + status.getUser().getScreenName() + ":" + status.getText());
            prepareMessage(status);
        }
    }
}