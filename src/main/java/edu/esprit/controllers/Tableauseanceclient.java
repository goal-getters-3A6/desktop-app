package edu.esprit.controllers;

import edu.esprit.entities.Seance;
import edu.esprit.services.ServiceSeance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Tableauseanceclient {

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
    private TableColumn<Seance,String> colonneduree;

    @FXML
    private TableColumn<Seance,String> colonnehoraire;

    @FXML
    private TableColumn<Seance,Integer> colonnejour;

    @FXML
    private HBox hboxtopseancefront;

    @FXML
    private ImageView imageuser;

    @FXML
    private ImageView logo;

    @FXML
    private Button retour;

    @FXML
    private TableView<Seance> tableauseance;
    private String nomSeance;
    // Méthode pour définir l'ID de la séance
    public void setNomSeance(String nomSeance)
    {
        this.nomSeance = nomSeance;
        // Mettre à jour les informations de la séance dans la nouvelle page
        updateSeanceInfo();
    }
    private void updateSeanceInfo() {
        // Utiliser le service pour récupérer les informations de la séance par son nom
        /*ServiceSeance ss = new ServiceSeance();
        Seance seance = ss.getSeanceByNom(nomSeance); // Méthode à implémenter dans ServiceSeance

        // Créer une liste observable contenant uniquement la séance actuelle
        ObservableList<Seance> seanceList = FXCollections.observableArrayList();
        seanceList.add(seance);

        // Mettre à jour les colonnes avec les informations de la séance
        colonnehoraire.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        colonnejour.setCellValueFactory(new PropertyValueFactory<>("jourseance"));
        colonneduree.setCellValueFactory(new PropertyValueFactory<>("duree"));

        // Mettre à jour le TableView avec la liste observable
        tableauseance.setItems(seanceList);*/
        // Utiliser le service pour récupérer toutes les séances avec le même nom
        ServiceSeance ss = new ServiceSeance();
        List<Seance> seances = null; // Méthode à implémenter dans ServiceSeance
        try {
            seances = ss.getAllByNom(nomSeance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Créer une liste observable contenant toutes les séances avec le même nom
        ObservableList<Seance> seanceList = FXCollections.observableArrayList(seances);

        // Mettre à jour les colonnes avec les informations de toutes les séances
        colonnehoraire.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        colonnejour.setCellValueFactory(new PropertyValueFactory<>("jourseance"));
        colonneduree.setCellValueFactory(new PropertyValueFactory<>("duree"));

        // Mettre à jour le TableView avec la liste observable contenant toutes les séances
        tableauseance.setItems(seanceList);
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

    @FXML
    void retourner(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leseancesfront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }

    }

}
