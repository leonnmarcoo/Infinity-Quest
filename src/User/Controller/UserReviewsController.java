package User.Controller;

import java.util.List;

import Database.DatabaseHandler;
import Objects.User;
import User.Session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import Objects.User;
import Objects.Content;
import User.Session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

import Database.DatabaseHandler;
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
import javafx.scene.text.Text;

public class UserReviewsController {

    @FXML
    private Button backButton;

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private VBox contentVBox;

    @FXML
    private Label reviewsLabel;


    @FXML
    public void initialize() {
        loadUserReviews();
    }

private void loadUserReviews() {
    contentVBox.getChildren().clear();
    User user = SessionManager.getCurrentUser();
    if (user == null) {
        showAlert(Alert.AlertType.ERROR, "No user session found.");
        return;
    }
    // System.out.println("Current user ID: " + user.getUserID());

    // Use the new method that returns List<Object[]> with Content and reviewText
    List<Object[]> reviewed = DatabaseHandler.getReviewedContentAndTextByUser(user.getUserID());
    // System.out.println("Reviews found: " + reviewed.size());
    for (Object[] arr : reviewed) {
        Content content = (Content) arr[0];
        String reviewTextStr = (String) arr[1];
        System.out.println(content.getContentTitle() + ": " + reviewTextStr);

        HBox reviewBox = new HBox();
        reviewBox.setSpacing(10);

        // Poster
        if (content.getContentPoster() != null && !content.getContentPoster().isEmpty()) {
            File file = new File(content.getContentPoster());
            if (file.exists()) {
                ImageView posterView = new ImageView(new Image(file.toURI().toString()));
                posterView.setFitHeight(320);
                posterView.setFitWidth(86);
                posterView.setPreserveRatio(true);
                reviewBox.getChildren().add(posterView);
            }
        }

        // Title and Review Text
        VBox textBox = new VBox();
        textBox.setSpacing(5);
        Label titleLabel = new Label(content.getContentTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        Text reviewText = new Text(reviewTextStr);
        reviewText.setStyle("-fx-fill: white;");
        textBox.getChildren().addAll(titleLabel, reviewText);

        reviewBox.getChildren().add(textBox);
        contentVBox.getChildren().add(reviewBox);
    }

    if (reviewed.isEmpty()) {
        Label noReviews = new Label("You have not written any reviews yet.");
        contentVBox.getChildren().add(noReviews);
    }
}

    @FXML
    private void backButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserProfile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Could not load profile screen");
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
