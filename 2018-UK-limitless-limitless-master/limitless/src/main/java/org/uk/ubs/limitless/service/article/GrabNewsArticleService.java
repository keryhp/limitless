package org.uk.ubs.limitless.service.article;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.uk.ubs.limitless.api.GrabNewsArticle;
import org.uk.ubs.limitless.api.StoreService;
import org.uk.ubs.limitless.domain.Items;
import org.uk.ubs.limitless.domain.RSSFeed;
import org.uk.ubs.limitless.util.FeedUtil;
import org.uk.ubs.limitless.util.JsonUtil;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GrabNewsArticleService implements GrabNewsArticle {

    private static Logger LOG = Logger.getLogger(GrabNewsArticle.class);

    @Override
    public void extractNews(String url){
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            String title = doc.title();
            LOG.info("Title: " + title);
            LOG.info("Doc Text: " + doc.text());
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                // get the value from href attribute
                LOG.info("\nLink : " + link.attr("href"));
                LOG.info("Text : " + link.text());
            }

        } catch (IOException e) {
            LOG.error("IO Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void extractNewsArticle(String urlLink) throws BoilerpipeProcessingException, MalformedURLException {
        URL url = new URL(urlLink);
        String text = ArticleExtractor.INSTANCE.getText(url);
        LOG.info("Text : " + text);
    }

    public void extractAllLocalFeedNewsAndWriteToLocal() throws IOException, BoilerpipeProcessingException {
        //searchLocalData();
        sampleFullArticleGrabber();
    }

    private RSSFeed searchLocalData() throws IOException, BoilerpipeProcessingException {
        RSSFeed aggFeed = new RSSFeed();
        RSSFeed rssFeed = (RSSFeed) JsonUtil.convertFromJson("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\rss1.json", RSSFeed.class);
        aggFeed.setItems(rssFeed.getItems());
        rssFeed = (RSSFeed) JsonUtil.convertFromJson("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\rss2.json", RSSFeed.class);
        aggFeed.getItems().addAll(rssFeed.getItems());
        rssFeed = (RSSFeed) JsonUtil.convertFromJson("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\rss3.json", RSSFeed.class);
        aggFeed.getItems().addAll(rssFeed.getItems());
        for (Items items : aggFeed.getItems()) {
            URL url = new URL(items.getLink());
            String text = ArticleExtractor.INSTANCE.getText(url);
            LOG.info("Text : " + text);
            writeToFile(text, items.getTitle());
        }
        //LOG.info(aggFeed.getItems().toString());
        return aggFeed;
    }

    private void writeToFile(String text, String fileName){
        File file = new File("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\newscontent\\" + fileName);
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void sampleFullArticleGrabber() throws MalformedURLException, BoilerpipeProcessingException {
        URL url = new URL("https://www.bbc.co.uk/news/business-45626881");
        String text = ArticleExtractor.INSTANCE.getText(url);
        File file = new File("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\newscontent\\sample_full");
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
