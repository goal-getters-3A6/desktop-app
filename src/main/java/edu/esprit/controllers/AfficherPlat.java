package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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

public class AfficherPlat {


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



                                ImageView imageView = new ImageView(new Image(plat.getPhotop()));
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


}
