package Objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Dislike {

    private final SimpleIntegerProperty dislikeID;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty contentTitle;


    public Dislike (int dislikeID, String userName, String contentTitle) {
        this.dislikeID = new SimpleIntegerProperty(dislikeID);
        this.userName = new SimpleStringProperty(userName);
        this.contentTitle = new SimpleStringProperty(contentTitle);

    }

    public int getdislikeID() {
        return dislikeID.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getContentTitle() {
        return contentTitle.get();
    }

}
