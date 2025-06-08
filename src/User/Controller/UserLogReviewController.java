package User.Controller;

import Objects.Content;
import Objects.Review;
import Objects.Like;
import Objects.Dislike;
import Objects.Rating;
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

public class UserLogReviewController implements Initializable{

    // ============================== FXML ================================
    @FXML
    private Label titleLabel;

    @FXML
    private Label reviewLabel;

    @FXML
    private Label headerLabel;

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private Label likeDislikeLabel;

    @FXML
    private Button dislikeButton;

    @FXML
    private Button likeButton;

    @FXML
    private Label ratingLabel;

    @FXML
    private Button oneStarButton;

    @FXML
    private Button twoStarButton;

    @FXML
    private Button threeStarButton;

    @FXML
    private Button fourStarButton;

    @FXML
    private Button fiveStarButton;

    @FXML
    private Button logReviewButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button backButton;
    
    // ============================== Variables ================================
    private Content content;
    private String username;
    private String title;
    private Integer rating = null;
    private Boolean liked = null;
    private Boolean disliked = null;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    // ============================== Methods ================================
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize buttons
        setupButtons();
    }
    
    private void setupButtons() {
        // Set up rating buttons
        oneStarButton.setOnAction(this::handleRatingButton);
        twoStarButton.setOnAction(this::handleRatingButton);
        threeStarButton.setOnAction(this::handleRatingButton);
        fourStarButton.setOnAction(this::handleRatingButton);
        fiveStarButton.setOnAction(this::handleRatingButton);
        
        // Set up like/dislike buttons
        likeButton.setOnAction(this::handleLikeButton);
        dislikeButton.setOnAction(this::handleDislikeButton);

        // Set up log review button
        logReviewButton.setOnAction(this::logReviewButtonHandler);

        // Set up cancel and back buttons
        cancelButton.setOnAction(this::cancelButtonHandler);
        backButton.setOnAction(this::backButtonHandler);
    }
    
    public void setContent(Content content) {
        this.content = content;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setTitle(String title) {
        this.title = title;
        titleLabel.setText(title);
    }
    
    private void handleRatingButton(ActionEvent event) {
        // Reset styling for all buttons
        resetRatingButtonStyles();
        
        // Get the button that was clicked
        Button clickedButton = (Button) event.getSource();
        
        // Set the selected style
        clickedButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 12;");
        
        // Determine the rating based on the button clicked
        if (clickedButton == oneStarButton) {
            rating = 1;
        } else if (clickedButton == twoStarButton) {
            rating = 2;
        } else if (clickedButton == threeStarButton) {
            rating = 3;
        } else if (clickedButton == fourStarButton) {
            rating = 4;
        } else if (clickedButton == fiveStarButton) {
            rating = 5;
        }
    }
    
    private void resetRatingButtonStyles() {
        oneStarButton.setStyle("-fx-background-color: Transparent; -fx-background-radius: 12;");
        twoStarButton.setStyle("-fx-background-color: Transparent; -fx-background-radius: 12;");
        threeStarButton.setStyle("-fx-background-color: Transparent; -fx-background-radius: 12;");
        fourStarButton.setStyle("-fx-background-color: Transparent; -fx-background-radius: 12;");
        fiveStarButton.setStyle("-fx-background-color: Transparent; -fx-background-radius: 12;");
    }
    
    private void handleLikeButton(ActionEvent event) {
        if (liked != null && liked) {
            // Already liked, so remove like
            liked = null;
            likeButton.setStyle("");
        } else {
            // Set like
            liked = true;
            disliked = null;
            likeButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 12;");
            dislikeButton.setStyle("-fx-background-color: #343434; -fx-background-radius: 12;");
        }
    }
    
    private void handleDislikeButton(ActionEvent event) {
        if (disliked != null && disliked) {
            // Already disliked, so remove dislike
            disliked = null;
            dislikeButton.setStyle("");
        } else {
            // Set dislike
            disliked = true;
            liked = null;
            dislikeButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 12;");
            likeButton.setStyle("-fx-background-color: #343434; -fx-background-radius: 12;");
        }
    }
    
    @FXML
    public void logReviewButtonHandler(ActionEvent event) {
        if (content == null || username == null) {
            showAlert(Alert.AlertType.ERROR, "Error: Missing content or user information");
            return;
        }
        
        boolean success = true;
        
        // Get the review text (if any)
        String reviewText = reviewTextArea.getText().trim();
        if (!reviewText.isEmpty()) {
            // Add review to database
            success = success && DatabaseHandler.addReview(username, content.getContentID(), reviewText);
        }
        
        // Add rating if selected
        if (rating != null) {
            success = success && DatabaseHandler.addRating(username, content.getContentID(), rating);
        }
        
        // Add like/dislike if selected
        if (liked != null && liked) {
            success = success && DatabaseHandler.addLike(username, content.getContentID());
        } else if (disliked != null && disliked) {
            success = success && DatabaseHandler.addDislike(username, content.getContentID());
        }
        
        // Mark content as watched
        success = success && DatabaseHandler.addToWatched(username, content.getContentID());
        
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Your feedback for \"" + title + "\" has been submitted!");
            // Navigate back to home screen
            navigateToHomeScreen(event);
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to submit your feedback. Please try again.");
        }
    }
    
    @FXML
    public void cancelButtonHandler(ActionEvent event) {
        navigateToHomeScreen(event);
    }
    
    @FXML
    public void backButtonHandler(ActionEvent event) {
        navigateToHomeScreen(event);
    }
    
    private void navigateToHomeScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserHome.fxml"));
            root = loader.load();
            
            UserHomeController controller = loader.getController();
            controller.setUsername(username);
            controller.initializeUserHome();
            
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Could not load home screen");
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
