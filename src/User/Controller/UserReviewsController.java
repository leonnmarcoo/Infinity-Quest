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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import Objects.Content;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import java.io.File;
import javafx.scene.image.Image;

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

    List<Object[]> reviewed = DatabaseHandler.getReviewedContentAndText(user.getUserID());
    for (Object[] arr : reviewed) {
        Content content = (Content) arr[0];
        String reviewsLabelStr = (String) arr[1];

        HBox reviewBox = new HBox();
        reviewBox.setSpacing(10);

        if (content.getContentPoster() != null && !content.getContentPoster().isEmpty()) {
            File file = new File(content.getContentPoster());
            if (file.exists()) {
                ImageView posterView = new ImageView(new Image(file.toURI().toString()));
                posterView.setFitHeight(188);
                posterView.setFitWidth(125);
                posterView.setPreserveRatio(true);
                reviewBox.getChildren().add(posterView);
            }
        }

        VBox textBox = new VBox();
        textBox.setSpacing(5);
        Label titleLabel = new Label(content.getContentTitle());
        titleLabel.setStyle("-fx-font-weight: Bold; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Geist';");
        titleLabel.setMaxWidth(260);
        titleLabel.setWrapText(true);
        Label reviewsLabel = new Label(reviewsLabelStr);
        reviewsLabel.setStyle("-fx-text-fill: #D4D4D4; -fx-font-size: 12px;");
        reviewsLabel.setFont(Font.font("Geist Regular", 12));
        reviewsLabel.setMaxWidth(260);
        reviewsLabel.setMaxHeight(135);
        reviewsLabel.setWrapText(true);
        textBox.getChildren().addAll(titleLabel, reviewsLabel);

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
