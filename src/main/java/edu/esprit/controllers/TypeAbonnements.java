package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.services.ClientService;
import edu.esprit.utils.SessionManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class TypeAbonnements {


    @FXML
    private AnchorPane anchorpanedash;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnaccueil;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnf;

    @FXML
    private Button btnor;

    @FXML
    private Button btnp;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private ImageView logo1;

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
    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    // UserService us=new UserService();
    ClientService cs=new ClientService();
    Client u;

    {
        try {
            u = cs.getOneByEmail(mail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
   @FXML
    void remplir_ordinaire(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));
       Parent root = loader.load();
       btnor.getScene().setRoot(root);
   }

}
