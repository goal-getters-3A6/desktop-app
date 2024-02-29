package edu.esprit.tests;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainFx extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
      //  FXMLLoader loader= new FXMLLoader(getClass().getResource("/TypeAbonnements.fxml"));
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/AbonnemntsBack.fxml"));
      //  FXMLLoader loader= new FXMLLoader(getClass().getResource("/acceuil.fxml"));

        Parent root=loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setTitle("GoFit");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

