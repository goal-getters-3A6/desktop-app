package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReclamation;
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

public class MesReclamations {

    @FXML
    private TextField aaa;

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
    private ListView<Reclamation> listview;

    @FXML
    private ImageView logo1;

    @FXML
    private ScrollPane scrollpane;

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
    private TextArea descriptionRecid;

    ServiceReclamation sr =new ServiceReclamation();
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
        System.out.println("Initialize method called");

        // Initialiser la liste observable pour stocker les réservations
        ObservableList<Reclamation> ReclamationsList = FXCollections.observableArrayList();
        // Récupérer les réservations depuis le service ou tout autre moyen
        List<Reclamation> reclamations = null; // Récupérer les réservations depuis votre service
        reclamations = sr.getAllByUser("mayssahakimi@gmail.com"); // Suppose que vous avez une méthode getAll() dans votre service

        // Ajouter les réservations à la liste observable
        if (reclamations != null) {
            ReclamationsList.addAll(reclamations);
        }

        // Lier la liste observable à la ListView
        listview.setItems(ReclamationsList);

        // Personnaliser l'affichage des éléments de la ListView si nécessaire
        listview.setCellFactory(param -> new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                } else {
                    // Assurez-vous que l'utilisateur associé à la réservation est un client
                    if (u instanceof Client) {

                        Client client = (Client) u; // Convertir l'utilisateur en client

                        setText("Ma Reclamation concernant : " + reclamation.getCategorieRec() + "     "   +
                         " Son conntenu comme suit " + reclamation.getDescriptionRec());
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

                aaa.setText(newValue.getCategorieRec());
                descriptionRecid.setText(newValue.getDescriptionRec());


            }
        });
    }


    @FXML
    void modifier(ActionEvent event) {

        String cat = aaa.getText().trim();
        String desc =descriptionRecid.getText().trim();

// Expressions régulières pour le nom et le prénom
        String CatRegex = "^[A-Za-zÀ-ÿ\\s-]{1,50}$"; // Autorise les lettres, les espaces, les tirets et les apostrophes, jusqu'à 50 caractères
        //String prenomRegex = "^[A-Za-zÀ-ÿ-]{1,50}$"; // Autorise les lettres et les tirets, jusqu'à 50 caractères
        String descRegex = "^[A-Za-zÀ-ÿ].*\\.$";
// Définition des constantes pour les messages d'erreur
        final String ERROR_TITLE = "Error";

// Vérifier que les champs sont remplis et respectent les contraintes de saisie
        boolean isValid = true;
        if (cat.isEmpty() || desc.isEmpty()) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ERROR_TITLE);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            if (!cat.matches(CatRegex) || !desc.matches(descRegex)) {
                isValid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir en commencant par majuscule ");
                alert.showAndWait();


            }

            if (isValid) {
                // Les saisies sont valides, procéder à la modification
                Reclamation selectedReservation = listview.getSelectionModel().getSelectedItem();
                if (selectedReservation != null) {
                    //Client client = (Client) selectedReservation.getUser();
                    // Modifier les propriétés du client
                    // selectedReservation.setPoids(Float.parseFloat(poidsStr));
                    // selectedReservation.setTaille(Float.parseFloat(tailleStr));


                    selectedReservation.setCategorieRec(cat);
                    selectedReservation.setDescriptionRec(desc);

                    // Mettre à jour la réservation dans la base de données
                    sr.modifier(selectedReservation);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification");
                    alert.setContentText("Votre réclamation est modifiée avec succès.");
                    alert.showAndWait();

                    //  clearForm(); // Effacer les champs du formulaire après la modification
                    listview.refresh();
                } else {
                    // Afficher un message à l'utilisateur indiquant qu'aucune réservation n'a été sélectionnée
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(ERROR_TITLE);
                    alert.setContentText("Veuillez sélectionner une réclamation à modifier.");
                    alert.showAndWait();
                }

            }
        }
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
