package Admin.Controller;

import Database.DatabaseHandler;
import Objects.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AdminUserController implements Initializable {

    ObservableList<User> userList = FXCollections.observableArrayList();  

    private User selectedUser = null;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<User, String> bioColumn;

    @FXML
    private Label bioLabel;

    @FXML
    private TextField bioTextField;

    // NAVIGATION BUTTONS

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
    private Button dislikeButton;

    @FXML
    private Button likeButton;

    // CRUD BUTTONS

    @FXML
    private Button createButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label userLabel;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, Integer> userIDColumn;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsers();

        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        bioColumn.setCellValueFactory(new PropertyValueFactory<>("userBio"));

        userTable.setEditable(true);
        usernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setCellFactory(column -> {
            TextFieldTableCell<User, String> cell = new TextFieldTableCell<>(new javafx.util.converter.DefaultStringConverter()) {
                @Override
                public void startEdit() {
                    super.startEdit();
                    if (isEditing() && getGraphic() instanceof TextField) {
                        TextField editor = (TextField) getGraphic();
                        editor.textProperty().addListener((obs, oldVal, newVal) -> {
                            User user = getTableView().getItems().get(getIndex());
                            if (selectedUser != null && user.getUserID() == selectedUser.getUserID()) {
                                passwordTextField.setText(newVal);
                            }
                        });
                    }
                }
            };
            return cell;
        });
        emailColumn.setCellFactory(column -> {
            TextFieldTableCell<User, String> cell = new TextFieldTableCell<>(new javafx.util.converter.DefaultStringConverter()) {
                @Override
                public void startEdit() {
                    super.startEdit();
                    if (isEditing() && getGraphic() instanceof TextField) {
                        TextField editor = (TextField) getGraphic();
                        editor.textProperty().addListener((obs, oldVal, newVal) -> {
                            User user = getTableView().getItems().get(getIndex());
                            if (selectedUser != null && user.getUserID() == selectedUser.getUserID()) {
                                emailTextField.setText(newVal);
                            }
                        });
                    }
                }
            };
            return cell;
        });

        bioColumn.setCellFactory(column -> {
            TextFieldTableCell<User, String> cell = new TextFieldTableCell<>(new javafx.util.converter.DefaultStringConverter()) {
                @Override
                public void startEdit() {
                    super.startEdit();
                    if (isEditing() && getGraphic() instanceof TextField) {
                        TextField editor = (TextField) getGraphic();
                        editor.textProperty().addListener((obs, oldVal, newVal) -> {
                            User user = getTableView().getItems().get(getIndex());
                            if (selectedUser != null && user.getUserID() == selectedUser.getUserID()) {
                                bioTextField.setText(newVal);
                            }
                        });
                    }
                }
            };
            return cell;
        });

        // Handle selection - update the class field!
        userTable.setOnMouseClicked(event -> {
            selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                usernameTextField.setText(selectedUser.getUserName());
                passwordTextField.setText(selectedUser.getUserPassword());
                emailTextField.setText(selectedUser.getUserEmail());
                bioTextField.setText(selectedUser.getUserBio());
            }
        });

        // Sync edited values to DB and text fields
        passwordColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setUserPassword(event.getNewValue());
            DatabaseHandler.updateUser(user);
            passwordTextField.setText(event.getNewValue());
            displayUsers();
        });

        emailColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setUserEmail(event.getNewValue());
            DatabaseHandler.updateUser(user);
            emailTextField.setText(event.getNewValue());
            displayUsers();
        });

        bioColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setUserBio(event.getNewValue());
            DatabaseHandler.updateUser(user);
            bioTextField.setText(event.getNewValue());
            displayUsers();
        });
    }

    private void displayUsers() {
        userList.clear();
        ResultSet result = DatabaseHandler.getUser();
        try {
            while (result.next()) {
                int userID = result.getInt("userID");
                String username = result.getString("userName");
                String password = result.getString("userPassword");
                String email = result.getString("userEmail");
                String bio = result.getString("userBio");
                String profile = result.getString("userProfile");

                User user = new User(userID, username, password, email, bio, profile);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userTable.setItems(userList);
    }

    private void clearForm() {
        usernameTextField.clear();
        passwordTextField.clear();
        emailTextField.clear();
        bioTextField.clear();
        selectedUser = null;
        updateButton.setDisable(true);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void createUser(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String email = emailTextField.getText();
        String bio = bioTextField.getText();

        User user = new User(0, username, password, email, bio,"");

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || bio.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all fields");
            return;
        }

        if (DatabaseHandler.createUser(user)) {
            showAlert(AlertType.INFORMATION, "Success", "User created successfully!");
            clearForm();
        } else {
            showAlert(AlertType.ERROR, "Error", "Failed to create an account.");
        }
        displayUsers();
    }

    @FXML
    private void deleteUser(ActionEvent event) throws IOException {
        if (selectedUser == null) {
            showAlert(AlertType.ERROR, "Error", "Please select a user to delete.");
            return;
        }
        if (DatabaseHandler.deleteUser(selectedUser)) {
            showAlert(AlertType.INFORMATION, "Success", "User deleted successfully!");
            displayUsers();
        } else {
            showAlert(AlertType.ERROR, "Error", "Failed to delete the account.");
        }
    }

    @FXML
    private void updateUser(ActionEvent event) {
        if (selectedUser != null) {
            // Update selectedUser with text field values
            selectedUser.setUserPassword(passwordTextField.getText());
            selectedUser.setUserEmail(emailTextField.getText());
            selectedUser.setUserBio(bioTextField.getText());
            // If you want to allow username editing, uncomment the next line:
            // selectedUser.setUserName(usernameTextField.getText());

            if (DatabaseHandler.updateUser(selectedUser)) {
                showAlert(Alert.AlertType.INFORMATION, "User Updated", "User information updated successfully.");
                displayUsers();
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update user information.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to update.");
        }
    }

    // === NAVIGATION=====================================================================================
    @FXML
    private void userButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminUser.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void contentButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminContent.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void castButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminCast.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void watchlistButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminWatchlist.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void backButtonHandler(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Admin/FXML/AdminHome.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void ratingButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminRating.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reviewButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminReview.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void likeButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminLike.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
    
    @FXML
    private void dislikeButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminDislike.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
// =========================================================================================================================
} 




// package Admin.Controller;

// import Database.DatabaseHandler;
// import Objects.User;

// import java.io.IOException;
// import java.net.URL;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ResourceBundle;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.fxml.Initializable;
// import javafx.scene.Node;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.TextField;
// import javafx.scene.control.cell.PropertyValueFactory;
// import javafx.scene.control.cell.TextFieldTableCell;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;
// import javafx.stage.Stage;

// public class AdminUserController implements Initializable {

//     ObservableList<User> userList = FXCollections.observableArrayList();  
    
//     private User selectedUser = null;
    
//     @FXML
//     private Button backButton;

//     @FXML
//     private TableColumn<User, String> bioColumn;

//     @FXML
//     private Label bioLabel;

//     @FXML
//     private TextField bioTextField;

//     // NAVIGATION BUTTONS

//     @FXML
//     private Button userButton;

//     @FXML
//     private Button contentButton;

//     @FXML
//     private Button castButton;

//     @FXML
//     private Button watchlistButton;

//     @FXML
//     private Button ratingButton;

//     @FXML
//     private Button reviewButton;

//     @FXML
//     private Button dislikeButton;

//     @FXML
//     private Button likeButton;

//     // CRUD BUTTONS

//     @FXML
//     private Button createButton;

//     @FXML
//     private Button deleteButton;

//     @FXML
//     private Button updateButton;

//     @FXML
//     private TableColumn<User, String> emailColumn;

//     @FXML
//     private Label emailLabel;

//     @FXML
//     private TextField emailTextField;

//     @FXML
//     private TableColumn<User, String> passwordColumn;

//     @FXML
//     private Label passwordLabel;

//     @FXML
//     private TextField passwordTextField;

//     @FXML
//     private Label userLabel;

//     @FXML
//     private TableView<User> userTable;

//     @FXML
//     private TableColumn<User, String> usernameColumn;

//     @FXML
//     private TableColumn<User, Integer> userIDColumn;

//     @FXML
//     private Label usernameLabel;

//     @FXML
//     private TextField usernameTextField;

//     private Stage stage;
//     private Scene scene;
//     private Parent root;

// @Override
// public void initialize(URL url, ResourceBundle rb) {
//     initializeCol();
//     displayUsers();

//     userTable.setEditable(true);
//     usernameColumn.setEditable(false);
//     passwordColumn.setEditable(true);
//     emailColumn.setEditable(true);
//     bioColumn.setEditable(true);

//     passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//     emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//     bioColumn.setCellFactory(TextFieldTableCell.forTableColumn());

//     passwordColumn.setOnEditCommit(event -> {
//         User user = event.getRowValue();
//         user.setUserPassword(event.getNewValue());
//         DatabaseHandler.updateUser(user);
//         displayUsers();
//     });
//     emailColumn.setOnEditCommit(event -> {
//         User user = event.getRowValue();
//         user.setUserEmail(event.getNewValue());
//         DatabaseHandler.updateUser(user);
//         displayUsers();
//     });
//     bioColumn.setOnEditCommit(event -> {
//         User user = event.getRowValue();
//         user.setUserBio(event.getNewValue());
//         DatabaseHandler.updateUser(user);
//         displayUsers();
//     });

//     userTable.setOnMouseClicked(event -> {
//         if (event.getClickCount() == 1 && userTable.getSelectionModel().getSelectedItem() != null) {
//             User selectedUser = userTable.getSelectionModel().getSelectedItem();
//             usernameTextField.setText(selectedUser.getUserName());
//             passwordTextField.setText(selectedUser.getUserPassword());
//             emailTextField.setText(selectedUser.getUserEmail());
//             bioTextField.setText(selectedUser.getUserBio());
//         }
//     });
// }

//     private void initializeCol() {
//         userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
//         usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
//         passwordColumn.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
//         emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
//         bioColumn.setCellValueFactory(new PropertyValueFactory<>("userBio"));
//     }

//     private void displayUsers() {
//         userList.clear();
//         ResultSet result = DatabaseHandler.getUser();
//         try {
//             while (result.next()) {
//                 int userID = result.getInt("userID");
//                 String username = result.getString("userName");
//                 String password = result.getString("userPassword");
//                 String email = result.getString("userEmail");
//                 String bio = result.getString("userBio");

//                 User user = new User(userID, username, password, email, bio);
//                 userList.add(user);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         userTable.setItems(userList);
//     }

//     private void clearForm() {
//         usernameTextField.clear();
//         passwordTextField.clear();
//         emailTextField.clear();
//         bioTextField.clear();
//         selectedUser = null;
//         updateButton.setDisable(true);
//     }

//     @FXML
//     private void createUser(ActionEvent event) throws IOException {
//         String username = usernameTextField.getText();
//         String password = passwordTextField.getText();
//         String email = emailTextField.getText();
//         String bio = bioTextField.getText();

//         User user = new User(0, username, password, email, bio);

//         if (username.isEmpty() || password.isEmpty() || email.isEmpty() || bio.isEmpty()) {
//             Alert alert = new Alert(AlertType.ERROR);
//             alert.setTitle("Error");
//             alert.setHeaderText("Please fill in all fields");
//             alert.showAndWait();
//             return;
//         }

//         if (DatabaseHandler.createUser(user)) {
//             Alert alert = new Alert(AlertType.INFORMATION);
//             alert.setTitle("Success");
//             alert.setHeaderText(null);
//             alert.setContentText("User created successfully!");
//             alert.showAndWait();

//             clearForm();

//         } else {
//             Alert alert = new Alert(AlertType.ERROR);
//             alert.setTitle("Error");
//             alert.setHeaderText(null);
//             alert.setContentText("Failed to create an account.");
//             alert.showAndWait();
//         }
//         displayUsers();
//     }
    
//     @FXML
//     private void deleteUser(ActionEvent event) throws IOException {
//         User selectedUser = userTable.getSelectionModel().getSelectedItem();

//         if (selectedUser == null) {
//             Alert alert = new Alert(AlertType.ERROR);
//             alert.setTitle("Error");
//             alert.setHeaderText("No user selected");
//             alert.setContentText("Please select a user to delete.");
//             alert.showAndWait();
//             return;
//         }
//         if (DatabaseHandler.deleteUser(selectedUser)) {
//             Alert alert = new Alert(AlertType.INFORMATION);
//             alert.setTitle("Success");
//             alert.setHeaderText(null);
//             alert.setContentText("User deleted successfully!");
//             alert.showAndWait();
//             displayUsers();
//         } else {
//             Alert alert = new Alert(AlertType.ERROR);
//             alert.setTitle("Error");
//             alert.setHeaderText(null);
//             alert.setContentText("Failed to delete the account.");
//             alert.showAndWait();
//         }
//     }

// @FXML
// private void updateUser(ActionEvent event) throws IOException {
//     User selectedUser = userTable.getSelectionModel().getSelectedItem();

//     if (selectedUser == null) {
//         Alert alert = new Alert(AlertType.ERROR);
//         alert.setTitle("Error");
//         alert.setHeaderText("No user selected");
//         alert.setContentText("Please select a user to update.");
//         alert.showAndWait();
//         return;
//     }

//     // Get updated values from text fields
//     String username = usernameTextField.getText();
//     String password = passwordTextField.getText();
//     String email = emailTextField.getText();
//     String bio = bioTextField.getText();

    
//     User updatedUser = new User(
//         selectedUser.getUserID(),
//         username,
//         password,
//         email,
//         bio
//     );

//     if (DatabaseHandler.updateUser(updatedUser)) {
//         Alert alert = new Alert(AlertType.INFORMATION);
//         alert.setTitle("Success");
//         alert.setHeaderText(null);
//         alert.setContentText("User updated successfully!");
//         alert.showAndWait();
//         displayUsers();
//     } else {
//         Alert alert = new Alert(AlertType.ERROR);
//         alert.setTitle("Error");
//         alert.setHeaderText(null);
//         alert.setContentText("Failed to update the account.");
//         alert.showAndWait();
//     }
// }

//     // ============================ NAVIGATION ===============================

//     @FXML
//     private void backButtonHandler(ActionEvent event) throws IOException {
//         root = FXMLLoader.load(getClass().getResource("/Admin/FXML/AdminHome.fxml"));
//         stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//         scene = new Scene(root);
//         stage.setScene(scene);
//         stage.show();
//     }

//     @FXML
//     private void contentButtonHandler(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminContent.fxml"));
//             root = loader.load();
//             stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//             scene = new Scene(root);
//             stage.setScene(scene);
//             stage.show();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     @FXML
//     private void castButtonHandler(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminCast.fxml"));
//             root = loader.load();
//             stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//             scene = new Scene(root);
//             stage.setScene(scene);
//             stage.show();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     @FXML
//     private void watchlistButtonHandler(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/AdminWatchlist.fxml"));
//             root = loader.load();
//             stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//             scene = new Scene(root);
//             stage.setScene(scene);
//             stage.show();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }