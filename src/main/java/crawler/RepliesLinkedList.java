package crawler;

import org.apache.log4j.Logger;

/**
 *
 * @author mike
 *
 * This Linked List will contain every level of replies to root Tweets...
 * meaning that it will save replies to a reply....
 *
 * It will hold value type: TweetEntry
 * @param <TweetEntry> TweetEntry will act as Node in LinkedList
 *
 */
public class RepliesLinkedList<TweetEntry> {
    
    static final Logger LOGGER = Logger.getLogger(RepliesLinkedList.class);
    /*
        @param head: The first tweet/reply tweet in the level
     */
    private TweetNode head = null;

    private int size = 0;

    //creates a singleLinkedList and initializes the head with the first tweet
    private void addFirst(TweetEntry tweet) {
        head = new TweetNode(tweet, head);
        size++;
    }

    //adds tweets after head
    private void addAfter(TweetNode node, TweetEntry tweet) {
        node.next = new TweetNode(tweet, node.next);
        size++;
    }

    //removes tweet from end
    private TweetEntry removeAfter(TweetNode node) {
        TweetNode temp = node.next;
        if (temp != null) {
            node.next = temp.next;
            size--;
            return temp.data;
        } else {
            return null;
        }
    }

    //removes first node in linkedList
    private TweetEntry removeFirst() {
        TweetNode temp = head;
        if (head != null) {
            head = head.next;
        }
        if (temp != null) {
            size--;
            return temp.data;
        } else {
            return null;
        }
    }

    // Traverses the linked list and implements a toString()
    @Override
    public String toString() {
        TweetNode nodeRef = head;
        StringBuilder result = new StringBuilder();
        while (nodeRef != null) {
            result.append(nodeRef.data);
            if (nodeRef.next != null) {
                result.append("==>");
            }
            nodeRef = nodeRef.next;
        }
        return result.toString();
    }

    //gets a specified node
    private TweetNode getNode(int index) {
        TweetNode node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    //getters and setters
    //gets specified node and returns its data
    public TweetEntry get(int index) {
        if (index < 0 || index > size) {
           LOGGER.error(Integer.toString(index));
        }
        TweetNode node = getNode(index);
        return node.data;
    }

    //sets a specified node with a new value
    public TweetEntry set(int index, TweetEntry tweet) {
        if (index < 0 || index > size) {
            LOGGER.error(Integer.toString(index));
        }
        TweetNode node = getNode(index);
        TweetEntry result = node.data;
        node.data = tweet;
        return result;
    }

    //adds node at specified index
    public void add(int index, TweetEntry tweet) {
        if (index < 0 || index > size) {
            LOGGER.error(Integer.toString(index));
        }
        if (index == 0) {
            addFirst(tweet);
        } else {
            TweetNode node = getNode(index - 1);
            addAfter(node, tweet);
        }
    }

    //adds an node to end of list
    public boolean add(TweetEntry item) {
        add(size, item);
        return true;
    }

    public int getSize() {
        return size;
    }

    /*
        This acts as the SingleLinkedList nodes
        
        <Data> holds the value of the node which is a TweetEntry object
        <Next> holds the next node in the LinkedList
     */
    public class TweetNode {

        private TweetEntry data;

        private TweetNode next;

        private TweetNode(TweetEntry tweet) {
            data = tweet;
            next = null;

        }

        private TweetNode(TweetEntry tweet, TweetNode nodeRef) {
            data = tweet;
            next = nodeRef;
        }

    }

}
