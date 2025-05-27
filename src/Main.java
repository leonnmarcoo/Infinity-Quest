import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Admin/FXML/AdminLogin.fxml"));
        primaryStage.setTitle("Avengers");
        primaryStage.setScene(new Scene(root, 1512, 982));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    } 
}