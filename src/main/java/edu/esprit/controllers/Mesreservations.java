package edu.esprit.controllers;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mesreservations {

    @FXML
    private TableColumn<?, ?> age;

    @FXML
    private Button btnaccueil;
    @FXML
    private Button btnabonnement;
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
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;

    @FXML
    private RadioButton femme;

    @FXML
    private RadioButton homme;
    @FXML
    private ImageView logo;

    @FXML
    private TextField nom;
    @FXML
    private TextField agef;
    @FXML
    private TextField poidsf;
    @FXML
    private TextField prenomf;
    @FXML
    private TextField taillef;
    @FXML
    private Label seancelabel;

    @FXML
    private TableColumn<?, ?> nompersonne;


    @FXML
    private TableColumn<?, ?> nomseance;

    @FXML
    private TableColumn<?, ?> poids;

    @FXML
    private TableColumn<?, ?> prenom;

    @FXML
    private TableColumn<?, ?> sexe;

    @FXML
    private TableColumn<?, ?> taille;
    @FXML
    private TableView<Reservation> tablereservation;
    ServiceReservation sr =new ServiceReservation();
    private ToggleGroup sexeToggleGroup;
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
    void abonnement(ActionEvent event) {

    }

    @FXML
    void planning(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leseancesfront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnplanning.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void profil(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }
    public void initialize() throws IOException {

        // Créer un groupe pour les RadioButton
        sexeToggleGroup = new ToggleGroup();

        // Ajouter les RadioButton au groupe
        homme.setToggleGroup(sexeToggleGroup);
        femme.setToggleGroup(sexeToggleGroup);
        tablereservation.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifie si c'est un simple clic
                Reservation selectedSeance = tablereservation.getSelectionModel().getSelectedItem();
                if (selectedSeance != null) {
                    // Afficher les informations de la séance sélectionnée dans le formulaire
                    displayReservationInfo(selectedSeance);
                }
            }
        });
        ShowReservation();
    }

    @FXML
    void modifierreservation(ActionEvent event) {

        // Récupérer les informations modifiées depuis le formulaire
        String nomseance=seancelabel.getText();
       String noms = nom.getText();
        String prenom = prenomf.getText();
        String ageStr = agef.getText();
        String poidsStr = poidsf.getText();
        String tailleStr = taillef.getText();
        String sexe = getSelectedSexe();
        final String ERROR_TITLE = "Validation Error";
        final String ERROR_STYLE = "-fx-text-fill: red;";
        final String ERROR_MSG_GENERIC = "Please correct the input fields marked in red.";

        // Mettre à jour la séance dans la base de données
        Reservation selectedSeance = tablereservation.getSelectionModel().getSelectedItem();
        if (selectedSeance != null) {
            selectedSeance.setNom(noms);
            selectedSeance.setPrenom(prenom);
            selectedSeance.setAge(Integer.parseInt(ageStr));
            selectedSeance.setPoids(Float.parseFloat(poidsStr));
            selectedSeance.setTaille(Float.parseFloat(tailleStr));
            selectedSeance.setSexe(sexe);


            // Mettre à jour la séance dans la base de données
            try {
                sr.modifier(selectedSeance);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification");
                alert.setContentText("reservation modified succesfully");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Exeption");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            // Rafraîchir l'affichage des séances dans la TableView


        }
        clearForm();
        try {
            ShowReservation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerreservation(ActionEvent event) {
        Reservation selectedSeance = tablereservation.getSelectionModel().getSelectedItem();

        if (selectedSeance != null) {
            // Supprimer la séance de la base de données
            try {
                sr.supprimer(selectedSeance.getIdReservation());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression");
                alert.setContentText("Seance Deleted succesfully");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Exeption");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            // Rafraîchir les données dans le TableView
            try {
                ShowReservation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Afficher un message à l'utilisateur indiquant qu'aucune séance n'a été sélectionnée
            System.out.println("Veuillez sélectionner une séance à supprimer.");
        }
        clearForm();
    }
    private void clearForm () {
        nom.clear();
        prenomf.clear();
        agef.clear();
        poidsf.clear();
        taillef.clear();
        // Désélectionner les boutons radio du groupe "sexe"
        sexeToggleGroup.getSelectedToggle().setSelected(false);
    }
    List<Reservation> resList;
    public void ShowReservation() throws IOException {

        try {
            resList = sr.getAll();
            System.out.println(resList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Reservation> filtredResList= new ArrayList<>();
        User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
        ClientService us = new ClientService();
        User u = us.getClientById(6);
        for (Reservation r:resList) {
            if (r.getUser().equals(u)) {
                filtredResList.add(r);
            }
        }

        nomseance.setCellValueFactory(new PropertyValueFactory<>("seance"));
        nompersonne.setCellValueFactory(new PropertyValueFactory<>("nompersonne"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        poids.setCellValueFactory(new PropertyValueFactory<>("poids"));
        taille.setCellValueFactory(new PropertyValueFactory<>("taille"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        if (tablereservation != null && tablereservation instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Reservation>) tablereservation).setItems(FXCollections.observableArrayList(filtredResList));
        }

    }
    private void displayReservationInfo(Reservation reservation) {
        seancelabel.setText(reservation.getSeance().getNom());
        nom.setText(reservation.getNompersonne());
        prenomf.setText(reservation.getPrenom());
        agef.setText(String.valueOf(reservation.getAge())); // Convertir l'âge en chaîne de caractères
        poidsf.setText(String.valueOf(reservation.getPoids())); // Convertir le poids en chaîne de caractères
        taillef.setText(String.valueOf(reservation.getTaille())); // Convertir la taille en chaîne de caractères
        // Pour le sexe, il faut sélectionner le bouton radio correspondant
        if (reservation.getSexe().equals("homme") || reservation.getSexe().equals("HOMME")) {
            homme.setSelected(true); // Supposons que vous avez des boutons radio pour "Masculin" et "Féminin"
        } else if (reservation.getSexe().equals("femme") || reservation.getSexe().equals("FEMME")) {
            femme.setSelected(true);
        }
    }
    private String getSelectedSexe() {
        RadioButton selectedRadioButton = (RadioButton) sexeToggleGroup.getSelectedToggle();
        return selectedRadioButton.getText();
    }
}
