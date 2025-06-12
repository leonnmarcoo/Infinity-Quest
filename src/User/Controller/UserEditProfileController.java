package User.Controller;

import java.io.File;
import java.io.IOException;

import Database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Objects.User;
import User.Session.SessionManager;

@SuppressWarnings("unused")
public class UserEditProfileController {

    @FXML
    private Button backButton;

    @FXML
    private TextField bioTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ImageView profileImageView;

    @FXML
    private TextField profileTextField;

    @FXML
    private Label reviewLabel;

    @FXML
    private Label reviewLabel1;

    @FXML
    private Button updateButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private ImageView tonyDefaultPic;

    @FXML
    private Button homeButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    @FXML
    public void initialize() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            usernameTextField.setText(currentUser.getUserName());
            emailTextField.setText(currentUser.getUserEmail());
            passwordTextField.setText(currentUser.getUserPassword());
            bioTextField.setText(currentUser.getUserBio());
            profileTextField.setText(currentUser.getUserProfile());

            updateProfileImageView(currentUser.getUserProfile());
        }

        profileTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateProfileImageView(newVal);
        });      
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void backButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserProfile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load profile screen.");
        }
    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserProfile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load profile screen.");
        }
    }

    @FXML
    void updateButtonHandler(ActionEvent event) {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            currentUser.setUserPassword(passwordTextField.getText());
            currentUser.setUserEmail(emailTextField.getText());
            currentUser.setUserBio(bioTextField.getText());
            currentUser.setUserProfile(profileTextField.getText());
            // currentUser.setUserName(usernameTextField.getText());

            if (DatabaseHandler.updateUser(currentUser)) {
                showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Your profile was updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update your profile.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No User", "No user is logged in.");
        }
    } 

    private void updateProfileImageView(String path) {
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            if (file.exists()) {
                profileImageView.setImage(new javafx.scene.image.Image(file.toURI().toString()));
                profileImageView.setVisible(true);
                tonyDefaultPic.setVisible(false); // Hide Tony when custom pic is set
            } else {
                profileImageView.setImage(null);
                profileImageView.setVisible(false);
                tonyDefaultPic.setVisible(true); // Show Tony when invalid path
            }
        } else {
            profileImageView.setImage(null);
            profileImageView.setVisible(false);
            tonyDefaultPic.setVisible(true); // Show Tony when no path
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
