package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Id;
import edu.esprit.services.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tray.notification.TrayNotification;

import javax.swing.text.html.ImageView;
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

    }

    @FXML
    void profil(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }
}