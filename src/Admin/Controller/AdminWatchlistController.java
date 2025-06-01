package Admin.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Objects.Watched;
import Objects.Watchlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminWatchlistController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button castButton;

    @FXML
    private Button contentButton;

    @FXML
    private Button dislikeButton;

    @FXML
    private Button likeButton;

    @FXML
    private Button ratingButton;

    @FXML
    private Button reviewButton;

    @FXML
    private Button userButton;
 
    @FXML
    private Button watchedUpdateButton;
    
    @FXML
    private Button watchedDeleteButton;

    @FXML
    private Button watchlistButton;

    @FXML
    private Button watchlistUpdateButton;

    @FXML
    private Button watchlistDeleteButton;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializedCol();
        displayWatchlist();
    }


    
   // ================================= WATCHLIST ================================================

    @FXML
    private TableView<Watchlist> watchlistDataTable;

    @FXML
    private TableColumn<Watchlist, Integer> watchlistIDCol;

    @FXML
    private TableColumn<Watchlist, Integer> watchlistUserCol;

    @FXML
    private TableColumn<Watchlist, Integer> watchlistContentCol;

    private void initializedCol(){

        watchlistIDCol.setCellValueFactory(new PropertyValueFactory<>("watchedID"));
        watchlistUserCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        watchlistContentCol.setCellValueFactory(new PropertyValueFactory<>("contentID"));
        
    }
    
    private void displayWatchlist(){

    }

    // update delete
    
    @FXML
    void watchlistDeleteButtonHandler(ActionEvent event) {

    }

    @FXML
    void watchlistUpdateButtonHandler(ActionEvent event) {

    }

// ================================= WATCHED ================================================

    @FXML
    private TableView<Watched> watchedDataTable;

    @FXML
    private TableColumn<Watched, Integer> watchedIDCol;

    @FXML
    private TableColumn<Watched, Integer> watchedUserCol;

     @FXML
    private TableColumn<Watched, Integer> watchedContentCol;

// update delete

    @FXML
    void watchedDeleteButtonHandler(ActionEvent event) {
    }

    @FXML
    void watchedUpdateButtonHandler(ActionEvent event) {
    }

// ==============================NAVIGATION=====================================================================================

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

