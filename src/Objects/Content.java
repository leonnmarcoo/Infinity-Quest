package Objects;
import javafx.beans.property.*;
import java.time.LocalDate;

public class Content {

    private final SimpleIntegerProperty contentID;
    private final SimpleStringProperty contentTitle;
    private final SimpleStringProperty contentRuntime;
    private final SimpleObjectProperty<Integer> contentSeason;
    private final SimpleObjectProperty<Integer> contentEpisode;
    private final SimpleObjectProperty<LocalDate> contentReleaseDate;
    private final SimpleStringProperty contentSynopsis;
    private final SimpleStringProperty contentDirector;
    private final SimpleIntegerProperty contentPhase;
    private final SimpleStringProperty contentAgeRating;
    private final SimpleIntegerProperty contentChronologicalOrder;
    private final SimpleStringProperty contentPoster;
    private final SimpleStringProperty contentTrailer;

    public Content(int contentID, String contentTitle, String contentRuntime,
        Integer contentSeason, Integer contentEpisode, LocalDate contentReleaseDate,
        String contentSynopsis, String contentDirector, int contentPhase,
        String contentAgeRating, int contentChronologicalOrder,
        String contentPoster, String contentTrailer) {

        this.contentID = new SimpleIntegerProperty(contentID);
        this.contentTitle = new SimpleStringProperty(contentTitle);
        this.contentRuntime = new SimpleStringProperty(contentRuntime);
        this.contentSeason = new SimpleObjectProperty<>(contentSeason);
        this.contentEpisode = new SimpleObjectProperty<>(contentEpisode);
        this.contentReleaseDate = new SimpleObjectProperty<>(contentReleaseDate);
        this.contentSynopsis = new SimpleStringProperty(contentSynopsis);
        this.contentDirector = new SimpleStringProperty(contentDirector);
        this.contentPhase = new SimpleIntegerProperty(contentPhase);
        this.contentAgeRating = new SimpleStringProperty(contentAgeRating);
        this.contentChronologicalOrder = new SimpleIntegerProperty(contentChronologicalOrder);
        this.contentPoster = new SimpleStringProperty(contentPoster);
        this.contentTrailer = new SimpleStringProperty(contentTrailer);
    }

    public int getContentID() { 
        return contentID.get(); 
    }

    public String getContentTitle() { 
        return contentTitle.get(); 
    }

    public String getContentRuntime() { 
        return contentRuntime.get(); 
    }

    public Integer getContentSeason() { 
        return contentSeason.get(); 
    }

    public Integer getContentEpisode() { 
        return contentEpisode.get(); 
    }

    public LocalDate getContentReleaseDate() { 
        return contentReleaseDate.get(); 
    }

    public String getContentSynopsis() { 
        return contentSynopsis.get(); 
    }

    public String getContentDirector() { 
        return contentDirector.get(); 
    }

    public int getContentPhase() { 
        return contentPhase.get(); 
    }

    public String getContentAgeRating() { 
        return contentAgeRating.get(); 
    }

    public int getContentChronologicalOrder() { 
        return contentChronologicalOrder.get(); 
    }

    public String getContentPoster() { 
        return contentPoster.get(); 
    }

    public String getContentTrailer() { 
        return contentTrailer.get(); 
    } 

}
