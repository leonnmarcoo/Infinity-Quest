package Objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Rating {

    private final SimpleIntegerProperty ratingID;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty contentTitle;
    private final SimpleIntegerProperty rating;


    public Rating (int ratingID, String userName, String contentTitle, int rating) {
        this.ratingID = new SimpleIntegerProperty(ratingID);
        this.userName = new SimpleStringProperty(userName);
        this.contentTitle = new SimpleStringProperty(contentTitle);
        this.rating = new SimpleIntegerProperty(rating);

    }

    public int getRatingID() {
        return ratingID.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getContentTitle() {
        return contentTitle.get();
    }

    public int getRating() {
        return rating.get();
    }

    
}
