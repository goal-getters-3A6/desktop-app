package edu.esprit.utils;

public class CalorieCalculator {
    // Constants for Calorie calculation
    private static final int MALE_CONSTANT = 5;
    private static final int FEMALE_CONSTANT = -161;
    private static final int AGE_CONSTANT = 5;
    private static final int WEIGHT_CONSTANT = 10;

    // Method to calculate perfect calorie intake
    public static int calculateCalories(int weight, int age, String gender) {
        int calorieIntake;

        // Adjust calorie calculation based on gender
        int genderConstant = gender.equalsIgnoreCase("male") ? MALE_CONSTANT : FEMALE_CONSTANT;

        // Calculate calorie intake
        calorieIntake = (WEIGHT_CONSTANT * weight) + (AGE_CONSTANT * age) + genderConstant;

        return calorieIntake;
    }
}
