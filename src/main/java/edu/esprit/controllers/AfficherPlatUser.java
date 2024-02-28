package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
                                // Create an HBox to hold both buttons
                                HBox buttonsBox = new HBox();

                                // Customize how each item is displayed
                                setText(" Nom: " + plat.getNomP() + " | Prix: " + plat.getPrixP()+ " | Etat: " + (plat.getEtatP() ? "En stock" : "rupture stock"));

                                // Add button to view details
                                Button detailsButton = new Button("Details");
                                detailsButton.setOnAction(event -> {
                                    openDetails(plat.getIdP());
                                });

                                // Add button to modify plat
                                Button modifierButton = new Button("Modifier");
                                modifierButton.setOnAction(event -> {
                                    openModifier(plat.getIdP());
                                });

                                // Add buttons to the HBox
                                buttonsBox.getChildren().addAll(detailsButton, modifierButton);

                                // Set the HBox as the graphic of the ListCell
                                setGraphic(buttonsBox);
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
}