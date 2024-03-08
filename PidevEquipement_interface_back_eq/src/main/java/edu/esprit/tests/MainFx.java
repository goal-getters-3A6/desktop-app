package edu.esprit.tests;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterEquipement.fxml"));
        //FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterEquipementFront.fxml"));
       // FXMLLoader loader= new FXMLLoader(getClass().getResource("/AvisEquipementFront.fxml"));
       //  FXMLLoader loader= new FXMLLoader(getClass().getResource("/AvisEquipementBack.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ajouter equipement");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
