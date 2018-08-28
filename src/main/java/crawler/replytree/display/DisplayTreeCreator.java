package crawler.replytree.display;

import org.apache.log4j.Logger;
import crawler.replytree.node.TweetNode;
import crawler.replytree.LevelWiseTweetTree;
import crawler.replytree.DepthFirstTweetTree;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mike
 */
public class DisplayTreeCreator extends JFrame {
    
    static final Logger LOGGER = Logger.getLogger(DisplayTreeCreator.class);
    private DepthFirstTweetTree dfTree;
    private LevelWiseTweetTree lwTree;
    private JTree jTree;
    private Stack<Combo> comboStack;
    private DefaultMutableTreeNode rootArticle;

    /**
     * Primary Constructor used to create a graphic display of @param tweeTree.
     *
     * @param tweetTree
     * @param typeTree
     */
    public DisplayTreeCreator(DepthFirstTweetTree tweetTree, String typeTree) {
        comboStack = new Stack();
        dfTree = tweetTree;
        DefaultMutableTreeNode invRoot = new DefaultMutableTreeNode(typeTree);//...invisible invRoot: not shown on GUI
        rootArticle = new DefaultMutableTreeNode(tweetTree.getRootArticle().articleToString());
        processDFReplies();
        invRoot.add(rootArticle);
        jTree = new JTree(invRoot);//...create the jTree by passing in the rootDMTN node
        jTree.setRootVisible(true);
        jTree.setShowsRootHandles(true);
        jTree.putClientProperty("JTree.lineStyle", "Angled");
        
        
    }
    
    /**
     * Primary Constructor used to create a graphic display of @param tweeTree.
     *
     * @param tweetTree
     * @param typeTree
     */
    public DisplayTreeCreator(LevelWiseTweetTree tweetTree, String typeTree) {
        comboStack = new Stack();
        lwTree = tweetTree;
        DefaultMutableTreeNode invRoot = new DefaultMutableTreeNode(typeTree);//...invisible invRoot: not shown on GUI
        rootArticle = new DefaultMutableTreeNode(tweetTree.getRootArticle().articleToString());
        processLWReplies();
        invRoot.add(rootArticle);
        jTree = new JTree(invRoot);//...create the jTree by passing in the rootDMTN node
        jTree.setRootVisible(true);
        jTree.setShowsRootHandles(true);
        jTree.putClientProperty("JTree.lineStyle", "Angled");
    }
    
    /**
     * Method that invokes recursive call for Depth First
     */
    private void processDFReplies() {
        //...add first level comments
        ArrayList<Combo> comboList = createComboList(dfTree.getRootArticle().getReplyList());
        addRepliesToStack(comboList);
        //...set first level replies as DMTN objects 
        setComboListAsDMTNChildren(rootArticle, comboList);
        processReplies(comboStack.pop());
    }
    
    /**
     * Method that invokes recurisive call for Level Wise
     */
    private void processLWReplies() {
        //...add first level comments
        ArrayList<Combo> comboList = createComboList(lwTree.getRootArticle().getReplyList());
        addRepliesToStack(comboList);
        //...set first level replies as DMTN objects 
        setComboListAsDMTNChildren(rootArticle, comboList);
        processReplies(comboStack.pop());
    }
    
    
    /**
     * recursive function
     * @param c to be processed
     * @return next to be processed
     */
    private TweetNode processReplies(Combo c) {
        if (comboStack.empty()) {
            return null;
        }

        ArrayList<Combo> comboList = createComboList(c.tweet.getReplyList());
        addRepliesToStack(comboList);
        setComboListAsDMTNChildren(c.dmtn, comboList);
        return processReplies(comboStack.pop());
    }

    /**
     * Adds entirety of TweetNode's replyList to comboStack
     *
     * @param replyList
     */
    private void addRepliesToStack(ArrayList<Combo> comboList) {
        for (int i = comboList.size() - 1; i >= 0; i--) {
            comboStack.push(comboList.get(i));
        }
    }

    /**
     *
     */
    private ArrayList<Combo> createComboList(ArrayList<TweetNode> replyList) {
        ArrayList<Combo> comboList = new ArrayList<>();
        for (int i = 0; i < replyList.size(); i++) {
            comboList.add(new Combo(replyList.get(i)));
        }
        return comboList;
    }

    /**
     * Takes in replyList and sets each TweetNode as a child of passed in DMTN
     *
     * @param replyList
     */
    private void setComboListAsDMTNChildren(DefaultMutableTreeNode dmtn, ArrayList<Combo> comboList) {
        for (int i = 0; i < comboList.size(); i++) {
            dmtn.add(comboList.get(i).dmtn);
        }
    }
    
    public JTree getJTree() {
        return jTree;
    }

    /**
     * Combo class to hold two objects required for tree creation
     */
    private class Combo {

        protected TweetNode tweet;
        protected DefaultMutableTreeNode dmtn;

        public Combo() {
            tweet = null;
            dmtn = null;
        }

        //automatically creates dmtn from tweet node
        public Combo(TweetNode tweet) {
            this.tweet = tweet;
            dmtn = new DefaultMutableTreeNode(this.tweet.tweetToString());
        }
    }

}
