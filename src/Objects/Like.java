package Objects;

import javafx.beans.property.SimpleIntegerProperty;

public class Like {

    private final SimpleIntegerProperty likeID;
    private final SimpleIntegerProperty userID;
    private final SimpleIntegerProperty contentID;


    public Like (int likeID, int userID, int contentID) {
        this.likeID = new SimpleIntegerProperty(likeID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contentID = new SimpleIntegerProperty(contentID);

    }

    public int getLikeID() {
        return likeID.get();
    }

    public int getuserID() {
        return userID.get();
    }

    public int getcontentID() {
        return contentID.get();
    }

}
