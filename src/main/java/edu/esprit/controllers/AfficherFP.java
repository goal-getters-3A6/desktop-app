package edu.esprit.controllers;

import edu.esprit.services.ServicesPlat;
import edu.esprit.entities.Plat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Set;

public class AfficherFP {

    @FXML
    private VBox platesContainer;

    public void initialize() {
        // Initialize ServicesPlat
        ServicesPlat servicesPlat = new ServicesPlat();

        try {
            // Get all plates from the database
            Set<Plat> plates = servicesPlat.getAll();

            // Populate platesContainer with individual boxes for each plate name
            for (Plat plate : plates) {
                // Create a new HBox to hold plate name label
                HBox plateBox = new HBox();
                plateBox.getStyleClass().add("plate-box"); // Add CSS class for styling

                // Create a new Label to display the plate name
                Label nameLabel = new Label(plate.getNomP());

                // Add the nameLabel to the plateBox
                plateBox.getChildren().add(nameLabel);

                // Add the plateBox to platesContainer
                platesContainer.getChildren().add(plateBox);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException appropriately
        }
    }
}