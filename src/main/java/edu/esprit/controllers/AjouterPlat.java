package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
public class AjouterPlat {

    private final ServicesPlat servicePlat = new ServicesPlat();

    @FXML
    private TextField NomPlatField;

    @FXML
    private TextField prixPlatField;


    @FXML
    private TextField alergiePlatField;

    @FXML
    private CheckBox etatPlatCheckbox;




    @FXML
    void Ajout (ActionEvent event) {
        try {


            servicePlat.ajouter(new Plat(NomPlatField.getText(), Float.parseFloat(prixPlatField.getText()), alergiePlatField.getText(), etatPlatCheckbox.isSelected() ));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Plat added successfully");
            alert.showAndWait();




        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }



}
