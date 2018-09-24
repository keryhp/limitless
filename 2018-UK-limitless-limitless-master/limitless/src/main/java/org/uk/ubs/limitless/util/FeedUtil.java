package org.uk.ubs.limitless.util;


import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FeedUtil {

    private static Logger LOG = Logger.getLogger(FeedUtil.class);
    private static SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    private static List<String> categories = new ArrayList<>();

    public static JSONObject toJson(SyndFeed feed) {
        JSONObject json = new JSONObject();
        json.put("title", feed.getTitle());
        json.put("description", feed.getDescription());
        json.put("link", feed.getLink());
        json.put("pubDate", toJson(feed.getPublishedDate()));
        json.put("guid", getRandomCategory());
        json.put("author", feed.getAuthor());
        json.put("thumbnail", "");
        json.put("content", feed.getDescription());
        LOG.info("RSS feed:" + json.toString());
        return json;

    }

    public static List<JSONObject> toJson(List<SyndEntry> entries, Date maxDate) {
        List<JSONObject> result = new ArrayList<>(entries.size());
        for (SyndEntry entry : entries) {
            result.add(toJson(entry));
        }
        return result;
    }

    public static JSONObject toJson(SyndEntry entry) {
        JSONObject json = new JSONObject();
        json.put("title", entry.getTitle());
        Date published = entry.getPublishedDate();
        if (published == null) {
            LOG.warn("!!!!!! The RSS has no published date : this will lead to duplicates entry");
            published = new Date();
        }
        json.put("pubDate", toJson(published));
        json.put("link", entry.getLink());
        SyndContent description = entry.getDescription();
        if (description != null) {
            json.put("description", description.getValue());
            json.put("content", entry.getDescription());
        } else {
            json.put("content", "");
        }

        json.put("guid", getRandomCategory());
        json.put("author", entry.getAuthor());
        json.put("thumbnail", "");
        LOG.info("RSS feed:" + json.toString());
        return json;
    }

    static Date getDate(String isoDate) throws ParseException {
        return isoDateFormat.parse(isoDate);
    }

    static String toJson(Date date) {
        if (date == null) {
            return null;
        }
        return isoDateFormat.format(date);
    }

    public static void readCategories() throws IOException {
        File file = new File("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\categories.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            categories.add(st);
        }
    }

    public static String getRandomCategory() {
        return categories.get(getRandomNumberInRange());
    }

    private static int getRandomNumberInRange() {
        Random r = new Random();
        return r.nextInt(8);
    }
}