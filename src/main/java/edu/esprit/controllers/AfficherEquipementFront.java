package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceEquipement;
import edu.esprit.utils.SessionManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import javafx.scene.image.Image;

public class AfficherEquipementFront {

    @FXML
    private ImageView logo1;
    @FXML
    private GridPane gridPaneEq;

    @FXML
    private ScrollPane scrollPaneEq;

    @FXML
    private Pagination pagination;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnaccueil;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnreclamation;
    private final int itemsPerPage = 3;



    private void setEqPagination() {
        try {
            List<Equipement> equipements = ES.getAll();
            int pageCount = (int) Math.ceil((double) equipements.size() / itemsPerPage);

            pagination.setPageCount(pageCount);
            pagination.setPageFactory(pageIndex -> createEqGridPane(pageIndex, equipements));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private final ServiceEquipement ES = new ServiceEquipement();
    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    // UserService us=new UserService();
    ClientService cs=new ClientService();
    Client user;

    {
        try {
            user = cs.getOneByEmail(mail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        setEqPagination();
    }

    private GridPane createEqGridPane(int pageIndex, List<Equipement> equipements) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, equipements.size());

        for (int i = startIndex; i < endIndex; i++) {
            Equipement equipement = equipements.get(i);
            VBox eqCard = createEqCard(equipement);
            gridPane.add(eqCard, i % 3, i / 3);
            GridPane.setMargin(eqCard, new Insets(10));
        }

        return gridPane;
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
        detailButton.setStyle("-fx-font-size: 15px;-fx-background-color: #db1f48;"); // Utilisez la couleur de fond que vous souhaitez

// Changer la couleur du texte
        detailButton.setTextFill(javafx.scene.paint.Color.WHITE);
        detailButton.setOnAction(event -> afficherDetail(detailButton, equipement)); // Associez l'action de détail
        eqCard.getChildren().add(detailButton);

        return eqCard;
    }

    private void afficherDetail(Button detailButton, Equipement equipement) {

        try {
            SessionManagement ss = new SessionManagement();
            String mail = ss.getEmail();
            ClientService cs = new ClientService();

                Client user = cs.getOneByEmail(mail);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEquipement.fxml"));
            Parent root = loader.load();


            DetailsEquipement controller = loader.getController();
            controller.setParentController(this);


            controller.initialize(equipement.getIdEq());
            controller.initData(equipement);
            detailButton.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void reclamation(javafx.event.ActionEvent actionEvent) {
    }
    @FXML
    public void evenement(javafx.event.ActionEvent actionEvent) {
    }
    @FXML
    public void alimentaire(javafx.event.ActionEvent actionEvent) {
    }
    @FXML
    public void abonnement(javafx.event.ActionEvent actionEvent) {
    }
    @FXML
    public void planning(javafx.event.ActionEvent actionEvent) {
    }
    @FXML
    public void equipement(javafx.event.ActionEvent actionEvent) {
    }
    @FXML
    public void accueil(javafx.event.ActionEvent actionEvent) {
    }
}
