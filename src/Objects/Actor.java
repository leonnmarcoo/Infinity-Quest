package Objects;

import javafx.beans.property.*;

public class Actor {

    private final SimpleIntegerProperty actorID;
    private final SimpleStringProperty actorName;

    public Actor(int actorID, String actorName) {
        this.actorID = new SimpleIntegerProperty(actorID);
        this.actorName = new SimpleStringProperty(actorName);
    }

    public int getActorID() {
    return actorID.get();
    }

    public String getActorName() {
        return actorName.get();
    }
    
}
