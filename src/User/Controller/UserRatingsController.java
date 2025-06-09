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

    private List<Object[]> allRatedContent;
    @FXML
    public void initialize() {
        loadAllRatings();
        displayRatings(-1); // -1 means show all, how tf right?
    }

    private void loadAllRatings() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;
        allRatedContent = DatabaseHandler.getRatedContentAndRatingByUser(user.getUserID());
    }

    private void displayRatings(int filterStar) {
        contentGridPane.getChildren().clear();
        int col = 0, row = 0;
        int maxCols = 3;

        List<Object[]> filtered = (filterStar == -1)
                ? allRatedContent
                : allRatedContent.stream().filter(arr -> (int) arr[1] == filterStar).collect(Collectors.toList());

        for (Object[] arr : filtered) {
            Content content = (Content) arr[0];
            int rating = (int) arr[1];

            VBox box = new VBox();
            box.setSpacing(5);

            // Kuha ng Poster
            if (content.getContentPoster() != null && !content.getContentPoster().isEmpty()) {
                File file = new File(content.getContentPoster());
                if (file.exists()) {
                    ImageView posterView = new ImageView(new Image(file.toURI().toString()));
                    posterView.setFitHeight(120);
                    posterView.setFitWidth(80);
                    posterView.setPreserveRatio(true);
                    box.getChildren().add(posterView);
                }
            }

            // for Title and Rating nmn
            Text title = new Text(content.getContentTitle());
            Text ratingText = new Text("Your Rating: " + rating + "★");
            box.getChildren().addAll(title, ratingText);

            contentGridPane.add(box, col, row);
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }

    @FXML
    private void oneStarButtonHandler() { displayRatings(1); }
    @FXML
    private void twoStarButtonHandler() { displayRatings(2); }
    @FXML
    private void threeStarButtonHandler() { displayRatings(3); }
    @FXML
    private void fourStarButtonHandler() { displayRatings(4); }
    @FXML
    private void fiveStarButtonHandler() { displayRatings(5); }
    @FXML
    private void showAllButtonHandler() { displayRatings(-1); }

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

}
