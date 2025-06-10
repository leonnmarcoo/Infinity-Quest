package User.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import Objects.Content;
import User.Session.SessionManager;
import Objects.User;
import Database.DatabaseHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class UserRatingsController {
    
    @FXML
    private Button backButton;

    @FXML
    private GridPane contentGridPane;

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private Label filterLabel;

    @FXML
    private Button fiveStarButton;

    @FXML
    private ImageView fiveStarButtonImage;

    @FXML
    private Button fourStarButton;

    @FXML
    private ImageView fourStarButtonImage;

    @FXML
    private Button oneStarButton;

    @FXML
    private ImageView oneStarButtonImage;

    @FXML
    private ScrollPane ratingScrollPane;

    @FXML
    private Button threeStarButton;

    @FXML
    private ImageView threeStarButtonImage;

    @FXML
    private Button twoStarButton;

    @FXML
    private ImageView twoStarButtonImage;
    
    @FXML
    private Button showAllButton;

    private List<Object[]> allRatedContent;
    @FXML
    public void initialize() {
        loadAllRatings();
        // Set the "Show All" button as active initially
        resetButtonColors();
        showAllButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 8; -fx-text-fill: white;");
        displayRatings(-1); // -1 means show all
    }

    private void loadAllRatings() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;
        allRatedContent = DatabaseHandler.getRatedContentAndRating(user.getUserID());
    }

    private void displayRatings(int filterStar) {
        contentGridPane.getChildren().clear();
        int col = 0, row = 0;
        int maxCols = 3;
        
        contentGridPane.getRowConstraints().clear();

        List<Object[]> filtered = (filterStar == -1)
                ? allRatedContent
                : allRatedContent.stream().filter(arr -> (int) arr[1] == filterStar).collect(Collectors.toList());

        for (Object[] arr : filtered) {
            Content content = (Content) arr[0];
            int rating = (int) arr[1];

            VBox box = new VBox();
            box.setSpacing(5);

            if (content.getContentPoster() != null && !content.getContentPoster().isEmpty()) {
                File file = new File(content.getContentPoster());
                if (file.exists()) {
                    ImageView posterView = new ImageView(new Image(file.toURI().toString()));
                    posterView.setFitHeight(188);
                    posterView.setFitWidth(125);
                    posterView.setPreserveRatio(true);
                    box.getChildren().add(posterView);
                }
            }

            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < rating; i++) {
                stars.append("★");
            }
            Text ratingText = new Text(stars.toString());
            ratingText.setStyle("-fx-fill: #CE2431;");
            box.getChildren().add(ratingText);

            contentGridPane.add(box, col, row);
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
        
        if (filterStar == -1) {
            filterLabel.setText("All Ratings");
        } else {
            filterLabel.setText(filterStar + " Star");
        }
    }

    @FXML
    private void oneStarButtonHandler() { 
        resetButtonColors();
        oneStarButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 8;");
        displayRatings(1); 
    }
    @FXML
    private void twoStarButtonHandler() { 
        resetButtonColors();
        twoStarButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 8;");
        displayRatings(2); 
    }
    @FXML
    private void threeStarButtonHandler() { 
        resetButtonColors();
        threeStarButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 8;");
        displayRatings(3); 
    }
    @FXML
    private void fourStarButtonHandler() { 
        resetButtonColors();
        fourStarButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 8;");
        displayRatings(4); 
    }
    @FXML
    private void fiveStarButtonHandler() { 
        resetButtonColors();
        fiveStarButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 8;");
        displayRatings(5); 
    }
    @FXML
    private void showAllButtonHandler() { 
        resetButtonColors();
        showAllButton.setStyle("-fx-background-color: #CE2431; -fx-background-radius: 8; -fx-text-fill: white;");
        displayRatings(-1); 
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

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
    }

    private void resetButtonColors() {
        oneStarButton.setStyle("-fx-background-color: #343434; -fx-background-radius: 8;");
        twoStarButton.setStyle("-fx-background-color: #343434; -fx-background-radius: 8;");
        threeStarButton.setStyle("-fx-background-color: #343434; -fx-background-radius: 8;");
        fourStarButton.setStyle("-fx-background-color: #343434; -fx-background-radius: 8;");
        fiveStarButton.setStyle("-fx-background-color: #343434; -fx-background-radius: 8;");
        showAllButton.setStyle("-fx-background-color: #343434; -fx-background-radius: 8; -fx-text-fill: white;");
    }
}
