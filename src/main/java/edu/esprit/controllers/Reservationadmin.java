package edu.esprit.controllers;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceSeance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Reservationadmin {
    @FXML
    private Button btnrecherche;

    @FXML
    private Button btntri;
    @FXML
    private ComboBox<String> combotri;
    @FXML
    private TextField textrecherche;
    @FXML
    private TableColumn<?, ?> age;

    @FXML
    private Button btnannuler;

    @FXML
    private Button btnrefraichir;
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
        ObservableList<String> elementstri = FXCollections.observableArrayList("age", "sexe", "poids");
        combotri.setItems(elementstri);
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
    @FXML
    void statistique(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistiquesreservation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void tri(ActionEvent event) {
        String critereTri =  combotri.getValue(); // Récupérer le critère de tri à partir du ComboBox comboBoxTri

        // Vérifier si le critère de tri est vide
        if (critereTri == null || critereTri.isEmpty()) {
            // Afficher un message à l'utilisateur pour indiquer que le critère de tri n'est pas sélectionné
            showAlert(Alert.AlertType.WARNING, "Critère de tri non sélectionné", "Veuillez sélectionner un critère de tri.");
            return;
        }

        // Récupérer les séances actuellement affichées dans la table
        ObservableList<Reservation> seanceList = table.getItems();

        // Vérifier si des séances sont affichées dans la table
        if (!seanceList.isEmpty()) {
            // Trier les séances en fonction du critère sélectionné
            switch (critereTri) {
                case "age":
                    Collections.sort(seanceList, Comparator.comparing(Reservation::getAge));
                    break;
                case "sexe":
                    Collections.sort(seanceList, Comparator.comparing(Reservation::getSexe));
                    break;
                case "poids":
                    Collections.sort(seanceList, Comparator.comparing(Reservation::getPoids));
                    break;
                default:
                    // Le critère de tri n'est pas reconnu
                    showAlert(Alert.AlertType.ERROR, "Critère de tri non reconnu", "Le critère de tri sélectionné n'est pas pris en charge.");
                    return;
            }

            // Mettre à jour le TableView avec les séances triées
            table.setItems(seanceList);
        } else {
            // Afficher un message si aucune séance n'est actuellement affichée dans la table
            showAlert(Alert.AlertType.INFORMATION, "Aucune reservation", "Aucune reservation à trier.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void rechercher(ActionEvent event) {
        String nomSeance = textrecherche.getText(); // Supposons que vous avez un champ de texte nommé nomSeanceField

        // Vérifier si le nom de la séance est vide
        if (nomSeance == null || nomSeance.isEmpty()) {
            // Afficher un message à l'utilisateur pour indiquer que le champ est vide
            showAlert(Alert.AlertType.WARNING, "Champ vide", "Veuillez entrer le nom de la recherche.");
            return;
        }

        // Utiliser le service pour rechercher la séance par son nom
        ServiceReservation sr = new ServiceReservation(); // Créez une instance de ServiceSeance
        try {
            Reservation reservation = sr.getReservationByNomPersonne(nomSeance); // Utilisez la méthode pour obtenir la séance par son nom
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Reservation> list;
        try {
            list = sr.getAllByNom(nomSeance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Vérifier si la séance est trouvée
        if (!list.isEmpty()) {
            // Effacer la table pour afficher les résultats de la recherche
            table.getItems().clear();

            // Créer une liste observable contenant la séance trouvée
            ObservableList<Reservation> seanceList = FXCollections.observableArrayList(list);

            // Mettre à jour les colonnes avec les informations de la séance
            seance.setCellValueFactory(new PropertyValueFactory<>("seance"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nompersonne"));
            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            age.setCellValueFactory(new PropertyValueFactory<>("age"));
            poids.setCellValueFactory(new PropertyValueFactory<>("poids"));
            taille.setCellValueFactory(new PropertyValueFactory<>("taille"));
            sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));

            // Mettre à jour le TableView avec la liste observable contenant la séance trouvée
            table.setItems(seanceList);


        } else {
            // Afficher un message si aucune séance n'est trouvée avec ce nom
            showAlert(Alert.AlertType.INFORMATION, "Aucun résultat", "Aucune séance trouvée avec le nom \"" + nomSeance + "\".");
        }

    }
    @FXML
    void refraichir(ActionEvent event) {
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
