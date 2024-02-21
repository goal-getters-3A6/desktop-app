package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjouterEquipement {

    private final ServiceEquipement ES = new ServiceEquipement();
    private Equipement eqMod;

    @FXML
    private ComboBox<String> categEqId;

    @FXML
    private TableColumn<?, ?> categEqId1;

    @FXML
    private TextArea descEqId;

    @FXML
    private TableColumn<?, ?> descEqId1;

    @FXML
    private TableColumn<?, ?> docEqId1;

    @FXML
    private TextArea docEqId;

    @FXML
    private TextField imageEqId;

    @FXML
    private TableColumn<?, ?> imageEqId1;

    @FXML
    private TextField nomEqId;

    @FXML
    private TableColumn<?, ?> nomEqId1;

    @FXML
    private TableView<Equipement> tableViewEqId;

    @FXML
    void AjouterEquipement(ActionEvent event) {

        try {
            ObservableList<String> list = FXCollections.observableArrayList("Fitness", "Cardio-training", "Musculation");
            categEqId.setItems(list);

            ES.ajouter(new Equipement(imageEqId.getText(), nomEqId.getText(), categEqId.getValue(), descEqId.getText(), docEqId.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Equipement added succesfully");
            alert.showAndWait();
            initialize();


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void ModifierEquipement(ActionEvent event) {
        try {

            // Update the attributes of the Plat object with the new values from the input fields
            eqMod.setNomEq(nomEqId.getText());
            eqMod.setImageEq(nomEqId.getText());
            eqMod.setCategEq(categEqId.getValue());
            eqMod.setDescEq(descEqId.getText());
            eqMod.setDocEq(docEqId.getText());


            // Call the service to update the Plat object in the database
            ES.modifier(eqMod);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Equipement updated succesfully");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    @FXML
    void SupprimerEquipement(ActionEvent event)  {

        try {
            List<Equipement> Ps= new ArrayList<>(ES.getAll());
            ObservableList<Equipement> observableList = FXCollections.observableList(Ps);
            tableViewEqId.setItems(observableList);
            ObservableList<Equipement> all, Single;
            all = tableViewEqId.getItems();
            Single = tableViewEqId.getSelectionModel().getSelectedItems();
            Equipement eq = Single.get(0);
            ES.supprimer(eq.getIdEq());
            Single.forEach(all::remove);
            initialize();
        }  catch (SQLException e){}

    }

    @FXML
    void initialize() {

        try {
            List<Equipement> Ps= new ArrayList<>(ES.getAll());
            ObservableList<Equipement> observableList = FXCollections.observableList(Ps);
            tableViewEqId.setItems(observableList);
        }
        catch (SQLException e){}

        imageEqId1.setCellValueFactory(new PropertyValueFactory<>("imageEq"));
        nomEqId1.setCellValueFactory(new PropertyValueFactory<>("nomEq"));
        categEqId1.setCellValueFactory(new PropertyValueFactory<>("categEq"));
        descEqId1.setCellValueFactory(new PropertyValueFactory<>("descEq"));
        docEqId1.setCellValueFactory(new PropertyValueFactory<>("docEq"));

    }

    @FXML
    private void insert_image(ActionEvent event) {

    }

}
