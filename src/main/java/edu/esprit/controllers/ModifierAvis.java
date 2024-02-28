package edu.esprit.controllers;

import edu.esprit.entities.AvisP;
import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesAvisPlat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

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



    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

