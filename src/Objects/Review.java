package Objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Review {

    private final SimpleIntegerProperty reviewID;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty contentTitle;
    private final SimpleStringProperty reviewText;


    public Review (int reviewID, String userName, String contentTitle, String reviewText) {
        this.reviewID = new SimpleIntegerProperty(reviewID);
        this.userName = new SimpleStringProperty(userName);
        this.contentTitle = new SimpleStringProperty(contentTitle);
        this.reviewText = new SimpleStringProperty(reviewText);

    }

    public int getReviewID() {
        return reviewID.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getContentTitle() {
        return contentTitle.get();
    }

    public String getReviewText() {
        return reviewText.get();
    }

    
}
