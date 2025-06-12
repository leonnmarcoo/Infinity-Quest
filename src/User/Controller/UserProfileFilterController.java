package User.Controller;

import Objects.Content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHandler;
import Objects.User;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserProfileFilterController {

    @FXML
    private Button backButton;

    @FXML
    private GridPane contentGridPane;

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private Label filterLabel;

    @FXML
    private Button profileButton;

    @FXML
    private Button searcButton;

    @FXML
    private Button homeButton;

    private List<Content> contentList = new ArrayList<>();
    private String currentFilterType; // Store the current filter type

    public void setWatchedFilter() {
        currentFilterType = "watched";
        filterLabel.setText("Watched");
        User user = SessionManager.getCurrentUser();
        contentList = DatabaseHandler.getWatchedContent(user.getUserID());
        displayFilteredContent();
    }

    public void setWatchlistFilter() {
        currentFilterType = "watchlist";
        filterLabel.setText("Watchlist");
        User user = SessionManager.getCurrentUser();
        contentList = DatabaseHandler.getWatchlistContent(user.getUserID());
        displayFilteredContent();
    }

    public void setLikesFilter() {
        currentFilterType = "likes";
        filterLabel.setText("Likes");
        User user = SessionManager.getCurrentUser();
        contentList = DatabaseHandler.getLikedContent(user.getUserID());
        displayFilteredContent();
    }

    public void setDislikesFilter() {
        currentFilterType = "dislikes";
        filterLabel.setText("Dislikes");
        User user = SessionManager.getCurrentUser();
        contentList = DatabaseHandler.getDislikedContent(user.getUserID());
        displayFilteredContent();
    }

    @SuppressWarnings("unused")
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
            controller.setPreviousFXMLPath("/User/FXML/UserProfileFilter.fxml");
            controller.setFilterType(currentFilterType);

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
