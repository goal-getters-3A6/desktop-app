package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.UserService;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

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


    @FXML
    private void initialize() {
        Client client = clientService.getClientByEmail("mayarboukraa@gmail.com");
        nomTxt.setText(client.getNom());
        prenomTxt.setText(client.getPrenom());
        emailTxt.setText(client.getMail());
    }

    @FXML
    private void modify(ActionEvent modify) throws IOException {
        int userId = clientService.getClientByEmail(emailTxt.getText()).getId();
        System.out.println(userId);
        Client client = new Client(userId,nomTxt.getText(), prenomTxt.getText(), emailTxt.getText());
        if (clientService.modifierClient(client)) {
            System.out.println("Modify success");
        } else {
            System.out.println("Modify failed");
        }
    }
    // Champ pour stocker l'e-mail de l'utilisateur
    private String userEmail;
    public void initData(String email) {
        this.userEmail = email;
        // Vous pouvez faire d'autres traitements avec l'e-mail si nécessaire
        // Par exemple, afficher l'e-mail dans un champ de texte ou effectuer des requêtes supplémentaires avec cet e-mail
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