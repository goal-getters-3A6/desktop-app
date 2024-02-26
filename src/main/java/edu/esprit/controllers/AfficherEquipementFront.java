package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import javafx.scene.image.Image;

public class AfficherEquipementFront {

    @FXML
    private GridPane gridPaneEq;

    @FXML
    private ScrollPane scrollPaneEq;

    private final ServiceEquipement ES = new ServiceEquipement();

    public void initialize() {
        setEqGridPaneList();
    }

    private void setEqGridPaneList() {
        try {
            List<Equipement> equipements = ES.getAll();

            int colIndex = 0;
            int rowIndex = 0;

            for (Equipement equipement : equipements) {
                // Création de la carte d'équipement
                VBox eqCard = createEqCard(equipement);

                // Ajout de la carte à la grille
                gridPaneEq.add(eqCard, colIndex, rowIndex);
                GridPane.setMargin(eqCard, new Insets(10));

                // Gestion des index pour la prochaine carte
                colIndex++;
                if (colIndex == 3) {
                    colIndex = 0;
                    rowIndex++;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private VBox createEqCard(Equipement equipement) {
        // Création de la carte VBox
        VBox eqCard = new VBox(10);
        eqCard.setAlignment(Pos.CENTER);
        eqCard.setStyle("-fx-border-color: white; -fx-border-width: 1px; -fx-padding: 10px;");

        // Image de l'équipement
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200); // Ajustez la largeur de l'image selon vos besoins
        imageView.setFitHeight(200); // Ajustez la hauteur de l'image selon vos besoins


        try {
            Image image = new Image(new File(equipement.getImageEq()).toURI().toString());
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'erreur si le chargement de l'image échoue
        }
        eqCard.getChildren().add(imageView);

        // Ajout du nom de l'équipement
        Label nomButton = new Label(equipement.getNomEq());
        nomButton.setStyle("-fx-font-size: 25px; -fx-background-color: transparent; -fx-border-color: transparent;");
        //nomButton.setEditable(false);
        nomButton.setAlignment(Pos.CENTER);
        eqCard.getChildren().add(nomButton);

        // Ajout du bouton "Détails"
        Button detailButton = new Button("Détails");
        detailButton.getStyleClass().add("icon-button"); // Ajoutez des styles CSS au besoin
        detailButton.setOnAction(event -> afficherDetail(detailButton, equipement)); // Associez l'action de détail
        eqCard.getChildren().add(detailButton);

        return eqCard;
    }

    private void afficherDetail(Button detailButton, Equipement equipement) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEquipement.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la nouvelle interface
            DetailsEquipement controller = loader.getController();

            controller.initialize(equipement.getIdEq());
            detailButton.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
