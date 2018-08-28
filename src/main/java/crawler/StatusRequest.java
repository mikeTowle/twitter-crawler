package crawler;

import crawler.replytree.node.TweetEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author mike
 */
public class StatusRequest {

    static final Logger LOGGER = Logger.getLogger(StatusRequest.class);
    private final Twitter twitter;

    public StatusRequest() {
        OAuthHandler oauth = new OAuthHandler();
        twitter = oauth.getTwitterHandle();

    }

    public List<Status> queryTweet(TweetEntry tweet) throws InterruptedException {

        List<Status> replies = new ArrayList<>();
        List<Status> all;
        try {

            String screenName = tweet.getScreenName();
            String tweetId = tweet.getID();
            String queryStr = "to:" + screenName + " since_id:" + tweetId;
            Query query = new Query(queryStr);
            query.setCount(100);

            QueryResult results = twitter.search(query);
            //limit("/users/search");

            LOGGER.info("Sleeping for 5 seconds");
            Thread.sleep(5000); // need to sleep 5500 miliseconds 

            all = new ArrayList<>();

            List<Status> tweets = results.getTweets();
            for (Status t : tweets) {
                if (tweet.getScreenName().equals(t.getInReplyToScreenName())) {
                    all.add(t);
                    //rawJson = TwitterObjectFactory.getRawJSON(t);
                    //rawJsons.add(rawJson);
                }
            }
            if (all.size() > 0) {
                for (int i = all.size() - 1; i >= 0; i--) {
                    if (replies.contains(all.get(i))) {
                        break;
                    } else {
                        replies.add(all.get(i));
                    }

                }
                all.clear();
            }

        } catch (TwitterException te) {
            //System.out.println("TwitterException in queryTweet!");
            //System.out.println(te);
            LOGGER.error(te);
            twitterExceptionChecks(te);
        }

        return replies;
    }

    public void limit(String endpoint) throws TwitterException {
        String family = endpoint.split("/", 3)[1];
        RateLimitStatus status = twitter.getRateLimitStatus(family).get(endpoint);
        LOGGER.info(status);
    }

    /**
     *
     * @param topic to be queried
     * @return linkedList with initial query return
     * @throws InterruptedException
     */
    public RepliesLinkedList queryTopic(String topic) throws InterruptedException {
        LOGGER.info("Initial Query");
        List<Status> tweetList;
        RepliesLinkedList baseReplies = new RepliesLinkedList();

        if (topic == null || topic.length() == 0) {
            return null;
        }
        try {
            Query query = new Query(topic);
            query.setCount(100);
            QueryResult result = twitter.search(query);
            do {
                Thread.sleep(5 * 1000);
                tweetList = result.getTweets();
                for (Status tweet : tweetList) {
                    baseReplies.add(new TweetEntry(tweet));
                }
                LOGGER.info("Results: " + tweetList.size());
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
            //Usually this means we hit a rate limit, so we try to sleep as long as we have to
            LOGGER.debug(te);
            twitterExceptionChecks(te);
            //util.waitForRateLimit();
        }
        return baseReplies;
    }

    public RepliesLinkedList query(QueryResult result) {
        List<Status> tweetList = result.getTweets();
        RepliesLinkedList baseReplies = new RepliesLinkedList();
        if (tweetList.isEmpty()) {
            LOGGER.info("No First-Level Tweets");
            return null;
        } else {
            do {
                tweetList.stream().forEach((tweet) -> {
                    baseReplies.add(new TweetEntry(tweet));
                });
            } while (result.nextQuery() != null);
        }
        return baseReplies;
    }

    public QueryResult query(String topic) {
        QueryResult result = null;

        try {
            Query query = new Query(topic);
            query.setCount(100);
            query.setSinceId(0);
            result = twitter.search(query);
        } catch (TwitterException te) {
            LOGGER.debug(te);
        }
        return result;
    }

    public void twitterExceptionChecks(TwitterException e) {
        LOGGER.error(e.getStatusCode());
        LOGGER.error(e.getExceptionCode());
        LOGGER.error(e.exceededRateLimitation());
        LOGGER.error(e.isCausedByNetworkIssue());
        LOGGER.error(e.resourceNotFound());
        if (e.isErrorMessageAvailable()) {
            LOGGER.error(e.getErrorMessage());
        }
    }

    /**
     * Tells the calling thread to sleep for as long as it takes to reset the
     * Twitter API rate limit. It is better to avoid rate limits altogether by
     * calling {@link Thread.sleep} for a brief time (10 seconds) after each
     * query than to use this method, but it is better to use this method than
     * to crash the program. <br><br>
     *
     * Additionally, this message will print some other useful information if a
     * {@code TwitterException} is thrown while trying to access the original
     * {@code TwitterException}.
     *
     */
    public void waitForRateLimit() {
        RateLimitStatus lim;

        try {
            lim = twitter.getRateLimitStatus().get("/users/search");
            int reset = lim.getSecondsUntilReset();
            long sysTimeMillis = System.currentTimeMillis();
            long resetTimeMillis = sysTimeMillis + reset * 1000;
            Date currentDate = new Date(sysTimeMillis);
            Date resetDate = new Date(resetTimeMillis);

            try {
                LOGGER.info("Current time is: " + currentDate);
                LOGGER.info("Resetting again at " + resetDate);
                Thread.sleep(reset * 1000);
            } catch (InterruptedException e) {
                //I don't see this ever happening, but it might.
                //Just tell the user and exit the program.
                LOGGER.error(e);
            }
        } catch (TwitterException e) {
            //A twitter exception here is probably very bad.
            LOGGER.error(e);
            twitterExceptionChecks(e);
        }

    }

}
