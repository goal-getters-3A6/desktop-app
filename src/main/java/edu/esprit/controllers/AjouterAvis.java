package edu.esprit.controllers;

import edu.esprit.entities.AvisP;
import edu.esprit.services.ServicesAvisPlat;
import edu.esprit.services.ServicesPlat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.sql.SQLException;
import edu.esprit.entities.Plat;
public class AjouterAvis {


    private final ServicesAvisPlat serviceAvis = new ServicesAvisPlat();
    private final ServicesPlat servicePlat = new ServicesPlat();
    @FXML
    private TextField commAPField;

    @FXML
    private TextField starField;

    @FXML
    private CheckBox favCheckbox;

    public AjouterAvis() {
    }

    @FXML
    void Ajout(ActionEvent event) {
        try {
            String commAP = commAPField.getText();
            int star = Integer.parseInt(starField.getText());
            boolean fav = favCheckbox.isSelected();

            Plat platt = servicePlat.getOneById(11);
            serviceAvis.ajouter(new AvisP(commAP, star, fav, platt,1));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Avis added successfully");
            alert.showAndWait();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAvis.fxml"));
            Parent root = loader.load();
            commAPField.getScene().setRoot(root);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter a valid star rating.");
            alert.showAndWait();
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
