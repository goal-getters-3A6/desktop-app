package edu.esprit.controllers;

import edu.esprit.entities.AvisP;
import edu.esprit.services.ServicesAvisPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.Set;

public class Affcommplat {
    @FXML
    private ListView<String> commentsListView;

    private final ServicesAvisPlat servicesAvisPlat = new ServicesAvisPlat();

    @FXML
    public void initialize() {
        // Assuming the plate ID is 11
        int plateId = 11;
        Set<AvisP> comments = servicesAvisPlat.getAllP(plateId);

        ObservableList<String> commentsList = FXCollections.observableArrayList();
        for (AvisP avis : comments) {
            commentsList.add(avis.getCommAP() + " - Star: " + avis.getStar());
        }
        commentsListView.setItems(commentsList);
    }
}
