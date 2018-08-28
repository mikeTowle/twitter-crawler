package database;

/**
 *
 * @author mike
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import crawler.replytree.node.ArticleEntry;
import org.apache.log4j.Logger;
import crawler.replytree.node.TweetEntry;
import static java.lang.System.exit;
import java.sql.SQLException;

public final class DBClient {

    private Connection connection;
    static final Logger LOGGER = Logger.getLogger(DBClient.class);

    public DBClient() {
        connection = null;
    }

    public DBClient(String url, String userName, String psw) {
        boolean isConnected = dbConnection(url, userName, psw);
        if (isConnected == false) {
            exit(0);
        }
    }

    public boolean dbConnection(String url, String userName, String psw) {

        String driverName = "com.mysql.jdbc.Driver";
        String enCoding = "?useUnicode=true&characterEncoding=gb2312";
        url = url + enCoding;
        try {
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection(url, userName, psw);
            LOGGER.info("Connection to MySQL successful");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            LOGGER.error(e);
        }
        return connection != null;
    }

    public boolean insertArticles(long id, String twitterURL, String url, String title, Timestamp pubTime) {

        boolean insertResult = false;
        try {
            String sql = "INSERT INTO NewsArticle (ArticleID, Title, URL, TwitterURL,"
                    + " PublishTime, LastTwitterAccessTime) VALUES (?,?,?,?,?,?)"
                    + " ON DUPLICATE KEY UPDATE ArticleID=?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, title);
                ps.setString(3, url);
                ps.setString(4, twitterURL);
                ps.setTimestamp(5, pubTime);
                ps.setTimestamp(6, null);
                ps.setLong(7, id);
                ps.executeUpdate();
            }

            insertResult = true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return insertResult;
    }

    public List<ArticleEntry> getArticlesWithinCrawlInterval() {
        List<ArticleEntry> articles = new ArrayList<>();
        PreparedStatement statement;
        String sql = "SELECT ArticleID, Title, TwitterURL, URL, PublishTime, LastTwitterAccessTime  FROM newsarticle"
                + " WHERE DaysCrawled<8 OR DaysCrawled is NULL";
        try {
            statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                ArticleEntry ae = new ArticleEntry();
                ae.setArticleId(results.getLong(1));
                ae.setTitle(results.getString(2));
                ae.setShortURL(results.getString(3));
                ae.setURL(results.getString(4));
                ae.setPublishTime(results.getTimestamp(5));
                ae.setLastAccessTime(results.getTimestamp(6));
                articles.add(ae);
            }
            if (results != null) {
                results.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return articles;
    }

    public List<ArticleEntry> getArticlesNotCrawled() {

        List<ArticleEntry> la = new ArrayList<>();
        PreparedStatement st = null;
        try {
            String sql = "SELECT ArticleID, Title, TwitterURL, URL, PublishTime, LastTwitterAccessTime  FROM newsarticle"
                    + " WHERE PublishTime < SUBDATE(NOW(), INTERVAL 7 DAY) AND" //limit 1 is easiest to test with
                    + " (LastCrawlTime is null)";//Lihong comment: maybe <
            st = connection.prepareStatement(sql);
            ResultSet re = st.executeQuery();
            while (re.next()) {
                ArticleEntry ae = new ArticleEntry();
                ae.setArticleId(re.getLong(1));
                ae.setTitle(re.getString(2));
                ae.setShortURL(re.getString(3));
                ae.setURL(re.getString(4));
                ae.setPublishTime(re.getTimestamp(5));
                ae.setLastAccessTime(re.getTimestamp(6));
                la.add(ae);
            }
            if (re != null) {
                re.close();
            }
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return la;
    }

    public List<ArticleEntry> getArticles(int daysPerCheck) {

        List<ArticleEntry> la = new ArrayList<>();
        PreparedStatement st = null;
        try {
            String sql = "SELECT ArticleID, Title, TwitterURL, URL, PublishTime, LastTwitterAccessTime  FROM newsarticle"
                    + " WHERE PublishTime < SUBDATE(NOW(), INTERVAL 7 DAY) AND" //limit 1 is easiest to test with
                    + " (LastTwitterAccessTime is null or LastTwitterAccessTime<SUBDATE(NOW(), INTERVAL ? DAY)) LIMIT 100";//Lihong comment: maybe <
            st = connection.prepareStatement(sql);
            st.setInt(1, daysPerCheck);
            //st.setInt(2, articleLimit);

            ResultSet re = st.executeQuery();

            while (re.next()) {
                ArticleEntry ae = new ArticleEntry();
                ae.setArticleId(re.getLong(1));
                ae.setTitle(re.getString(2));
                ae.setShortURL(re.getString(3));
                ae.setURL(re.getString(4));
                ae.setPublishTime(re.getTimestamp(5));
                ae.setLastAccessTime(re.getTimestamp(6));
                la.add(ae);
            }

            if (re != null) {
                re.close();
            }
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return la;
    }

    public boolean updateArticleAccessTime(long AID) {

        boolean re = false;
        PreparedStatement statement;
        try {
            String sql = "UPDATE newsarticle SET LastTwitterAccessTime=? WHERE ArticleID=?";
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setLong(2, AID);
            statement.executeUpdate();
            if (statement != null) {
                statement.close();
            }
            re = true;
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return re;
    }

    public void updateArticleCrawlTime(long articleID) {
        PreparedStatement statement;
        try {
            String sql = "UPDATE newsarticle SET LastCrawlTime=? WHERE ArticleID=?";
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setLong(2, articleID);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public int getDaysCrawled(long articleID) {
        PreparedStatement statement;
        int daysCrawled = 0;
        String sql = "SELECT DaysCrawled FROM newsarticle WHERE ArticleID=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, articleID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                daysCrawled = result.getInt("DaysCrawled");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return daysCrawled;
    }

    public void updateDaysCrawled(long articleID) {
        PreparedStatement statement;
        try {
            String sql = "SELECT DaysCrawled FROM newsarticle WHERE ArticleID=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, articleID);
            ResultSet result = statement.executeQuery();
            int currentDaysCrawled = 0;
            while (result.next()) {
                currentDaysCrawled = result.getInt("DaysCrawled");
            }
            currentDaysCrawled += 1;
            sql = "UPDATE newsarticle SET DaysCrawled=? WHERE ArticleID=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, currentDaysCrawled);
            statement.setLong(2, articleID);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void updateHasComments(int hasComments, long articleID) {
        PreparedStatement statement;
        try {
            String sql = "UPDATE newsarticle SET HasComments=? WHERE ArticleID=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(2, articleID);
            statement.setInt(1, hasComments);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     * Inserts <TweetEntry> object into table tweets
     *
     * @param te
     */
    public void insertTweet(TweetEntry te) {
        try {
            //ON DUPLICATE KEY UPDATE statement		
            String sql = "INSERT INTO tweets (ID, authorName, parentID, retweetID,"
                    + " content, createTime, firstRetrievalTime, lastRetrievalTime,"
                    + " retweetCount, favoriteCount, ArticleID, hitCount)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
                    + " ON DUPLICATE KEY UPDATE"
                    + " lastRetrievalTime=?, retweetCount=?, favoriteCount=?, hitCount=hitCount+1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, te.getID());
            ps.setString(2, te.getScreenName());
            ps.setString(3, te.getParentID());
            ps.setString(4, te.getRetweetID());
            ps.setString(5, te.getText());
            ps.setTimestamp(6, te.getCreateTime());
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(7, currentTime);//first retrieval
            ps.setTimestamp(8, currentTime);//last retrieval
            ps.setInt(9, te.getRetweetCount());
            ps.setInt(10, te.getFavoriteCount());
            ps.setLong(11, te.getArticleID());
            //hitCount should be set to 1 if we are inserting
            ps.setInt(12, 1);
            ps.setTimestamp(13, currentTime);
            ps.setInt(14, te.getRetweetCount());
            ps.setInt(15, te.getFavoriteCount());
            ps.executeUpdate();
            if (ps != null) {
                ps.close();
            }

        } catch (Exception e) {
            LOGGER.error(e);
        }

    }

    public boolean dbClose() {

        boolean closeResult = false;
        try {
            connection.close();
            closeResult = true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return closeResult;
    }

}
