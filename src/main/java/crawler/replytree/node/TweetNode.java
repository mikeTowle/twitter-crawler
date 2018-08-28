package crawler.replytree.node;

import crawler.replytree.node.TweetEntry;
import crawler.replytree.node.ArticleEntry;
import java.util.ArrayList;

/**
 *
 * @author mike
 *
 * TweetNode object. This object contains information on the tweet (Status) as
 * well as a reference to a List of its replies.
 *
 */
public class TweetNode {

    private TweetEntry tweet;
    private ArticleEntry article; // for root node only
    private final ArrayList<TweetNode> replyList;
    private boolean repliesExist;
    private boolean processed;

    public TweetNode() {

        tweet = null;
        article = null;
        replyList = null;
        processed = false;

    }

    public TweetNode(TweetEntry tweet) {

        this.tweet = tweet;
        article = null;
        replyList = new ArrayList<>();
        processed = false;

    }

    public TweetNode(ArticleEntry article) {

        this.article = article;
        tweet = null;
        replyList = new ArrayList<>();
        processed = false;
    }

    public void setArticle(ArticleEntry article) {
        this.article = article;
    }

    public void setTweet(TweetEntry tweet) {
        this.tweet = tweet;
    }

    public ArticleEntry getArticle() {
        return article;
    }

    public TweetEntry getTweet() {
        return tweet;
    }

    public boolean hasReplies() {
        return replyList.size() >= 1;
    }

    public int getReplySize() {
        return replyList.size();
    }

    public ArrayList<TweetNode> getReplyList() {
        return replyList;
    }

    public String tweetToString() {
        return "<html><FONT COLOR=RED><B>" + tweet.getScreenName() + "</B></FONT><html>" + " tweeted " + "<html><FONT COLOR=BLUE><B>" + tweet.getText() + "</B></FONT><html>";
    }

    public String articleToString() {
        return "Article Title: " + article.getTitle() + " Article Twitter URL: " + article.getShortURL();
    }

    /**
     * @return the processed
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * @param processed the processed to set
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isLeaf() {
        return hasReplies();
    }
    
    /**
     * Compare to method is comparing tweet id for its use in Dynamic Tree
     * @param n
     * @return
     */
    public int compareTo(TweetNode n) {
        if (this.getTweet().getID().equals(n.getTweet().getID())) {
            return 0;
        } else if (this.getTweet().getID().compareTo(n.getTweet().getID()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
    
}
