/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

/**
 *
 * @author mike
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertiesFileReader {


    static final Logger LOGGER = Logger.getLogger(PropertiesFileReader.class);

    public Properties readConfig(String fileName) throws ClassNotFoundException, IOException {
        Properties p = new Properties();
        try {
            InputStream strm = PropertiesFileReader.class.getResourceAsStream(fileName);
            p.load(strm);
        } catch (IOException e) {
            LOGGER.error(e);
        }

        return p;
    }

    public String getProjectPath() {
        //"/D:/Users/Administrator/workspace/TestUI/bin/"
        String dir = this.getClass().getResource("/").getPath();

        return dir;
    }

    public ArrayList<String[]> getStringList(String filepath) {
        ArrayList<String[]> strList = new ArrayList<>();
        File file = new File(filepath);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() >= 1) {
                    String item[] = line.split("=");
                    if (item.length == 2) {
                        strList.add(item);

                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }

        return strList;
    }

    public int binarySearch(ArrayList<String[]> wordList, int from, int to, String key) {
        if ((from <= to) && (from >= 0) && (to >= 0)) {
            int middle = (from + to) >> 1;
            String temp = wordList.get(middle)[0];
            if (temp.compareTo(key) > 0) {
                to = middle - 1;
            } else if (temp.compareTo(key) < 0) {
                from = middle + 1;
            } else {
                return middle;
            }
        } else {

            return -1;
        }
        return binarySearch(wordList, from, to, key);
    }

}
