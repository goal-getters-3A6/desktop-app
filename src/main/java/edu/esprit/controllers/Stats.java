package edu.esprit.controllers;

import edu.esprit.services.ServicesAvisPlat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.sql.SQLException;
import java.util.Map;

public class Stats {

    @FXML
    private BarChart<String, Number> avisChart;

    @FXML
    private CategoryAxis xCategoryAxis;

    @FXML
    private NumberAxis yNumberAxis;

    private final ServicesAvisPlat servicesAvis = new ServicesAvisPlat();

    @FXML
    public void initialize() {
        try {
            Map<Integer, Integer> ratingCounts = servicesAvis.getRatingCounts();

            // Create an observable list of data for the chart
            ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
            for (int i = 1; i <= 5; i++) {
                int count = ratingCounts.getOrDefault(i, 0);
                data.add(new XYChart.Data<>(String.valueOf(i), count));
            }

            // Create a series and add the data to it
            XYChart.Series<String, Number> series = new XYChart.Series<>(data);

            // Set the name of the series (this will be displayed in the legend)
            series.setName("Avis Ratings");

            // Add the series to the chart
            avisChart.getData().add(series);

            // Set labels for axes
            xCategoryAxis.setLabel("Star Rating");
            yNumberAxis.setLabel("Number of Avis");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
