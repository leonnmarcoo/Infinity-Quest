package User.Controller;

import Objects.Content;
import Database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserSearchController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;
    
    @FXML
    private ScrollPane contentScrollPane;
    
    @FXML
    private GridPane contentGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    public void restoreSearchState(String searchTerm) {
        searchTextField.setText(searchTerm);
        List<Content> searchResults = DatabaseHandler.searchContent(searchTerm);
        displaySearchResults(searchResults);
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

    @FXML
    private void searchButtonHandler(ActionEvent event) {
        String searchText = searchTextField.getText().trim();
        if (searchText.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Please enter a search term");
            return;
        }
        
        List<Content> searchResults = DatabaseHandler.searchContent(searchText);
        
        displaySearchResults(searchResults);
    }


    @SuppressWarnings("unused")
    private void displaySearchResults(List<Content> results) {
        contentGridPane.getChildren().clear();
        contentGridPane.getRowConstraints().clear();

    
    if (results.isEmpty()) {
        Label noResults = new Label("No results found for your search");
        noResults.setPrefWidth(400);
        noResults.setAlignment(Pos.CENTER);
        noResults.setFont(Font.font("Geist", FontWeight.BOLD, 16));
        noResults.setStyle("-fx-text-fill: white;");
        
        contentGridPane.setAlignment(Pos.CENTER);
        
        VBox centeringBox = new VBox(noResults);
        centeringBox.setAlignment(Pos.CENTER);
        centeringBox.setPrefHeight(500);
        
        contentGridPane.add(centeringBox, 0, 0, 3, 1);
        return;
    }
    contentGridPane.setAlignment(Pos.TOP_LEFT);
 
        int col = 0;
        int row = 0;
        int maxCols = 3;
        
        for (Content content : results) {
            VBox posterBox = new VBox();
            posterBox.setSpacing(5);
            posterBox.setAlignment(javafx.geometry.Pos.CENTER);
            
            if (content.getContentPoster() != null && !content.getContentPoster().isEmpty()) {
                File file = new File(content.getContentPoster());
                if (file.exists()) {
                    ImageView posterView = new ImageView(new Image(file.toURI().toString()));
                    posterView.setFitHeight(188);
                    posterView.setFitWidth(125);
                    posterView.setPreserveRatio(true);
                    
                    posterView.setOnMouseClicked(e -> showContentDetails(content));
                    posterBox.getChildren().add(posterView);
                }
            }
                
            contentGridPane.add(posterBox, col, row);
            
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }
    
    private void showContentDetails(Content content) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/FXML/UserInformation.fxml"));
            Parent root = loader.load();
            
            UserInformationController controller = loader.getController();
            controller.setContent(content);
            controller.setPreviousFXMLPath("/User/FXML/UserSearch.fxml");
            
            String currentSearchTerm = searchTextField.getText().trim();
            if (!currentSearchTerm.isEmpty()) {
                controller.setSearchTerm(currentSearchTerm);
            }
            
            Stage stage = (Stage) contentGridPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading content details");
        }
    }
}
