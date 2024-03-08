package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class AfficherPlatUser {

    @FXML
    private ListView<Plat> platListView;

    @FXML
    private TextField searchField;

    private final ServicesPlat servicesPlat = new ServicesPlat();
    private ObservableList<Plat> platObservableList;

    @FXML
    void initialize() {
        try {
            List<Plat> plats = new ArrayList<>(servicesPlat.getAll());
            platObservableList = FXCollections.observableList(plats);
            platListView.setItems(platObservableList);

            // Customize the appearance of each item in the ListView
            platListView.setCellFactory(new Callback<ListView<Plat>, ListCell<Plat>>() {
                @Override
                public ListCell<Plat> call(ListView<Plat> param) {
                    return new ListCell<Plat>() {
                        @Override
                        protected void updateItem(Plat plat, boolean empty) {
                            super.updateItem(plat, empty);
                            if (empty || plat == null) {
                                setText(null);
                            } else {
                                HBox contentBox = new HBox(); // Container for text and image
                                HBox buttonsBox = new HBox(); // Container for buttons

                               // ImageView imageView = new ImageView(new Image(plat.getPhotop()));
                                ImageView imageView = new ImageView(new Image("file:" + plat.getPhotop()));

                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);


                                // Create a VBox to hold text details
                                VBox detailsBox = new VBox();
                                // Set text details
                                detailsBox.getChildren().addAll(
                                        new Label("Nom: " + plat.getNomP()),
                                        new Label("Prix: " + plat.getPrixP()),
                                        new Label("Etat: " + (plat.getEtatP() ? "En stock" : "Rupture stock"))
                                );

                                // Add detailsBox and buttonsBox to contentBox
                                contentBox.getChildren().addAll(imageView, detailsBox, buttonsBox);

                                // Set contentBox as the graphic of the ListCell
                                setGraphic(contentBox);

                                // Button handling remains the same
                                Button detailsButton = new Button("Details");
                                detailsButton.setOnAction(event -> {
                                    openDetails(plat.getIdP());
                                });

                                Button modifierButton = new Button("Modifier");
                                modifierButton.setOnAction(event -> {
                                    openModifier(plat.getIdP());
                                });

                                buttonsBox.getChildren().addAll(detailsButton, modifierButton);
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
        ObservableList<Plat> filteredPlats = FXCollections.observableArrayList();

        for (Plat plat : platObservableList) {
            if (plat.getNomP().toLowerCase().contains(searchText)) {
                filteredPlats.add(plat);
            }
        }

        platListView.setItems(filteredPlats);
    }

    private void openDetails(int platId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Detailsuser.fxml"));
            Stage stage = (Stage) platListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load()));
            Detailsuser detailsController = loader.getController();
            detailsController.initialize(platId);
            detailsController.initData(platId);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openModifier(int platId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPlat.fxml"));
            Stage stage = (Stage) platListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load()));
            ModifierPlat modifierPlatController = loader.getController();
            modifierPlatController.initialize(platId);
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
    private void ajouter(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPlat.fxml"));
            Parent root = loader.load();


            searchField.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void exportToExcel(ActionEvent event) {
        ObservableList<Plat> plats = platListView.getItems();
        String filePath = "C:\\Users\\Yosr\\Downloads\\desktop-app\\src\\main\\resources\\PlatsData.xlsx";

        try {
            ExcelExporter.exportPlatsToExcel(plats, filePath);
            System.out.println("Data exported to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void openStatsView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stats.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void tableaudebord(ActionEvent actionEvent) {
    }

    public void evenement(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenementListeView.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void equipement(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abonnement(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnemntsBack.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void alimentaire(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlatUser.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void reclamation(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationB.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void planning(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/seanceadmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
