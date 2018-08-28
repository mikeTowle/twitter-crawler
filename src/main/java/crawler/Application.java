package crawler;

import crawler.replytree.node.ArticleEntry;
import java.util.List;
import java.util.Properties;
import database.DBClient;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import twitter4j.TwitterException;

/**
 *
 * @author mike
 */
public class Application {

    static final Logger LOGGER = Logger.getLogger(Application.class);
    private static DBClient db_instance;
    private static Properties properties;

    /**
     * Drives the crawler...calls CrawlControl-> invokes the creation of reply
     * tree
     *
     * @param args
     * @throws InterruptedException
     * @throws IOException
     * @throws TwitterException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws InterruptedException, IOException, TwitterException, ClassNotFoundException {
        PropertyConfigurator.configure(Application.class.getClassLoader().getResource("log4j.properties"));
        LOGGER.info("Entering main");
        crawlControl();
    }

    /**
     * controls when to start and sleep Twitter Crawler
     *
     * @throws InterruptedException
     * @throws IOException
     * @throws TwitterException
     * @throws java.lang.ClassNotFoundException
     */
    public static void crawlControl() throws InterruptedException, IOException, TwitterException, ClassNotFoundException {
        db_instance = new DBClient();
        PropertiesFileReader pf = new PropertiesFileReader();
        properties = pf.readConfig("/configMike.properties");
        LOGGER.info("*****************NEW ROUND*****************");
        LOGGER.info("TWITTER CRAWLER BEGINS");
        beginCollection();
    }

    /**
     * Calls Twitter Crawler and instigates comment harvesting
     *
     * @throws InterruptedException
     * @throws IOException
     * @throws TwitterException
     * @throws java.lang.ClassNotFoundException
     */
    public static void beginCollection() throws InterruptedException, IOException, TwitterException, ClassNotFoundException {

        CrawlControl treeCreator = new CrawlControl();
        intitalizeDB();
        List<ArticleEntry> articleList = db_instance.getArticlesWithinCrawlInterval();
        int tweetsPerPage = Integer.parseInt(getProperties().getProperty("TweetsPerPage"));
        LOGGER.info(articleList.size() + " articles collected from DB");

        if (articleList.isEmpty()) {
            LOGGER.error("No Articles to crawl in this round");

        } else {
            
            ArticleEntry ae;
            long articleID;
            String articleURL;
            for (int i = 0; i <= articleList.size(); i++) {
                ae = articleList.get(i);
                articleID = ae.getArticleId();
                articleURL = ae.getURL();
                LOGGER.info("Searching Twitter with article: " + ae.getTitle());
                treeCreator.beginTreeCreation(articleURL, tweetsPerPage, ae, db_instance);
                db_instance.updateArticleAccessTime(articleID);
                db_instance.updateArticleCrawlTime(articleID);
            }
            
            LOGGER.info("Crawler finished with all articles!");
        }

        db_instance.dbClose();
    }

    /**
     * creates connection to DBClient
     */
    private static void intitalizeDB() {
        String dburl = getProperties().getProperty("DBurl");
        String dbuserName = getProperties().getProperty("DBusername");
        String dbpsw = getProperties().getProperty("DBpwd");
        db_instance = new DBClient(dburl, dbuserName, dbpsw);
    }

    /**
     * @return the properties
     */
    public static Properties getProperties() {
        return properties;
    }

}
