package edu.esprit.controllers;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reservationadmin {

    @FXML
    private TableColumn<?, ?> age;

    @FXML
    private Button btnannuler;

    @FXML
    private TableColumn<?, ?> nom;

    @FXML
    private TableColumn<?, ?> poids;

    @FXML
    private TableColumn<?, ?> prenom;

    @FXML
    private TableColumn<?, ?> seance;

    @FXML
    private TableColumn<?, ?> sexe;

    @FXML
    private TableColumn<?, ?> taille;

    @FXML
    private TableView<Reservation> table;

    ServiceReservation sr =new ServiceReservation();

    public void initialize() throws IOException {

        ShowReservation();

    }
    @FXML
    void annulerreservation(ActionEvent event) {
        Reservation selectedSeance = table.getSelectionModel().getSelectedItem();

        if (selectedSeance != null) {
            // Supprimer la séance de la base de données
            try {
                sr.supprimer(selectedSeance.getIdReservation());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression");
                alert.setContentText("Seance Deleted succesfully");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Exeption");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            // Rafraîchir les données dans le TableView
            try {
                ShowReservation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Afficher un message à l'utilisateur indiquant qu'aucune séance n'a été sélectionnée
            System.out.println("Veuillez sélectionner une séance à supprimer.");
        }

    }

    List<Reservation> resList;
    public void ShowReservation() throws IOException {

        try {
            resList = sr.getAll();
            System.out.println(resList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Reservation> filtredResList= new ArrayList<>();
        User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
        ClientService us = new ClientService();
        User u = us.getClientById(6);
        for (Reservation r:resList) {
            if (r.getUser().equals(u)) {
                filtredResList.add(r);
            }
        }
        seance.setCellValueFactory(new PropertyValueFactory<>("seance"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nompersonne"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        poids.setCellValueFactory(new PropertyValueFactory<>("poids"));
        taille.setCellValueFactory(new PropertyValueFactory<>("taille"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        if (table != null && table instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Reservation>) table).setItems(FXCollections.observableArrayList(filtredResList));
        }

    }
}
