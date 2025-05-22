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
import java.util.Arrays;
import java.util.List;
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


public class AdminContentController implements Initializable {

// ======================= TABLEVIEW AND COLUMNS =======================

@FXML private TableView<Content> contentTable;

@FXML 
private TableColumn<Content, Integer> ContentIDColumn;

@FXML 
private TableColumn<Content, String> TitleColumn;

@FXML 
private TableColumn<Content, String> RuntimeColumn;

@FXML 
private TableColumn<Content, Integer> SeasonColumn;

@FXML 
private TableColumn<Content, Integer> EpisodeColumn;

@FXML 
private TableColumn<Content, LocalDate> ReleaseDateColumn;

@FXML 
private TableColumn<Content, String> SynopsisColumn;

@FXML 
private TableColumn<Content, String> DirectorColumn;

@FXML 
private TableColumn<Content, Integer> PhaseColumn;

@FXML 
private TableColumn<Content, String> AgeRatingColumn;

@FXML 
private TableColumn<Content, Integer> ChronologicalOrderColumn;

@FXML 
private TableColumn<Content, String> PosterColumn;

@FXML 
private TableColumn<Content, String> TrailerColumn;

// ========================== FORM CONTROLS ===========================

@FXML 
private TextField mTitleTextfield;

@FXML 
private TextField mRuntimeTextfield;

@FXML 
private TextField mPosterTextfield;

@FXML 
private TextField mChronologicalOrderTextfield;

@FXML 
private TextArea mSynopsisTextfield;

@FXML 
private DatePicker mReleaseDatePicker;

@FXML 
private ComboBox<String> mDirectorCombobox;

@FXML 
private ComboBox<String> mPhaseCombobox;

@FXML 
private ComboBox<String> mAgeRatingCombobox;

@FXML 
private TextField tShowTitleTextField;

@FXML 
private TextField tSeasonTextField;

@FXML 
private TextField tEpisodeTextfield;

@FXML 
private TextArea tSynopsisTextfield;

@FXML 
private DatePicker tReleaseDatePicker;

@FXML 
private ComboBox<String> tDirectorTextfield;

@FXML 
private ComboBox<String> tPhaseTextfield;

@FXML 
private ComboBox<String> tAgeRatingTextfield;

@FXML 
private TextField tAverageRuntimeTextField;

@FXML
private TextField mTrailerLinkTextField;

@FXML
private TextField tTrailerLinkTextField;

@FXML
private TextField tPosterTextField;

@FXML
private TextField tChronologicalOrderTextField;

// ======================== LABELS FOR DISPLAY =========================

@FXML 
private Label mTitleLabel;

@FXML 
private Label mRuntimeLabel;

@FXML 
private Label tTvShowTitleLabel;

@FXML 
private Label tSeasonLabel;

@FXML 
private Label tEpisodeLabel;

@FXML 
private Label tReleaseDateLabel;

@FXML 
private Label tSynopsisLabel;

@FXML 
private Label tDirectorLabel;

@FXML 
private Label tPhaseLabel;

@FXML 
private Label tAgeRatingLabel;

@FXML 
private Label tAverageRuntimeLabel;

@FXML
private Label mTrailerlinkLabel;

@FXML
private Label tTrailerLinkLabel;

@FXML
private Label tPosterLabel;

@FXML
private Label tChronologicalOrderLabel;

@FXML 
private Label userLabel;

// ============================ BUTTONS ===============================

@FXML 
private Button backButton;

@FXML 
private Button userButton;

@FXML 
private Button contentButton;

@FXML 
private Button castButton;

@FXML 
private Button watchlistButton;

@FXML 
private Button ratingButton;

@FXML 
private Button reviewButton;

@FXML 
private Button likeButton;

@FXML 
private Button dislikeButton;

@FXML 
private Button createButton;

@FXML 
private Button deleteButton;

@FXML 
private Button updateButton;

// ============================== TABS ================================

@FXML 
private TabPane contentTabPane;

@FXML 
private Tab movieTab;

@FXML 
private Tab tvshowTab;

    private Stage stage;

    private Scene scene;

    private Parent root;

    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        loadPhaseOptions();
        loadAgeRatingOptions();
        displayContent();
        loadDirectorOptions();

        List<String> allPhases = Arrays.asList("1", "2", "3", "4", "5", "6");
            tPhaseTextfield.setItems(FXCollections.observableArrayList(allPhases));

        List<String> allAgeRatings = Arrays.asList("G", "PG", "PG-13", "R", "NC-17");
            tAgeRatingTextfield.setItems(FXCollections.observableArrayList(allAgeRatings));

        ObservableList<String> directors = FXCollections.observableArrayList();
        try {
            ResultSet rs = DatabaseHandler.getAllDirectors();
            while (rs.next()) {
                directors.add(rs.getString("directorName"));
            }
            tDirectorTextfield.setItems(directors);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        contentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            mTitleTextfield.setText(newSelection.getContentTitle());
            mRuntimeTextfield.setText(newSelection.getContentRuntime());
            mSynopsisTextfield.setText(newSelection.getContentSynopsis());
            mDirectorCombobox.setValue(newSelection.getContentDirector());
            mPhaseCombobox.setValue(String.valueOf(newSelection.getContentPhase()));
            mAgeRatingCombobox.setValue(newSelection.getContentAgeRating());
            mChronologicalOrderTextfield.setText(String.valueOf(newSelection.getContentChronologicalOrder()));
            mPosterTextfield.setText(newSelection.getContentPoster());
            mTrailerLinkTextField.setText(newSelection.getContentTrailer());
            mReleaseDatePicker.setValue(newSelection.getContentReleaseDate());

            tShowTitleTextField.setText(newSelection.getContentTitle());
            tAverageRuntimeTextField.setText(newSelection.getContentRuntime());
            tSynopsisTextfield.setText(newSelection.getContentSynopsis());
            tDirectorTextfield.setValue(newSelection.getContentDirector());
            tPhaseTextfield.setValue(String.valueOf(newSelection.getContentPhase()));
            tAgeRatingTextfield.setValue(newSelection.getContentAgeRating());
            tSeasonTextField.setText(newSelection.getContentSeason() != null ? String.valueOf(newSelection.getContentSeason()) : "");
            tEpisodeTextfield.setText(newSelection.getContentEpisode() != null ? String.valueOf(newSelection.getContentEpisode()) : "");
            tPosterTextField.setText(newSelection.getContentPoster());
            tTrailerLinkTextField.setText(newSelection.getContentTrailer());
            tReleaseDatePicker.setValue(newSelection.getContentReleaseDate());
            tChronologicalOrderTextField.setText(String.valueOf(newSelection.getContentChronologicalOrder()));
        }
    });
}

    private void loadPhaseOptions() {
        List<String> allPhases = Arrays.asList("1", "2", "3", "4", "5", "6");
        mPhaseCombobox.setItems(FXCollections.observableArrayList(allPhases));
        
}

    private void loadAgeRatingOptions() {
        List<String> allAgeRatings = Arrays.asList("G", "PG", "PG-13", "R", "NC-17");
        mAgeRatingCombobox.setItems(FXCollections.observableArrayList(allAgeRatings));

}

private void loadDirectorOptions() {
    ObservableList<String> directors = FXCollections.observableArrayList();
    try {
        ResultSet rs = DatabaseHandler.getAllDirectors();
        while (rs.next()) {
            directors.add(rs.getString("directorName"));
        }
        mDirectorCombobox.setItems(directors);
        tDirectorTextfield.setItems(directors); // <-- Add this line
    } catch (SQLException e) {
        e.printStackTrace();
    }
    contentTable.setEditable(true);
}

    @FXML
    public void initializeCol() {
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
    
}

    private void displayContent() {
        try {
            ResultSet rs = DatabaseHandler.getContent();
            ObservableList<Content> contentList = FXCollections.observableArrayList();
            while (rs.next()) {
                int contentID = rs.getInt("contentID");
                String title = rs.getString("contentTitle");
                String runtime = rs.getString("contentRuntime");
                Integer season = rs.getInt("contentSeason");
                Integer episode = rs.getInt("contentEpisode");
                java.sql.Date sqlDate = rs.getDate("contentReleaseDate");
                LocalDate releaseDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
                String synopsis = rs.getString("contentSynopsis");
                String director = rs.getString("contentDirector");
                int phase = rs.getInt("contentPhase");
                String ageRating = rs.getString("contentAgeRating");
                int chronologicalOrder = rs.getInt("contentChronologicalOrder");
                String poster = rs.getString("contentPoster");
                String trailer = rs.getString("contentTrailer");

                Content content = new Content(contentID, title, runtime, season, episode, releaseDate, synopsis, director, phase, ageRating, chronologicalOrder, poster, trailer);
                contentList.add(content);
            }
            contentTable.setItems(contentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {

        mTitleTextfield.clear();
        mRuntimeTextfield.clear();
        mPosterTextfield.clear();
        mChronologicalOrderTextfield.clear();
        mSynopsisTextfield.clear();
        mReleaseDatePicker.setValue(null);
        mDirectorCombobox.getSelectionModel().clearSelection();
        mPhaseCombobox.getSelectionModel().clearSelection();
        mAgeRatingCombobox.getSelectionModel().clearSelection();
        mTrailerLinkTextField.clear();


        tShowTitleTextField.clear();
        tSeasonTextField.clear();
        tEpisodeTextfield.clear();
        tSynopsisTextfield.clear();
        tReleaseDatePicker.setValue(null);
        tDirectorTextfield.getSelectionModel().clearSelection();
        tPhaseTextfield.getSelectionModel().clearSelection();
        tAgeRatingTextfield.getSelectionModel().clearSelection();
        tAverageRuntimeTextField.clear();
        tTrailerLinkTextField.clear();
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
        String title, runtime, synopsis, director, ageRating, poster, trailer;
        Integer season = null, episode = null, chronologicalOrder = null, phase = null;
        LocalDate releaseDate;

        if (contentTabPane.getSelectionModel().getSelectedItem() == movieTab) {

            title = mTitleTextfield.getText();
            runtime = mRuntimeTextfield.getText();
            synopsis = mSynopsisTextfield.getText();
            director = mDirectorCombobox.getValue();
            phase = mPhaseCombobox.getValue() != null ? Integer.parseInt(mPhaseCombobox.getValue()) : null;
            ageRating = mAgeRatingCombobox.getValue();
            chronologicalOrder = mChronologicalOrderTextfield.getText().isEmpty() ? null : Integer.parseInt(mChronologicalOrderTextfield.getText());
            poster = mPosterTextfield.getText();
            trailer = mTrailerLinkTextField.getText();
            releaseDate = mReleaseDatePicker.getValue();

        } else {

            title = tShowTitleTextField.getText();
            runtime = tAverageRuntimeTextField.getText();
            synopsis = tSynopsisTextfield.getText();
            director = tDirectorTextfield.getValue();
            phase = tPhaseTextfield.getValue() != null ? Integer.parseInt(tPhaseTextfield.getValue()) : null;
            ageRating = tAgeRatingTextfield.getValue();
            season = tSeasonTextField.getText().isEmpty() ? null : Integer.parseInt(tSeasonTextField.getText());
            episode = tEpisodeTextfield.getText().isEmpty() ? null : Integer.parseInt(tEpisodeTextfield.getText());
            poster = tPosterTextField.getText();
            trailer = tTrailerLinkTextField.getText();
            releaseDate = tReleaseDatePicker.getValue();
            chronologicalOrder = tChronologicalOrderTextField.getText().isEmpty() ? null : Integer.parseInt(tChronologicalOrderTextField.getText());
        }

        Content content = new Content(
            0, title, runtime, season, episode, releaseDate,
            synopsis, director, phase != null ? phase : 0, ageRating, 
            chronologicalOrder != null ? chronologicalOrder : 0, poster, trailer
        );

        DatabaseHandler.createContent(content);

        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Content Added");
            alert.setHeaderText(null);
            alert.setContentText("Content has been created successfully.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();

        displayContent();
        
        clearForm();
    }

    @FXML
    private void deleteContentHandler(ActionEvent event) {
        Content selectedContent = contentTable.getSelectionModel().getSelectedItem();
        if (selectedContent == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a content item to delete.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }

        boolean success = DatabaseHandler.deleteContent(selectedContent);
        if (success) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Deleted");
            alert.setHeaderText(null);
            alert.setContentText("Content has been deleted successfully.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to delete content.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
            displayContent();
            clearForm();
    }

    @FXML
    private void updateContentHandler(ActionEvent event) {
        Content selectedContent = contentTable.getSelectionModel().getSelectedItem();
        if (selectedContent == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a content item to update.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }

        String title, runtime, synopsis, director, ageRating, poster, trailer;
        Integer season = null, episode = null, chronologicalOrder = null, phase = null;
        LocalDate releaseDate;

        if (contentTabPane.getSelectionModel().getSelectedItem() == movieTab) {
            title = mTitleTextfield.getText();
            runtime = mRuntimeTextfield.getText();
            synopsis = mSynopsisTextfield.getText();
            director = mDirectorCombobox.getValue();
            phase = mPhaseCombobox.getValue() != null ? Integer.parseInt(mPhaseCombobox.getValue()) : null;
            ageRating = mAgeRatingCombobox.getValue();
            chronologicalOrder = mChronologicalOrderTextfield.getText().isEmpty() ? null : Integer.parseInt(mChronologicalOrderTextfield.getText());
            poster = mPosterTextfield.getText();
            trailer = mTrailerLinkTextField.getText();
            releaseDate = mReleaseDatePicker.getValue();
        } else {
            title = tShowTitleTextField.getText();
            runtime = tAverageRuntimeTextField.getText();
            synopsis = tSynopsisTextfield.getText();
            director = tDirectorTextfield.getValue();
            phase = tPhaseTextfield.getValue() != null ? Integer.parseInt(tPhaseTextfield.getValue()) : null;
            ageRating = tAgeRatingTextfield.getValue();
            season = tSeasonTextField.getText().isEmpty() ? null : Integer.parseInt(tSeasonTextField.getText());
            episode = tEpisodeTextfield.getText().isEmpty() ? null : Integer.parseInt(tEpisodeTextfield.getText());
            poster = tPosterTextField.getText();
            trailer = tTrailerLinkTextField.getText();
            releaseDate = tReleaseDatePicker.getValue();
            chronologicalOrder = tChronologicalOrderTextField.getText().isEmpty() ? null : Integer.parseInt(tChronologicalOrderTextField.getText());
        }

        Content updatedContent = new Content(
            selectedContent.getContentID(),
            title, runtime, season, episode, releaseDate,
            synopsis, director, phase != null ? phase : 0, ageRating,
            chronologicalOrder != null ? chronologicalOrder : 0, poster, trailer
        );

        boolean success = DatabaseHandler.updateContent(updatedContent);
        if (success) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Updated");
            alert.setHeaderText(null);
            alert.setContentText("Content has been updated successfully.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            displayContent();
            clearForm();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update content.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
    }

}