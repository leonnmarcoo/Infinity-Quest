package Admin.Controller;

import Database.DatabaseHandler;
import Objects.Content;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;


public class AdminContentController {

// ======================= TABLEVIEW AND COLUMNS =======================

@FXML private TableView<Content> contentTable;

@FXML private TableColumn<Content, Integer> ContentIDColumn;
@FXML private TableColumn<Content, String> TitleColumn;
@FXML private TableColumn<Content, String> RuntimeColumn;
@FXML private TableColumn<Content, Integer> SeasonColumn;
@FXML private TableColumn<Content, Integer> EpisodeColumn;
@FXML private TableColumn<Content, LocalDate> ReleaseDateColumn;
@FXML private TableColumn<Content, String> SynopsisColumn;
@FXML private TableColumn<Content, String> DirectorColumn;
@FXML private TableColumn<Content, Integer> PhaseColumn;
@FXML private TableColumn<Content, Integer> AgeRatingColumn;
@FXML private TableColumn<Content, Integer> ChronologicalOrderColumn;
@FXML private TableColumn<Content, String> PosterColumn;
@FXML private TableColumn<Content, String> TrailerColumn;

// ========================== FORM CONTROLS ===========================

// Movie tab input controls
@FXML private TextField mTitleTextfield;
@FXML private TextField mRuntimeTextfield;
@FXML private TextField mPosterTextfield;
@FXML private TextField mChronologicalOrderTextfield;
@FXML private TextArea mSynopsisTextfield;
@FXML private DatePicker mReleaseDatePicker;
@FXML private ComboBox<String> mDirectorCombobox;
@FXML private ComboBox<String> mPhaseCombobox;
@FXML private ComboBox<String> mAgeRatingCombobox;

// TV Show tab input controls
@FXML private TextField tShowTitleTextField;
@FXML private TextField tSeasonTextField;
@FXML private TextField tEpisodeTextfield;
@FXML private TextArea tSynopsisTextfield;
@FXML private DatePicker tReleaseDatePicker;
@FXML private ComboBox<String> tDirectorTextfield;
@FXML private ComboBox<String> tPhaseTextfield;
@FXML private ComboBox<String> tAgeRatingTextfield;
@FXML private TextField tAverageRuntimeTextField;

// ======================== LABELS FOR DISPLAY =========================

@FXML private Label mTitleLabel;
@FXML private Label mRuntimeLabel;

@FXML private Label tTvShowTitleLabel;
@FXML private Label tSeasonLabel;
@FXML private Label tEpisodeLabel;
@FXML private Label tReleaseDateLabel;
@FXML private Label tSynopsisLabel;
@FXML private Label tDirectorLabel;
@FXML private Label tPhaseLabel;
@FXML private Label tAgeRatingLabel;
@FXML private Label tAverageRuntimeLabel;

@FXML private Label userLabel;

// ============================ BUTTONS ===============================

@FXML private Button backButton;
@FXML private Button userButton;
@FXML private Button contentButton;
@FXML private Button castButton;
@FXML private Button watchlistButton;
@FXML private Button ratingButton;
@FXML private Button reviewButton;
@FXML private Button likeButton;
@FXML private Button dislikeButton;
@FXML private Button createButton;
@FXML private Button deleteButton;
@FXML private Button updateButton;

// ============================== TABS ================================

@FXML private TabPane contentTabPane;
@FXML private Tab movieTab;
@FXML private Tab tvshowTab;

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    public void initialize() {
    // Bind table columns to Content properties
        ContentIDColumn.setCellValueFactory(new PropertyValueFactory<>("contentID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("contentTitle"));
        RuntimeColumn.setCellValueFactory(new PropertyValueFactory<>("contentRuntime"));
        SeasonColumn.setCellValueFactory(new PropertyValueFactory<>("contentSeason"));
        EpisodeColumn.setCellValueFactory(new PropertyValueFactory<>("contentEpisode"));
        ReleaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("contentReleaseDate"));
        SynopsisColumn.setCellValueFactory(new PropertyValueFactory<>("contentSynopsis"));
        DirectorColumn.setCellValueFactory(new PropertyValueFactory<>("contentDirector"));
        PhaseColumn.setCellValueFactory(new PropertyValueFactory<>("contentPhase"));
        AgeRatingColumn.setCellValueFactory(new PropertyValueFactory<>("contentAgeRating"));
        ChronologicalOrderColumn.setCellValueFactory(new PropertyValueFactory<>("contentChronologicalOrder"));
        PosterColumn.setCellValueFactory(new PropertyValueFactory<>("contentPoster"));
        TrailerColumn.setCellValueFactory(new PropertyValueFactory<>("contentTrailer"));
        
        ObservableList<Content> contentList = FXCollections.observableArrayList(DatabaseHandler.getAllContent());
        contentTable.setItems(contentList);
}



    @FXML
    private void userButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminUser.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backButtonHandler(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Admin/FXML/AdminHome.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void createContentHandler(ActionEvent event) throws IOException {

    String title = mTitleTextfield.getText();
    String runtime = mRuntimeTextfield.getText();
    Integer season = (tSeasonTextField.getText().isEmpty()) ? null : Integer.parseInt(tSeasonTextField.getText());
    Integer episode = (tEpisodeTextfield.getText().isEmpty()) ? null : Integer.parseInt(tEpisodeTextfield.getText());
    LocalDate releaseDate = mReleaseDatePicker.getValue();
    String synopsis = mSynopsisTextfield.getText();
    String director = mDirectorCombobox.getValue();
    int phase = Integer.parseInt(mPhaseCombobox.getValue());
    int ageRating = Integer.parseInt(mAgeRatingCombobox.getValue());
    int chronologicalOrder = Integer.parseInt(mChronologicalOrderTextfield.getText());
    String poster = mPosterTextfield.getText();
    String trailer = "";


    Content content = new Content(
        0, title, runtime, season, episode, releaseDate,
        synopsis, director, phase, ageRating, chronologicalOrder, poster, trailer
    );

    boolean success = DatabaseHandler.createContent(content);
    if (success) {
        System.out.println("Content created successfully.");
        // Optional: clear fields or refresh table
    } else {
        System.out.println("Failed to create content.");
    }
}


}