package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class Details {

    @FXML
    private Label idLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private Label descLabel;

    @FXML
    private Label alergieLabel;

    @FXML
    private Label etatLabel;

    @FXML
    private Label photopLabel;

    @FXML
    private Label caloriesLabel;

    private ServicesPlat servicePlat;

    public Details() {
        servicePlat = new ServicesPlat(); // Initialize your service
    }

    public void initialize() {
        try {
            int platId = 12;
            Plat plat = servicePlat.getOneById(platId);
            if (plat != null) {
                nomLabel.setText(plat.getNomP());
                prixLabel.setText(String.valueOf(plat.getPrixP())+"DT");
                descLabel.setText(plat.getDescP());
                alergieLabel.setText(plat.getAlergieP());
                etatLabel.setText(plat.getEtatP() ? "Active" : "Inactive");
                caloriesLabel.setText(String.valueOf(plat.getCalories())+" CAL");
            } else {
                // Handle case where Plat is not found
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
