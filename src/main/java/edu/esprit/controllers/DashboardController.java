package edu.esprit.controllers;


import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.*;


public class DashboardController implements Initializable {
    ObservableList<User> datauser = FXCollections.observableArrayList();
    @FXML
    private TextField search;

    @FXML
    private ListView userslistview;


    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    @FXML
    private BarChart<String, Integer> barChart ;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private ComboBox<String> AddUserBox;
    ObservableList<String> list = FXCollections.observableArrayList("ADMIN", "CLIENT");


    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        AddUserBox.setItems(list);
        AddUserBox.getValue();

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
        ClientService us = new ClientService();
        try {
            datauser = FXCollections.observableArrayList(us.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        userslistview.setItems(datauser);



    }


    @FXML
    private void search(KeyEvent event) throws SQLException{
        userslistview.getItems().clear();
        new ClientService().getAll().stream().filter(u -> u.getNom().toLowerCase().contains(search.getText().toLowerCase()))
                .forEach(u -> userslistview.getItems().add(u));
        userslistview.refresh();

    }

    @FXML void deleteUser(ActionEvent event) throws SQLException {
        Client u = (Client) userslistview.getSelectionModel().getSelectedItem();
        new ClientService().supprimer(u.getId());
        datauser.clear();
        datauser = FXCollections.observableArrayList(new ClientService().getAll());
        userslistview.setItems(datauser);
    }

    public void tableaudebord(ActionEvent actionEvent) {
    }

    public void evenement(ActionEvent actionEvent) {
    }

    public void equipement(ActionEvent actionEvent) {
    }

    public void abonnement(ActionEvent actionEvent) {
    }

    public void alimentaire(ActionEvent actionEvent) {
    }

    public void reclamation(ActionEvent actionEvent) {
    }

    public void planning(ActionEvent actionEvent) {
    }
}
