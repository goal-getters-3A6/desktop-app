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

public class Detailsuser {



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
    private edu.esprit.controllers.AfficherPlat AfficherPlat;
    private int platId;

    public Detailsuser() {
        servicePlat = new ServicesPlat(); // Initialize your service
    }
    public void initData(int platId) {

        this.platId = platId;
    }
    public void initialize(int platId) {
        try {

            Plat plat = servicePlat.getOneById(platId);
            if (plat != null) {
                nomLabel.setText(plat.getNomP());
                prixLabel.setText(String.valueOf(plat.getPrixP())+"DT");
                descLabel.setText(plat.getDescP());
                alergieLabel.setText(plat.getAlergieP());
                etatLabel.setText(plat.getEtatP() ? "Enstock" : "rupture stock");
                caloriesLabel.setText(String.valueOf(plat.getCalories())+" CAL");
            } else {
                // Handle case where Plat is not found
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        Set<AvisP> comments = servicesAvisPlat.getAllP(platId);

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
    private void goBack(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlatUser.fxml"));
            Parent root = loader.load();


            nomLabel.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openModifierPage(ActionEvent actionEvent) {
    }
}

