package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherReclamationB {
    private final ServiceReclamation SR = new ServiceReclamation();
    @FXML
    private TableColumn<Reclamation, String> CatégorieRéclamationid;

    @FXML
    private TableColumn<Reclamation, String> DescriptionRéclamtionid;

    @FXML
    private TableColumn<Reclamation, String> Etatid;

    @FXML
    private TableColumn<Reclamation, String> ODDid;

    @FXML
    private TableColumn<Reclamation, String> PiéceJointeid;

    @FXML
    private TableColumn<Reclamation, String> Serviceid;

    @FXML
    private TableView<Reclamation> tabRecid;
    @FXML
    private TextField CatégorieRéclamationidd;

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void initialize() {

        try {


            String regex = "[01]";

            // Créer un TextFormatter avec une expression régulière
            TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();

                // Vérifier si le nouveau texte correspond à l'expression régulière
                if (newText.matches(regex) || newText.isEmpty()) {
                    return change;
                } else {
                    showAlert("Saisie incorrecte", "Veuillez saisir uniquement les chiffres 0 ou 1.");
                    return null; // Rejeter le changement s'il ne correspond pas à l'expression régulière
                }
            });
            // Appliquer le TextFormatter au TextField
            CatégorieRéclamationidd.setTextFormatter(textFormatter);

            List<Reclamation> rec = new ArrayList<>(SR.getAll());
            ObservableList<Reclamation> observableList = FXCollections.observableList(rec);
            tabRecid.setItems(observableList);
            tabRecid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Afficher le commAEq dans le TextArea
                    CatégorieRéclamationidd.setText(String.valueOf(newValue.getEtatRec()));
                }
            });
        }
        catch (SQLException e){}

        CatégorieRéclamationid.setCellValueFactory(new PropertyValueFactory<>("categorieRec"));
        DescriptionRéclamtionid.setCellValueFactory(new PropertyValueFactory<>("descriptionRec"));
        PiéceJointeid.setCellValueFactory(new PropertyValueFactory<>("piéceJointeRec"));
        ODDid.setCellValueFactory(new PropertyValueFactory<>("oddRec"));
        Serviceid.setCellValueFactory(new PropertyValueFactory<>("serviceRec"));
        Etatid.setCellValueFactory(new PropertyValueFactory<>("etatRec"));

    }
    @FXML
    void supprimerRec(ActionEvent event) {
        try {
            List<Reclamation> rec = new ArrayList<>(SR.getAll());
            ObservableList<Reclamation> observableList = FXCollections.observableList(rec);
            tabRecid.setItems(observableList);
            ObservableList<Reclamation> all, Single;
            all = tabRecid.getItems();
            Single = tabRecid.getSelectionModel().getSelectedItems();
            Reclamation re = Single.get(0);
            SR.supprimer(re.getIdRec());
            Single.forEach(all::remove);
            initialize();
        }  catch (SQLException e){}
    }

    @FXML
    void modifierRec(ActionEvent event) {
        ObservableList<Reclamation> all, Single;
        all = tabRecid.getItems();
        Single = tabRecid.getSelectionModel().getSelectedItems();
        Reclamation aeqMod = Single.get(0);

        aeqMod.setEtatRec(Integer.parseInt(CatégorieRéclamationidd.getText()));
        SR.modifier(aeqMod);
        initialize();
        tabRecid.refresh();
        CatégorieRéclamationidd.clear();
    }
}