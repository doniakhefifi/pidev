package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {


    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LivreurCrud.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Livreur");
        stage.setScene(scene);
           stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
