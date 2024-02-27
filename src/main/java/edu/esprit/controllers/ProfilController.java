package edu.esprit.controllers;

import com.dlsc.phonenumberfx.PhoneNumberField;
import edu.esprit.entities.Client;
import edu.esprit.services.ClientService;
import edu.esprit.utils.SessionManagement;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.converter.LocalDateStringConverter;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import static edu.esprit.utils.UploadToDropBox.uploadPhoto;

public class ProfilController {

    @FXML
    private TextField nomTxt;
    @FXML
    private TextField prenomTxt;
    @FXML
    private TextField emailTxt;

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
    private ImageView userimage;

    @FXML
    private VBox profilvbox;
    String photoURL;
    private ClientService clientService = new ClientService();
    Client client = new Client();

    PhoneNumberField phoneNumberField = new PhoneNumberField();

    @FXML
    private void initialize() {
        try {
          client = clientService.getOneByEmail(SessionManagement.getEmail());
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
        }
        nomTxt.setText(client.getNom());
        prenomTxt.setText(client.getPrenom());
        emailTxt.setText(client.getMail());
        LocalDate ld = Instant.ofEpochMilli(client.getDate_naissance().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        dateNaissance.setValue(ld);
        if (client.getSexe().equals("Femme")){
            radioF.setSelected(true);
        }else{
            radioH.setSelected(true);
        }
        taille.setValue(client.getTaille());
        poids.setValue(client.getPoids());
        userimage.setImage(new Image(client.getImage().isEmpty() ? "https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/default-avatar.png" : client.getImage()));
        photoURL = client.getImage();
        phoneNumberField.setMaxWidth(150);

        phoneNumberField.setMaxHeight(26);

        phoneNumberField.setRawPhoneNumber(client.getTel());
        profilvbox.getChildren().add(phoneNumberField);

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
    private void modify(ActionEvent modify) throws SQLException {
        int userId = client.getId();
        String pass = client.getMdp();
        if (nomTxt.getText().isEmpty()
                || prenomTxt.getText().isEmpty()
                || emailTxt.getText().isEmpty()
                || dateNaissance.getValue() == null
                || phoneNumberField.getRawPhoneNumber().isEmpty()
                || poids.getValue() == 0
                || taille.getValue() == 0
                || photoURL.isEmpty() ) {
            TrayNotification tray = new TrayNotification();
            NotificationType notification = NotificationType.ERROR;
            tray.setNotificationType(notification);
            tray.setTitle("Error");
            tray.setMessage("All fields are required");
            tray.showAndDismiss(javafx.util.Duration.seconds(2));
            return;
        }
        try {
            clientService.modifier( new Client(userId,
                    nomTxt.getText(),
                    prenomTxt.getText(),
                    pass,
                    emailTxt.getText(),
                    phoneNumberField.getRawPhoneNumber(),
                    true, 0,
                    photoURL,
                    Date.from(dateNaissance.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    (float) poids.getValue(),
                    (float) taille.getValue(),
                    radioH.isSelected() ? "Homme" : "Femme"));

            TrayNotification tray = new TrayNotification();
            NotificationType notification = NotificationType.SUCCESS;
            tray.setNotificationType(notification);
            tray.setTitle("Success");
            tray.setMessage("Profile updated successfully");
            tray.showAndDismiss(javafx.util.Duration.seconds(2));

        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            TrayNotification tray = new TrayNotification();
            NotificationType notification = NotificationType.ERROR;
            tray.setNotificationType(notification);
            tray.setTitle("Error");
            tray.setMessage("Profile not updated");
            tray.showAndDismiss(javafx.util.Duration.seconds(2));
        }
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