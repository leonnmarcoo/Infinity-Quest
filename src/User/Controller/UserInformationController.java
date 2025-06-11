package User.Controller;

import Objects.Content;
import Objects.Director;
import Objects.User;
import Objects.Cast;
import User.Session.SessionManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class UserInformationController implements Initializable{

    @FXML
    private Button backButton;

    // ============================== INFORMATION ================================

    @FXML
    private ImageView contentImageView;

    @FXML
    private Hyperlink trailerHyperlink;

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
    private Label seasonLabel;

    @FXML
    private Label episodeLabel;

    @FXML
    private Label synopsisLabel;

    @FXML
    private Button watchlistButton;

    @FXML
    private Button watchButton;

    @FXML
    private BarChart<String, Number> ratingBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ListView<String> castListView;

    @FXML
    private ListView<String> reviewListView;

    @FXML
    private Button profileButton;

    @FXML
    private Button searcButton;

    @FXML
    private Button homeButton;
    
    private Content content;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username;
    private String previousFXMLPath;
    private String searchTerm; // For preserving search state
    private String filterType; // For preserving filter state
    private String filterTitle; // For UserFilterController
    private Integer phaseNumber; // For UserFilterController  
    private String sortType; // For UserFilterController
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    
    public void setContent(Content content) {
        this.content = content;
        displayContentDetails();
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPreviousFXMLPath(String previousFXMLPath) {
        this.previousFXMLPath = previousFXMLPath;
    }
    
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    
    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
    
    public void setFilterData(String filterTitle, Integer phaseNumber, String sortType) {
        this.filterTitle = filterTitle;
        this.phaseNumber = phaseNumber;
        this.sortType = sortType;
    }
    
    private void displayContentDetails() {
        if (content == null) {
            return;
        }
        
        titleLabel.setText(content.getContentTitle());
        
        try {
            ResultSet rs = DatabaseHandler.getDirectorById(content.getDirectorID());
            if (rs.next()) {
                directorLabel.setText(rs.getString("directorName"));
            } else {
                directorLabel.setText("Unknown Director");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            directorLabel.setText("Unknown Director");
        }
        
        if (content.getContentReleaseDate() != null) {
            releaseDateLabel.setText(content.getContentReleaseDate().toString());
        } else {
            releaseDateLabel.setText("Unknown Release Date");
        }
        
        runtimeLabel.setText(content.getContentRuntime());
        ageRatingLabel.setText(content.getContentAgeRating());
        synopsisLabel.setText(content.getContentSynopsis());
        
        if (content.getContentSeason() != null) {
            seasonLabel.setText("Season: " + content.getContentSeason());
            seasonLabel.setVisible(true);
        } else {
            seasonLabel.setVisible(false);
        }
        
        if (content.getContentEpisode() != null) {
            episodeLabel.setText("Episode: " + content.getContentEpisode());
            episodeLabel.setVisible(true);
        } else {
            episodeLabel.setVisible(false);
        }
        
        String trailerURL = content.getContentTrailer();
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
            String posterPath = content.getContentPoster();
            if (posterPath != null && !posterPath.isEmpty()) {
                File file = new File(posterPath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    contentImageView.setImage(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading poster image: " + e.getMessage());
        }
        
        displayRatingBarChart();
        displayCastList();
        displayReviewList();
    }
    
    private void displayRatingBarChart() {
        if (content == null || ratingBarChart == null) {
            return;
        }
        
        ratingBarChart.getData().clear();
        
        Map<Integer, Integer> ratingDistribution = DatabaseHandler.getContentRatingDistribution(content.getContentID());
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        
        for (int rating = 1; rating <= 5; rating++) {
            int count = ratingDistribution.getOrDefault(rating, 0);
            series.getData().add(new XYChart.Data<>(String.valueOf(rating), count));
        }
        
        ratingBarChart.getData().add(series);
        
        if (xAxis != null) {
            xAxis.setLabel(null);
        }
        if (yAxis != null) {
            yAxis.setLabel(null);
        }
        
        for (XYChart.Data<String, Number> data : series.getData()) {
            Node node = data.getNode();
            if (node != null) {
                node.setStyle("-fx-bar-fill: #CE2431; -fx-background-radius: 8 8 0 0;");
                
                Tooltip tooltip = new Tooltip(
                    "Rating: " + data.getXValue() + " stars\n" +
                    "Count: " + data.getYValue() + " users"
                );
                Tooltip.install(node, tooltip);
            }
        }
    }
    
    private void displayCastList() {
        if (content == null || castListView == null) {
            return;
        }
        
        castListView.getItems().clear();
        
        List<Cast> castMembers = DatabaseHandler.getCastByContentID(content.getContentID());
        
        ObservableList<String> castItems = FXCollections.observableArrayList();
        for (Cast cast : castMembers) {
            String castEntry = cast.getActorName() + " as " + cast.getRoleName();
            castItems.add(castEntry);
        }
        
        castListView.setItems(castItems);
    }
    
    private void displayReviewList() {
        if (content == null || reviewListView == null) {
            return;
        }
        
        reviewListView.getItems().clear();
        
        List<Object[]> reviews = DatabaseHandler.getReviewsByContentID(content.getContentID());
        
        ObservableList<String> reviewItems = FXCollections.observableArrayList();
        for (Object[] review : reviews) {
            String userName = (String) review[0];
            String reviewText = (String) review[1];
            
            String reviewEntry = userName + ":\n\n" + reviewText;
            reviewItems.add(reviewEntry);
        }
        
        reviewListView.setItems(reviewItems);
        
        // If there are no reviews, show a message
        if (reviews.isEmpty()) {
            reviewItems.add("No reviews yet for this content.");
            reviewListView.setItems(reviewItems);
        }
    }
    
    @FXML
    private void backButtonHandler(ActionEvent event) {
        try {
            // If no previous FXML path is set, default to UserHome.fxml
            String fxmlPath = (previousFXMLPath != null && !previousFXMLPath.isEmpty()) 
                ? previousFXMLPath 
                : "/User/FXML/UserHome.fxml";
                
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            root = loader.load();
            
            // Restore state based on the controller type
            if (fxmlPath.equals("/User/FXML/UserSearch.fxml") && searchTerm != null) {
                UserSearchController controller = loader.getController();
                controller.restoreSearchState(searchTerm);
            } else if (fxmlPath.equals("/User/FXML/UserFilter.fxml")) {
                UserFilterController controller = loader.getController();
                if (phaseNumber != null && phaseNumber > 0) {
                    controller.setFilterData(filterTitle, phaseNumber, username);
                } else if (sortType != null) {
                    controller.setSortData(filterTitle, sortType, username);
                }
            } else if (fxmlPath.equals("/User/FXML/UserProfileFilter.fxml") && filterType != null) {
                UserProfileFilterController controller = loader.getController();
                switch (filterType) {
                    case "watched": controller.setWatchedFilter(); break;
                    case "watchlist": controller.setWatchlistFilter(); break;
                    case "likes": controller.setLikesFilter(); break;
                    case "dislikes": controller.setDislikesFilter(); break;
                }
            }
            
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Could not load previous screen");
        }
    }
    
    @FXML
    private void watchlistButtonHandler(ActionEvent event) {
        if (content == null) {
            showAlert(Alert.AlertType.ERROR, "No content selected");
            return;
        }
        
        // String username = ((Node) event.getSource()).getScene().getWindow().getUserData().toString();
        // Again naka session na tyo.

        // New code pra makuha ung current user session.
        User user = SessionManager.getCurrentUser();
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "No user session found.");
            return;
        }
        String username = user.getUserName();

        
        // Check if content is already in watchlist
        if (DatabaseHandler.isContentInWatchlist(username, content.getContentID())) {
            showAlert(Alert.AlertType.ERROR, "\"" + content.getContentTitle() + "\" is already in your watchlist!");
            return;
        }
        
        // Check if content has already been watched
        if (DatabaseHandler.isContentWatched(username, content.getContentID())) {
            showAlert(Alert.AlertType.ERROR, "\"" + content.getContentTitle() + "\" has already been watched. No need to add to watchlist!");
            return;
        }
        
        if (DatabaseHandler.addToWatchlist(username, content.getContentID())) {
            showAlert(Alert.AlertType.INFORMATION, "Successfully added \"" + content.getContentTitle() + "\" to your watchlist!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to add to watchlist. Please try again.");
        }
    }
    
    // @FXML
    // private void watchButtonHandler(ActionEvent event) {
    //     if (content == null) {
    //         showAlert(Alert.AlertType.ERROR, "No content selected");
    //         return;
    //     }

    //     // Check if content has already been watched
    //     if (DatabaseHandler.isContentWatched(username, content.getContentID())) {
    //         showAlert(Alert.AlertType.ERROR, "\"" + content.getContentTitle() + "\" has already been watched!");
    //         return;
    //     }
        
    //     try {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserLogReview.fxml"));
    //         Parent root = loader.load();
            
    //         UserLogReviewController controller = loader.getController();
    //         controller.setContent(content);
            
    //         // Get username from window user data
    //         String username = ((Node) event.getSource()).getScene().getWindow().getUserData().toString();
    //         controller.setUsername(username);
            
    //         // Pass the content title to be displayed
    //         controller.setTitle(content.getContentTitle());
            
    //         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    //         Scene scene = new Scene(root);
    //         stage.setScene(scene);
    //         stage.show();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         showAlert(Alert.AlertType.ERROR, "Could not load review screen");
    //     }
    // }


    //  Updated code sa watchButtonHandler, nka session narin ung user, so no need na i-pass ung username sa controller.
    @FXML
    private void watchButtonHandler(ActionEvent event) {
        if (content == null) {
            showAlert(Alert.AlertType.ERROR, "No content selected");
            return;
        }

        User user = SessionManager.getCurrentUser();
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "No user session found.");
            return;
        }
        String username = user.getUserName();

        // Check if content has already been watched
        if (DatabaseHandler.isContentWatched(username, content.getContentID())) {
            showAlert(Alert.AlertType.ERROR, "\"" + content.getContentTitle() + "\" has already been watched!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserLogReview.fxml"));
            Parent root = loader.load();

            UserLogReviewController controller = loader.getController();
            controller.setContent(content);
            controller.setUsername(username);
            controller.setTitle(content.getContentTitle());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Could not load review screen");
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
    
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Infinity Quest");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
