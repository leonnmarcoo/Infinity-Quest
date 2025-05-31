package Objects;

import javafx.beans.property.SimpleIntegerProperty;

public class Rating {

    private final SimpleIntegerProperty ratingID;
    private final SimpleIntegerProperty userID;
    private final SimpleIntegerProperty contentID;
    private final SimpleIntegerProperty rating;


    public Rating (int ratingID, int userID, int contentID, int rating) {
        this.ratingID = new SimpleIntegerProperty(ratingID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contentID = new SimpleIntegerProperty(contentID);
        this.rating = new SimpleIntegerProperty(rating);

    }

    public int getratingID() {
        return ratingID.get();
    }

    public int getuserID() {
        return userID.get();
    }

    public int getcontentID() {
        return contentID.get();
    }

    public int getrating() {
        return rating.get();
    }

    
}
