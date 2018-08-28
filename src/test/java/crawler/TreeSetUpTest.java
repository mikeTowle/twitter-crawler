/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import crawler.replytree.node.ArticleEntry;
import database.DBClient;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import twitter4j.Twitter;

/**
 *
 * @author mike
 */
public class TreeSetUpTest {
    
    public TreeSetUpTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of beginTreeCreation method, of class CrawlControl.
     */
    @Test
    public void testSearchByTopic() throws Exception {
        System.out.println("searchByTopic");
        String topic = "";
        int tweetsPerPage = 0;
        ArticleEntry article = null;
        ArrayList<TreeContainer> treeList = null;
        DBClient db_instance = null;
        CrawlControl instance = new CrawlControl();
        instance.beginTreeCreation(topic, tweetsPerPage, article, db_instance);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testInitialQuery() throws Exception {
        System.out.println("InitialQuery");
        String topic = "";
        OAuthHandler oauth = new OAuthHandler();
        Twitter twitter = oauth.getTwitterHandle();
    }
    
}
