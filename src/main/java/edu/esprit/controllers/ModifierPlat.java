package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import edu.esprit.services.ServicesAvisPlat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ModifierPlat {

    private final ServicesPlat servicePlat = new ServicesPlat();
    private final ServicesAvisPlat serviceAvisPlat = new ServicesAvisPlat();
    private Plat platToModify;

    @FXML
    private TextField NomPlatField;

    @FXML
    private TextField prixPlatField;

    @FXML
    private TextField alergiePlatField;

    @FXML
    private CheckBox etatPlatCheckbox;
    @FXML
    private TextField descPField; // TextField for descP attribute

    @FXML
    private TextField photopField; // TextField for photop attribute

    @FXML
    private TextField caloriesField; // TextField for calories attribute

    // Method to initialize the controller with the Plat to modify
    public void initialize() {
        int platId = 5; // Set the ID directly
        try {
            platToModify = servicePlat.getOneById(platId); // Retrieve the Plat by ID
            if (platToModify != null) {
                NomPlatField.setText(platToModify.getNomP());
                prixPlatField.setText(platToModify.getPrixP().toString());
                alergiePlatField.setText(platToModify.getAlergieP());
                etatPlatCheckbox.setSelected(platToModify.getEtatP());
                descPField.setText(platToModify.getDescP());
                photopField.setText(platToModify.getPhotop());
                caloriesField.setText(String.valueOf(platToModify.getCalories()));
            } else {
                // Handle the case where the Plat with the given ID does not exist
                showAlert("Error", "Plat with ID " + platId + " not found");
            }
        } catch (SQLException e) {
            // Handle any potential SQL exceptions
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    void ModifierPlat(ActionEvent event) {
        try {

            // Update the attributes of the Plat object with the new values from the input fields
            platToModify.setNomP(NomPlatField.getText());
            platToModify.setPrixP(Float.parseFloat(prixPlatField.getText()));
            platToModify.setAlergieP(alergiePlatField.getText());
            platToModify.setEtatP(etatPlatCheckbox.isSelected());
            platToModify.setDescP(descPField.getText());
            platToModify.setPhotop(photopField.getText());
            platToModify.setCalories(Integer.parseInt(caloriesField.getText()));

            // Call the service to update the Plat object in the database
            servicePlat.modifier(platToModify);

            showAlert("Information", "Plat modified successfully");
        } catch (SQLException e) {
            // Handle any potential SQL exceptions
            showAlert("Error", e.getMessage());
        }
    }
    @FXML
    void SupprimerPlat(ActionEvent event) {
        int platId = 5;
        try {
            if (platToModify != null) {
                // Delete associated AvisP entities first
                serviceAvisPlat.deleteByPlatId(platId);

                // Then delete the Plat entity
                servicePlat.supprimer(platToModify.getIdP());

                showAlert("Information", "Plat deleted successfully");
            } else {
                showAlert("Error", "No Plat selected for deletion");
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
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
