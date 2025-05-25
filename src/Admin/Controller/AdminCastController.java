package Admin.Controller;

import Objects.Actor;
import Objects.Role;
import Objects.Cast;
import Objects.Director;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;

import com.mysql.cj.xdevapi.Table;

public class AdminCastController implements Initializable {

    ObservableList<Actor> actorList = FXCollections.observableArrayList();

    ObservableList<Role> roleList = FXCollections.observableArrayList();

    ObservableList<Cast> castList = FXCollections.observableArrayList();

    ObservableList<Director> directorList = FXCollections.observableArrayList();

// ============================== ACTOR & ROLE TAB ================================
    @FXML
    private Tab actorAndRoleTab;

    @FXML
    private TextField actorTextField;

    @FXML
    private TableView<Actor> actorTable;

    @FXML 
    private TableColumn<Actor, Integer> actorIDColumn;

    @FXML
    private TableColumn<Actor, String> actorNameColumn;

    @FXML
    private Button addActorButton;

    @FXML
    private Button updateActorButton;

    @FXML
    private Button deleteActorButton;

    @FXML
    private TextField roleTextField;

    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, Integer> roleIDColumn;

    @FXML
    private TableColumn<Role, String> roleNameColumn;

    @FXML
    private Button createRoleButton;

    @FXML
    private Button updateRoleButton;

    @FXML
    private Button deleteRoleButton;

// ============================== CAST TAB ================================


// ============================== DIRECTOR TAB ================================
    @FXML
    private Tab directorTab;

    @FXML
    private TextField directorTextField;

    @FXML
    private TableView<Director> directorTable;

    @FXML
    private TableColumn<Director, Integer> directorIDColumn;

    @FXML
    private TableColumn<Director, String> directorNameColumn;

    @FXML
    private Button addDirectorButton;

    @FXML
    private Button updateDirectorButton;

    @FXML
    private Button deleteDirectorButton;

// ============================== NAVIGATION ================================

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

    @FXML
    private Button backButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeActorColumn();
        initializeRoleColumn();
        initializeCastColumn();
        initializeDirectorColumn();
        
        displayActor();
    }

    private void initializeActorColumn() {
        actorIDColumn.setCellValueFactory(new PropertyValueFactory<>("actorID"));
        actorNameColumn.setCellValueFactory(new PropertyValueFactory<>("actorName"));
    }

    private void initializeRoleColumn() {
        roleIDColumn.setCellValueFactory(new PropertyValueFactory<>("roleID"));
        roleNameColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
    }

    private void initializeCastColumn() {

    }

    private void initializeDirectorColumn() {
        directorIDColumn.setCellValueFactory(new PropertyValueFactory<>("directorID"));
        directorNameColumn.setCellValueFactory(new PropertyValueFactory<>("directorName"));
    }

// ============================ ACTOR ===============================

    private void displayActor() {
        actorList.clear();

        ResultSet result = DatabaseHandler.getActor();

        try {
            while (result.next()) {
                int actorID = result.getInt("actorID");
                String actorName = result.getString("actorName");
                Actor actor = new Actor(actorID, actorName);
                actorList.add(actor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        actorTable.setItems(actorList);
    }

    @FXML
    private void createActor(ActionEvent event) throws IOException {

        String actorName = actorTextField.getText();
        
        actorName = actorName.trim();

        Actor actor = new Actor(0, actorName);

        if (DatabaseHandler.createActor(actor)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Actor created successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to create actor");
            alert.showAndWait();
        }
        displayActor();
    }

    @FXML
    private void deleteActor (ActionEvent event) {

        Actor actor = actorTable.getSelectionModel().getSelectedItem();

        int actorID = actor.getActorID();

        if (DatabaseHandler.deleteActor(actor)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Actor deleted successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to delete actor");
            alert.showAndWait();
        }
        displayActor();
    }

// ============================ ROLE ===============================

    private void displayRole() {
        roleList.clear();

        ResultSet result = DatabaseHandler.getRole();

        try {
            while (result.next()) {
                int roleID = result.getInt("roleID");
                String roleName = result.getString("roleName");
                Role role = new Role(roleID, roleName);
                roleList.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        roleTable.setItems(roleList);
    }

    @FXML
    private void createRole(ActionEvent event) throws IOException {

        String roleName = roleTextField.getText();
        
        roleName = roleName.trim();

        Role role = new Role(0, roleName);

        if (DatabaseHandler.createRole(role)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Role created successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to create role");
            alert.showAndWait();
        }
        displayRole();
    }

    @FXML
    private void deleteRole (ActionEvent event) {

        Role role = roleTable.getSelectionModel().getSelectedItem();

        int roleID = role.getRoleID();

        if (DatabaseHandler.deleteRole(role)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Actor deleted successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to delete actor");
            alert.showAndWait();
        }
        displayRole();
    }

}
