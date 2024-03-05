package edu.esprit.controllers;

import edu.esprit.entities.Abonnement;
import edu.esprit.services.ServiceAbonnement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class AbonnemntsBack {
    private final ServiceAbonnement SA = new ServiceAbonnement();
    @FXML
    private ListView<Abonnement> ListViewAbId;

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
    private Button btntdb11;

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

    @FXML
    void initialize() {

            List<Abonnement> Ps = new ArrayList<>(SA.getAll());
            ObservableList<Abonnement> observableList = FXCollections.observableList(Ps);
            ListViewAbId.setItems(observableList);
            // Configuration de la cell factory pour afficher le nom de l'équipement avec des boutons
            ListViewAbId.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Abonnement item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        //setText(item.getTypeAb() + "      " + item.getDateExpirationAb()+ "      " +item.getCodePromoAb() + "      " + item.getMontantAb()+ "      " + item.getUtilisateur().getNom());
                        setText("Abonnement est de type  : " + item.getTypeAb() + " " + "et de date d'expiration est " + item.getDateExpirationAb() );
                        int x =item.getUtilisateur().getId();
                        System.out.println(x);

                        // Créer les boutons avec des icônes
                        ImageView detailIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/document.png")));
                        Button detailButton = new Button("", detailIcon);
                        detailIcon.setFitWidth(25);
                        detailIcon.setFitHeight(25);
                        detailButton.getStyleClass().add("icon-button");
                        detailButton.setOnAction(event -> {
                            afficherDetail(detailButton); // Appeler la méthode supprimerEquipement avec l'équipement associé
                        });
                        detailButton.setUserData(item);

                        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/bin.png")));
                        Button deleteButton = new Button("", deleteIcon);
                        deleteIcon.setFitWidth(25);
                        deleteIcon.setFitHeight(25);
                        deleteButton.getStyleClass().add("icon-button");
                        deleteButton.setUserData(item);
                        deleteButton.setOnAction(event -> {
                            supprimerEquipement(deleteButton); // Appeler la méthode supprimerEquipement avec l'équipement associé
                        });

                        // Créer un HBox pour contenir les boutons et l'espace flexible
                        HBox hbox = new HBox();
                        hbox.getChildren().addAll(deleteButton,detailButton);

                        // Créer un espace flexible pour pousser les boutons à la fin de la ligne
                        Region spacer = new Region();
                        HBox.setHgrow(spacer, Priority.ALWAYS);

                        // Ajouter les boutons et l'espace flexible à la cellule
                        HBox cellBox = new HBox();
                        cellBox.getChildren().addAll(spacer, hbox);
                        setGraphic(cellBox);
                    }
                }
            });




    }


    // Méthodes pour gérer les actions des boutons
    private void afficherDetail(Button detailButton) {
        try {
            // Récupérer l'abonnement associé au bouton editButton
            Abonnement abonnement = (Abonnement) detailButton.getUserData();

            // Charger le fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnemntsBackDetails.fxml"));
            Parent root = loader.load();


            // Récupérer le contrôleur de la nouvelle interface
            DetailsAbonnmentB controller = loader.getController();

            // Passer une référence à ce contrôleur (AfficherAbonnemntsBack)
           controller.setParentController(this);

            controller.initData(abonnement);

            detailButton.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void supprimerEquipement(Button deleteButton) {
        // Récupérer l'abonnement associé au bouton de suppression
        Abonnement abonnement = (Abonnement) deleteButton.getUserData();

        // Afficher une boîte de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de la suppression");
        confirmationDialog.setHeaderText("Voulez-vous vraiment supprimer cet abonnement ?");
        confirmationDialog.setContentText("Cette action est irréversible.");

        // Obtenir le résultat de la boîte de confirmation
        ButtonType result = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

        // Si l'utilisateur confirme la suppression, procéder à la suppression
        if (result == ButtonType.OK) {
            // Supprimer l'abonnement de la base de données et de la liste
            SA.supprimer(abonnement.getIdA());
            ListViewAbId.getItems().remove(abonnement);
        }
    }





}





