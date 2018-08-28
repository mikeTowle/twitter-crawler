/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import com.google.gson.stream.JsonReader;
import crawler.replytree.node.StatusFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import crawler.replytree.node.UserObject;


/**
 *
 * @author mike
 */
public class JsonStatusFactory {

    public static void main(String[] args) throws IOException {
        ArrayList<StatusFactory> statuslist = readJsonStream(connectInputStream("/2563157-depthFirst.json"));
    }

    public static InputStream connectInputStream(String fileName) {
        InputStream in = JsonStatusFactory.class.getResourceAsStream("fileName");
        return in;
    }
    
    public static ArrayList<StatusFactory> readJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"))) {
            return readStatusArray(reader);
        }
    }

    private static ArrayList<StatusFactory> readStatusArray(JsonReader reader) throws IOException {
        ArrayList<StatusFactory> statuses = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            statuses.add(readStatus(reader));
        }
        reader.endArray();
        return statuses;
    }

    private static StatusFactory readStatus(JsonReader reader) throws IOException {
        StatusFactory tempStatus = new StatusFactory();

        reader.beginObject();
        while (reader.hasNext()) {
            String field = reader.nextName();
            switch (field) {
                case "createdAt":
                    tempStatus.setCreatedAt(reader.nextString());
                    break;
                case "id":
                    tempStatus.setId(reader.nextLong());
                    break;
                case "text":
                    tempStatus.setText(reader.nextString());
                    break;
                case "source":
                    tempStatus.setSource(reader.nextString());
                    break;
                case "inReplyToStatusId":
                    tempStatus.setInReplyToStatusId(reader.nextLong());
                    break;
                case "inReplyToUserId":
                    tempStatus.setInReplyToUserId(reader.nextLong());
                    break;
                case "isFavorited":
                    tempStatus.setIsFavorited(reader.nextBoolean());
                    break;
                case "isRetweeted":
                    tempStatus.setIsRetweet(reader.nextBoolean());
                    break;
                case "favoriteCount":
                    tempStatus.setFavoriteCount(reader.nextInt());
                    break;
                case "inReplyToScreenName":
                    tempStatus.setInReplyToScreenName(reader.nextString());
                    break;
                case "retweetCount":
                    tempStatus.setRetweetCount(reader.nextInt());
                    break;
                case "user":
                    tempStatus.setUser(readUser(reader));
                    break;
                case "quotedStatusId":
                    tempStatus.setQuotedStatusId(reader.nextLong());
                    break;
                default:
                    reader.skipValue();
                    break;
            }

        }
        reader.endObject();
        return tempStatus;

    }

    private static UserObject readUser(JsonReader reader) throws IOException {
        UserObject user = new UserObject();
        
        reader.beginObject();
        
        while (reader.hasNext()) {
            String field = reader.nextName();
            switch (field) {
                case "id":
                    user.setId(reader.nextLong());
                    break;
                case "name":
                    user.setName(reader.nextString());
                    break;
                case "screenName":
                    user.setScreenName(reader.nextString());
                    break;
                case "followersCount":
                    user.setFollowersCount(reader.nextInt());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return user;
    }

}
