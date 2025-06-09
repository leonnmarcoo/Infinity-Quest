package User.Controller;

import Objects.Content;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHandler;
import Objects.User;
import User.Session.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class UserProfileFilterController {

    @FXML
    private Button backButton;

    @FXML
    private GridPane contentGridPane;

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private Label filterLabel;

    private List<Content> contentList = new ArrayList<>();

    public void setWatchedFilter() {
        filterLabel.setText("Watched");
        User user = SessionManager.getCurrentUser();
        contentList = DatabaseHandler.getWatchedContentByUser(user.getUserID());
        displayFilteredContent();
    }

    public void setWatchlistFilter() {
        filterLabel.setText("Watchlist");
        User user = SessionManager.getCurrentUser();
        contentList = DatabaseHandler.getWatchlistContentByUser(user.getUserID());
        displayFilteredContent();
    }

    public void setLikesFilter() {
        filterLabel.setText("Likes");
        User user = SessionManager.getCurrentUser();
        contentList = DatabaseHandler.getLikedContentByUser(user.getUserID());
        displayFilteredContent();
    }

    public void setDislikesFilter() {
        filterLabel.setText("Dislikes");
        User user = SessionManager.getCurrentUser();
        contentList = DatabaseHandler.getDislikedContentByUser(user.getUserID());
        displayFilteredContent();
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
                javafx.scene.image.ImageView posterView = new javafx.scene.image.ImageView();
                String posterPath = content.getContentPoster();

                if (posterPath != null && !posterPath.isEmpty()) {
                    java.io.File file = new java.io.File(posterPath);
                    if (file.exists()) {
                        javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
                        posterView.setImage(image);
                        posterView.setFitHeight(188);
                        posterView.setFitWidth(125);
                        posterView.setPreserveRatio(true);

                        javafx.scene.layout.VBox posterContainer = new javafx.scene.layout.VBox(12);
                        posterContainer.setAlignment(javafx.geometry.Pos.CENTER);
                        posterContainer.getChildren().add(posterView);

                        posterView.setOnMouseClicked(event -> showContentDetails(content));

                        contentGridPane.add(posterContainer, columnIndex, rowIndex);

                        columnIndex++;
                        if (columnIndex >= maxColumns) {
                            columnIndex = 0;
                            rowIndex++;
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
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/User/FXML/UserInformation.fxml"));
            javafx.scene.Parent root = loader.load();

            UserInformationController controller = loader.getController();
            controller.setContent(content);

            javafx.stage.Stage stage = (javafx.stage.Stage) filterLabel.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backButtonHandler(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/User/FXML/UserProfile.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
