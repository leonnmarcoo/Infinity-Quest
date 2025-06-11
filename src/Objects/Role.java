package Objects;

import javafx.beans.property.*;

public class Role {

    private final SimpleIntegerProperty roleID;
    private final SimpleStringProperty roleName;

    public Role(int roleID, String roleName) {
        this.roleID = new SimpleIntegerProperty(roleID);
        this.roleName = new SimpleStringProperty(roleName);
    }

    public int getRoleID() {
        return roleID.get();
    }

    public String getRoleName() {
        return roleName.get();
    }
    
    @Override
    public String toString() {
        return roleName.get();
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }
}