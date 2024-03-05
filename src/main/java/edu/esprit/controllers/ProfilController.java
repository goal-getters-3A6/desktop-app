//package edu.esprit.controllers;

/*import edu.esprit.entities.Client;
import edu.esprit.entities.Id;
import edu.esprit.services.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tray.notification.TrayNotification;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ProfilController {

    @FXML
    private AnchorPane profile;
    @FXML
    public ImageView profilepic;
    @FXML
    public TextField nomTxt;
    @FXML
    public TextField prenomTxt;

    @FXML
    public TextField emailTxt;
    @FXML
    private Button modifierBtn;
    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnmodifierreservation;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private Button btnsupprimerreservation;


    @FXML
    private ImageView imageuser;

    @FXML
    private ImageView logo;

    private ClientService clientService = new ClientService();
    Client client = new Client();

    @FXML
    private void initialize() {
        try {
            client    = clientService.getOneById(Id.user);
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
        }

        nomTxt.setText(client.getNom());
        prenomTxt.setText(client.getPrenom());
        emailTxt.setText(client.getMail());
    }

    @FXML
    private void modify(ActionEvent modify) throws SQLException {
        int userId = clientService.getOneByEmail(emailTxt.getText()).getId();
        System.out.println(userId);
        Client client = new Client(userId,nomTxt.getText(), prenomTxt.getText(), emailTxt.getText());
        try {
            clientService.modifier(client);
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Success");
            tray.setMessage("Profile updated successfully");
            tray.showAndDismiss(javafx.util.Duration.seconds(2));

        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Error");
            tray.setMessage("Profile not updated");
            tray.showAndDismiss(javafx.util.Duration.seconds(2));
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
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leseancesfront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnplanning.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void profil(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }
}*/
package edu.esprit.controllers;

import com.dlsc.phonenumberfx.PhoneNumberField;
import edu.esprit.entities.Client;
import edu.esprit.services.ClientService;
import edu.esprit.utils.SessionManagement;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
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
    private Button btnplanning;
    @FXML
    private VBox profilvbox;
    String photoURL;
    private final ClientService clientService = new ClientService();
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
      //  userimage.setImage(new Image(client.getImage().isEmpty() ? "https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/default-avatar.png" : client.getImage()));
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
    private void modify() {
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
    private void importProfilePic() {
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
    void abonnement() {

    }

    @FXML
    void accueil() {

    }

    @FXML
    void alimentaire() {

    }

    @FXML
    void equipement() {

    }

    @FXML
    void evenement() {

    }


    @FXML
    void planning() {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leseancesfront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnplanning.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }

    }

    @FXML
    void reclamation() {

    }
}