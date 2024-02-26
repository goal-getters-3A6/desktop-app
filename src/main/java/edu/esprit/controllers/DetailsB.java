package edu.esprit.controllers;

import edu.esprit.entities.AvisP;
import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesAvisPlat;
import edu.esprit.services.ServicesPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class DetailsB {

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
    private final ServicesAvisPlat servicesAvisPlat = new ServicesAvisPlat();
    private Plat currentPlat; // Added field to store the current Plat object

    public DetailsB() {
        servicePlat = new ServicesPlat(); // Initialize your service
    }

    // Updated initialize method to accept Plat object as parameter
    public void initialize(Plat plat) {
        currentPlat = plat; // Set the current Plat object
        if (plat != null) {
            nomLabel.setText(plat.getNomP());
            prixLabel.setText(String.valueOf(plat.getPrixP()) + "DT");
            descLabel.setText(plat.getDescP());
            alergieLabel.setText(plat.getAlergieP());
            etatLabel.setText(plat.getEtatP() ? "Enstock" : "rupture stick");
            caloriesLabel.setText(String.valueOf(plat.getCalories()) + " CAL");
        } else {
            // Handle case where Plat is not found
        }

        Set<AvisP> comments = servicesAvisPlat.getAllP(plat.getIdP()); // Use plat.getIdP() to get the ID

        ObservableList<String> commentsList = FXCollections.observableArrayList();
        for (AvisP avis : comments) {
            commentsList.add(avis.getCommAP() + " - Star: " + avis.getStar());
        }
        commentsListView.setItems(commentsList);
    }

    private final ServicesAvisPlat serviceAvis = new ServicesAvisPlat();
    @FXML
    private ListView<String> commentsListView;

    @FXML
    private TextField commAPField;

    @FXML
    private TextField starField;

    @FXML
    private CheckBox favCheckbox;

    @FXML
    private void openModifierPage(ActionEvent event) {
        try {
            // Load the ModifierPlat.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPlat.fxml"));
            Parent root = loader.load();

            // Get the controller of the ModifierPlat.fxml file
            ModifierPlat modifierPlatController = loader.getController();

            // Pass the current Plat object to the ModifierPlat controller
            //modifierPlatController.initialize(currentPlat);

            // Show the modifier page
            nomLabel.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Load the AfficherPlat.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();

            // Set the scene back to AfficherPlat.fxml
            nomLabel.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
