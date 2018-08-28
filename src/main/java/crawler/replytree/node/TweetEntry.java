package crawler.replytree.node;

import java.sql.Timestamp;
import twitter4j.Status;

/**
 *
 * @author mike
 */
public final class TweetEntry {

    private Status originalStatus;
    private String ID;
    private String screenName;
    private String parentID;
    private String retweetID;
    private long articleID;
    private String text;
    private Timestamp createTime;
    private Timestamp firstRetrievalTime; //time when returned form twitter
    private Timestamp lastRetrievalTime;    //when taken from database
    private int retweetCount;
    private int favoriteCount;
    private int hitCount;

    /**
     * A no-args, do-nothing constructor. <br>
     * <p>
     * Mostly only useful for testing, this constructor does not initialize any
     * of the fields. <br>
     * This should probably only be used deliberately to create a TweetEntry
     * that will have its fields specified manually.
     * </p>
     */
    public TweetEntry() {
    }

    /**
     * A one-arg constructor to initialize a <code>TweetEntry</code> from a
     * <code>Twitter4j.Status</code>.
     * <p>
     * This constructor calls on the setters to verify the data that is entered
     * into the record.
     * </p>
     *
     * @param status the original status to construct the entry from.
     * @see TweetEntryUtils
     */
    public TweetEntry(Status status) {
        originalStatus = status;
        setID(String.valueOf(status.getId()));
        setScreenName(status.getUser().getScreenName());
        if (status.getInReplyToStatusId() > 0) {
            setParentID(String.valueOf(status.getInReplyToStatusId()));
            
        } else {
            setParentID(null);
        }
        
        if (status.isRetweet()) {
            Status original = status.getRetweetedStatus();
            setRetweetID(String.valueOf(original.getId()));
            //if we wanted to get the userID associated to the original status, we can do this:
            //this.retweetUserID = original.getUser().getId();
        } else {
            setRetweetID(null);
        }
            
        setText(status.getText());

        if (status.getCreatedAt() == null) {
            setCreateTime(null);
        } else {
            setCreateTime(new Timestamp(status.getCreatedAt().getTime()));
        }

        /**
         * We do not set first and last RetrievalTimes in the constructor
         * because these should be set by the System Time during the insert
         * statement. It does not make sense here to set them in the
         * constructor. Grabbing the system time here would tell us when we
         * constructed the object, not when we inserted it into the database.
         */
        setRetweetCount(status.getRetweetCount());

        setFavoriteCount(status.getFavoriteCount());

        /**
         * We do not set hitCount here because this should be set either to 0 on
         * record insertion or incremented on update. It would be directly
         * problematic to set it here as it should be set dynamically based on
         * how many times we have retrieved this tweet from the API.
         */
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String authorName) {
        this.screenName = authorName;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getRetweetID() {
        return retweetID;
    }

    public void setRetweetID(String retweetID) {
        this.retweetID = retweetID;
    }

    public long getArticleID() {
        return articleID;
    }

    public void setArticleID(long articleID) {
        this.articleID = articleID;
    }

    public String getText() {
        return text;
    }

    public void setText(String content) {
        this.text = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getFirstRetrievalTime() {
        return firstRetrievalTime;
    }

    public void setFirstRetrievalTime(Timestamp firstRetrievalTime) {
        this.firstRetrievalTime = firstRetrievalTime;
    }

    public Timestamp getLastRetrievalTime() {
        return lastRetrievalTime;
    }

    public void setLastRetrievalTime(Timestamp lastRetrievalTime) {
        this.lastRetrievalTime = lastRetrievalTime;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    @Override
    public String toString() {
        return "<html><FONT COLOR=RED><B>" + screenName + "</B></FONT><html>" + " tweeted " + "<html><FONT COLOR=BLUE><B>" + getOriginalStatus().getText() + "</B></FONT><html>";
    }

    /**
     * @return the originalStatus
     */
    public Status getOriginalStatus() {
        return originalStatus;
    }

    /**
     * @param originalStatus the originalStatus to set
     */
    public void setOriginalStatus(Status originalStatus) {
        this.originalStatus = originalStatus;
    }
}
