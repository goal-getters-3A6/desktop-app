package edu.esprit.controllers;

import edu.esprit.entities.GMailer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;

public class EmailInputController {

    @FXML
    private AnchorPane emailinputpane;
    @FXML
    private VBox emailinputvbox;
    @FXML
    private TextField email;
    @FXML
    private TextField emailinputTxt;





    public void sendEmail(ActionEvent event) {
        try {
            String subject = "Congratulations";
            String message = "Dear reader,\nHello world.\n\nBest regards,";
            String recipient = email.getText();

            new GMailer().sendMail(subject, message, recipient);

            TrayNotification tray = new TrayNotification();
            tray.setTitle("Email sent");
            tray.setMessage("Check your email for the verification ");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(3));
            Stage stage = (Stage) emailinputpane.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        emailinputTxt.setText("Enter your email to receive a verification code");
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) emailinputpane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) emailinputpane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void maximize(ActionEvent event) {
        Stage stage = (Stage) emailinputpane.getScene().getWindow();
        stage.setFullScreen(true);
    }

    @FXML
    public void restore(ActionEvent event) {
        Stage stage = (Stage) emailinputpane.getScene().getWindow();
        stage.setFullScreen(false);
    }

    @FXML
    private void forgotPassword(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/emailinput.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newWindow = new Stage();
            newWindow.setTitle("Recover Password");
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
