package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class AfficherEquipementBack {
    private final ServiceEquipement ES = new ServiceEquipement();
    @FXML
    private ListView<Equipement> ListViewEqId;

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
    void initialize() {
        try {


            List<Equipement> Ps = new ArrayList<>(ES.getAll());
            ObservableList<Equipement> observableList = FXCollections.observableList(Ps);
            ListViewEqId.setItems(observableList);

            // Configuration de la cell factory pour afficher le nom de l'équipement avec des boutons
            ListViewEqId.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Equipement item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.getNomEq() + "      " + item.getCategEq());

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


                        ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/pen.png")));
                        Button editButton = new Button("", editIcon);
                        editIcon.setFitWidth(25);
                        editIcon.setFitHeight(25);
                        editButton.getStyleClass().add("icon-button");
                        editButton.setOnAction(event -> modifierEquipement(editButton)); // Appeler la méthode modifierEquipement avec l'équipement associé

                        // Définir les données utilisateur du bouton editButton avec l'équipement associé
                        editButton.setUserData(item);

                        // Créer un HBox pour contenir les boutons et l'espace flexible
                        HBox hbox = new HBox();
                        hbox.getChildren().addAll(detailButton, deleteButton, editButton);

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

        } catch (SQLException e) {
            e.printStackTrace(); // À remplacer par une gestion appropriée des erreurs
        }
    }


    // Méthodes pour gérer les actions des boutons
    private void afficherDetail(Button detailButton) {
        try {
            // Récupérer l'équipement associé au bouton editButton
            Equipement equipement = (Equipement) detailButton.getUserData();

            // Charger le fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEquipementBack.fxml"));
            Parent root = loader.load();


            // Récupérer le contrôleur de la nouvelle interface
            DetailsEquipementBack controller = loader.getController();

            // Passer une référence à ce contrôleur (AfficherEquipementBack)
            controller.setParentController(this);

            // Initialiser les données de l'équipement dans ModifierEquipementBack
            controller.initData(equipement);

            detailButton.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void supprimerEquipement(Button deleteButton) {
        try {
            // Récupérer l'équipement associé au bouton de suppression
            Equipement equipement = (Equipement) deleteButton.getUserData();

            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cet équipement ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la suppression
                // Supprimer l'équipement de la base de données et de la liste
                ES.supprimer(equipement.getIdEq());
                ListViewEqId.getItems().remove(equipement);
            } else {
                // L'utilisateur a annulé la suppression
                // Vous pouvez ajouter un message ou des actions supplémentaires ici si nécessaire
            }
        } catch (SQLException e) {
            e.printStackTrace(); // À remplacer par une gestion appropriée des erreurs
        }
    }



    private void modifierEquipement(Button editButton) {
        try {
            // Récupérer l'équipement associé au bouton editButton
            Equipement equipement = (Equipement) editButton.getUserData();

            // Charger le fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEquipementBack.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la nouvelle interface
            ModifierEquipementBack controller = loader.getController();

            // Passer une référence à ce contrôleur (AfficherEquipementBack)
            controller.setParentController(this);

            // Initialiser les données de l'équipement dans ModifierEquipementBack
            controller.initData(equipement);

            // Accéder à la scène actuelle à partir du bouton
            Scene scene = editButton.getScene();

            // Définir la nouvelle interface en tant que racine de la scène
            scene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void AjouterEqB(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEquipement.fxml"));
            Parent root = loader.load();
            ListViewEqId.getScene().setRoot(root);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void listeAvisB(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AvisEquipementBack.fxml"));
            Parent root = loader.load();
            ListViewEqId.getScene().setRoot(root);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void abonnement(javafx.event.ActionEvent event) {

    }

    @FXML
    void alimentaire(javafx.event.ActionEvent event) {

    }

    @FXML
    void equipement(javafx.event.ActionEvent event) {

    }

    @FXML
    void evenement(javafx.event.ActionEvent event) {

    }



    @FXML
    void planning(javafx.event.ActionEvent event) {

    }

    @FXML
    void reclamation(javafx.event.ActionEvent event) {

    }

    @FXML
    void tableaudebord(ActionEvent event) {

    }

}
