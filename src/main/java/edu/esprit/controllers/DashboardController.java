package edu.esprit.controllers;


import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.*;


public class DashboardController implements Initializable {

    ArrayList datareports = new ArrayList();
    ObservableList<User> datauser = FXCollections.observableArrayList();
    @FXML
    private TextField search;
    @FXML
    private TableColumn<?, ?> tid;
    @FXML
    private TableColumn<?, ?> tnom;
    @FXML
    private TableColumn<?, ?> tprenom;
    @FXML
    private TableColumn<?, ?> tmdp;
    @FXML
    private TableColumn<?, ?> temail;
    @FXML
    private TableColumn<?, ?> ttel;
    @FXML
    private TableColumn<?, ?> tdate_naissance;
    @FXML
    private TableColumn<?, ?> tdate_inscription;

    @FXML
    private TableView tableviewuser;


    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    @FXML
    private BarChart<String, Integer> barChart ;

    @FXML
    private CategoryAxis xAxis;



    @Override
    public void initialize(URL url, ResourceBundle rb)  {

        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));
        xAxis.setCategories(monthNames);
        Calendar cal = Calendar.getInstance();
        List<Client> usersdata= new ArrayList<>();
        try {
          usersdata   = new ClientService().getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int[] monthCounter = new int[12];
        for (Client u : usersdata) {
            cal.setTime(u.getDate_naissance());
            int month = cal.get(Calendar.MONTH);
            monthCounter[month]++;
        }
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }
        barChart.getData().add(series);


        datauser.clear();
        datareports.clear();
        ClientService us = new ClientService();
        try {
            datauser = FXCollections.observableArrayList(us.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        temail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tmdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        ttel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tdate_naissance.setCellValueFactory(new PropertyValueFactory<>("date_naissance"));
        tdate_inscription.setCellValueFactory(new PropertyValueFactory<>("date_inscription"));

        tableviewuser.setItems(datauser);

    }


    @FXML
    private void search(KeyEvent event) throws SQLException{
        tableviewuser.getItems().clear();
        new ClientService().getAll().stream().filter(u -> u.getNom().indexOf(search.getText()) != -1)
                .forEach(u -> tableviewuser.getItems().add(u));
        tableviewuser.refresh();

    }

}
