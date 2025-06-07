package Objects;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class Watchlist {

    private final SimpleIntegerProperty watchlistID;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty contentTitle;
    private final ObjectProperty<LocalDateTime> watchlistDateTime;


    public Watchlist(int watchlistID, String userName, String contentTitle, LocalDateTime watchlistDateTime) {
        this.watchlistID = new SimpleIntegerProperty(watchlistID);
        this.userName = new SimpleStringProperty(userName);
        this.contentTitle = new SimpleStringProperty(contentTitle);
        this.watchlistDateTime = new SimpleObjectProperty<>(watchlistDateTime);
    }

    public int getWatchlistID() {
        return watchlistID.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getContentTitle() {
        return contentTitle.get();
    }
    
    public LocalDateTime getWatchlistDateTime(){
        return watchlistDateTime.get();
    }
    
}
