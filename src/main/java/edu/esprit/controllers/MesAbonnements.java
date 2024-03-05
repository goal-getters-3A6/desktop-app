package edu.esprit.controllers;

import edu.esprit.entities.Abonnement;
import edu.esprit.entities.Client;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.sql.SQLException;
import java.util.List;

public class MesAbonnements {

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
    private Button btnmodifier;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private Button btnsupprimer;

    @FXML
    private GridPane gridpane;

    @FXML
    private ListView<Abonnement> listview;

    @FXML
    private ImageView logo1;

    @FXML
    private TextField kk;

    @FXML
    private DatePicker dateid;

    @FXML
    private ScrollPane scrollpane;

    ServiceAbonnement sr =new ServiceAbonnement();
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

    public void initialize() {
        // Initialiser la liste observable pour stocker les réservations
        ObservableList<Abonnement> AbonnementsList = FXCollections.observableArrayList();
        // Récupérer les réservations depuis le service ou tout autre moyen
        List<Abonnement> abonnements = null; // Récupérer les réservations depuis votre service
        abonnements = sr.getAllByUser("mayssahakimi@gmail.com"); // Suppose que vous avez une méthode getAll() dans votre service

        // Ajouter les réservations à la liste observable
        if (abonnements != null) {
            AbonnementsList.addAll(abonnements);
        }

        // Lier la liste observable à la ListView
        listview.setItems(AbonnementsList);

        // Personnaliser l'affichage des éléments de la ListView si nécessaire
        listview.setCellFactory(param -> new ListCell<Abonnement>() {
            @Override
            protected void updateItem(Abonnement abonnement, boolean empty) {
                super.updateItem(abonnement, empty);
                if (empty || abonnement == null) {
                    setText(null);
                } else {
                    // Assurez-vous que l'utilisateur associé à la réservation est un client
                    if (u instanceof Client) {
                        // Si c'est un client, afficher les détails de la réservation dans la cellule
                        //  Client client = (Client) reservation.getUser();
                        Client client = (Client) u; // Convertir l'utilisateur en client

                        setText("Mon Abonnement est de type  : " + abonnement.getTypeAb() +
                               ", sa date d'expiration est " + abonnement.getDateExpirationAb() +" avec un prix de "+ abonnement.getMontantAb());
                    } else {
                        // Si l'utilisateur n'est pas un client, afficher un message par défaut
                        setText("L'utilisateur associé à cette réservation n'est pas un client.");
                    }
                }
            }
        });

        // Gérer les événements de sélection dans la ListView
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour les champs de texte avec les détails de la réservation sélectionnée
                // Assurez-vous que l'utilisateur associé à la réservation est un client
                dateid.setValue(newValue.getDateExpirationAb().toLocalDate());
                kk.setText(String.valueOf(newValue.getMontantAb()));


            }
        });
    }

    @FXML
    void modifier(ActionEvent event) {

        String cat = kk.getText().trim();
// Définition des constantes pour les messages d'erreur
        final String ERROR_TITLE = "Error";

// Vérifier que les champs sont remplis et respectent les contraintes de saisie
        boolean isValid = true;
        if (cat.isEmpty()) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ERROR_TITLE);
            alert.setContentText("Veuillez remplir le montant.");
            alert.showAndWait();
        }
        if (isValid) {
            // Les saisies sont valides, procéder à la modification
            Abonnement selectedReservation = listview.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                // Modifier le type d'abonnemnts du client
                selectedReservation.setMontantAb(Float.parseFloat(cat));
                Float nouveauMontant = (float) Double.parseDouble(cat);
                // Vérifier que le nouveau montant est supérieur au montant existant + 50
                if (nouveauMontant > selectedReservation.getMontantAb() + 50){
                    // Modifier le type d'abonnement du client
                    selectedReservation.setTypeAb(cat);}
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Veuillez saisir un montant supérieure de 50 au celle avant prolongement");
                    alert.showAndWait();
                    }

                    LocalDate newExpirationDate = dateid.getValue();
                    // Check if the expiration date is at least 3 days in the future
                    LocalDate currentDate = LocalDate.now();
                    Period period = Period.between(currentDate, selectedReservation.getDateExpirationAb().toLocalDate());

                    if (period.getDays() <= 3) {
                        // Update the expiration date
                        selectedReservation.setDateExpirationAb(java.sql.Date.valueOf(newExpirationDate));


                        // Mettre à jour l'abonnement dans la base de données
                        sr.modifier(selectedReservation);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Modification");
                        alert.setContentText("Votre réclamation est modifiée avec succès.");
                        alert.showAndWait();

                        //  clearForm(); // Effacer les champs du formulaire après la modification
                        listview.refresh();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setContentText("Votre abonnement reste encore valable");
                        alert.showAndWait();
                    }
                }
            else {
                    // Afficher un message à l'utilisateur indiquant qu'aucune réservation n'a été sélectionnée
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(ERROR_TITLE);
                    alert.setContentText("Veuillez sélectionner une abonnement à modifier.");
                    alert.showAndWait();
                }

            }
        }



    @FXML
    void abonnement(ActionEvent event) {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesAbonnements.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TypeAbonnements.fxml"));

            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) listview.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
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

    @FXML
    void supprimer(ActionEvent event) {

    }

}
