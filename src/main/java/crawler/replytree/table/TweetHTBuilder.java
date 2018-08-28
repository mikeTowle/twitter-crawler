package crawler.replytree.table;

import org.apache.log4j.Logger;
import crawler.replytree.node.TweetNode;
import java.util.ArrayList;

/**
 *
 * @author mike 
 * Builds a hash table for each reply-tree and their respective
 * tweets
 */
public class TweetHTBuilder {
    
    static final Logger LOGGER = Logger.getLogger(TweetHTBuilder.class);
    private final TweetHT hashTable;
    private final ArrayList<TweetNode> tweets;

    public TweetHTBuilder(ArrayList<TweetNode> tweets) {
        hashTable = new TweetHT(tweets.size());
        this.tweets = tweets;
    }

    public void buildHashTable() {
        try {
            for (int i = 0; i < tweets.size(); i++) {
                String sn = tweets.get(i).getTweet().getScreenName();
                String id = tweets.get(i).getTweet().getID();
                hashTable.put(sn, id);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }

    }

    public TweetHT getHashTable() {
        return hashTable;
    }

}
