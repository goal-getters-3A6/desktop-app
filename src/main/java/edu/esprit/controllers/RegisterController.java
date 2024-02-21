package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.services.ClientService;
import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;

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


    ClientService clientService = new ClientService();
    Image image1 = null;
    File file = null;
    @FXML
    private void importProfilePic(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your profile pic");
        Window stage = null;
        file = fileChooser.showOpenDialog(stage);
        image1 = new Image(file.toURI().toString());


    }
    @FXML
    void register() throws Exception {
        LocalDate localDate = dateNaissance.getValue();
        Date date = java.sql.Date.valueOf(localDate);
        Client client = new Client(nomTxt.getText(), prenomTxt.getText(), mdpRegisterTxt.getText(), emailTxt.getText(), telTxt.getText(), true, 0,file.toURI().toString().getBytes() ,date );
        if (clientService.ajouterClient(client)) {
            System.out.println("Register success");
        } else {
            System.out.println("Register failed");
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