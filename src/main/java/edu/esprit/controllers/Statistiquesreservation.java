package edu.esprit.controllers;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceSeance;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Statistiquesreservation implements Initializable {

    @FXML
    private Button btntdb;
    @FXML
    private Button btnabonnement;
    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;



    @FXML
    private Button btnreclamation;
    @FXML
    private ImageView logo;
    @FXML
    private PieChart chart1;
    @FXML
    private Button btnplanning1;
    @FXML
    private ImageView planningimg;
    @FXML
    private ImageView planningimg1;

    @FXML
    private ImageView planningimg11;

    @FXML
    private ImageView planningimg111;

    @FXML
    private ImageView planningimg1111;

    @FXML
    private ImageView planningimg2;

    @FXML
    private ImageView planningimg3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Récupérer les données des séances depuis votre source de données
      /*  ServiceReservation sr = new ServiceReservation();
        try {
            List<Reservation> r = sr.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Créer une carte pour stocker les fréquences des séances
        Map<String, Integer> seanceFrequencyMap = sr.getReservationFrequencyMap();

        // Créer une liste observable pour les données du graphique
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Parcourir la carte des fréquences et ajouter les données au graphique
        for (Map.Entry<String, Integer> entry : seanceFrequencyMap.entrySet()) {
            String nomSeance = entry.getKey();
            int frequency = entry.getValue();
            PieChart.Data data = new PieChart.Data(nomSeance,frequency);
            pieChartData.add(data);
        }

        // Liens dynamiques pour afficher les valeurs des données dans les libellés
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), " : ", (int) data.getPieValue(), " fois")
                )
        );

        // Ajouter les données du graphique au graphique
        chart1.setData(pieChartData);*/
        // Récupérer les données des séances réservées depuis votre source de données
        ServiceReservation sr = new ServiceReservation();
        List<Reservation> reservations;
        try {
            reservations = sr.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

// Créer une carte pour stocker les fréquences des séances réservées
        Map<String, Integer> seanceFrequencyMap = new HashMap<>();

// Parcourir la liste des réservations et compter la fréquence de chaque séance
        for (Reservation reservation : reservations) {
            String nomSeance = reservation.getSeance().getNom();
            seanceFrequencyMap.put(nomSeance, seanceFrequencyMap.getOrDefault(nomSeance, 0) + 1);
        }

// Créer une liste observable pour les données du graphique
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

// Parcourir la carte des fréquences et ajouter les données au graphique
        for (Map.Entry<String, Integer> entry : seanceFrequencyMap.entrySet()) {
            String nomSeance = entry.getKey();
            int frequency = entry.getValue();
            PieChart.Data data = new PieChart.Data(nomSeance, frequency);
            pieChartData.add(data);
        }

// Liens dynamiques pour afficher les valeurs des données dans les libellés
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), " : ", (int) data.getPieValue(), " fois")
                )
        );

// Ajouter les données du graphique au graphique
        chart1.setData(pieChartData);

    }
   /* @FXML
    void retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservationadmin.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnretour.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }*/
    @FXML
    void abonnement(ActionEvent event) {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Adminreservation.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnplanning1.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void tableaudebord(ActionEvent event) {

    }
}
