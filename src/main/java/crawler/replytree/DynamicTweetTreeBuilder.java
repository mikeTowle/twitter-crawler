package crawler.replytree;

import crawler.replytree.node.TweetNode;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 *
 * ******************NOTE!THIS WONT WORK....NEED TO CREATE A NEW TREE
 *
 * @author mike
 */
public class DynamicTweetTreeBuilder {

    private Stack<TweetNode> tweetStack;

    public DynamicTweetTreeBuilder(DepthFirstTweetTree tree) {

    }

    private void addToStack(ArrayList<TweetNode> replyList) {

        for (int i = replyList.size() - 1; i >= 0; i++) {
            tweetStack.add(replyList.get(i));
        }

    }

    private boolean search(ArrayList<TweetNode> replyList, String id) {

        ArrayList<TweetNode> sorted = sort(replyList, 0, replyList.size() - 1);
        for (TweetNode tn : sorted) {
            if (tn.getTweet().getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Quick sort method to sort based on Tweet ID's
     *
     * @param replyList
     * @param a
     * @param b
     * @return
     */
    private ArrayList<TweetNode> sort(ArrayList<TweetNode> replyList, int a, int b) {
        if (a >= b) {
            return replyList;
        }

        String pivot = replyList.get(b).getTweet().getID();
        int left = a, right = b;
        while (left < right) {
            while (replyList.get(left).getTweet().getID().compareTo(pivot) < 0) {
                left++;
            }
            while (replyList.get(right).getTweet().getID().compareTo(pivot) < 0) {
                right++;
            }
            if (right > left) {
                Collections.swap(replyList, left, right);
            }
        }
        sort(replyList, a, right - 1);
        sort(replyList, right + 1, b);
        return replyList;
    }

    /**
     * Pseudo Code for Dynamic Crawl
     *
     * 1. Take in a built tree 2. For each <TweetNode>
     * a. 3. compare dynamic/static tree
     */
}
