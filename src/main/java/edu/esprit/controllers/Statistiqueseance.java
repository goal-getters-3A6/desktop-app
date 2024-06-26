package edu.esprit.controllers;

import edu.esprit.entities.Seance;
import edu.esprit.services.ServiceSeance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Statistiqueseance  implements Initializable {

    @FXML
    private PieChart chart1;
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
    private Button btnreclamation;

    @FXML
    private Button btnretour;

    @FXML
    private Button btntdb;
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


    @FXML
    private ImageView logo1;

    @FXML
    private PieChart chart2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            // Récupérer les données des séances depuis votre source de données
            ServiceSeance serviceSeance = new ServiceSeance();
            try {
                List<Seance> seances = serviceSeance.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Créer une carte pour stocker les fréquences des séances
            Map<String, Integer> seanceFrequencyMap = serviceSeance.getSeanceFrequencyMap();

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
    /*@FXML
    void retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SeanceFormulaire.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/seanceadmin.fxml"));
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
    void reclamation(ActionEvent event) {

    }

    @FXML
    void tableaudebord(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}