package edu.esprit.controllers;


import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesAvisPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.Set;

public class favoris {
    @FXML
    private ListView<Plat> platsListView;

    private final ServicesAvisPlat servicesAvisPlat = new ServicesAvisPlat();

    @FXML
    public void initialize() {
        // Assuming userId is obtained from somewhere
        int userId = 14; // Example user ID

        // Get the set of reviewed plats by the user
        Set<Plat> reviewedPlats = servicesAvisPlat.getReviewedPlatsByUser(userId);

        // Populate ListView with reviewed plats data
        ObservableList<Plat> platsObservableList = FXCollections.observableArrayList(reviewedPlats);
        platsListView.setItems(platsObservableList);

        // Customize cell rendering if needed
        platsListView.setCellFactory(param -> new PlatListCell());
    }
}



