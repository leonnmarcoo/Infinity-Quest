package User.Controller;

import java.io.IOException;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserLoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private TextField usernameTextField;


    //     public void loginButtonHandler(ActionEvent event) throws IOException {

    //     String username = usernameTextField.getText();
    //     String password = passwordPasswordField.getText();

    //     if (DatabaseHandler.validateUserLogin(username, password)) {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserHome.fxml"));
    //         root = loader.load();
            
    //         // Pass username to UserHomeController
    //         UserHomeController homeController = loader.getController();
    //         homeController.setUsername(username);
    //         homeController.initializeUserHome();
            
    //         stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    //         scene = new Scene(root);
    //         stage.setScene(scene);
    //         stage.show();
    //     } else {
    //         Alert alert = new Alert(AlertType.ERROR);
    //         alert.setTitle("Login Failed");
    //         alert.setHeaderText(null);
    //         alert.setContentText("Invalid username or password.");
    //         alert.showAndWait();
    //     }

    // }

    public void loginButtonHandler(ActionEvent event) throws IOException {

        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        if (DatabaseHandler.validateUserLogin(username, password)) {

            User user = DatabaseHandler.getUserByUsername(username);
            user.setWatched(DatabaseHandler.getWatchedTitles(user.getUserID()));
            user.setWatchlist(DatabaseHandler.getWatchlistTitles(user.getUserID()));
            user.setReviews(DatabaseHandler.getReviews(user.getUserID()));

            SessionManager.setCurrentUser(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserHome.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return;
        }

        User user = DatabaseHandler.getUserByUsername(username);
        if (user == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("User not found");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

}
