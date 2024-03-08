package edu.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Afficherlesseances {

    @FXML
    private ImageView bodyattack;

    @FXML
    private ImageView bodypump;

    @FXML
    private BorderPane borderpane;

    @FXML
    private ImageView boxe;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnaccueil;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btndetailbodypump;

    @FXML
    private Button btndetailsbodyattack;

    @FXML
    private Button btndetailscrossfit;

    @FXML
    private Button btndetailsspinning;

    @FXML
    private Button btndetailsyoga;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private Button btnreserverbodyattack;

    @FXML
    private Button btnreserverbodypump;

    @FXML
    private Button btnreservercrossfit;

    @FXML
    private Button btnreserverspinning;

    @FXML
    private Button btnreserveryoga;

    @FXML
    private ImageView crossfit;

    @FXML
    private Button detailboxe;

    @FXML
    private HBox hboxtopseancefront;

    @FXML
    private ImageView imageuser;

    @FXML
    private ImageView logo;

    @FXML
    private Button reserverboxe;

    @FXML
    private ImageView spinning;

    @FXML
    private ImageView yoga;

    @FXML
    void abonnement(ActionEvent event) {

    }

    @FXML
    void accueil(ActionEvent event) {

    }

    @FXML
    void alimentaire(ActionEvent event) {

    }

    @FXML
    void btndetailsboxe(ActionEvent event) {

    }

    @FXML
    void btnreserverboxe(ActionEvent event) {

    }

    @FXML
    void crossfitdetails(ActionEvent event) {

    }

    @FXML
    void detailsbodyattack(ActionEvent event) {

    }

    @FXML
    void detailsbodypump(ActionEvent event) {

    }

    @FXML
    void detailsspinning(ActionEvent event) {

    }

    @FXML
    void detailsyoga(ActionEvent event) {

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
    void profil(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void reserverbodyattack(ActionEvent event) {

    }

    @FXML
    void reserverbodypump(ActionEvent event) {

    }

    @FXML
    void reservercrossfit(ActionEvent event) {

    }

    @FXML
    void reserverspinning(ActionEvent event) {

    }

    @FXML
    void reserveryoga(ActionEvent event) {

    }
   /* void afficherlesphotos()
    {
        String imageUrl = resultSet.getString("image");
        Produit produit = fetchProductDetailsFromDatabase(imageUrl);
        Image image = new Image(imageUrl, 200, 200, true, true);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);

        Button detailButton = new Button("Détails");
        detailButton.setOnAction(event -> {
            System.out.println("Afficher les détails du produit : ");
        });
        detailButton.setStyle("-fx-background-color: #56ab2f; -fx-background-radius: 100; -fx-padding: 10px; ");

        imageView.setOnMouseClicked(event -> handleImageClick(produit));

        imagesGrid.add(imageView, column, row);
        imagesGrid.add(detailButton, column, row + 1);

// Incrémenter les indices de colonne et de ligne
        column++;
        if (column > 2) {
            column = 0;
            row += 2;
        }
    }
*/

}
