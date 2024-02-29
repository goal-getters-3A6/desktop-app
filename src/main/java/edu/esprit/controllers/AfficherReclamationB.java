package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        //try {
            // Récupérer la réclamation sélectionnée
            ObservableList<Reclamation> all, Single;
            all = tabRecid.getItems();
            Single = tabRecid.getSelectionModel().getSelectedItems();

            if (Single.isEmpty()) {
                showAlert("Sélection incorrecte", "Veuillez sélectionner une réclamation à supprimer.");
                return;
            }

            Reclamation reclamation = Single.get(0);

            // Afficher une alerte de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer cette réclamation ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Si l'utilisateur clique sur le bouton OK, supprimer la réclamation
            if (result.isPresent() && result.get() == ButtonType.OK) {
                SR.supprimer(reclamation.getIdRec());
                Single.forEach(all::remove);
                initialize();
            }
        } /*catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de la suppression de la réclamation.");
        }*/

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


    @FXML
    private Button btnabonnement1;

    @FXML
    private Button btnalimentaire1;

    @FXML
    private Button btnequipement1;

    @FXML
    private Button btnevenement1;

    @FXML
    private Button btnplanning1;

    @FXML
    private Button btnreclamation1;

    @FXML
    private Button btntdb1;

    @FXML
    private ImageView logo1;

    @FXML
    private ImageView planningimg1;

    @FXML
    private ImageView planningimg111;

    @FXML
    private ImageView planningimg1111;

    @FXML
    private ImageView planningimg11111;

    @FXML
    private ImageView planningimg21;

    @FXML
    private ImageView planningimg31;



    @FXML
    void abonnement(ActionEvent event) {

    }

    @FXML
    void alimentaire(ActionEvent event) {

    }

    @FXML
    void equipement(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {

    }


    @FXML
    void planning(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }


    @FXML
    void tableaudebord(ActionEvent event) {

    }
}