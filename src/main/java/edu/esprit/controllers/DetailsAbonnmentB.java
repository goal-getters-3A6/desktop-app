package edu.esprit.controllers;

import edu.esprit.entities.Abonnement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DetailsAbonnmentB {
    private Abonnement abonnement;
    private AbonnemntsBack AfficherAbonnemntsBack;
    @FXML
    private Label CategEq;

    @FXML
    private Label CategEqId;

    @FXML
    private Label DescEq;

    @FXML
    private Label DescEqId;

    @FXML
    private VBox DetailsEq;

    @FXML
    private Label DocEq;

    @FXML
    private Label DocEqId;

    @FXML
    private Label DocEq1;


    @FXML
    private Label DocEqId1;

    @FXML
    private Label NomEqId;

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
    private ImageView imageViewEq;

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
    public void initData(Abonnement abonnement) {
        this.abonnement = abonnement;
        // Remplissez les champs avec les données de l'équipement
        NomEqId.setText(abonnement.getTypeAb());
        NomEqId.setStyle("-fx-font-size: 25px; -fx-background-color: transparent; -fx-border-color: transparent;");
       CategEqId.setText(String.valueOf(abonnement.getDateExpirationAb()));
        DescEqId.setText(String.valueOf(abonnement.getMontantAb()));
        DescEqId.setStyle(" -fx-background-color: transparent; -fx-border-color: transparent;");
        DocEqId.setText(abonnement.getCodePromoAb());
        DocEqId.setStyle(" -fx-background-color: transparent; -fx-border-color: transparent;");
        DocEqId1.setText(abonnement.getUtilisateur().getNom());
        DocEqId1.setStyle(" -fx-background-color: transparent; -fx-border-color: transparent;");

        /*String imagePath = equipement.getImageEq();
        Image image = new Image("file:" + imagePath); // Supposant que le chemin est absolu, sinon ajustez-le en conséquence
        imageViewEq.setImage(image);*/
    }
    public void setParentController( AbonnemntsBack parentController) {
        this.AfficherAbonnemntsBack = parentController;
    }

    @FXML
    void ConsulterEqB(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnemntsBack.fxml"));
            Parent root = loader.load();
            NomEqId.getScene().setRoot(root);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



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
