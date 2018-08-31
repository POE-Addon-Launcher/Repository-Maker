import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 */
public class Launcher extends Application
{
    public static Stage stage;
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/ui.fxml"));
        primaryStage.setTitle("PAL: Repo Maker");
        Scene scene = new Scene(root, 355, 625);
        scene.getStylesheets().add("text.css");
        primaryStage.setScene(scene);
        stage = primaryStage;
        primaryStage.show();
    }
}
