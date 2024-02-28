package edu.esprit.controllers;

import edu.esprit.entities.AvisP;
import edu.esprit.services.ServicesAvisPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class Affcommuser {
    @FXML
    private ListView<HBox> commentsListView;

    private final ServicesAvisPlat servicesAvisPlat = new ServicesAvisPlat();

    @FXML
    public void initialize() {
        int userid = 1; // Assuming the user ID is 1
        Set<AvisP> comments = servicesAvisPlat.getAllU(userid);

        ObservableList<HBox> commentsList = FXCollections.observableArrayList();
        for (AvisP avis : comments) {
            Button modifierButton = new Button("Modifier");
            modifierButton.setOnAction(event -> {
                openModifierAvis(avis.getIdAP());
            });
            Label avisLabel = new Label("  Commentaire: "+avis.getCommAP() + " | Star: " + avis.getStar());



            HBox hbox = new HBox();
            hbox.getChildren().addAll(modifierButton , avisLabel);
            commentsList.add(hbox);
        }
        commentsListView.setItems(commentsList);
    }
    @FXML
    private void backToAfficherPlat(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();


            commentsListView.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openModifierAvis(int avisId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAvis.fxml"));
            Stage stage = (Stage) commentsListView.getScene().getWindow(); // Assuming commentsListView is in the same stage
            stage.setScene(new Scene(loader.load()));
            ModifierAvis modifierAvisController = loader.getController();
            modifierAvisController.initialize(avisId); // Pass the Avis ID
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
