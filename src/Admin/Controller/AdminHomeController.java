package Admin.Controller;

import Objects.Admin;
import Database.DatabaseHandler;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;

public class AdminHomeController implements Initializable {

    ObservableList<Admin> adminList = FXCollections.observableArrayList();

    private Admin selectedAdmin = null;

    @FXML
    private TableView<Admin> adminTable;

    @FXML
    private TableColumn<Admin, Integer> adminIDColumn;

    @FXML
    private TableColumn<Admin, String> adminNameColumn;

    @FXML
    private TableColumn<Admin, String> adminPasswordColumn;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    // NAVIGATION

    @FXML
    private Button userButton;

    @FXML
    private Button contentButton;

    @FXML
    private Button castButton;

    @FXML
    private Button watchlistButton;

    @FXML
    private Button ratingButton;

    @FXML
    private Button reviewButton;

    @FXML
    private Button likeButton;

    @FXML 
    private Button dislikeButton;

    // CRUD

    @FXML
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button logoutButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayAdmin();

        adminTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleTableDoubleClick();
            }
        });
        updateButton.setDisable(true);
    }

    private void handleTableDoubleClick() {
        selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
        
        if (selectedAdmin != null) {
            usernameTextField.setText(selectedAdmin.getAdminName());
            passwordPasswordField.setText(selectedAdmin.getAdminPassword());
            
            updateButton.setDisable(false);
        }
    }

    private void initializeCol() {
        adminIDColumn.setCellValueFactory(new PropertyValueFactory<>("adminID"));
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
        adminPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("adminPassword"));
    }

    private void displayAdmin() {
        adminList.clear();

        ResultSet result = DatabaseHandler.getAdmin();

        try {
            while (result.next()) {
                int adminID = result.getInt("adminID");
                String adminName = result.getString("adminName");
                String adminPassword = result.getString("adminPassword");
                Admin newAdmin = new Admin(adminID, adminName, adminPassword);
                adminList.add(newAdmin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adminTable.setItems(adminList);
    }

    private void clearForm() {
        usernameTextField.clear();
        passwordPasswordField.clear();
        selectedAdmin = null;
        updateButton.setDisable(true);
    }

    @FXML
    private void createAdmin(ActionEvent event) throws IOException {

        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        username = username.trim();
        password = password.trim();

        Admin admin = new Admin(0, username, password);

        if (DatabaseHandler.createAdmin(admin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Admin created successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to create admin");
            alert.showAndWait();
        }
        displayAdmin();
    }

    @FXML
    private void deleteAdmin (ActionEvent event) {

        Admin admin = adminTable.getSelectionModel().getSelectedItem();

        int adminID = admin.getAdminID();

        if (DatabaseHandler.deleteAdmin(admin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Admin deleted successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to delete admin");
            alert.showAndWait();
        }
        displayAdmin();
    }

    @FXML
    private void updateAdmin(ActionEvent event) throws IOException {
        if (selectedAdmin == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please double-click on an admin to update");
            alert.showAndWait();
            return;
        }

        String username = usernameTextField.getText().trim();
        String password = passwordPasswordField.getText().trim();

        Admin updatedAdmin = new Admin(selectedAdmin.getAdminID(), username, password);

        if (DatabaseHandler.updateAdmin(updatedAdmin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Admin updated successfully!");
            alert.showAndWait();
            
            clearForm();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update admin");
            alert.showAndWait();
        }
        displayAdmin();
    }

    // @FXML
    // private void updateAdmin (ActionEvent event) throws IOException {

    //     String username = usernameTextField.getText();
    //     String password = passwordPasswordField.getText();

    //     username = username.trim();
    //     password = password.trim();

    //     Admin admin = new Admin(0, username, password);

    //     if (DatabaseHandler.updateAdmin(admin)) {
    //         Alert alert = new Alert(AlertType.INFORMATION);
    //         alert.setTitle("Success");
    //         alert.setHeaderText(null);
    //         alert.setContentText("Admin updated successfully!");
    //         alert.showAndWait();
    //     } else {
    //         Alert alert = new Alert(AlertType.ERROR);
    //         alert.setTitle("Error");
    //         alert.setHeaderText(null);
    //         alert.setContentText("Failed to update admin");
    //         alert.showAndWait();
    //     }
    //     displayAdmin();
    // }

    @FXML
    private void logoutButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminLogin.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}