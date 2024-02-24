package edu.esprit.controllers;

import com.dlsc.phonenumberfx.PhoneNumberField;
import edu.esprit.entities.Client;
import edu.esprit.services.ClientService;
import edu.esprit.utils.SessionManagement;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.sql.SQLException;
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
    private AnchorPane registerpane;

    @FXML
            private RadioButton radioH;
    @FXML
            private RadioButton radioF;
    @FXML
            private Slider poids;
    @FXML
            private Slider taille;
    @FXML
            private Label poidslabel;
    @FXML
            private  Label taillelabel;

    @FXML
    private VBox registervbox;

    PhoneNumberField phoneNumberField = new PhoneNumberField();
    ClientService clientService = new ClientService();
    String photoURL = "";



    @FXML
    void initialize() {

        phoneNumberField.setMaxWidth(150);
        phoneNumberField.setMaxHeight(26);
        phoneNumberField.setPromptText("Phone number");
        registervbox.getChildren().add(phoneNumberField);

        poidslabel.textProperty().bind(
                Bindings.format(
                        "%.2f",
                        poids.valueProperty()
                )
        );
        taillelabel.textProperty().bind(
                Bindings.format(
                        "%.2f",
                        taille.valueProperty()
                )
        );

    }
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
    void register() throws  IOException{
        if ((emailTxt.getText().isBlank())
                || (nomTxt.getText().isBlank())
                || (prenomTxt.getText().isBlank())
                || (mdpRegisterTxt.getText().isBlank())
                || (phoneNumberField.getRawPhoneNumber().isBlank())
                || (dateNaissance.getValue() == null)
                || (!radioH.isSelected() && !radioF.isSelected())
                || (poids.getValue() == 0)
                || (taille.getValue() == 0)
        )
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
            Client client = new Client(nomTxt.getText(), prenomTxt.getText(), mdpRegisterTxt.getText(), emailTxt.getText(), phoneNumberField.getRawPhoneNumber(), true, 0,photoURL , date, (float) poids.getValue(), (float) taille.getValue(), radioH.isSelected() ? "Homme" : "Femme");
           try {
               clientService.ajouter(client);
               String title = "Welcome!";
               String message = "You have been registered successfully!";
               NotificationType notification = NotificationType.SUCCESS;
               TrayNotification tray = new TrayNotification();
               tray.setTitle(title);
               tray.setMessage(message);
               tray.setNotificationType(notification);
               tray.showAndDismiss(Duration.seconds(3));
               Id.user = clientService.getOneByEmail(emailTxt.getText()).getId();
               SessionManagement.saveSession(emailTxt.getText(), mdpRegisterTxt.getText());
               AnchorPane pane = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
               registerpane.getChildren().setAll(pane);

           } catch (SQLException e){

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