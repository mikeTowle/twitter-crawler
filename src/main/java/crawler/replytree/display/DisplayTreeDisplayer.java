package crawler.replytree.display;

import crawler.TreeContainer;
import crawler.replytree.table.TweetHTContainer;
import crawler.replytree.LevelWiseTweetTree;
import crawler.replytree.DepthFirstTweetTree;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;

/**
 *
 * @author mike
 */
public class DisplayTreeDisplayer {

    private final DepthFirstTweetTree depthFirstTree;
    private final LevelWiseTweetTree levelWiseTree;
    private static JTree depthFirstJTree;
    private static JTree levelWiseJTree;
    private TweetHTContainer hashTables;

    public DisplayTreeDisplayer(TreeContainer container) {
        depthFirstTree = container.getDepthFirstT();
        levelWiseTree = container.getLevelWiseT();
        hashTables = container.getHashTables();
        createDisplayTree();
    }

    private void createDisplayTree() {
        DisplayTreeCreator dFJT = new DisplayTreeCreator(depthFirstTree, "DEPTH-FIRST");
        depthFirstJTree = dFJT.getJTree();
        DisplayTreeCreator lWJT = new DisplayTreeCreator(levelWiseTree, "LEVEL-WISE");
        levelWiseJTree = lWJT.getJTree();

    }

    /**
     * Main display method; calls constructor
     *
     */
    public void displayGUI() {
        
        
        //create scroll panes for JTrees
        JScrollPane dfSP = new JScrollPane(depthFirstJTree);
        dfSP.createVerticalScrollBar();
        dfSP.setWheelScrollingEnabled(true);
        dfSP.setSize(new Dimension(600, 400));
        JScrollPane lwSP = new JScrollPane(levelWiseJTree);
        lwSP.createVerticalScrollBar();
        lwSP.setWheelScrollingEnabled(true);
        lwSP.setSize(new Dimension(600, 400));
        //add the two scroll panes with each JTree to a SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setRightComponent(lwSP);
        splitPane.setLeftComponent(dfSP);
        splitPane.setPreferredSize(new Dimension(600, 400));
        splitPane.setVisible(true);
        
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("TreeDisplay", splitPane);
        JSplitPane hashSplit = new JSplitPane();
        
        JTextArea leftText = new JTextArea();
        JTextArea rightText = new JTextArea();
        leftText.setText(hashTables.getDuplicateItems().toString());
        rightText.setText(hashTables.getUniqueItems().toString());
        JScrollPane leftHash = new JScrollPane(leftText);
        leftHash.createVerticalScrollBar();
        JScrollPane rightHash = new JScrollPane(rightText);
        rightHash.createVerticalScrollBar();
        hashSplit.setLeftComponent(leftHash);
        hashSplit.setRightComponent(rightHash);
        tabbedPane.add("HashTable Results", hashSplit);
        tabbedPane.setMaximumSize(new Dimension(1800, 1000));
        JPanel pane = new JPanel();
        pane.add(tabbedPane);
        pane.setOpaque(true);
        
        
        //add the rest to a JFrame to be displayed
        String s = depthFirstTree.getRootArticle().getArticle().getTitle();
        JFrame frame = new JFrame(s);
        frame.setContentPane(pane);
        frame.setBackground(Color.BLUE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 400));
        frame.pack();
        frame.setVisible(true);
        

    }

}
