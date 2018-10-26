import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ScreenSize;


public class Main extends Application {

    //this is where program starts....
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/LoginPage.fxml"));
        Parent loginScreen = loader.load();
        Scene scene = new Scene(loginScreen, ScreenSize.getScreenWidth() - 20, ScreenSize.getScreenHeight()-60);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CENTRAL PERTH HOSPITAL");
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
