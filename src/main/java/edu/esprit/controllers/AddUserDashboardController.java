package edu.esprit.controllers;

import com.dlsc.phonenumberfx.PhoneNumberField;
import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Id;
import edu.esprit.services.AdminService;
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
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

import static edu.esprit.utils.UploadToDropBox.uploadPhoto;

public class AddUserDashboardController {
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
    private AnchorPane adduserpane;

    @FXML
    private RadioButton radioH;
    @FXML
    private RadioButton radioF;
    @FXML
    private RadioButton radioClient;
    @FXML
    private RadioButton radioAdmin;
    @FXML
    private Slider poids;
    @FXML
    private Slider taille;
    @FXML
    private Label poidslabel;
    @FXML
    private  Label taillelabel;

    @FXML
    private VBox adduservbox;

    PhoneNumberField phoneNumberField = new PhoneNumberField();
    ClientService clientService = new ClientService();
    AdminService adminService = new AdminService();
    String photoURL = "";
    String emailRegex = "^(.+)@(\\S+)$";
    String phoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";



    @FXML
    void initialize() {

        phoneNumberField.setMaxWidth(150);
        phoneNumberField.setMaxHeight(26);
        phoneNumberField.setPromptText("Phone number");
        adduservbox.getChildren().add(phoneNumberField);

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
    void add() throws IOException {
        if( radioClient.isSelected()){
        if (emailTxt.getText().isBlank() || !patternMatches(emailTxt.getText(), emailRegex) )
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
                Stage stage = (Stage) adduserpane.getScene().getWindow();
                stage.close();

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
        else if (radioAdmin.isSelected()){
            if (emailTxt.getText().isBlank() || !patternMatches(emailTxt.getText(), emailRegex) ) {
                String title = "Verify your information!";
                String message = "Email is not valid!";
                NotificationType notification = NotificationType.ERROR;
                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndDismiss(Duration.seconds(3));
            } else if (!mdpRegisterTxt.getText().equals(mdpRegisterTxt1.getText())) {
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
            ) {
                String title = "Verify your information!";
                String message = "You must fill all the fields!";
                NotificationType notification = NotificationType.ERROR;
                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndDismiss(Duration.seconds(3));
            }
            else {
                try {
                    adminService.ajouter(new Admin(nomTxt.getText(), prenomTxt.getText(), mdpRegisterTxt.getText(), emailTxt.getText(), photoURL));
                    String title = "Welcome!";
                    String message = "Admin was added successfully!";
                    NotificationType notification = NotificationType.SUCCESS;
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle(title);
                    tray.setMessage(message);
                    tray.setNotificationType(notification);
                    tray.showAndDismiss(Duration.seconds(3));
                    // close window
                    Stage stage = (Stage) adduserpane.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    String title = "Something went wrong!";
                    NotificationType notification = NotificationType.ERROR;
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle(title);
                    tray.setNotificationType(notification);
                    tray.showAndDismiss(Duration.seconds(3));
                }

            }
        } else {
            String title = "Verify your information!";
            String message = "You must choose a role!";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(3));
        }
    }
    public static boolean patternMatches(String stringToValidate, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(stringToValidate)
                .matches();
    }
}
