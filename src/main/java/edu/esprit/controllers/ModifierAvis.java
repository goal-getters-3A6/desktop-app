package edu.esprit.controllers;

import edu.esprit.entities.AvisP;
import edu.esprit.entities.Client;
import edu.esprit.entities.Plat;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServicesAvisPlat;
import edu.esprit.utils.SessionManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ModifierAvis {

    private final ServicesAvisPlat serviceAvisPlat = new ServicesAvisPlat();
    private AvisP avisPToModify;

    @FXML
    private TextField commAPField;

    @FXML
    private TextField starField;

    @FXML
    private CheckBox favCheckBox;

    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    // UserService us=new UserService();
    ClientService cs=new ClientService();
    Client u;

    {
        try {
            u = cs.getOneByEmail(mail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize(int avisId) {

        avisPToModify = serviceAvisPlat.getOneById(avisId);
        if (avisPToModify != null) {
            commAPField.setText(avisPToModify.getCommAP());
            starField.setText(String.valueOf(avisPToModify.getStar()));
            favCheckBox.setSelected(avisPToModify.isFav());
        } else {
            showAlert("Information", "Avis nexiste pas", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void modifierAvis(ActionEvent event) {
        try {

            avisPToModify.setCommAP(commAPField.getText());

            int star = Integer.parseInt(starField.getText());
            if (star < 1 || star > 5) {
                showAlert("Error", "Star rating doit etre entre 1 et 5.", Alert.AlertType.ERROR);
                return;
            }
            avisPToModify.setStar(star);
            avisPToModify.setFav(favCheckBox.isSelected());
            serviceAvisPlat.modifier(avisPToModify);

            showAlert("Information", "Avis a été modifie avec success.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Error", "Veillez entrer une valeur valide (numeric value).", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void supprimerAvis(ActionEvent event) {
        try {
            if (avisPToModify != null) {
                int avisIdToDelete = avisPToModify.getIdAP();


                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Voulez-vous vraiment supprimer cet avis ?");

                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // User confirmed, proceed with deletion
                    serviceAvisPlat.supprimer(avisIdToDelete);
                    showAlert("Information", "Avis a été supprime avec succss", Alert.AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Load the AfficherPlat.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();

            // Set the scene back to AfficherPlat.fxml
            commAPField.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

