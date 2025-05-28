package Admin.Controller;

import java.io.IOException;
import Database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class AdminLoginController {

    @FXML
    Label usernameLabel;

    @FXML
    Label passwordLabel;

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordPasswordField;

    @FXML
    Button loginButton;

    @FXML
    Button clickhereButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loginButtonHandler(ActionEvent event) throws IOException {

        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        if (DatabaseHandler.validateadminLogin(username, password)) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminHome.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

        @FXML
    private void clickhereButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserWelcome.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}