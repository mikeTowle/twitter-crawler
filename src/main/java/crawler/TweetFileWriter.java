package crawler;

import com.google.gson.Gson;
import crawler.replytree.DepthFirstTweetTree;
import crawler.replytree.LevelWiseTweetTree;
import crawler.replytree.node.TweetNode;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import twitter4j.Status;

/**
 *
 * @author mike
 */
public class TweetFileWriter {

    //global
    private FileWriter fileWriter;
    private BufferedWriter buffWriter;
    private File file;
    private String env = System.getProperty("user.dir");
    static final Logger LOGGER = Logger.getLogger(TweetFileWriter.class);

    public TweetFileWriter() {

    }

    public TweetFileWriter(String JSONfileName, int treeType) {
        JSONfileName = JSONfileName + ".json";
        String baseDirPath = makeBaseDirectory();
        //String jsonDirectPath = baseDir.getAbsolutePath();

        String dfPath = makeDFDirectories(baseDirPath);
        //String dfPath = df.getAbsolutePath();

        String lwPath = makeLWDirectories(baseDirPath);
        //String lwPath = lw.getAbsolutePath();

        try {
            //if depthFirst
            File out;
            if (treeType == 0) {
                out = new File(dfPath + "/" + JSONfileName);
                if (!out.exists()) {
                    out.createNewFile();
                }
                fileWriter = new FileWriter(out.getAbsoluteFile(), true);
                buffWriter = new BufferedWriter(fileWriter);
            } else if (treeType == 1) {
                out = new File(lwPath + "/" + JSONfileName);
                if (!out.exists()) {
                    out.createNewFile();
                }
                fileWriter = new FileWriter(out.getAbsoluteFile(), true);
                buffWriter = new BufferedWriter(fileWriter);
            }

        } catch (Exception e) {
            LOGGER.error("Error creating tweetfile data dump directories");
            LOGGER.error(e);
        }

    }

    private static String makeBaseDirectory() {
        File jsonDirect = null;
        String baseDir = "src/main/resources/out/data/";
        try {
            jsonDirect = new File(baseDir);
            if (!jsonDirect.exists() && !jsonDirect.isDirectory()) {
                jsonDirect.mkdir();
            }
        } catch (Exception e) {
            LOGGER.error("Error makeBaseDirectory()");
            LOGGER.error(e);
        }
        return baseDir;
    }

    private static String makeDFDirectories(String path) {
        File df = null;
        String dfDirPath = path + "/Depth_First/";
        try {
            df = new File(dfDirPath);
            if (!df.exists() && !df.isDirectory()) {
                df.mkdir();
            }
        } catch (Exception e) {
            LOGGER.error("Error makeDFFirectories()");
        }
        return dfDirPath;
    }

    private static String makeLWDirectories(String path) {
        File lw = null;
        String lwDirPath = path + "/Level_Wise/";
        try {
            lw = new File(lwDirPath);
            if (!lw.exists() && !lw.isDirectory()) {
                lw.mkdir();
            }
        } catch (Exception e) {
            LOGGER.error("Error makeLWDirectories");
        }
        return lwDirPath;
    }

    public void close() {
        try {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ignore) {
                    LOGGER.error("Error CLose");
                }
            }
            if (buffWriter != null) {
                try {
                    buffWriter.close();
                } catch (IOException ignore) {
                    LOGGER.error("Error Close");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error Close()");
        }
    }

    public void writeJsonToFile(Status tweet) {
        Gson gson = new Gson();
        String rawJSON = gson.toJson(tweet);

        try {
            buffWriter.write(rawJSON);
            buffWriter.newLine();
            buffWriter.newLine();
            buffWriter.newLine();
        } catch (IOException e) {
            LOGGER.error("Error writeJsonToFile()");
        }
    }

    public void writeJsonToFileDF(DepthFirstTweetTree dfTree) throws IOException {
        ArrayList<TweetNode> tweets = dfTree.getTweets();
        Gson gson = new Gson();

        
            buffWriter.write("Crawl Started at: " + dfTree.getCrawlStartTime() + "\n");
            buffWriter.write("Crawl Ended at: " + dfTree.getCrawlEndTime() + "\n");
        
            int p = dfTree.getProcessed();
            int np = dfTree.getUnProcessed();
            int union = dfTree.getProcessed() + dfTree.getUnProcessed();
            buffWriter.write("Processed: " + p + "\n");
            buffWriter.write("UnProcessed: " + np + "\n");
            buffWriter.write("Ratio |P| / |P U NP|: |" + p + "| / |" + union + "|" + "\n");
            buffWriter.write("Depth_First Tree Depth: " + dfTree.getDepth() + "\n");
            buffWriter.write("Depth_First Tree Width:" + dfTree.getWidth() + "\n");
            buffWriter.write("Depth_First Tree Size:" + dfTree.getTweets().size() + "\n\n");
            buffWriter.write("(# Query : # tweets returned)\n");
            Iterator<Map.Entry<Integer, Integer>> iter = dfTree.getQueryAndCounts().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer, Integer> entry = iter.next();
                buffWriter.write(entry.getKey() + " : " + entry.getValue() + "\n");
            }
            buffWriter.write("[");
            for (TweetNode tn : tweets) {
                Status status = tn.getTweet().getOriginalStatus();
                String rawJSON = gson.toJson(status);
                try {
                    buffWriter.write(rawJSON + ",");
                    buffWriter.newLine();
                    buffWriter.newLine();
                    buffWriter.newLine();
                } catch (IOException e) {
                    LOGGER.error("Error writeJSonToFile(): " + e);
                }
            }
            buffWriter.write("]");
        

    }

    public void writeJsonToFileLW(LevelWiseTweetTree lwTree) throws IOException {
        ArrayList<TweetNode> tweets = lwTree.getTweets();
        Gson gson = new Gson();

        buffWriter.write("Crawl Started at: " + lwTree.getCrawlStartTime());
        buffWriter.write("Crawl Ended at: " + lwTree.getCrawlEndTime());

        int p = lwTree.getProcessed();
        int np = lwTree.getUnProcessed();
        int union = lwTree.getProcessed() + lwTree.getUnProcessed();
        buffWriter.write("Processed: " + p + "\n");
        buffWriter.write("UnProcessed: " + np + "\n");
        buffWriter.write("Ratio |P| / |P U NP|: |" + p + "| / |" + union + "|" + "\n");
        buffWriter.write("Level_Wise Tree Depth: " + lwTree.getDepth() + "\n");
        buffWriter.write("Level_Wise Tree Width:" + lwTree.getWidth() + "\n");
        buffWriter.write("Level_Wise Tree Size:" + lwTree.getTweets().size() + "\n\n");
        buffWriter.write("(# Query : # tweets returned)\n");
        Iterator<Map.Entry<Integer, Integer>> iter = lwTree.getQueryAndCounts().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = iter.next();
            buffWriter.write(entry.getKey() + " : " + entry.getValue() + "\n");
        }
        buffWriter.write("[");
        for (TweetNode tn : tweets) {
            Status status = tn.getTweet().getOriginalStatus();
            String rawJSON = gson.toJson(status);
            try {
                buffWriter.write(rawJSON + ",");
                buffWriter.newLine();
                buffWriter.newLine();
                buffWriter.newLine();
            } catch (IOException e) {
                LOGGER.error("Error writeJSonToFile(): " + e);
            }
        }
        buffWriter.write("]");
    }
}

