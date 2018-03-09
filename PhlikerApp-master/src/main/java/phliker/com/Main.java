package phliker.com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage mainStage;

    /**
     * Main method of the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);

    }

    /**
     * Start method for setting root stage
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainStage = primaryStage;

        AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("PhotoView.fxml"));
        primaryStage.setTitle("Phliker");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
