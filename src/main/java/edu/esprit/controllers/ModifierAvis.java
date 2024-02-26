package edu.esprit.controllers;

import edu.esprit.entities.AvisP;
import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesAvisPlat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ModifierAvis {

    private final ServicesAvisPlat serviceAvisPlat = new ServicesAvisPlat();
    private AvisP avisPToModify;

    @FXML
    private TextField commAPField;

    @FXML
    private TextField starField;

    @FXML
    private CheckBox favCheckBox;

    public void initialize() {
        int avisId = 32; // Set the ID for the AvisP to modify
        avisPToModify = serviceAvisPlat.getOneById(avisId); // Retrieve the AvisP by ID
        if (avisPToModify != null) {
            commAPField.setText(avisPToModify.getCommAP());
            starField.setText(String.valueOf(avisPToModify.getStar()));
            favCheckBox.setSelected(avisPToModify.isFav());
        } else {
            showAlert("Error", "AvisP with ID " + avisId + " not found");
        }
    }

    @FXML
    void modifierAvis(ActionEvent event) {
        try {
            // Update the attributes of the AvisP object with the new values from the input fields
            avisPToModify.setCommAP(commAPField.getText());
            avisPToModify.setStar(Integer.parseInt(starField.getText()));
            avisPToModify.setFav(favCheckBox.isSelected());

            // Call the service to update the AvisP object in the03 database
            serviceAvisPlat.modifier(avisPToModify);

            showAlert("Information", "AvisP modified successfully");
        } catch (SQLException e) {
            showAlert("Information", e.getMessage());
        }
    }

    // Method to show an alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
