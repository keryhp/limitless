package org.uk.ubs.limitless;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.uk.ubs.limitless.api.StoreService;
import org.uk.ubs.limitless.domain.Items;
import org.uk.ubs.limitless.messaging.MessagePublisher;
import org.uk.ubs.limitless.service.article.GrabNewsArticleService;
import org.uk.ubs.limitless.service.nlg.SampleNLP;
import org.uk.ubs.limitless.service.runnables.RSSFeedRunner;
import org.uk.ubs.limitless.service.runnables.TwitterFeedRunner;
import org.uk.ubs.limitless.service.store.StoreServiceImpl;
import org.uk.ubs.limitless.util.FeedUtil;

import javax.jms.Queue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@EnableJms
public class Application {

    private static Logger LOG = Logger.getLogger(Application.class);

    public static void main(String[] args) throws IOException, InterruptedException, BoilerpipeProcessingException {

        setLogLevels();
        LOG.info("Application Starting");
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        MessagePublisher messagePublisher = (MessagePublisher) context.getBean("messagePublisher");
        StoreService storeService = new StoreServiceImpl();

        FeedUtil.readCategories();
        runFeedServices(messagePublisher, storeService);
        Thread.sleep(1000);
        for(Items items : StoreServiceImpl.inMemoryStore.values()) {
            LOG.info(items.toString());
        }

        //read rss json and read their link - extract news article from link
        // now extracted to newscontent folder
        GrabNewsArticleService grabNewsArticleService = new GrabNewsArticleService();
        grabNewsArticleService.extractAllLocalFeedNewsAndWriteToLocal();

        //pass through nlp
        //File nlpFolder = new File("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\newscontent");
        //ReadAllNewsFiles(nlpFolder);
        SampleNLP sampleNLP = new SampleNLP();
        sampleNLP.runAnnotators("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\newscontent\\sample");
        //categorize and tag
        //push to ui


        LOG.info("Application running");
        return;
    }

    private static void runFeedServices(MessagePublisher messagePublisher, StoreService storeService) throws IOException {
        TwitterFeedRunner twitterFeedRunner = new TwitterFeedRunner(messagePublisher, storeService);
        Thread twitterThread = new Thread(twitterFeedRunner);
        twitterThread.start();

        List<String> urls = getRSSUrls();
        for (String url : urls) {
            RSSFeedRunner rssFeedRunner = new RSSFeedRunner(url);
            Thread rssThread = new Thread(rssFeedRunner);
            rssThread.start();
        }
    }

    private static void setLogLevels() {
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for (Logger logger : loggers) {
            logger.setLevel(Level.OFF);
        }
    }

    private static List<String> getRSSUrls() throws IOException {
        File file = new File("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\rss-urls.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> rssUrls = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            rssUrls.add(st);
        }
        return rssUrls;
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("processor_queue");
    }

    private static void walkAllNewsFiles(){
        SampleNLP sampleNLP = new SampleNLP();
        try (Stream<Path> paths = Files.walk(Paths.get("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\newscontent"))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(file -> sampleNLP.runAnnotators(file.getFileName().toString()));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void ReadAllNewsFiles(final File folder) {
        SampleNLP sampleNLP = new SampleNLP();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                ReadAllNewsFiles(fileEntry);
            } else {
               LOG.info(fileEntry.getName());
                sampleNLP.runAnnotators("D:\\Work\\HarishKeri\\Limitless\\2018-UK-limitless-limitless-master\\limitless\\src\\main\\resources\\newscontent\\" + fileEntry.getName());
            }
        }
    }

}
