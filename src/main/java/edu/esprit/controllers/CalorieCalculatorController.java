
    package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import edu.esprit.utils.CalorieCalculator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
    public class CalorieCalculatorController {

        @FXML
        private TextField weightField;

        @FXML
        private TextField ageField;

        @FXML
        private CheckBox maleCheckbox;

        @FXML
        private CheckBox femaleCheckbox;

        @FXML
        private Label resultLabel;
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

        @FXML
        private ListView<String> suggestedPlatsListView;

        private final ServicesPlat servicesPlat = new ServicesPlat();

        @FXML
        private void initialize() {
            suggestedPlatsListView.setVisible(false); // Initially hide the ListView
        }
        @FXML
        private void backToAfficherPlat(ActionEvent event) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
                Parent root = loader.load();


                resultLabel.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void calculateCalories() {
            try {
                int weight = Integer.parseInt(weightField.getText());
                int age = Integer.parseInt(ageField.getText());
                String gender = getSelectedGender();

                int calorieIntake = CalorieCalculator.calculateCalories(weight, age, gender);
                resultLabel.setText(calorieIntake + " calories");


                List<Plat> allPlats = servicesPlat.getAll();


                List<String> suggestedPlatNames = new ArrayList<>();
                for (Plat plat : allPlats) {
                    if (Math.abs(plat.getCalories() - calorieIntake) <= 100) {
                        suggestedPlatNames.add(plat.getNomP());
                    }
                }

                // Display suggested plat names
                suggestedPlatsListView.getItems().setAll(suggestedPlatNames);
                suggestedPlatsListView.setVisible(true);
            } catch (NumberFormatException | SQLException e) {
                resultLabel.setText("Inserrez des nombres vaides.");
            }
        }

        private String getSelectedGender() {
            if (maleCheckbox.isSelected()) {
                return "male";
            } else if (femaleCheckbox.isSelected()) {
                return "female";
            } else {
                return ""; // No gender selected
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



