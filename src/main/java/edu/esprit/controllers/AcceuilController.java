package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


import javax.swing.text.html.ImageView;
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
    private Hyperlink forgotpassword;
    @FXML
    private Hyperlink toregister;
    @FXML
    private ImageView imageuser;
    @FXML
    private MenuButton profilbuttonmenu;
    @FXML
    private MenuItem profilitem;
    @FXML
    private MenuItem logoutitem;
    @FXML
            private Button loginbtn;
    UserService userService = new UserService();

    @FXML
    public void toRegister(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/register.fxml"));
        acceuilpane.getChildren().setAll(pane);
    }

    public void initialize() {
        if (checkFile()) {
            mdpTxt.setVisible(false);
            emailTxt.setVisible(false);
            forgotpassword.setVisible(false);
            toregister.setVisible(false);
            loginbtn.setVisible(false);
            String imageURL = "https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/default-avatar.png";
            Image image = new Image(imageURL);
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            profilbuttonmenu.setGraphic(imageView);
            profilbuttonmenu.setText(getEmail());
        } else {
            mdpTxt.setVisible(true);
            emailTxt.setVisible(true);
            forgotpassword.setVisible(true);
            toregister.setVisible(true);
            profilbuttonmenu.setVisible(false);

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
                if (!checkFile()) {
                    saveSession(emailTxt.getText(), mdpTxt.getText());

                }

            } else {
                AnchorPane panee = FXMLLoader.load(getClass().getResource("/profil.fxml"));
                acceuilpane.getChildren().setAll(panee);
                String title = "Welcome!";
                String message = "";
                NotificationType notification = NotificationType.SUCCESS;

                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndDismiss(Duration.seconds(4));
                if (!checkFile()) {
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
    private void toProfile(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/profil.fxml"));
        acceuilpane.getChildren().setAll(pane);
    }
    @FXML
    private void logout(ActionEvent event) throws IOException {
        if (checkFile()) {
            deleteSession();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
            acceuilpane.getChildren().setAll(pane);
        }
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