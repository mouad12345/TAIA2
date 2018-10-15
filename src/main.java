import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
    public static controller controller;
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fenetre1.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Tp TAIA");
        primaryStage.setScene(scene);
        primaryStage.show();
        controller=loader.getController();

    }

    public static void main(String[] args) { launch(args); }


}
