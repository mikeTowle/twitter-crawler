package crawler.replytree.node;

import java.sql.Timestamp;

/**
 *
 * @author mike
 */


public final class ArticleEntry {

    private long articleId;
    private String shortURL;
    private String URL; //long url
    private String title;
    private Timestamp publishTime;
    private Timestamp lastAccessTime;

    /**
     * A no-args constructor for Article objects that does nothing. <br>
     * Returns an Article object with null fields. <br>
     */
    public ArticleEntry() {
        setArticleId(0);
        setShortURL(null);
        setURL(null);
        setTitle(null);
        setLastAccessTime(null);
    }

    /**
     * A constructor for Article objects that expects full parameters.
     *
     * @param twitterURL A String containing a short-form URL like fxn.ws or
     * t.co.
     * @param URL A String containing a long-form URL like foxnews.com or
     * twitter.com.
     * @param title A String containing the headline to an article.
     * @param publishTime A timestamp representing the publish time of an
     * article.
     * @param lastAccessTime A timestamp representing the last time this article
     * was processed.
     */
    public ArticleEntry(String title, String URL, String twitterURL,
            Timestamp publishTime, Timestamp lastAccessTime) {
        setShortURL(twitterURL);
        setURL(URL);
        setTitle(title);
        setPublishTime(publishTime);
        setLastAccessTime(lastAccessTime);
    }

    public long getArticleId() {
        return articleId;
    }

    //Should only be used when we get ArticleEntry objects from Queries.
    //When we insert ArticleEntry objects into the DB, the ID will be
    //auto-incremented.
    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String twitterURL) {
        this.shortURL = twitterURL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public Timestamp getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Timestamp lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public String toString() {
        return "Title: " + title;
    }

}
