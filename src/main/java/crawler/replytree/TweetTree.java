/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.replytree;

import crawler.replytree.node.ArticleEntry;
import crawler.RepliesLinkedList;
import crawler.replytree.node.TweetNode;
import java.util.ArrayList;
import twitter4j.Status;

/**
 *
 * @author mike
 */
public interface TweetTree {

    final int MAX_TWEET_COUNT = 2000;

    final int CRAWL_TIME = 30;//1 hour: Change time limit here

    void build() throws InterruptedException;

    void populateReplyList(TweetNode x) throws InterruptedException;

    ArrayList<TweetNode> convertStatusListToNodeList(ArrayList<Status> replies);

    void buildFirstLevel(ArticleEntry article, RepliesLinkedList ll);

    TweetNode getRootArticle();

    int getTweetCount();

    void setRootArticle(TweetNode rootArticle);

    void setWidth(int width);

    void setDepth(int depth);

    int getWidth();

    int getDepth();

}
