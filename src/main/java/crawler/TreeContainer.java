package crawler;

import crawler.replytree.table.TweetHTContainer;
import crawler.replytree.LevelWiseTweetTree;
import crawler.replytree.DepthFirstTweetTree;
import crawler.replytree.node.ArticleEntry;
import database.DBClient;
import java.io.IOException;

/**
 *
 * @author mike
 *
 * This class holds both types of trees for one single article. Will be used to
 * later for displaying article-comment tree output side by side.
 */
public final class TreeContainer {

    /**
     * Tree Generated from Level-Wise build
     */
    private LevelWiseTweetTree levelWiseT;

    /**
     * Tree Generated from Depth-First Build
     */
    private DepthFirstTweetTree depthFirstT;

    private TweetHTContainer hashTables;

    /**
     * No-args constructor (Rarely used)
     */
    public TreeContainer() {
        levelWiseT = null;
        depthFirstT = null;
    }

    /**
     *
     * @param levelWiseT
     * @param depthFirstT
     */
    public TreeContainer(LevelWiseTweetTree levelWiseT, DepthFirstTweetTree depthFirstT) {
        this.levelWiseT = levelWiseT;
        this.depthFirstT = depthFirstT;
    }

    public TreeContainer(ArticleEntry article, RepliesLinkedList baseReplies, DBClient db_instance) throws ClassNotFoundException, IOException, InterruptedException {
        DepthFirstTweetTree dfTree = new DepthFirstTweetTree(article, baseReplies, db_instance);
        dfTree.build();
        this.setDepthFirstT(dfTree);
        LevelWiseTweetTree lwTree = new LevelWiseTweetTree(article, baseReplies, db_instance);
        lwTree.build();
        this.setLevelWiseT(lwTree);

    }

    /**
     * @return the levelWiseT
     */
    public LevelWiseTweetTree getLevelWiseT() {
        return levelWiseT;
    }

    /**
     * @param levelWiseT the levelWiseT to set
     *
     */
    public void setLevelWiseT(LevelWiseTweetTree levelWiseT) {
        this.levelWiseT = levelWiseT;
    }

    /**
     * @return the depthFirstT
     */
    public DepthFirstTweetTree getDepthFirstT() {
        return depthFirstT;
    }

    /**
     * @param depthFirstT the depthFirstT to set
     */
    public void setDepthFirstT(DepthFirstTweetTree depthFirstT) {
        this.depthFirstT = depthFirstT;
    }

    public void setHashTables(TweetHTContainer hashTables) {
        this.hashTables = hashTables;
    }

    public TweetHTContainer getHashTables() {
        return hashTables;
    }

}
