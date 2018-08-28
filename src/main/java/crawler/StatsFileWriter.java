/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author mike
 */
public class StatsFileWriter {
    
    static final Logger LOGGER = Logger.getLogger(TweetFileWriter.class);
    
    public StatsFileWriter(ArrayList<TreeContainer> treeList) {
        exportStats(treeList);
    }
    
    
    public void exportStats(ArrayList<TreeContainer> treeList) {
        FileWriter fileWriter;
        BufferedWriter buffWriter;
        File statsOutputDir;
        File statsOutput;
        try {
            statsOutputDir = new File("src/main/recourses/out/data/articleStats");
            if (!statsOutputDir.exists()) {
                statsOutputDir.mkdir();
            }
            
            fileWriter = new FileWriter(statsOutputDir.getAbsoluteFile(), true);
            buffWriter = new BufferedWriter(fileWriter);
        } catch (Exception e) {
            LOGGER.error("Error exporting stats");
            LOGGER.error(e);
        }
        
        
    }
}
