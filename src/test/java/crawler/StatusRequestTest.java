/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import crawler.replytree.node.ArticleEntry;
import database.DBClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import twitter4j.QueryResult;
import twitter4j.URLEntity;


/**
 *
 * @author mike
 */
public class StatusRequestTest {
    
    static final Logger LOGGER = Logger.getLogger(StatusRequestTest.class);
    String topic = "https://www.washingtonpost.com/opinions/global-opinions/is-ivanka-trump-receiving-unseemly-favors-from-china/2017/06/02/5dfce500-46ef-11e7-bcde-624ad94170ab_story.html";
    
    /*
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        List<ArticleEntry> brokenLongURLs = getBrokenLinks();
        LOGGER.info("Broken Links");
        for (ArticleEntry ae: brokenLongURLs) {
            LOGGER.info(ae.getTitle());
        }
       
    }*/
    
    
    public static List<ArticleEntry> getBrokenLinks() throws ClassNotFoundException, IOException, InterruptedException {
        PropertiesFileReader pf = new PropertiesFileReader();
        Properties properties = pf.readConfig("/configMike.properties");
        String dbuserName = properties.getProperty("DBusername");
        String dbpsw = properties.getProperty("DBpwd");
        String dburl = properties.getProperty("DBurl");
        DBClient db_instance = new DBClient(dburl, dbuserName, dbpsw);
        
        List<ArticleEntry> articles = db_instance.getArticlesWithinCrawlInterval();
        StatusRequest retriever = new StatusRequest();
        QueryResult queryResult;
        List<ArticleEntry> brokenURLs = new ArrayList<>();
        
        for (ArticleEntry article: articles) {
            queryResult = retriever.query(article.getURL());
            if (queryResult.getTweets().isEmpty()) {
                brokenURLs.add(article);
            }
            Thread.sleep(5 * 1000);
        }
        return brokenURLs;
    }
    
    public static List<ArticleEntry> testShortURLQuery(List<ArticleEntry> brokenURLs) throws InterruptedException {
        List<ArticleEntry> brokenShorts = new ArrayList<>();
        StatusRequest retriever = new StatusRequest();
        QueryResult queryResult;
        for (ArticleEntry article: brokenURLs) {
            queryResult = retriever.query(article.getShortURL());
            if (queryResult.getTweets().isEmpty()) {
                brokenShorts.add(article);
            }
            Thread.sleep(5 * 1000);
        }
        return brokenShorts;
    }
    
    public static Map<ArticleEntry, URLEntity[]> compareShortURLs(List<ArticleEntry> brokenShorts) throws InterruptedException {
        
        Map<ArticleEntry, URLEntity[]> URLMap = new HashMap<>();
        StatusRequest retriever = new StatusRequest();
        QueryResult queryResult;
        for (ArticleEntry article: brokenShorts) {
            queryResult = retriever.query(article.getTitle());
            if (queryResult.getTweets().isEmpty() == false) {
                URLEntity[] urls = queryResult.getTweets().get(0).getURLEntities();
                URLMap.put(article, urls);
            }
            Thread.sleep(5 * 1000);
        }
        return URLMap;
    }
    
    public static void printHashMap(Map<ArticleEntry, URLEntity[]> URLMap) {
        LOGGER.info("\n\n\n*********PRINTING HASHMAP RESULTS*********\n\n\n");
        int i = 1;
        for (Map.Entry<ArticleEntry, URLEntity[]> entry : URLMap.entrySet()) {
            LOGGER.info(i + ":\n");
            LOGGER.info("Short URL from DB: " + entry.getKey().getShortURL() + "");
            LOGGER.info("Short URL from Tweets: \n");
            for (URLEntity urlEntity : entry.getValue()) {
                LOGGER.info(urlEntity.getURL());
            }
            LOGGER.info("\n\n");
            i++;
        }
    }
    
    /*
    
    
    @Test
    public void testQueryTopic() throws Exception {
        System.out.println("queryTopic");
        StatusRequest instance = new StatusRequest();
        RepliesLinkedList result = instance.queryTopic(topic);
        assertTrue(result.getSize() != 0);
        
    }
    
    @Test
    public void testQuery() {
        System.out.println("query");
        StatusRequest instance = new StatusRequest();
        QueryResult result = instance.query(topic);
       
        assertFalse(result.getTweets().isEmpty());
    }
    
    @Test
    public void testQueryPaging() {
        System.out.println("query(QueryResult)");
        StatusRequest instance = new StatusRequest();
        QueryResult results = instance.query(topic);
        RepliesLinkedList baseReplies = instance.query(results);
        assertTrue(baseReplies.getSize() != 0);
    }


*/
    
}
