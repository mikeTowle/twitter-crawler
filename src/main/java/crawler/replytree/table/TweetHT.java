/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.replytree.table;

import java.util.LinkedList;

/**
 *
 * @author mike
 */
public class TweetHT {

    private final int ARR_SIZE;
    protected final LinkedList<TweetHTObject>[] arr;

    public TweetHT(int size) {
        ARR_SIZE = size;
        arr = new LinkedList[ARR_SIZE];
        for (int i = 0; i < ARR_SIZE; i++) {
            arr[i] = null;
        }
    }

    private TweetHTObject getObject(String key) {
        if (key == null) {
            return null;
        }
        int idx = Math.abs(key.hashCode()) % ARR_SIZE;
        LinkedList<TweetHTObject> items = arr[idx];
        if (items == null) {
            return null;
        }
        for (TweetHTObject item : items) {
            if (item.key.equals(key)) {
                return item;
            }
        }
        return null;
    }

    /**
     * searches hash table for @param key. Returns its value if found, else null
     * @param key
     * @return 
     */
    public String get(String key) {
        
        TweetHTObject item = getObject(key);

        if (item == null) {
            return null;
        }
        return item.value;
    }

    /**
     *
     * Used to add items to, or update hash table. First applies hash function
     * to key in provided
     *
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        int idx = Math.abs(key.hashCode()) % ARR_SIZE;
        LinkedList<TweetHTObject> items = arr[idx];

        if (items == null) {
            items = new LinkedList<TweetHTObject>();
            TweetHTObject item = new TweetHTObject();
            item.key = key;
            item.value = value;

            items.add(item);
            arr[idx] = items;
        } else {
            for (TweetHTObject item : items) {
                if (item.key.equals(key)) {
                    item.value = value;
                    return;
                }
            }

            TweetHTObject item = new TweetHTObject();
            item.key = key;
            item.value = value;
            items.add(item);
        }

    }

    public void delete(String key) {
        int idx = Math.abs(key.hashCode()) % ARR_SIZE;
        LinkedList<TweetHTObject> items = arr[idx];
        if (items == null) {
            return;
        }

        for (TweetHTObject item : items) {
            if (item.key.equals(key)) {
                items.remove(item);
                return;
            }
        }
    }

    public static class TweetHTObject {

        public String key; //...ScreenName
        public String value; //...TweetId
        
        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return "\n[Key: " + key + " Value: " + value + "]\n";
        }
    }

}
