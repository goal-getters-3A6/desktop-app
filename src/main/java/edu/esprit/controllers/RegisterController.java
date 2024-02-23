package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.services.ClientService;
import edu.esprit.utils.SessionManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

import static edu.esprit.utils.UploadToDropBox.uploadPhoto;

public class RegisterController {
    @FXML
    private TextField nomTxt;
    @FXML
    private TextField prenomTxt;
    @FXML
    private TextField emailTxt; // Value injected by FXMLLoader
    @FXML
    private PasswordField mdpRegisterTxt; // Value injected by FXMLLoader
    @FXML
    private DatePicker dateNaissance;
    @FXML
    private TextField telTxt;
    @FXML
    private AnchorPane registerpane;



    ClientService clientService = new ClientService();
    String photoURL = "";



    @FXML
    private void importProfilePic(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your profile pic");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        Window stage = null;
        String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
        String name = "/" + fileChooser.showOpenDialog(stage).getName();
        photoURL = uploadPhoto(path, name);
    }
    @FXML
    void register() throws ParseException, IOException{

        if ((emailTxt.getText().isBlank())
                || (nomTxt.getText().isBlank())
                || (prenomTxt.getText().isBlank())
                || (mdpRegisterTxt.getText().isBlank())
                || (telTxt.getText().isBlank())
                || (dateNaissance.getValue() == null))
        {
            String title = "Verify your information!";
            String message = "You must fill all the fields!";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(3));

        } else {
            LocalDate localDate = dateNaissance.getValue();
            Date date = java.sql.Date.valueOf(localDate);
            Client client = new Client(nomTxt.getText(), prenomTxt.getText(), mdpRegisterTxt.getText(), emailTxt.getText(), telTxt.getText(), true, 0,photoURL , date );
           if( clientService.ajouterClient(client)) {
               String title = "Welcome!";
               String message = "You have been registered successfully!";
               NotificationType notification = NotificationType.SUCCESS;
               TrayNotification tray = new TrayNotification();
               tray.setTitle(title);
               tray.setMessage(message);
               tray.setNotificationType(notification);
               tray.showAndDismiss(Duration.seconds(3));
               Id.user = clientService.getClientByEmail(emailTxt.getText()).getId();
               SessionManagement.saveSession(emailTxt.getText(), mdpRegisterTxt.getText());
               AnchorPane pane = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
               registerpane.getChildren().setAll(pane);
           } else {
               String title = "Something went wrong!";
               String message = "Verify your informations !";
               NotificationType notification = NotificationType.ERROR;
               TrayNotification tray = new TrayNotification();
               tray.setTitle(title);
               tray.setMessage(message);
               tray.setNotificationType(notification);
               tray.showAndDismiss(Duration.seconds(3));
           }
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