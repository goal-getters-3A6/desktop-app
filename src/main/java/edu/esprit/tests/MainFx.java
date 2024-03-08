package edu.esprit.tests;

import edu.esprit.controllers.WeatherAppGui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private WeatherAppGui weatherAppGui;
    @Override
    public void start(Stage primaryStage) throws Exception {
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/acceuil.fxml"));

       // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Chatbot.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Go Fit Pro");
        primaryStage.setScene(scene);
        primaryStage.show();

        weatherAppGui = new WeatherAppGui();
    }


    public void showWeatherApp() {
        WeatherAppGui weatherAppGui = new WeatherAppGui();
        weatherAppGui.setVisible(true);
    }

}
