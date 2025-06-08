package User.Controller;

import Objects.Content;
import Objects.Director;
import Database.DatabaseHandler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserHomeController implements Initializable {

    @FXML
    private Label welcomeLabel;

    // ============================== LATEST RELEASE ================================

    @FXML
    private ImageView latestReleaseImageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label directorLabel;

    @FXML
    private Label releaseDateLabel;

    @FXML
    private Label runtimeLabel;

    @FXML
    private Label ageRatingLabel;

    @FXML
    private Label synopsisLabel; 

    @FXML
    private Hyperlink trailerHyperlink;

    // ================================ PHASE ================================

    @FXML
    private Button phaseOneButton;

    @FXML
    private Button phaseTwoButton;

    @FXML
    private Button phaseThreeButton;

    @FXML
    private Button phaseFourButton;

    @FXML
    private Button phaseFiveButton;

    @FXML
    private Button phaseSixButton;

    // ================================ RELEASE DATE ORDER ================================

    @FXML
    private ScrollPane releaseDateScrollPane;

    @FXML
    private HBox releaseDateHBox;

    @FXML
    private Button releaseDateButton;

    // ================================ TIMELINE ORDER ================================

    @FXML
    private ScrollPane timelineScrollPane;

    @FXML 
    private HBox timelineHBox;

    @FXML
    private Button timelineButton;
    
    private String username;
    private List<Content> contentList = new ArrayList<>();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (username != null) {
            initializeUserHome();
        }
    }
    
    public void initializeUserHome() {
        welcomeLabel.setText("On your left, " + username);
        loadContent();
        displayLatestRelease();
        setupReleaseDateOrder();
        setupTimelineOrder();
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    private void loadContent() {
        contentList.clear();
        ResultSet rs = DatabaseHandler.getContent();
        try {
            while (rs.next()) {
                String releaseDateStr = rs.getString("contentReleaseDate");
                LocalDate releaseDate = null;
                if (releaseDateStr != null && !releaseDateStr.isEmpty()) {
                    try {
                        releaseDate = LocalDate.parse(releaseDateStr);
                    } catch (Exception e) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            releaseDate = LocalDate.parse(releaseDateStr, formatter);
                        } catch (Exception ex) {
                            System.out.println("Could not parse date: " + releaseDateStr);
                        }
                    }
                }
                
                Content content = new Content(
                    rs.getInt("contentID"),
                    rs.getString("contentTitle"),
                    rs.getString("contentRuntime"),
                    rs.getObject("contentSeason", Integer.class),
                    rs.getObject("contentEpisode", Integer.class),
                    releaseDate,
                    rs.getString("contentSynopsis"),
                    rs.getInt("directorID"),
                    rs.getInt("contentPhase"),
                    rs.getString("contentAgeRating"),
                    rs.getInt("contentChronologicalOrder"),
                    rs.getString("contentPoster"),
                    rs.getString("contentTrailer")
                );
                contentList.add(content);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading content");
        }
    }
    
    // ================================ DISPLAY LATEST RELEASE ================================

    private void displayLatestRelease() {
        if (contentList.isEmpty()) {
            return;
        }
        
        Content latestContent = contentList.stream()
                .filter(c -> c.getContentReleaseDate() != null)
                .max(Comparator.comparing(Content::getContentReleaseDate))
                .orElse(contentList.get(0));
        
        titleLabel.setText(latestContent.getContentTitle());
        
        try {
            ResultSet rs = DatabaseHandler.getDirectorById(latestContent.getDirectorID());
            if (rs.next()) {
                directorLabel.setText(rs.getString("directorName"));
            } else {
                directorLabel.setText("Unknown Director");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            directorLabel.setText("Unknown Director");
        }
        
        if (latestContent.getContentReleaseDate() != null) {
            releaseDateLabel.setText(latestContent.getContentReleaseDate().toString());
        } else {
            releaseDateLabel.setText("Unknown Release Date");
        }
        
        runtimeLabel.setText(latestContent.getContentRuntime());
        ageRatingLabel.setText(latestContent.getContentAgeRating());
        synopsisLabel.setText(latestContent.getContentSynopsis());
        
        String trailerURL = latestContent.getContentTrailer();
        if (trailerURL != null && !trailerURL.isEmpty()) {
            trailerHyperlink.setOnAction(event -> {
                try {
                    Desktop.getDesktop().browse(new URI(trailerURL));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Could not open trailer URL: " + trailerURL);
                }
            });
            trailerHyperlink.setVisible(true);
        } else {
            trailerHyperlink.setVisible(false);
        }
        
        try {
            String posterPath = latestContent.getContentPoster();
            if (posterPath != null && !posterPath.isEmpty()) {
                File file = new File(posterPath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    latestReleaseImageView.setImage(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading poster image: " + e.getMessage());
        }
    }
    
    // ================================ DISPLAY RELEASE DATE ORDER ================================

    private void setupReleaseDateOrder() {
        if (contentList.isEmpty()) {
            return;
        }
        
        if (releaseDateHBox == null) {
            releaseDateHBox = new HBox(10);
            releaseDateHBox.setStyle("-fx-background-color: #141414;");
        } else {
            releaseDateHBox.getChildren().clear();
        }
        
        contentList.stream()
            .filter(c -> c.getContentReleaseDate() != null)
            .sorted(Comparator.comparing(Content::getContentReleaseDate))
            .limit(10)
            .forEach(content -> {
                try {
                    ImageView posterView = new ImageView();
                    String posterPath = content.getContentPoster();
                    
                    if (posterPath != null && !posterPath.isEmpty()) {
                        File file = new File(posterPath);
                        if (file.exists()) {
                            Image image = new Image(file.toURI().toString());
                            posterView.setImage(image);
                            posterView.setFitHeight(150);
                            posterView.setFitWidth(100);
                            posterView.setPreserveRatio(true);
                            
                            posterView.setOnMouseClicked(event -> showContentDetails(content));
                            
                            releaseDateHBox.getChildren().add(posterView);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        
        releaseDateScrollPane.setContent(releaseDateHBox);
        releaseDateScrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        releaseDateScrollPane.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
    }

    // ================================ DISPLAY TIMELINE ORDER ================================

    private void setupTimelineOrder() {
        if (contentList.isEmpty()) {
            return;
        }
        
        if (timelineHBox == null) {
            timelineHBox = new HBox(10);
            timelineHBox.setStyle("-fx-background-color: #141414;");
        } else {
            timelineHBox.getChildren().clear();
        }
        
        contentList.stream()
            .sorted(Comparator.comparing(Content::getContentChronologicalOrder))
            .limit(10)
            .forEach(content -> {
                try {
                    ImageView posterView = new ImageView();
                    String posterPath = content.getContentPoster();
                    
                    if (posterPath != null && !posterPath.isEmpty()) {
                        File file = new File(posterPath);
                        if (file.exists()) {
                            Image image = new Image(file.toURI().toString());
                            posterView.setImage(image);
                            posterView.setFitHeight(150);
                            posterView.setFitWidth(100);
                            posterView.setPreserveRatio(true);
                            
                            posterView.setOnMouseClicked(event -> showContentDetails(content));
                            
                            timelineHBox.getChildren().add(posterView);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        
        timelineScrollPane.setContent(timelineHBox);
        timelineScrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        timelineScrollPane.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
    }

    // ================================ METHOD FOR SHOWING THE CONTENT ONCE POSTER IS CLICKED ================================

    private void showContentDetails(Content content) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserInformation.fxml"));
            Parent root = loader.load();
            
            UserInformationController controller = loader.getController();
            controller.setContent(content);
            controller.setUsername(username);
            
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setUserData(username);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading content details screen");
        }
    }
    
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Infinity Quest");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ================================ PHASES BUTTON HANDLER ================================

    @FXML
    private void phaseOneButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserFilter.fxml"));
            Parent root = loader.load();
            
            UserFilterController controller = loader.getController();
            controller.setFilterData("Phase 1", 1, username);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading filter screen");
        }
    }

    @FXML
    private void phaseTwoButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserFilter.fxml"));
            Parent root = loader.load();

            UserFilterController controller = loader.getController();
            controller.setFilterData("Phase 2", 2, username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading filter screen");
        }
    }

    @FXML
    private void phaseThreeButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserFilter.fxml"));
            Parent root = loader.load();

            UserFilterController controller = loader.getController();
            controller.setFilterData("Phase 3", 3, username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading filter screen");
        }
    }

    @FXML
    private void phaseFourButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserFilter.fxml"));
            Parent root = loader.load();

            UserFilterController controller = loader.getController();
            controller.setFilterData("Phase 4", 4, username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading filter screen");
        }
    }

    @FXML
    private void phaseFiveButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserFilter.fxml"));
            Parent root = loader.load();

            UserFilterController controller = loader.getController();
            controller.setFilterData("Phase 5", 5, username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading filter screen");
        }
    }

    @FXML
    private void phaseSixButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserFilter.fxml"));
            Parent root = loader.load();

            UserFilterController controller = loader.getController();
            controller.setFilterData("Phase 6", 6, username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading filter screen");
        }
    }
    
    // ================================ RELEASE DATE BUTTON HANDLER ================================

    @FXML
    private void releaseDateButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserFilter.fxml"));
            Parent root = loader.load();
            
            UserFilterController controller = loader.getController();
            controller.setSortData("Release Date Order", "release_date", username);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading filter screen");
        }
    }

    // ================================ TIMELINE BUTTON HANDLER ================================

    @FXML
    private void timelineButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserFilter.fxml"));
            Parent root = loader.load();
            
            UserFilterController controller = loader.getController();
            controller.setSortData("Timeline Order", "chronological", username);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading filter screen");
        }
    }
}
