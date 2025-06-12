package Admin.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import Database.DatabaseHandler;
import Objects.Watched;
import Objects.Watchlist;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class AdminWatchlistController implements Initializable {
    ObservableList<Watchlist> watchlistList = FXCollections.observableArrayList();
    ObservableList<Watched> watchedList = FXCollections.observableArrayList();

// NAVIGATION BUTTONS
    @FXML private Button backButton;

    @FXML private Button castButton;

    @FXML private Button contentButton;

    @FXML private Button dislikeButton;

    @FXML private Button likeButton;

    @FXML private Button ratingButton;

    @FXML private Button reviewButton;

    @FXML private Button userButton;

    @FXML private Button watchlistButton;

// FXML INJECTIONS
    @FXML private TableView<Watchlist> watchlistDataTable;

    @FXML private TableColumn<Watchlist, Integer> watchlistIDCol;

    @FXML private TableColumn<Watchlist, String> watchlistUserNameCol;

    @FXML private TableColumn<Watchlist, String> watchlistContentCol;

    @FXML private TableColumn<Watchlist, LocalDateTime> watchlistDateCol;

    @FXML private TableView<Watched> watchedDataTable;

    @FXML private TableColumn<Watched, Integer> watchedIDCol;

    @FXML private TableColumn<Watched, String> watchedUserNameCol;

    @FXML private TableColumn<Watched, String> watchedContentCol;

    @FXML private TableColumn<Watched, LocalDateTime> watchedDateCol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeWatchlistCol();
        initializeWatchedCol();

        watchlistList.clear();
        watchedList.clear();

        displayWatchlist();
        displayWatched();
    }

    // ================================= WATCHLIST ================================================

    private void initializeWatchlistCol(){
        
        watchlistIDCol.setCellValueFactory(new PropertyValueFactory<>("watchlistID"));
        watchlistUserNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        watchlistContentCol.setCellValueFactory(new PropertyValueFactory<>("contentTitle"));
        watchlistDateCol.setCellValueFactory(new PropertyValueFactory<>("watchlistDateTime"));

        watchlistDateCol.setCellFactory(column -> new javafx.scene.control.TableCell<Watchlist, LocalDateTime>() {
        private final java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        protected void updateItem(LocalDateTime item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(formatter.format(item));
            }
        }
    });

        watchlistDataTable.setItems(watchlistList);
        watchlistDataTable.setEditable(false);
}
        


    private void displayWatchlist(){

        ResultSet result = DatabaseHandler.getWatchlist();

        try {
            while (result.next()) {
                int watchlistID = result.getInt("watchlistID");
                String userName = result.getString ("userName");
                String contentTitle = result.getString("contentTitle");
                LocalDateTime watchlistDateTime = result.getTimestamp("watchlistDateTime").toLocalDateTime();

                Watchlist watchlist = new Watchlist(watchlistID, userName, contentTitle, watchlistDateTime);
                watchlistList.add(watchlist);
            }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        

// ================================= WATCHED ================================================================================================
    private void initializeWatchedCol(){    

        watchedIDCol.setCellValueFactory(new PropertyValueFactory<>("watchedID"));
        watchedUserNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        watchedContentCol.setCellValueFactory(new PropertyValueFactory<>("contentTitle"));
        watchedDateCol.setCellValueFactory(new PropertyValueFactory<>("watchedDateTime"));

        watchedDateCol.setCellFactory(column -> new javafx.scene.control.TableCell<Watched, LocalDateTime>() {
        private final java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        protected void updateItem(LocalDateTime item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(formatter.format(item));
            }
        }
    });

        watchedDataTable.setItems(watchedList);
        watchedDataTable.setEditable(false);
}
        

    private void displayWatched(){

        ResultSet result = DatabaseHandler.getWatched();

        try {
            while (result.next()) {
                int watchedID = result.getInt("watchedID");
                String userName = result.getString ("userName");
                String contentTitle = result.getString("contentTitle");
                LocalDateTime watchedDateTime = result.getTimestamp("watchedDateTime").toLocalDateTime();

                Watched watched = new Watched(watchedID, userName, contentTitle, watchedDateTime);
                watchedList.add(watched);
            }

            } catch (SQLException e) {
                e.printStackTrace();
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