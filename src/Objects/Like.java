package Objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Like {

    private final SimpleIntegerProperty likeID;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty contentTitle;


    public Like (int likeID, String userName, String contentTitle) {
        this.likeID = new SimpleIntegerProperty(likeID);
        this.userName = new SimpleStringProperty(userName);
        this.contentTitle = new SimpleStringProperty(contentTitle);

    }

    public int getLikeID() {
        return likeID.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getContentTitle() {
        return contentTitle.get();
    }

}