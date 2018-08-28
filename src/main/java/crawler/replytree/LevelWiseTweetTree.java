package crawler.replytree;

import crawler.replytree.node.ArticleEntry;
import org.apache.log4j.Logger;
import crawler.RepliesLinkedList;
import crawler.replytree.node.TweetEntry;
import crawler.StatusRequest;
import crawler.replytree.node.TweetNode;
import database.DBClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import twitter4j.Status;

/**
 *
 * @author mike
 */
public final class LevelWiseTweetTree implements TweetTree {

    static final Logger LOGGER = Logger.getLogger(LevelWiseTweetTree.class);
    private static DBClient db_instance;
    private final StatusRequest teUtils;
    private TweetNode rootArticle;
    private final Queue<TweetNode> queue;
    private int tweetCount;
    private final ArrayList<TweetNode> tweets;
    private final RepliesLinkedList ll;
    private int depth;
    private int width;
    private final ArrayList<Integer> widths;
    //private static final long DURATION = TimeUnit.MILLISECONDS.convert(CRAWL_TIME, TimeUnit.MINUTES);
    //private long STARTTIME = 0;
    //private long ENDTIME = 0;
    private String crawlStartTime;
    private String crawlEndTime;
    private int numQueries;
    private int processed;
    private int unProcessed;
    private HashMap<Integer, Integer> queryAndCounts;
    
    /**
     * Constructor mainly used
     *
     * @param ae
     * @param ll
     * @param db
     */
    public LevelWiseTweetTree(ArticleEntry ae, RepliesLinkedList ll, DBClient db) {
        crawlStartTime = "";
        crawlEndTime = "";
        processed = 1;
        unProcessed = 0;
        numQueries = 1;
        queryAndCounts = new HashMap<>();
        queryAndCounts.put(numQueries, ll.getSize()); // this is the intial query for base replies
        this.ll = ll;
        setDb_instance(db);
        queue = new LinkedList<>();
        teUtils = new StatusRequest();
        tweetCount = ll.getSize();
        tweets = new ArrayList<>();
        widths = new ArrayList<>();
        setWidth(0);
        setDepth(0);
        //STARTTIME = System.currentTimeMillis();
        //ENDTIME = STARTTIME + DURATION;
        buildFirstLevel(ae, ll);
    }

    /**
     * initial build method...invokes recursive build()
     *
     * @throws InterruptedException
     */
    @Override
    public void build() throws InterruptedException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        crawlStartTime = sdf.format(cal.getTime());
        addRepliesToQueue(rootArticle.getReplyList());
        LOGGER.info("Initiating Build");
        build(queue.poll());
        queue.clear();
        LOGGER.info("Level-Wise Construction Finished");
        setWidth(getMaxWidth());
        LOGGER.info("Depth: " + depth);
        LOGGER.info("Width: " + width);
        LOGGER.info("TweetCount: " + tweets.size());

    }

    /**
     *
     * @param x to be queried
     * @return <TweetNode> next recursive call
     * @throws InterruptedException
     */
    private TweetNode build(TweetNode x) throws InterruptedException {
        populateReplyList(x);
        addRepliesToQueue(x.getReplyList());
        int num_tweets = tweets.size();
        if (queue.isEmpty() || (num_tweets >= MAX_TWEET_COUNT)) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            crawlEndTime = sdf.format(cal.getTime());
            return null;
        }
        LOGGER.info("Current Tweet Count: " + tweets.size());
        return build(queue.poll());
    }

    /**
     *
     * @param x's reply list to be populated
     * @throws InterruptedException
     */
    @Override
    public void populateReplyList(TweetNode x) throws InterruptedException {
        ArrayList<Status> statusReplies = (ArrayList<Status>) teUtils.queryTweet(x.getTweet());
        this.numQueries++;
        processed++; unProcessed--;
        getQueryAndCounts().put(numQueries, statusReplies.size());
        widths.add(statusReplies.size());
        if (!statusReplies.isEmpty()) {
            depth++;
        }
        ArrayList<TweetNode> replyList = convertStatusListToNodeList(statusReplies);
        replyList.stream().forEach((tn) -> {
            x.getReplyList().add(tn);
            getDb_instance().insertTweet(tn.getTweet());
        });
    }

    /**
     *
     * @param replyList is added to queue for future processing
     */
    private void addRepliesToQueue(ArrayList<TweetNode> replyList) {
        tweetCount += replyList.size();
        unProcessed += replyList.size();
        for (int i = 0; i < replyList.size(); i++) {
            TweetNode temp = replyList.get(i);
            queue.offer(temp);
            tweets.add(temp);
        }
    }

    /**
     *
     * @param replies (Status) converted to TweetNodes
     * @return
     */
    @Override
    public ArrayList<TweetNode> convertStatusListToNodeList(ArrayList<Status> replies) {
        ArrayList<TweetNode> replyList = new ArrayList<>();
        for (int i = 0; i < replies.size(); i++) {
            replyList.add(new TweetNode(new TweetEntry(replies.get(i))));
        }
        return replyList;
    }

    /**
     *
     * @param article's reply list is added to from ll
     * @param ll what is being added to articles reply list
     */
    @Override
    public void buildFirstLevel(ArticleEntry article, RepliesLinkedList ll) {
        if (ll.getSize() != 0) {
            depth++;
        }
        widths.add(ll.getSize());
        rootArticle = new TweetNode(article);
        for (int i = 0; i < ll.getSize(); i++) {
            TweetNode temp = new TweetNode((TweetEntry) ll.get(i));
            getRootArticle().getReplyList().add(temp);
            tweets.add(temp);
        }
    }

    private int getMaxWidth() {
        ArrayList<Integer> sortedWidths = quicksort(widths);

        Integer i = Collections.max(sortedWidths);
        return (int) i;
    }

    private ArrayList<Integer> quicksort(ArrayList<Integer> widths) {
        if (widths.size() <= 1) {
            return widths;
        }

        int middle = (int) Math.ceil((double) widths.size() / 2);
        int pivot = widths.get(middle);

        ArrayList<Integer> less = new ArrayList<>();
        ArrayList<Integer> greater = new ArrayList<>();

        for (int i = 0; i < widths.size(); i++) {
            {
                if (widths.get(i) <= pivot) {
                    if (i == middle) {
                        continue;
                    }
                    less.add(widths.get(i));

                } else {
                    greater.add(widths.get(i));
                }
            }
        }
        return concatenate(quicksort(less), pivot, quicksort(greater));
    }

    private ArrayList<Integer> concatenate(ArrayList<Integer> less, int pivot, ArrayList<Integer> greater) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }

        list.add(pivot);

        for (int i = 0; i < greater.size(); i++) {
            list.add(greater.get(i));
        }

        return list;
    }

    @Override
    public TweetNode getRootArticle() {
        return rootArticle;
    }

    @Override
    public void setRootArticle(TweetNode rootArticle) {
        this.rootArticle = rootArticle;
    }

    @Override
    public int getTweetCount() {
        return tweetCount;
    }

    public ArrayList<TweetNode> getTweets() {
        return tweets;
    }

    /**
     * @return the db_instance
     */
    public static DBClient getDb_instance() {
        return db_instance;
    }

    /**
     * @param aDb_instance the db_instance to set
     */
    public static void setDb_instance(DBClient aDb_instance) {
        db_instance = aDb_instance;
    }

    /**
     * @return the ll
     */
    public RepliesLinkedList getLl() {
        return ll;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    /**
     * @return the crawlStartTime
     */
    public String getCrawlStartTime() {
        return crawlStartTime;
    }

    /**
     * @return the crawlEndTime
     */
    public String getCrawlEndTime() {
        return crawlEndTime;
    }

    /**
     * @return the numQueries
     */
    public int getNumQueries() {
        return numQueries;
    }

    /**
     * @param numQueries the numQueries to set
     */
    public void setNumQueries(int numQueries) {
        this.numQueries = numQueries;
    }

    /**
     * @return the queryAndCounts
     */
    public HashMap<Integer, Integer> getQueryAndCounts() {
        return queryAndCounts;
    }

    /**
     * @return the processed
     */
    public int getProcessed() {
        return processed;
    }

    /**
     * @return the unProcessed
     */
    public int getUnProcessed() {
        return unProcessed;
    }

}
