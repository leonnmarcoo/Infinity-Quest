package Objects;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class Watched {

    private final SimpleIntegerProperty watchedID;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty contentTitle;
    private final ObjectProperty<LocalDateTime> watchedDateTime;


    public Watched(int watchedID, String userName, String contentTitle, LocalDateTime watchedDateTime) {
        this.watchedID = new SimpleIntegerProperty(watchedID);
        this.userName = new SimpleStringProperty(userName);
        this.contentTitle = new SimpleStringProperty(contentTitle);
        this.watchedDateTime = new SimpleObjectProperty<>(watchedDateTime);
    }

    public int getWatchedID() {
        return watchedID.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getContentTitle() {
        return contentTitle.get();
    }
    
    public LocalDateTime getWatchedDateTime(){
        return watchedDateTime.get();
    }
    
}
