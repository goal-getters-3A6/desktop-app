package edu.esprit.controllers;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherEquipementFront {

    private final ServiceEquipement ES = new ServiceEquipement();



    @FXML
    void Details(ActionEvent event) {
      //  FXMLLoader loader= new FXMLLoader(getClass().getResource("/DetailsEquipement.fxml"));
      //  Parent root=loader.load();
       // NomId.getScene().setRoot(root);
    }



}
