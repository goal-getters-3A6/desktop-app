package edu.esprit.controllers;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.services.ServiceAvisEquipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvisEquipementBack {
    private final ServiceAvisEquipement AES = new ServiceAvisEquipement();
    private final ServiceEquipement ES = new ServiceEquipement();
    @FXML
    private TableColumn<?, ?> aeqIdB;

    @FXML
    private TableColumn<?, ?> commAEqIdB;

    @FXML
    private TableView<AvisEquipement> tableViewAEqBack;

    @FXML
    void initialize() {
        try {
            List<AvisEquipement> Ps = new ArrayList<>(AES.getAll());
            ObservableList<AvisEquipement> observableList = FXCollections.observableList(Ps);
            tableViewAEqBack.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Définir les cell value factories pour chaque colonne
        commAEqIdB.setCellValueFactory(new PropertyValueFactory<>("commAEq"));
        // aeqIdB.setCellValueFactory(new PropertyValueFactory<>("nomEquipement"));
    }

}
