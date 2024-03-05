package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesAvisPlat;
import edu.esprit.services.ServicesPlat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ModifierPlat {

    private final ServicesPlat servicePlat = new ServicesPlat();
    private final ServicesAvisPlat serviceAvisPlat = new ServicesAvisPlat();
    private Plat platToModify;

    @FXML
    private TextField NomPlatField;

    @FXML
    private TextField prixPlatField;

    @FXML
    private TextField alergiePlatField;

    @FXML
    private CheckBox etatPlatCheckbox;
    @FXML
    private TextField descPField;

    @FXML
    private TextField photopField;

    @FXML
    private TextField caloriesField;
    @FXML
    private ImageView photopImageView;


    public void initialize(int platId) {
        try {
            platToModify = servicePlat.getOneById(platId);
            if (platToModify != null) {
                NomPlatField.setText(platToModify.getNomP());
                prixPlatField.setText(platToModify.getPrixP().toString());
                alergiePlatField.setText(platToModify.getAlergieP());
                etatPlatCheckbox.setSelected(platToModify.getEtatP());
                descPField.setText(platToModify.getDescP());
                caloriesField.setText(String.valueOf(platToModify.getCalories()));
                // Display photo
                Image image = new Image(platToModify.getPhotop());
                photopImageView.setImage(image);
            } else {
                showAlert("Error", "le plat nexiste pas", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void ModifierPlat(ActionEvent event) {
        try {
            if (platToModify != null) {

                if (NomPlatField.getText().isEmpty() || prixPlatField.getText().isEmpty() ||
                        alergiePlatField.getText().isEmpty() || descPField.getText().isEmpty() ||
                        caloriesField.getText().isEmpty()) {
                    showAlert("Error", "Veuillez remplir tous les champs requis.", Alert.AlertType.ERROR);
                    return;
                }


                if (!isNumeric(prixPlatField.getText()) || !isNumeric(caloriesField.getText())) {
                    showAlert("Error", "Le prix et les calories ne peuvent pas contenir des lettres.", Alert.AlertType.ERROR);
                    return;
                }


                float prix = Float.parseFloat(prixPlatField.getText());
                int calories = Integer.parseInt(caloriesField.getText());
                if (prix <= 0 || calories <= 0) {
                    showAlert("Error", "Le prix et les calories doivent etre des valeurs positives.", Alert.AlertType.ERROR);
                    return;
                }


                platToModify.setNomP(NomPlatField.getText());
                platToModify.setPrixP(prix);
                platToModify.setAlergieP(alergiePlatField.getText());
                platToModify.setEtatP(etatPlatCheckbox.isSelected());
                platToModify.setDescP(descPField.getText());
                platToModify.setCalories(Integer.parseInt(caloriesField.getText()));


                servicePlat.modifier(platToModify);

                showAlert("Information", "Plat modifié avec succès.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Aucun plat sélectionné pour la modification.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            // Handle any potential SQL exceptions
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void SupprimerPlat(ActionEvent event) {
        try {
            if (platToModify != null) {
                int platId = platToModify.getIdP();

                // Display confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce plat ?");

                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    serviceAvisPlat.deleteByPlatId(platId);
                    servicePlat.supprimer(platId);

                    showAlert("Information", "Le plat a ete supprime avec succees", Alert.AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void backToAfficherPlat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlatUser.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the loaded FXML
            caloriesField.getScene().setRoot(root);
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
    private boolean isNumeric(String str) {
        return str.matches("\\d+(\\.\\d+)?");
    }
}



