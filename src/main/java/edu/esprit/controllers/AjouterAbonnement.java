package edu.esprit.controllers;


import edu.esprit.entities.Abonnement;
import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceAbonnement;
import edu.esprit.utils.SessionManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//
//
//


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AjouterAbonnement {

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
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private TextField codeid;

    @FXML
    private DatePicker dateid;

    @FXML
    private TextField emailid;

    @FXML
    private ImageView logo1;

    @FXML
    private TextField montantid;

    @FXML
    private TextField typeAbid;

    private final ServiceAbonnement SA = new ServiceAbonnement();
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
    void initialize()
    {
        emailid.setText(u.getMail());
       // String clientEmail = emailid.getText();


    }

    @FXML
    void AjoutAbonnement(ActionEvent event) {
        try {
            emailid.setText(u.getMail());
            String str = String.valueOf(dateid.getValue());
            LocalDate date = LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
            java.sql.Date sqlDate = java.sql.Date.valueOf( date );
            //User us1 =new User (1,"mayssa","hakimi");

            SA.ajouter(new Abonnement(Float.parseFloat(montantid.getText()),codeid.getText(),"Ordinaire",sqlDate,u));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Abonnement added succesfully");
            alert.showAndWait();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

