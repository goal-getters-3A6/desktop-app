package controllers;

import Service.ServiceEvenement;
import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherEvenement implements Initializable {

    private final ServiceEvenement SE = new ServiceEvenement();

    @FXML
    public GridPane gridPane;
    @FXML
    private AnchorPane NomId;
    @FXML
    public HBox hBoxEvents;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Set<Evenement> evenements = SE.getAll();

            for (Evenement evenement : evenements) {
                // create the event image
                String imagePath = evenement.getImage_eve();
                Image image = null;
                if (imagePath != null) {
                    try {
                        File imageFile = new File(imagePath);
                        image = new Image(new FileInputStream(imageFile));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle case where image path is null
                    // You might want to provide a default image in this case
                }

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);

                // create the details button
                Button detailsButton = new Button("Details");
                detailsButton.setOnAction(event -> showEventDetails(evenement));

                // create the participate button
                Button participateButton = new Button("Participer");
                // Set the event ID as the button's user data
                participateButton.setUserData(evenement.getId_eve());
                participateButton.setOnAction(this::ajouter_part); // Use method reference

                // add image, details button, and participate button to a vertical box
                VBox eventBox = new VBox(imageView, detailsButton, participateButton);
                eventBox.setSpacing(15); // spacing between nodes
                hBoxEvents.getChildren().add(eventBox);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void showEventDetails(Evenement evenement) {
        // create a new stage to display the event details
        Stage eventDetailsStage = new Stage();

        // create the event details layout
        HBox eventDetailsPane = new HBox(10); // spacing between nodes
        eventDetailsPane.setPadding(new Insets(10));
        eventDetailsPane.getStyleClass().add("event-details");

        // create the event image
        String imagePath = evenement.getImage_eve();
        Image image = null;
        if (imagePath != null) {
            try {
                File imageFile = new File(imagePath);
                image = new Image(new FileInputStream(imageFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where image path is null
            // You might want to provide a default image in this case
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.getStyleClass().add("image-view");

        // create the event details label
        String details = "Détails de l'événement:\n" +
                "Non De L'Evenement :" +evenement.getNom_eve() + "\n" +
                "Adresse: " + evenement.getAdresse_eve() + "\n" +
                "Date de début: " + evenement.getDated_eve() + "\n" +
                "Date de fin: " + evenement.getDatef_eve() + "\n" +
                "Nombre maximum de participants: " + evenement.getNbr_max();
        Label eventDetailsLabel = new Label(details);
        eventDetailsLabel.setFont(new Font("Arial", 14));
        eventDetailsLabel.setWrapText(true);
        eventDetailsLabel.getStyleClass().add("label");

        // create the close button
        Button closeButton = createCloseButton(eventDetailsStage);
        closeButton.getStyleClass().add("close-button");

        // add the event image, details label, and close button to the layout
        eventDetailsPane.getChildren().addAll(imageView, eventDetailsLabel, closeButton);

        // add the layout to the stage
        Scene eventDetailsScene = new Scene(eventDetailsPane, 600, 300); // increased width to accommodate both image and text
        eventDetailsStage.setScene(eventDetailsScene);
        eventDetailsStage.show();
    }

    private Button createCloseButton(Stage eventDetailsStage) {
        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(event -> eventDetailsStage.close());
        return closeButton;
    }

    //////boutton departicipation//////////





    public void ajouter_part(ActionEvent actionEvent) {
        int eventId = (int)((Button) actionEvent.getSource()).getUserData();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajouter_Participation.fxml"));
            Parent root = loader.load();

            AjouterParticipation controller = loader.getController();
            controller.setEventId1(eventId);

            NomId.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //refrech aprés les modification cette méthode est aplé dans la classe modifierevent
    void refreshEvents() {
        hBoxEvents.getChildren().clear(); // Efface les événements précédents
        try {
            Set<Evenement> evenements = SE.getAll();

            for (Evenement evenement : evenements) {
                // create the event image
                String imagePath = evenement.getImage_eve();
                Image image = null;
                if (imagePath != null) {
                    try {
                        File imageFile = new File(imagePath);
                        image = new Image(new FileInputStream(imageFile));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle case where image path is null
                    // You might want to provide a default image in this case
                }

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);

                // create the details button
                Button detailsButton = new Button("Details");
                detailsButton.setOnAction(event -> showEventDetails(evenement));

                // create the participate button
                Button participateButton = new Button("Participer");
                // Set the event ID as the button's user data
                participateButton.setUserData(evenement.getId_eve());
                participateButton.setOnAction(this::ajouter_part); // Use method reference

                // add image, details button, and participate button to a vertical box
                VBox eventBox = new VBox(imageView, detailsButton, participateButton);
                eventBox.setSpacing(15); // spacing between nodes
                hBoxEvents.getChildren().add(eventBox);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}