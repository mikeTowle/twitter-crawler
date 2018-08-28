package database;

/**
 *
 * @author mike
 * This class will take an input .csv file, read, and add items to Database
 */
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import com.opencsv.CSVReader;
import crawler.PropertiesFileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class ArticleKeeper {

    static final Logger LOGGER = Logger.getLogger(ArticleKeeper.class);
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        //change article title for updated file 
        String articlePath = "/Users/mike/Desktop/2018Spring/URPResearchProject/TwitterApp/src/main/resources/in/August17.csv";
        //connect to db
        PropertiesFileReader pf = new PropertiesFileReader();
        String filepath = "/configMike.properties";
        Properties p = pf.readConfig(filepath);
        String dburl = p.getProperty("DBurl");
        String dbuserName = p.getProperty("DBusername");
        String dbpsw = p.getProperty("DBpwd");
        DBClient savedb = new DBClient();
        boolean con = savedb.dbConnection(dburl, dbuserName, dbpsw);

        //keeps articles based on publish time
        ArticleKeeper keep = new ArticleKeeper();
        keep.addArticlesToDB(articlePath, savedb);
        savedb.dbClose();

    }
   
    
    public final void addArticlesToDB(String path, DBClient savedb) {

        ArrayList<String[]> rowList = collectArticles(path);

        for (String[] row : rowList) {

            long articleID = Long.parseLong(row[0]);
            String title = row[1];
            String url = row[2];
            String publishTimeStr = row[3];
            String twitterURL = row[16];
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // may have to change formatting based on .csv file

            try {

                Date pubDate = simpleDateFormat2.parse(publishTimeStr);
                Timestamp publishTime = new Timestamp(pubDate.getTime());
                savedb.insertArticles(articleID, twitterURL, url, title, publishTime);

            } catch (Exception e) {
                LOGGER.error(e);
            }
        }
    }

    public static ArrayList<String[]> collectArticles(String csvReadPath) {

        ArrayList<String[]> inputList = new ArrayList<>();
        CSVReader reader = null;

        try {

            reader = new CSVReader(new FileReader(csvReadPath));
            String[] line = reader.readNext();//skip the headline

            while ((line = reader.readNext()) != null) {
                inputList.add(line);
            }

        } catch (IOException e) {
            LOGGER.error(e);
        }

        return inputList;
    }

}
