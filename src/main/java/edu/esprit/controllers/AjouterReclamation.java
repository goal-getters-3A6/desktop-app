package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AjouterReclamation {
    private final ServiceReclamation SR = new ServiceReclamation();
    @FXML
    private ComboBox<?> categorieRecid;

    @FXML
    private TextArea descriptionRecid;

    @FXML
    private TextField oddRecid;

    @FXML
    private TextField piéceJointeRecid;

    @FXML
    private ComboBox<?> serviceRecid;



    @FXML
    void Ajouter(ActionEvent event) {
        try {

            User us1 =new User (1,"mayssa","hakimi");
            SR.ajouter(new Reclamation(categorieRecid.getItems().toString(),descriptionRecid.getText(), oddRecid.getText(),piéceJointeRecid.getText(),serviceRecid.getItems().toString(),us1));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Réclamation added succesfully");
            alert.showAndWait();

            FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherReclamationB.fxml"));
            Parent root=loader.load();
            oddRecid.getScene().setRoot(root);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void AfficherRec(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationB.fxml"));
            Parent root = loader.load();
            oddRecid.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();  // Affichez les détails de l'exception dans la console
        }
    }

}