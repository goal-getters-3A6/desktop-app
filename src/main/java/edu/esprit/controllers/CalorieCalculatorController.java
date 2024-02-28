package edu.esprit.controllers;

import edu.esprit.utils.CalorieCalculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CalorieCalculatorController {

    @FXML
    private TextField weightField;

    @FXML
    private TextField ageField;

    @FXML
    private CheckBox maleCheckbox;

    @FXML
    private CheckBox femaleCheckbox;

    @FXML
    private Label resultLabel;

    @FXML
    private void calculateCalories() {
        try {
            int weight = Integer.parseInt(weightField.getText());
            int age = Integer.parseInt(ageField.getText());
            String gender = getSelectedGender();

            int calorieIntake = CalorieCalculator.calculateCalories(weight, age, gender);
            resultLabel.setText( calorieIntake + " calories");
        } catch (NumberFormatException e) {
            resultLabel.setText("numeros svp.");
        }
    }

    private String getSelectedGender() {
        if (maleCheckbox.isSelected()) {
            return "male";
        } else if (femaleCheckbox.isSelected()) {
            return "female";
        } else {
            return ""; // No gender selected
        }
    }
    @FXML
    private void backToAfficherPlat(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();


            resultLabel.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}