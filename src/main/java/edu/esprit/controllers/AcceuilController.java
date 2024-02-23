package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


import java.io.IOException;

import static edu.esprit.utils.SessionManagement.*;

public class AcceuilController {

    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField mdpTxt;

    @FXML
    private AnchorPane acceuilpane;
    @FXML
    public Hyperlink toRegister;
    @FXML
    private Hyperlink forgot;
    UserService userService = new UserService();

    @FXML
    public void toRegister(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/register.fxml"));
        acceuilpane.getChildren().setAll(pane);
    }

    public void initialize() {
        if (checkFile() == true) {
            emailTxt.setText(getEmail());
            mdpTxt.setText(getPassword());
        }
    }

    @FXML
    void login() throws IOException {
        if ((Id.user = userService.checklogin(emailTxt.getText(), mdpTxt.getText())) != null) {
            User u = userService.findUserById(Id.user);
            if (u.getRole().equals("ADMIN")) {
                AnchorPane panee = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
                acceuilpane.getChildren().setAll(panee);
                String title = "Welcome!";
                String message = "";
                NotificationType notification = NotificationType.SUCCESS;
                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndDismiss(Duration.seconds(4));
                if (checkFile()==false) {
                    saveSession(emailTxt.getText(), mdpTxt.getText());
                }

            } else {
                AnchorPane panee = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
                acceuilpane.getChildren().setAll(panee);
                String title = "Welcome!";
                String message = "";
                NotificationType notification = NotificationType.SUCCESS;

                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndDismiss(Duration.seconds(4));
                if (checkFile()==false) {
                    saveSession(emailTxt.getText(), mdpTxt.getText());
                }
            }
        } else {
            String title = "Something went wrong!";
            String message = "Verify your informations !";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(4));
        }
 

    }


    @FXML
    private void forgotPassword(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/forgotPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("Recover Password");
        newWindow.setScene(scene);
        newWindow.show();
    }
    @FXML
    void abonnement(ActionEvent event) {

    }

    @FXML
    void accueil(ActionEvent event) {

    }

    @FXML
    void alimentaire(ActionEvent event) {

    }

    @FXML
    void equipement(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {

    }


    @FXML
    void planning(ActionEvent event) {

    }

    @FXML
    void profil(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

}