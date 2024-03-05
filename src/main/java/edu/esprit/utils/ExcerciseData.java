package edu.esprit.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public class ExcerciseData {
    public static ArrayList getExcerciseData() throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/external-dependencies/exercises.json"));

        JSONArray jsonArray = (JSONArray) jsonObject.get("exercises");
        ArrayList excercises = new ArrayList();

        // Iterate over each exercise object
        for (Object obj : jsonArray) {
            JSONObject exercise = (JSONObject) obj;

            // Access specific values from the exercise object
            String title = (String) exercise.get("title");
            String primer = (String) exercise.get("primer");
            JSONArray imagesArray = (JSONArray) exercise.get("img");
            JSONArray stepsArray = (JSONArray) exercise.get("steps");
            ArrayList<String> steps = new ArrayList<>();

            // Add image URL if available, otherwise add an empty string
            String firstImage = "";
            if (imagesArray != null && !imagesArray.isEmpty()) {
                String firstImageFileName = (String) imagesArray.get(0);
                String cleanedFileName = firstImageFileName.replace("_images/web/", "");
                firstImage = "https://raw.githubusercontent.com/everkinetic/data/main/src/images-web/" + cleanedFileName;
            }

            // Add steps if available
            if (stepsArray != null && !stepsArray.isEmpty()) {
                for (Object step : stepsArray) {
                    steps.add((String) step);
                }
            }

            // Add exercise details to the list
            excercises.add(title);
            excercises.add(primer);
            excercises.add(firstImage);
            excercises.add(steps);
        }

        return excercises;
    }
}
