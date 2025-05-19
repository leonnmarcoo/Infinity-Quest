package Objects;

import javafx.beans.property.SimpleStringProperty;

public class User {
    
    private final SimpleStringProperty userName;
    private final SimpleStringProperty userPassword;
    private final SimpleStringProperty userEmail;
    private final SimpleStringProperty userBio;

        public User(String userName, String userPassword, String userEmail, String userBio) {
        this.userName = new SimpleStringProperty(userName);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.userBio = new SimpleStringProperty(userBio);
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
}
