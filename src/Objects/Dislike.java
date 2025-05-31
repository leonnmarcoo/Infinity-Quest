package Objects;

import javafx.beans.property.SimpleIntegerProperty;

public class Dislike {

    private final SimpleIntegerProperty dislikeID;
    private final SimpleIntegerProperty userID;
    private final SimpleIntegerProperty contentID;


    public Dislike (int dislikeID, int userID, int contentID) {
        this.dislikeID = new SimpleIntegerProperty(dislikeID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contentID = new SimpleIntegerProperty(contentID);

    }

    public int getdislikeID() {
        return dislikeID.get();
    }

    public int getuserID() {
        return userID.get();
    }

    public int getcontentID() {
        return contentID.get();
    }

}
