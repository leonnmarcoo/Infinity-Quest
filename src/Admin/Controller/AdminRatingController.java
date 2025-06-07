package Admin.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Objects.Rating;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Database.DatabaseHandler;


public class AdminRatingController implements Initializable {
    ObservableList<Rating> ratingList = FXCollections.observableArrayList();

    @FXML private Button backButton;

    @FXML private Button castButton;

    @FXML private Button contentButton;

    @FXML private Button dislikeButton;

    @FXML private Button likeButton;

    @FXML private Button reviewButton;

    @FXML private Button ratingButton;

    @FXML private Button userButton;

    @FXML private Label userLabel;

    @FXML private Button watchlistButton;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeRatingCol();
        ratingList.clear();
        displayRating();
    }

    private void initializeRatingCol(){
        
        ratingIDCol.setCellValueFactory(new PropertyValueFactory<>("ratingID"));
        ratingUserNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        ratingContentCol.setCellValueFactory(new PropertyValueFactory<>("contentTitle"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
    
        ratingDataTable.setItems(ratingList);
        ratingDataTable.setEditable(false);  
    }

    private void displayRating(){ 
        
        ResultSet result = null;

        try {
            result = DatabaseHandler.getRating();
            if (result != null) {
                while (result.next()) {
                    
                    int ratingID = result.getInt("ratingID");
                    String userName = result.getString("userName");
                    String contentTitle = result.getString("contentTitle");
                    int star = result.getInt("star");

                    Rating rating = new Rating(ratingID, userName, contentTitle, star);
                    ratingList.add(rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally { 
            try {
                if (result != null) {
                    result.close(); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
// =================================================
    @FXML private TableView<Rating> ratingDataTable;

    @FXML private TableColumn<Rating, Integer> ratingIDCol;    

    @FXML private TableColumn<Rating, String> ratingUserNameCol;    

    @FXML  private TableColumn<Rating, String> ratingContentCol;    

    @FXML private TableColumn<Rating, Integer> ratingCol;

// === Navigation ================================================

    private Stage stage;
    private Scene scene;
    private Parent root;

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
    
        @FXML
    private void backButtonHandler(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Admin/FXML/AdminHome.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
