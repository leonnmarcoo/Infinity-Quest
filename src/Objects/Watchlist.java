package Objects;

import javafx.beans.property.SimpleIntegerProperty;

public class Watchlist {

    private final SimpleIntegerProperty watchlistID;
    private final SimpleIntegerProperty userID;
    private final SimpleIntegerProperty contentID;

    public Watchlist(int watchlistID, int userID, int contentID) {
        this.watchlistID = new SimpleIntegerProperty(watchlistID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contentID = new SimpleIntegerProperty(contentID);
    }

    public int getWatchlistID() {
        return watchlistID.get();
    }

    public int getuserID() {
        return userID.get();
    }

    public int getcontentID() {
        return contentID.get();
    }
    
}
