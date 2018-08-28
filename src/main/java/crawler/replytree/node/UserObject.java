/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.replytree.node;

/**
 *
 * @author mike
 */
public class UserObject {
    
    private long id;
    private String name;
    private String screenName;
    private int followersCount;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    
    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * @return the followersCount
     */
    public int getFollowersCount() {
        return followersCount;
    }

    
    
    
}
