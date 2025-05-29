package Objects;

import javafx.beans.property.*;

public class Director {

    private final SimpleIntegerProperty directorID;
    private final SimpleStringProperty directorName;

    public Director(int directorID, String directorName) {
        this.directorID = new SimpleIntegerProperty(directorID);
        this.directorName = new SimpleStringProperty(directorName);
    }

    public int getDirectorID() {
        return directorID.get();
    }

    public String getDirectorName() {
        return directorName.get();
    }
    
    @Override
    public String toString() {
        return directorName.get();
    }
}
