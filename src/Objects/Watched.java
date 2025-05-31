package Objects;

import javafx.beans.property.SimpleIntegerProperty;

public class Watched {

    private final SimpleIntegerProperty watchedID;
    private final SimpleIntegerProperty userID;
    private final SimpleIntegerProperty contentID;

    public Watched(int watchedID, int userID, int contentID) {
        this.watchedID = new SimpleIntegerProperty(watchedID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contentID = new SimpleIntegerProperty(contentID);
    }

    public int getWatchedID() {
        return watchedID.get();
    }

    public int getuserID() {
        return userID.get();
    }

    public int getcontentID() {
        return contentID.get();
    }
    
}
