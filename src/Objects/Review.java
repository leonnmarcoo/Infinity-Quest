package Objects;

import javafx.beans.property.SimpleIntegerProperty;

public class Review {

    private final SimpleIntegerProperty reviewID;
    private final SimpleIntegerProperty userID;
    private final SimpleIntegerProperty contentID;
    private final SimpleIntegerProperty review;


    public Review (int reviewID, int userID, int contentID, int review) {
        this.reviewID = new SimpleIntegerProperty(reviewID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contentID = new SimpleIntegerProperty(contentID);
        this.review = new SimpleIntegerProperty(review);

    }

    public int getreviewID() {
        return reviewID.get();
    }

    public int getuserID() {
        return userID.get();
    }

    public int getcontentID() {
        return contentID.get();
    }

    public int getreview() {
        return review.get();
    }

    
}
