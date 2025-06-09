package Objects;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    
    private final SimpleIntegerProperty userID;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty userPassword;
    private final SimpleStringProperty userEmail;
    private final SimpleStringProperty userBio;

    private List<String> watched;
    private List<String> watchlist;
    private List<String> reviews;

        public User(int userId,String userName, String userPassword, String userEmail, String userBio) {
        this.userID = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.userBio = new SimpleStringProperty(userBio);
    }

    public int getUserID() {
        return userID.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public String getUserBio() {
        return userBio.get();
    }

    public void setUserPassword(String password) { this.userPassword.set(password); }

    public void setUserEmail(String email) { this.userEmail.set(email); }
    
    public void setUserBio(String bio) { this.userBio.set(bio); }

    public List<String> getWatched() {
        return watched;
    }

    public void setWatched(List<String> watched) {
        this.watched = watched;
    }

    public List<String> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<String> watchlist) {
        this.watchlist = watchlist;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
}
