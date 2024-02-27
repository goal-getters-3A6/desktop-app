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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
                        // Créer un label pour afficher le nom de l'équipement
                        Label equipementLabel = new Label(  item.getEquipement().getNomEq());
                        // Créer un label pour afficher le commentaire de l'avis
                        Label commAEqLabel = new Label(  item.getCommAEq());

                        // Créer un conteneur HBox pour contenir les labels
                        HBox hbox = new HBox( commAEqLabel,new Region() ,equipementLabel);
                        hbox.setSpacing(50);
                        // Aligner les éléments à gauche dans le conteneur HBox
                        hbox.setAlignment(Pos.CENTER_LEFT);

                        // Définir le conteneur HBox comme contenu graphique de la cellule
                        setGraphic(hbox);


                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace(); // À remplacer par une gestion appropriée des erreurs
        }
    }
    @FXML
    void consulterEq(ActionEvent event) {

    }


    public void consulterEq(javafx.event.ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root = loader.load();
            listViewAEqB.getScene().setRoot(root);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
