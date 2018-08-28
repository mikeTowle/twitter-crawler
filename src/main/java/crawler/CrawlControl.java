package crawler;

import crawler.replytree.node.ArticleEntry;
import database.DBClient;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author mike
 */
public class CrawlControl {

    static final Logger LOGGER = Logger.getLogger(CrawlControl.class);

    /**
     * instigates tweet tree construction with initalQuery() results., if any
     *
     * @param topic
     * @param tweetsPerPage
     * @param article
     * @param db_instance
     * @throws InterruptedException
     * @throws IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void beginTreeCreation(String topic, int tweetsPerPage, ArticleEntry article, DBClient db_instance)
            throws InterruptedException, IOException, ClassNotFoundException {

        //Gather base level tweets
        StatusRequest tweetRetriever = new StatusRequest();
        RepliesLinkedList baseReplies = tweetRetriever.queryTopic(topic);
        LOGGER.info("Found: " + baseReplies.getSize() + " first-level replies");

        //if base level tweets exist, create trees
        if (baseReplies.getSize() != 0) {

            db_instance.updateHasComments(1, article.getArticleId());
            TreeContainer container = new TreeContainer(article, baseReplies, db_instance);

            db_instance.updateDaysCrawled(article.getArticleId());
            int daysCrawled = db_instance.getDaysCrawled(article.getArticleId());
            String fileNameDF = article.getArticleId() + "_day_" + daysCrawled + "-depthFirst";
            String fileNameLW = article.getArticleId() + "_day_" + daysCrawled + "-levelWise";

            TweetFileWriter fileWriterDF = new TweetFileWriter(fileNameDF, 0);
            fileWriterDF.writeJsonToFileDF(container.getDepthFirstT());
            TweetFileWriter fileWriterLW = new TweetFileWriter(fileNameLW, 1);
            fileWriterLW.writeJsonToFileLW(container.getLevelWiseT());

            
            LOGGER.info("Depth_First Tree Depth:" + container.getDepthFirstT().getDepth());
            LOGGER.info("Depth_First Tree Width:" + container.getDepthFirstT().getWidth());
            LOGGER.info("Depth_First Tree Size:" + container.getDepthFirstT().getTweets().size());
            LOGGER.info("Level_Wise Tree Depth:" + container.getLevelWiseT().getDepth());
            LOGGER.info("Level_Wise Tree Width:" + container.getLevelWiseT().getWidth());
            LOGGER.info("Level_Wise Tree Size:" + container.getLevelWiseT().getTweets().size());

        }

    }

}
