package edu.esprit.controllers;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceAvisEquipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvisEquipementBack {

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



    private final ServiceAvisEquipement AES = new ServiceAvisEquipement();

    @FXML
    private ListView<AvisEquipement> listViewAEqB;

    @FXML
    void initialize() {
        try {


            List<AvisEquipement> Ps = new ArrayList<>(AES.getAll());
            ObservableList<AvisEquipement> observableList = FXCollections.observableList(Ps);
            listViewAEqB.setItems(observableList);

            // Configuration de la cell factory pour afficher le nom de l'équipement avec des boutons
            listViewAEqB.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(AvisEquipement item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {

                        HBox hbox = new HBox(10); // Add spacing between elements

                        Label userLabel = new Label("Nom: "+(item.getUser().getNom()));
                        Label userLabel1 = new Label("Prénom: "+(item.getUser().getPrenom()));
                        Label commAEqLabel = new Label("Commentaire: "+(item.getCommAEq()));
                        Label equipementLabel = new Label("Equipement: "+(item.getEquipement().getNomEq()));

                        userLabel.setStyle("-fx-font-weight: bold;");
                        userLabel1.setStyle("-fx-font-weight: bold;");
                        commAEqLabel.setStyle("-fx-font-weight: bold;");
                        equipementLabel.setStyle("-fx-font-weight: bold;");

                        hbox.getChildren().addAll(userLabel, userLabel1, commAEqLabel, equipementLabel);



                        hbox.setSpacing(50);
                        // Aligner les éléments à gauche dans le conteneur HBox
                        hbox.setAlignment(Pos.TOP_LEFT);


                        setGraphic(hbox);


                    }
                }
            });

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();// À remplacer par une gestion appropriée des erreurs
        }
    }
    @FXML
    public void consulterEq(javafx.event.ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root = loader.load();
            listViewAEqB.getScene().setRoot(root);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    public void tableaudebord(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void equipement(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void abonnement(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void alimentaire(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void evenement(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void reclamation(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void planning(javafx.event.ActionEvent actionEvent) {
    }
}
