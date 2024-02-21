package edu.esprit.controllers;

import edu.esprit.entities.CalorieCalculator;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
}