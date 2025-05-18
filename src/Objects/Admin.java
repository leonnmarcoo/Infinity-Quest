package Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Admin {

    private final SimpleIntegerProperty adminID;
    private final SimpleStringProperty adminName;
    private final SimpleStringProperty adminPassword;

    public Admin(int adminID, String adminName, String adminPassword) {
        this.adminID = new SimpleIntegerProperty(adminID);
        this.adminName = new SimpleStringProperty(adminName);
        this.adminPassword = new SimpleStringProperty(adminPassword);
    }

    public int getAdminID() {
        return adminID.get();
    }

    public String getAdminName() {
        return adminName.get();
    }

    public String getAdminPassword() {
        return adminPassword.get();
    }
}