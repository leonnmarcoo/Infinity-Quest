package Admin.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Database.DatabaseHandler;
import Objects.Like;
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


public class AdminLikeController implements Initializable {
    ObservableList<Like> likeList = FXCollections.observableArrayList(); 

    @FXML private Button backButton;

    @FXML private Button userButton;

    @FXML private Label userLabel;

    @FXML private Button contentButton;

    @FXML private Button castButton;

    @FXML private Button watchlistButton;

    @FXML private Button ratingButton;

    @FXML private Button reviewButton;

    @FXML private Button likeButton;

    @FXML private Button dislikeButton;
// ==================================================================
    @FXML private TableView<Like> likeDataTable;

    @FXML private TableColumn<Like, Integer> likeIDCol;

    @FXML private TableColumn<Like, String> likeUserNameCol;
    
    @FXML private TableColumn<Like, String> likeContentCol;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeLikeCol();
        likeList.clear();
        displayLike();
    }

    private void initializeLikeCol(){
        
        likeIDCol.setCellValueFactory(new PropertyValueFactory<>("likeID"));
        likeUserNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        likeContentCol.setCellValueFactory(new PropertyValueFactory<>("contentTitle"));

        likeDataTable.setItems(likeList);
        likeDataTable.setEditable(false);  
    }

    private void displayLike(){ 
        
        ResultSet result = null;

        try {
            result = DatabaseHandler.getLike();
            if (result != null) {
                while (result.next()) {
                    
                    int likeID = result.getInt("likeID");
                    String userName = result.getString("userName");
                    String contentTitle = result.getString("contentTitle");

                    Like like = new Like(likeID, userName, contentTitle);
                    likeList.add(like);
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

}
