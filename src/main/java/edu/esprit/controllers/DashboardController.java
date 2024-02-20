package edu.esprit.controllers;



import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


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
    @FXML
    private ListView<?> reportslistview;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    @FXML
    private BarChart<String, Integer> barChart ;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private Button managegames;
    @FXML
    private Button managehappyhours;
    @FXML
    private AnchorPane DashborardPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));
        xAxis.setCategories(monthNames);
        Calendar cal = Calendar.getInstance();
        List<Client> usersdata = new ClientService().getAllClientsList();
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
        datauser = FXCollections.observableArrayList(us.getAllClients());
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
    private void search(KeyEvent event) {
        tableviewuser.getItems().clear();
        new ClientService().getAllClients().stream().filter(u -> u.getNom().indexOf(search.getText()) != -1)
                .forEach(u -> tableviewuser.getItems().add(u));
        tableviewuser.refresh();

    }


}
