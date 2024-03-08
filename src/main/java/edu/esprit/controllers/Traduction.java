package edu.esprit.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Traduction {
/*
    public static String translate(String textToTranslate, String toLanguage) {
        try {
            // Replace "YOUR_SUBSCRIPTION_KEY" with your actual Azure subscription key
            String subscriptionKey = "YOUR_SUBSCRIPTION_KEY";
            String endpoint = "https://api.cognitive.microsofttranslator.com";
            String route = "/translate?api-version=3.0&to=" + toLanguage;

            String urlStr = endpoint + route;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
            conn.setDoOutput(true);

            String body = "[{'Text':'" + URLEncoder.encode(textToTranslate, "UTF-8") + "'}]";
            conn.getOutputStream().write(body.getBytes("UTF-8"));

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        String textToTranslate = "Hello, world!";
        String toLanguage = "fr"; // Change this to your target language code
        String translatedText = translate(textToTranslate, toLanguage);

        if (translatedText != null) {
            System.out.println("Original Text: " + textToTranslate);
            System.out.println("Translated Text: " + translatedText);
        } else {
            System.out.println("Translation failed.");
        }
    }
*/
}
