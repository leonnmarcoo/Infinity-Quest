import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Admin {

    private final SimpleIntegerProperty adminID;
    private final SimpleStringProperty adminUsername;
    private final SimpleStringProperty adminPassword;

    public Admin(int adminID, String adminUsername, String adminPassword) {
        this.adminID = new SimpleIntegerProperty(adminID);
        this.adminUsername = new SimpleStringProperty(adminUsername);
        this.adminPassword = new SimpleStringProperty(adminPassword);
    }

    public int getAdminID() {
        return adminID.get();
    }

    public String getAdminUsername() {
        return adminUsername.get();
    }

    public String getAdminPassword() {
        return adminPassword.get();
    }
}