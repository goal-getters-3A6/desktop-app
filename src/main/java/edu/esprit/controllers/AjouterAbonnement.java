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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//
//
//


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private Label emailid;

    @FXML
    private ImageView logo1;

    @FXML
    private TextField montantid;

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

    }

    @FXML
    void AjoutAbonnement(ActionEvent event) {
        try {

            String code = codeid.getText();
            if (!code.matches("GoFit[2-5][0-9]")) {
                showAlert("Validation du Code", "Format de code invalide. Il doit être de GoFit20 à GoFit50.");
                return;
            }


            LocalDate selectedDate = dateid.getValue();
            if (selectedDate == null || selectedDate.isBefore(LocalDate.now())) {
                showAlert("Validation de la Date", "Date invalide. Veuillez sélectionner une date future.");
                return;
            }


            String montant = montantid.getText();
            if (!montant.equals("50")) {
                showAlert("Validation du Montant", "Montant invalide. Il doit être exactement de 50 Dinars.");
                return;
            }
            String str = String.valueOf(dateid.getValue());
            LocalDate date = LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
            java.sql.Date sqlDate = java.sql.Date.valueOf( date );
            //User us1 =new User (1,"mayssa","hakimi");

            SA.ajouter(new Abonnement(Float.parseFloat(montantid.getText()),codeid.getText(),"Ordinaire",sqlDate,u));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("\"Abonnement enregistré\", \"Félicitations ! Votre abonnement a été enregistré avec succès. Merci ! ");

            alert.showAndWait();
            clearForm ();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearForm () {
        codeid.setText("");
        montantid.setText("");
        dateid.setValue(null);


    }
    @FXML
    void abonnement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesAbonnements.fxml"));
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/TypeAbonnements.fxml"));

            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) emailid.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }
    @FXML
    void test(ActionEvent event) {

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

