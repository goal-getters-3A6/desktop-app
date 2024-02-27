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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

                        setText("Mon Abonnement  : " + abonnement.getTypeAb() +
                               ", Date d'expiration " + client.getNom());
                             //  ", Prénom de la personne: " + client.getPrenom()+"poids: "+client.getPoids()+"Taille" + client.getTaille());
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

                kk.setText(newValue.getTypeAb());


            }
        });
    }

    @FXML
    void modifier(ActionEvent event) {

        String type = kk.getText().trim();
       // String prenomPersonne = textfieldprenom.getText().trim();


// Expressions régulières pour le nom et le prénom
        String nomRegex = "^[A-Za-zÀ-ÿ\\s-]{1,50}$"; // Autorise les lettres, les espaces, les tirets et les apostrophes, jusqu'à 50 caractères
        String prenomRegex = "^[A-Za-zÀ-ÿ-]{1,50}$"; // Autorise les lettres et les tirets, jusqu'à 50 caractères

// Définition des constantes pour les messages d'erreur
        final String ERROR_TITLE = "Validation Error";
        final String ERROR_STYLE = "-fx-text-fill: red;";

// Vérifier que les champs sont remplis et respectent les contraintes de saisie
        boolean isValid = true;
        if (type.isEmpty() ) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ERROR_TITLE);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            if (!type.matches(nomRegex)) {
                isValid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir un nom valide.");
                alert.showAndWait();
            }
           /* if (!prenomPersonne.matches(prenomRegex)) {
                isValid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir un prénom valide.");
                alert.showAndWait();
            }*/
        }

        if (isValid) {
            // Les saisies sont valides, procéder à la modification
            Abonnement selectedReservation = listview.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                //Client client = (Client) selectedReservation.getUser();
                // Modifier les propriétés du client
                // selectedReservation.setPoids(Float.parseFloat(poidsStr));
                // selectedReservation.setTaille(Float.parseFloat(tailleStr));

               // u.setNom(nomPersonne);
                //u.setPrenom(prenomPersonne);

                // Mettre à jour la réservation dans la base de données
                sr.modifier(selectedReservation);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification");
                alert.setContentText("Réservation modifiée avec succès.");
                alert.showAndWait();

                // clearForm(); // Effacer les champs du formulaire après la modification
                listview.refresh();
            } else {
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
