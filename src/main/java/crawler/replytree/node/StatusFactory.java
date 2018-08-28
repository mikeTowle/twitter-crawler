/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.replytree.node;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

/**
 *
 * @author mike
 */
public class StatusFactory {
    
    private String createdAt;
    private long Id;
    private String text;
    private int displayTextRangeStart;
    private int displayTextRangeEnd;
    private String source;
    private boolean truncated;
    private long inReplyToStatusId;
    private long inReplyToUserId;
    private String inReplyToScreenName;
    private GeoLocation geoLocation;
    private Place place;
    private boolean isFavorited;
    private boolean isRetweeted;
    private int favoriteCount;
    private UserObject user;
    private boolean isRetweet;
    private Status retweetedStatus;
    private long[] contributors;
    private int retweetCount;
    private boolean retweetedByMe;
    private long currentUserRetweetId;
    private boolean possiblySensetive;
    private String lang;
    private Scopes scopes;
    private String[] withheldInCountries;
    private long quotedStatusId;
    private Status quotedStatus;
    private RateLimitStatus rateLimitStatus;
    private int accessLevel;
    private UserMentionEntity[] userMentionEntities;
    private URLEntity[] URLEntities;
    private HashtagEntity[] hashtagEntities;
    private MediaEntity[] mediaEntities;
    private SymbolEntity[] symbolEntities;
    
    
    
    public String getCreatedAt() {
        return createdAt;
    }

    
    public long getId() {
        return Id;
    }

    
    public String getText() {
        return text;
    }

    
    public int getDisplayTextRangeStart() {
        return displayTextRangeStart;
    }

    
    public int getDisplayTextRangeEnd() {
        return displayTextRangeEnd;
    }

    
    public String getSource() {
        return source;
    }

    
    public boolean isTruncated() {
        return truncated;
    }

    
    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    
    public long getInReplyToUserId() {
        return inReplyToUserId;
    }

    
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

   
    public Place getPlace() {
        return place;
    }

    
    public boolean isFavorited() {
        return isFavorited;
    }

    
    public boolean isRetweeted() {
        return isRetweeted;
    }

    
    public int getFavoriteCount() {
        return favoriteCount;
    }

    
    public UserObject getUser() {
        return user; 
    }

    
    public boolean isRetweet() {
        return isRetweet;
    }

  
    public Status getRetweetedStatus() {
        return retweetedStatus;
    }

    
    public long[] getContributors() {
        return contributors;
    }

    
    public int getRetweetCount() {
        return retweetCount;
    }

    
    public boolean isRetweetedByMe() {
        return retweetedByMe;
    }

    
    public long getCurrentUserRetweetId() {
        return currentUserRetweetId;
    }

    
    public boolean isPossiblySensitive() {
        return possiblySensetive;
    }

    
    public String getLang() {
        return lang;
    }

    
    public Scopes getScopes() {
        return scopes;
    }

    
    public String[] getWithheldInCountries() {
        return withheldInCountries;
    }

    
    public long getQuotedStatusId() {
        return quotedStatusId;
    }

    
    public Status getQuotedStatus() {
        return quotedStatus;
    }

    public int compareTo(Status o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public UserMentionEntity[] getUserMentionEntities() {
        return userMentionEntities;
    }

   
    public URLEntity[] getURLEntities() {
        return URLEntities;
    }

    
    public HashtagEntity[] getHashtagEntities() {
        return hashtagEntities;
    }

    
    public MediaEntity[] getMediaEntities() {
        return mediaEntities;
    }

    
    public SymbolEntity[] getSymbolEntities() {
        return symbolEntities;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(long Id) {
        this.Id = Id;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @param displayTextRangeStart the displayTextRangeStart to set
     */
    public void setDisplayTextRangeStart(int displayTextRangeStart) {
        this.displayTextRangeStart = displayTextRangeStart;
    }

    /**
     * @param displayTextRangeEnd the displayTextRangeEnd to set
     */
    public void setDisplayTextRangeEnd(int displayTextRangeEnd) {
        this.displayTextRangeEnd = displayTextRangeEnd;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @param truncated the truncated to set
     */
    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    /**
     * @param inReplyToStatusId the inReplyToStatusId to set
     */
    public void setInReplyToStatusId(long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     * @param inReplyToUserId the inReplyToUserId to set
     */
    public void setInReplyToUserId(long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     * @param inReplyToScreenName the inReplyToScreenName to set
     */
    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     * @param geoLocation the geoLocation to set
     */
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * @param isFavorited the isFavorited to set
     */
    public void setIsFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    /**
     * @param isRetweeted the isRetweeted to set
     */
    public void setIsRetweeted(boolean isRetweeted) {
        this.isRetweeted = isRetweeted;
    }

    /**
     * @param favoriteCount the favoriteCount to set
     */
    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserObject user) {
        this.user = user;
    }

    /**
     * @param isRetweet the isRetweet to set
     */
    public void setIsRetweet(boolean isRetweet) {
        this.isRetweet = isRetweet;
    }

    /**
     * @param retweetedStatus the retweetedStatus to set
     */
    public void setRetweetedStatus(Status retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    /**
     * @param contributors the contributors to set
     */
    public void setContributors(long[] contributors) {
        this.contributors = contributors;
    }

    /**
     * @param retweetCount the retweetCount to set
     */
    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     * @param retweetedByMe the retweetedByMe to set
     */
    public void setRetweetedByMe(boolean retweetedByMe) {
        this.retweetedByMe = retweetedByMe;
    }

    /**
     * @param currentUserRetweetId the currentUserRetweetId to set
     */
    public void setCurrentUserRetweetId(long currentUserRetweetId) {
        this.currentUserRetweetId = currentUserRetweetId;
    }

    /**
     * @param possiblySensetive the possiblySensetive to set
     */
    public void setPossiblySensetive(boolean possiblySensetive) {
        this.possiblySensetive = possiblySensetive;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @param scopes the scopes to set
     */
    public void setScopes(Scopes scopes) {
        this.scopes = scopes;
    }

    /**
     * @param withheldInCountries the withheldInCountries to set
     */
    public void setWithheldInCountries(String[] withheldInCountries) {
        this.withheldInCountries = withheldInCountries;
    }

    /**
     * @param quotedStatusId the quotedStatusId to set
     */
    public void setQuotedStatusId(long quotedStatusId) {
        this.quotedStatusId = quotedStatusId;
    }

    /**
     * @param quotedStatus the quotedStatus to set
     */
    public void setQuotedStatus(Status quotedStatus) {
        this.quotedStatus = quotedStatus;
    }

    /**
     * @param rateLimitStatus the rateLimitStatus to set
     */
    public void setRateLimitStatus(RateLimitStatus rateLimitStatus) {
        this.rateLimitStatus = rateLimitStatus;
    }

    /**
     * @param accessLevel the accessLevel to set
     */
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * @param userMentionEntities the userMentionEntities to set
     */
    public void setUserMentionEntities(UserMentionEntity[] userMentionEntities) {
        this.userMentionEntities = userMentionEntities;
    }

    /**
     * @param URLEntities the URLEntities to set
     */
    public void setURLEntities(URLEntity[] URLEntities) {
        this.URLEntities = URLEntities;
    }

    /**
     * @param hashtagEntities the hashtagEntities to set
     */
    public void setHashtagEntities(HashtagEntity[] hashtagEntities) {
        this.hashtagEntities = hashtagEntities;
    }

    /**
     * @param mediaEntities the mediaEntities to set
     */
    public void setMediaEntities(MediaEntity[] mediaEntities) {
        this.mediaEntities = mediaEntities;
    }

    /**
     * @param symbolEntities the symbolEntities to set
     */
    public void setSymbolEntities(SymbolEntity[] symbolEntities) {
        this.symbolEntities = symbolEntities;
    }
    
}
