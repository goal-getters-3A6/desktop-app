package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;

public class AfficherPlat {


    @FXML
    private ListView<Plat> platListView;

    @FXML
    private TextField searchField;
    @FXML
    private Button sortButton1;
    @FXML
    private Button sortButton11;
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
    private Button btnreclamation;

    @FXML
    private ImageView logo1;

    private final ServicesPlat servicesPlat = new ServicesPlat();
    private ObservableList<Plat> platObservableList;

    @FXML
    void initialize() {
        try {
            List<Plat> plats = new ArrayList<>(servicesPlat.getAll());
            platObservableList = FXCollections.observableList(plats);
            platListView.setItems(platObservableList);

            platListView.setCellFactory(new Callback<ListView<Plat>, ListCell<Plat>>() {
                @Override
                public ListCell<Plat> call(ListView<Plat> param) {
                    return new ListCell<Plat>() {
                        @Override
                        protected void updateItem(Plat plat, boolean empty) {
                            super.updateItem(plat, empty);
                            if (empty || plat == null) {
                                setText(null);
                                setGraphic(null); // Clear the graphic if the item is empty
                            } else {

                                // Customize how each item is displayed
                                setText(  plat.getNomP() + "\nPrix: " + plat.getPrixP() + "\nDescription: " + plat.getDescP() + "\nCalories: " + plat.getCalories() + "\nEtat: " + (plat.getEtatP() ? "En stock" : "rupture stock"));



                               // ImageView imageView = new ImageView(new Image(plat.getPhotop()));
                                ImageView imageView = new ImageView(new Image("file:" + plat.getPhotop()));

                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);


                                Button detailsButton = new Button("Details");
                                detailsButton.setOnAction(event -> {
                                    openDetails(plat.getIdP());
                                });


                                VBox vbox = new VBox(imageView, detailsButton);
                                vbox.setSpacing(5);
                                vbox.setAlignment(Pos.CENTER);
                                setGraphic(vbox);
                                applyButtonAnimation(detailsButton);
                                applyButtonAnimation(sortButton1);
                                applyButtonAnimation(sortButton11);
                            }
                        }
                    };
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void searchPlat() {
        String searchText = searchField.getText().trim().toLowerCase();


        ObservableList<Plat> filteredPlats = platObservableList.filtered(plat ->
                plat.getNomP().toLowerCase().contains(searchText) ||
                        String.valueOf(plat.getCalories()).contains(searchText) ||
                        plat.getAlergieP().toLowerCase().contains(searchText) ||
                        String.valueOf(plat.getPrixP()).contains(searchText)
        );

        platListView.setItems(filteredPlats);
    }

    private void openDetails(int platId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Details.fxml"));
            Stage stage = (Stage) platListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load()));
            Details detailsController = loader.getController();
            detailsController.setParentController(this);
            detailsController.initialize(platId);
            detailsController.initData(platId);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openAfficherCommUsr(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affcommuser.fxml"));
            Stage stage = (Stage) platListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openCalorieCalculator(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CalorieCalculator.fxml"));
            Stage stage = (Stage) platListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void sortByPrix() {
        ObservableList<Plat> sortedList = platListView.getItems();
        sortedList.sort(Comparator.comparingDouble(Plat::getPrixP));
        platListView.setItems(sortedList);
    }
    @FXML
    void sortByPrixDESC() {
        ObservableList<Plat> sortedList = platListView.getItems();
        sortedList.sort(Comparator.comparingDouble(Plat::getPrixP).reversed());
        platListView.setItems(sortedList);
    }
    @FXML
    void sortByCaloriesDESC() {
        ObservableList<Plat> sortedList = platListView.getItems();
        sortedList.sort(Comparator.comparingInt(Plat::getCalories).reversed());
        platListView.setItems(sortedList);
    }
    @FXML
    void sortByCalories() {
        ObservableList<Plat> sortedList = platListView.getItems();
        sortedList.sort(Comparator.comparingInt(Plat::getCalories));
        platListView.setItems(sortedList);
    }
    private void applyButtonAnimation(Button button) {
        button.setOnMousePressed(event -> {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), button);
            transition.setToY(3); // Move the button down by 3 pixels when pressed
            transition.play();
        });

        button.setOnMouseReleased(event -> {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), button);
            transition.setToY(0); // Move the button back to its original position
            transition.play();
        });
    }
    @FXML
    void abonnement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesAbonnements.fxml"));
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
    void accueil(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/acceuil.fxml"));
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
    void alimentaire(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
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
    void equipement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementFront.fxml"));
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
    void evenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnevenement.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
    void reclamation(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
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
}
