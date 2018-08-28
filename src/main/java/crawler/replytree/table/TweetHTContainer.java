package crawler.replytree.table;

import org.apache.log4j.Logger;
import crawler.replytree.table.TweetHT;
import crawler.replytree.table.TweetHT.TweetHTObject;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author mike
 */
public class TweetHTContainer {

    static final Logger LOGGER = Logger.getLogger(TweetHTContainer.class);
    private final TweetHT dF; //...Depth first hashtable build
    private final TweetHT lW; //...Level Wise hashtable build
    private final ArrayList<TweetHTObject> duplicateItems;
    private final ArrayList<TweetHTObject> uniqueItems;

    public TweetHTContainer(TweetHT dF, TweetHT lW) {
        this.dF = dF;
        this.lW = lW;
        duplicateItems = new ArrayList<>();
        uniqueItems = new ArrayList<>();
        this.sort();
    }

    private void sort() {
        try {
            for (int i = 0; i < dF.arr.length; i++) {
                LinkedList<TweetHTObject> items = dF.arr[i];
                if (items == null) {
                    continue;
                }
                for (TweetHTObject item : items) {
                    if (item == null) {
                        continue;
                    }
                    String key = item.key;
                    if (dF.get(key).equals(lW.get(key))) {
                        duplicateItems.add(item);
                    } else if(!dF.get(key).equals(lW.get(key))) {
                        uniqueItems.add(item);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
    
    public void printDuplicates() {
        System.out.println("DUPLICATES");
        duplicateItems.stream().forEach((item) -> {
            System.out.println(item + "\n");
        });
    }
    public void printUniques() {
        System.out.println("UNIQUES");
        uniqueItems.stream().forEach((item) -> {
            System.out.println(item + "\n");
        });
        
    }

    public ArrayList<TweetHTObject> getDuplicateItems() {
        return duplicateItems;
    }

    public ArrayList<TweetHTObject> getUniqueItems() {
        return uniqueItems;
    }

}
