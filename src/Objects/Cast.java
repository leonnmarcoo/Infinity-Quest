package Objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cast {

    private final SimpleIntegerProperty castID;
    private final SimpleIntegerProperty actorID;
    private final SimpleIntegerProperty roleID;
    private final SimpleIntegerProperty contentID;
    private final SimpleStringProperty actorName;
    private final SimpleStringProperty roleName;
    private final SimpleStringProperty contentTitle;

    public Cast(int castID, int actorID, int roleID, int contentID, String actorName, String roleName, String contentTitle) {
        this.castID = new SimpleIntegerProperty(castID);
        this.actorID = new SimpleIntegerProperty(actorID);
        this.roleID = new SimpleIntegerProperty(roleID);
        this.contentID = new SimpleIntegerProperty(contentID);
        this.actorName = new SimpleStringProperty(actorName);
        this.roleName = new SimpleStringProperty(roleName);
        this.contentTitle = new SimpleStringProperty(contentTitle);
    }

    public int getCastID() {
        return castID.get();
    }

    public int getActorID() {
        return actorID.get();
    }

    public int getRoleID() {
        return roleID.get();
    }

    public int getContentID() {
        return contentID.get();
    }

    public String getActorName() {
        return actorName.get();
    }

    public String getRoleName() {
        return roleName.get();
    }

    public String getContentTitle() {
        return contentTitle.get();
    }

    public void setActorName(String name) {
        this.actorName.set(name);
    }

    public void setRoleName(String name) {
        this.roleName.set(name);
    }

    public void setContentTitle(String title) {
        this.contentTitle.set(title);
    }
    
}