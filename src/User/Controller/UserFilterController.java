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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserFilterController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Label filterLabel;

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private GridPane contentGridPane;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button homeButton;

    private String filterTitle;
    private int phaseNumber;
    private String username;
    private List<Content> contentList = new ArrayList<>();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(this::backButtonHandler);
        
        if (filterTitle != null) {
            if (phaseNumber > 0) {
                loadFilteredContent();
            } else if (sortType != null) {
                loadAllContent();
            }
        }
    }
    
    public void setFilterData(String filterTitle, int phaseNumber, String username) {
        this.filterTitle = filterTitle;
        this.phaseNumber = phaseNumber;
        this.username = username;
        
        filterLabel.setText(filterTitle);
        
        loadFilteredContent();
    }
    
    private void loadFilteredContent() {
        contentList.clear();
        ResultSet rs = DatabaseHandler.getContent();
        
        try {
            while (rs.next()) {
                int phase = rs.getInt("contentPhase");
                
                if (phase == phaseNumber) {
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
            }
            
            displayFilteredContent();
            
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading content");
        }
    }
    
    private void displayFilteredContent() {
        contentGridPane.getChildren().clear();
        
        contentGridPane.getRowConstraints().clear();
        
        contentGridPane.setVgap(12);
        contentGridPane.setHgap(12);
        
        int columnIndex = 0;
        int rowIndex = 0;
        int maxColumns = 3;
        
        for (Content content : contentList) {
            try {
                ImageView posterView = new ImageView();
                String posterPath = content.getContentPoster();
                
                if (posterPath != null && !posterPath.isEmpty()) {
                    File file = new File(posterPath);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        posterView.setImage(image);
                        posterView.setFitHeight(188);
                        posterView.setFitWidth(125);
                        posterView.setPreserveRatio(true);
                        
                        VBox posterContainer = new VBox(12);
                        posterContainer.setAlignment(javafx.geometry.Pos.CENTER);
                        posterContainer.getChildren().add(posterView);
                        
                        posterView.setOnMouseClicked(event -> showContentDetails(content));
                        
                        contentGridPane.add(posterContainer, columnIndex, rowIndex);
                        
                        columnIndex++;
                        if (columnIndex >= maxColumns) {
                            columnIndex = 0;
                            rowIndex++;
                            
                            javafx.scene.layout.RowConstraints rowConstraint = new javafx.scene.layout.RowConstraints();
                            rowConstraint.setMinHeight(188);
                            contentGridPane.getRowConstraints().add(rowConstraint);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void showContentDetails(Content content) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserInformation.fxml"));
            Parent root = loader.load();
            
            UserInformationController controller = loader.getController();
            controller.setContent(content);
            controller.setUsername(username);
            
            Stage stage = (Stage) filterLabel.getScene().getWindow();
            stage.setUserData(username);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading content details screen");
        }
    }
    
    private void backButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserHome.fxml"));
            Parent root = loader.load();
            
            UserHomeController controller = loader.getController();
            controller.setUsername(username);
            controller.initializeUserHome();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading home screen");
        }
    }
    
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Infinity Quest");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private String sortType;
    
    public void setSortData(String filterTitle, String sortType, String username) {
        this.filterTitle = filterTitle;
        this.sortType = sortType;
        this.username = username;
        this.phaseNumber = -1;
        
        filterLabel.setText(filterTitle);
        
        loadAllContent();
    }
    
    private void loadAllContent() {
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
            
            if (sortType != null) {
                if (sortType.equals("release_date")) {
                    contentList.sort(Comparator.comparing(Content::getContentReleaseDate, 
                        Comparator.nullsLast(Comparator.naturalOrder())));
                } else if (sortType.equals("chronological")) {
                    contentList.sort(Comparator.comparing(Content::getContentChronologicalOrder));
                }
            }
            
            displayFilteredContent();
            
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading content");
        }
    }

    @FXML
    private void profileButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserProfile.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading profile screen");
        }
    }

    @FXML
    private void searchButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserSearch.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading search screen");
        }
    }

    @FXML
    private void homeButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserHome.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading home screen");
        }
    }
}
