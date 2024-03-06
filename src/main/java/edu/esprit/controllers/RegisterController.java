package edu.esprit.controllers;

import com.dlsc.phonenumberfx.PhoneNumberField;
import edu.esprit.entities.Client;
import edu.esprit.entities.Id;
import edu.esprit.services.ClientService;
import edu.esprit.utils.SessionManagement;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

import static edu.esprit.utils.UploadToDropBox.uploadPhoto;


public class RegisterController {
    @FXML
    private TextField nomTxt;
    @FXML
    private TextField prenomTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField mdpRegisterTxt;
    @FXML
    private PasswordField mdpRegisterTxt1;
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
    String emailRegex = "^(.+)@(\\S+)$";
    String phoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";



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
    private void importProfilePic(ActionEvent event) throws FileNotFoundException {
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
        if ( emailTxt.getText().isBlank() || !patternMatches(emailTxt.getText(), emailRegex))
        {
            String title = "Verify your information!";
            String message = "Email is not valid!";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(3));
        } else if (phoneNumberField.getRawPhoneNumber() ==null || !patternMatches(phoneNumberField.getRawPhoneNumber(), phoneRegex) ) {
            String title = "Verify your information!";
            String message = "Phone number is not valid!";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(3));
        } else if (dateNaissance.getValue() == null || dateNaissance.getValue().isAfter(LocalDate.now().minusYears(14))){
            String title = "Warning!";
            String message = "You must be older than 14!";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(3));
        } else if ( !mdpRegisterTxt.getText().equals(mdpRegisterTxt1.getText())) {
            String title = "Verify your information!";
            String message = "Passwords don't match!";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(3));
        } else if (
                 (nomTxt.getText().isBlank())
                || (prenomTxt.getText().isBlank())
                || (mdpRegisterTxt.getText().isBlank())
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
    public static boolean patternMatches(String stringToValidate, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(stringToValidate)
                .matches();
    }

    @FXML
    void abonnement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesAbonnements.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerpane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void accueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/acceuil.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerpane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    @FXML
    void alimentaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPlat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerpane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void equipement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementFront.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerpane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void evenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_evenement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerpane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    void planning(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservationsclient.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerpane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void reclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerpane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}