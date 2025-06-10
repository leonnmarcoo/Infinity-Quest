package User.Controller;

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
import java.io.File;
import java.util.List;
import Database.DatabaseHandler;
import javafx.scene.image.Image;


public class UserProfileController {

    @FXML
    private Label bioLabel;

    @FXML
    private Label contentsWatchedLabel;

    @FXML
    private Button editProfileButton;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Label profileLabel;

    @FXML
    private HBox recentActivityHBox;

    @FXML
    private Button userDislike;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button userLike;

    @FXML
    private Button userRatings;

    @FXML
    private Button userReviews;

    @FXML
    private Button userWatched;

    @FXML
    private Button userWatchlist;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button userEditProfileButton;

    @FXML
    private ImageView userProfilePic;

    @FXML
    private ImageView tonyDefaultPic;

    @FXML
    private Button logOutButton;

    @FXML
    public void initialize() {
        User user = SessionManager.getCurrentUser();
        if (user != null) {
            usernameLabel.setText(user.getUserName());
            bioLabel.setText(user.getUserBio());

            // Profile picture logic
            if (user.getUserProfile() != null && !user.getUserProfile().isEmpty()) {
                File file = new File(user.getUserProfile());
                if (file.exists()) {
                    profileImageView.setImage(new Image(file.toURI().toString()));
                    profileImageView.setVisible(true);
                    tonyDefaultPic.setVisible(false);
                } else {
                    profileImageView.setImage(null);
                    profileImageView.setVisible(false);
                    tonyDefaultPic.setVisible(true);
                }
            } else {
                profileImageView.setImage(null);
                profileImageView.setVisible(false);
                tonyDefaultPic.setVisible(true);
            }

            int watchedCount = DatabaseHandler.getWatchedCount(user.getUserID());
            int totalCount = DatabaseHandler.getTotalContentCount();
            contentsWatchedLabel.setText(watchedCount + " out of " + totalCount + " contents watched");
        }
        loadRecentActivity();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
}

    private void loadRecentActivity() {
        recentActivityHBox.getChildren().clear();
        User user = SessionManager.getCurrentUser();
        if (user == null) return;

        List<Content> recentWatched = DatabaseHandler.getRecentWatchedContent(user.getUserID(), 4);

        for (Content content : recentWatched) {
            // Show poster if available, otherwise show title
            if (content.getContentPoster() != null && !content.getContentPoster().isEmpty()) {
                File file = new File(content.getContentPoster());
                if (file.exists()) {
                    ImageView posterView = new ImageView(new Image(file.toURI().toString()));
                    posterView.setFitHeight(136.68);
                    posterView.setFitWidth(91.12);
                    posterView.setPreserveRatio(true);
                    posterView.setOnMouseClicked(event -> showContentDetails(content));
                    recentActivityHBox.getChildren().add(posterView);
                }
            } else {
                Label titleLabel = new Label(content.getContentTitle());
                titleLabel.setOnMouseClicked(event -> showContentDetails(content));
                recentActivityHBox.getChildren().add(titleLabel);
            }
        }
    }

    private void showContentDetails(Content content) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserInformation.fxml"));
            Parent root = loader.load();
            UserInformationController controller = loader.getController();
            controller.setContent(content);
            controller.setPreviousFXMLPath("/User/FXML/UserProfile.fxml");
            Stage stage = (Stage) recentActivityHBox.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading content details");
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
            showAlert(Alert.AlertType.ERROR, "Error loading user home screen");
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
    private void userWatchedButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserProfileFilter.fxml"));
            Parent root = loader.load();

            // Call the watched filter setup
            UserProfileFilterController controller = loader.getController();
            controller.setWatchedFilter();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading watched list");
        }
    }

    @FXML
    private void userWatchlistButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserProfileFilter.fxml"));
            Parent root = loader.load();

            // Call the watchlist filter setup
            UserProfileFilterController controller = loader.getController();
            controller.setWatchlistFilter();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading watchlist");
        }
    }

    @FXML
    private void userRatingsButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserRatings.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading ratings list");
        }
    }

    @FXML
    private void userReviewsButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserReviews.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading reviews list");
        }
    }

    @FXML
    private void userLikeButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserProfileFilter.fxml"));
            Parent root = loader.load();

            // Call the likes filter setup
            UserProfileFilterController controller = loader.getController();
            controller.setLikesFilter();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading likes list");
        }
    }

    @FXML
    private void userDislikeButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserProfileFilter.fxml"));
            Parent root = loader.load();

            // Call the dislikes filter setup
            UserProfileFilterController controller = loader.getController();
            controller.setDislikesFilter();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading dislikes list");
        }
    }

    @FXML
    private void userEditProfileButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserEditProfile.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading edit profile screen");
        }
    }

    @FXML
    private void logOutButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminLogin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading user login screen");
        }
    }
    
}
