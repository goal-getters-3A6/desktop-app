package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherPlat {

    @FXML
    private TableColumn<Plat, String> nomPlatColumn;

    @FXML
    private TableColumn<Plat, Float> prixPlatColumn;

    @FXML
    private TableColumn<Plat, String> descPlatColumn;

    @FXML
    private TableColumn<Plat, String> alergiePlatColumn;

    @FXML
    private TableColumn<Plat, Boolean> etatPlatColumn;

    @FXML
    private TableView<Plat> platTableView;

    private final ServicesPlat servicesPlat = new ServicesPlat();

    @FXML
    void initialize() {
        try {
            List<Plat> plats = new ArrayList<>(servicesPlat.getAll());
            ObservableList<Plat> observableList = FXCollections.observableList(plats);
            platTableView.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nomPlatColumn.setCellValueFactory(new PropertyValueFactory<>("nomP"));
        prixPlatColumn.setCellValueFactory(new PropertyValueFactory<>("prixP"));
        descPlatColumn.setCellValueFactory(new PropertyValueFactory<>("descP"));
        alergiePlatColumn.setCellValueFactory(new PropertyValueFactory<>("alergieP"));
        etatPlatColumn.setCellValueFactory(new PropertyValueFactory<>("etatP"));
    }
}
