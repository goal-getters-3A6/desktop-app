package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
public class AjouterPlat {

    private final ServicesPlat servicePlat = new ServicesPlat();

    @FXML
    private TextField NomPlatField;

    @FXML
    private TextField prixPlatField;


    @FXML
    private TextField alergiePlatField;
    @FXML
    private TextField CaloriesPlatField;
    @FXML
    private TextArea descfield;

    @FXML
    private CheckBox etatPlatCheckbox;




    @FXML
    void Ajout(ActionEvent event) {
        try { if (NomPlatField.getText().isEmpty() || prixPlatField.getText().isEmpty() ||
                alergiePlatField.getText().isEmpty() || descfield.getText().isEmpty() ||
                CaloriesPlatField.getText().isEmpty()) {
            showAlert("Error", "Veuillez remplir tous les champs requis.", Alert.AlertType.ERROR);
            return;
        }


            if (!isNumeric(prixPlatField.getText()) || !isNumeric(CaloriesPlatField.getText())) {
                showAlert("Error", "Le prix et les calories ne peuvent pas contenir des lettres.", Alert.AlertType.ERROR);
                return;
            }


            float prix = Float.parseFloat(prixPlatField.getText());
            int calories = Integer.parseInt(CaloriesPlatField.getText());
            if (prix <= 0 || calories <= 0) {
                showAlert("Error", "Le prix et les calories doivent etre des valeurs positives.", Alert.AlertType.ERROR);
                return;
            }


            // Proceed with adding the Plat
            servicePlat.ajouter(new Plat(NomPlatField.getText(),
                    Float.parseFloat(prixPlatField.getText()),
                    descfield.getText(),
                    alergiePlatField.getText(),
                    etatPlatCheckbox.isSelected(),
                    Integer.parseInt(CaloriesPlatField.getText())));

            showAlert("Information", "Plat ajoute avec success", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Method to show an alert dialog
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void goBack(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlatUser.fxml"));
            Parent root = loader.load();


            NomPlatField.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to check if a string is numeric
    private boolean isNumeric(String str) {
        return str.matches("\\d+(\\.\\d+)?");  // tshouf ken da55alt DIGITS wella le
    }

}
