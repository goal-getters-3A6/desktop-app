package edu.esprit.controllers;

import edu.esprit.entities.Seance;
import edu.esprit.services.ServiceSeance;
import edu.esprit.utils.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class leseancesfront{

    @FXML
    private BorderPane borderpane;

    @FXML
    private Button btnabonnement;
    @FXML
    private Button btnmesreservations;

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
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hbox1;

    @FXML
    private HBox hbox2;

    @FXML
    private HBox hboxtopseancefront;

    @FXML
    private ImageView imageuser;

    @FXML
    private ImageView logo;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vbox;

    public leseancesfront() throws SQLException {
    }

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
    void equipement(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {

    }

    @FXML
    void planning(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leseancesfront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnplanning.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }

    }

    @FXML
    void profil(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }
    @FXML
    void mesreservations(ActionEvent event) {
        try {

            // Charger le fichier FXML correspondant à la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mesreservations.fxml"));
            Parent root = loader.load();
            Mesreservations controller = loader.getController();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            Stage stage = (Stage) grid.getScene().getWindow(); // Obtenez la scène actuelle du bouton ou de tout autre nœud
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception, par exemple, afficher un message d'erreur à l'utilisateur
        }

    }

    private final ServiceSeance ss=new ServiceSeance();
     List<Seance> seanceList=ss.getAll();

    public void initialize(){
        setSeanceGridPaneList();
    }
    private void setSeanceGridPaneList() {

        /*HBox hbox = new HBox(30);
        hbox.setPadding(new Insets(10)); // Optionnel : définir une marge autour du HBox
        for (Seance seance : seanceList) {
            // Créer un VBox pour chaque activité
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER); // Centrer les éléments dans le VBox// 10 est l'espacement entre les nœuds, ajustez selon vos besoins

            // Supposons que getImageA() renvoie le chemin du fichier image
            ImageView imageView = new ImageView();
            Label nomLabel = new Label(seance.getNom());
            nomLabel.setFont(new Font("Arial", 18)); // Ajustez la taille de la police selon vos besoins
            Button detailsButton = new Button("Détails");
            detailsButton.setStyle("-fx-background-color: #db1f48; -fx-text-fill: white;");
            detailsButton.setOnAction(e -> {
                try {
                    detailSeance(seance);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }); // Appeler la méthode detailSeance() lorsque le bouton est cliqué
            detailsButton.setId("btndetailseance");

            Button reserverButton = new Button("Réserver");
            reserverButton.setStyle("-fx-background-color: #db1f48; -fx-text-fill: white;");

            reserverButton.setOnAction(e -> {
                try {
                    reserverSeance(seance);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }); // Appeler la méthode reserverSeance() lorsque le bouton est cliqué
            reserverButton.setId("btnreserverseance");
            // Ajoutez un espace vertical entre les boutons
            VBox.setMargin(reserverButton, new Insets(5, 0, 0, 0)); // Espacement de 5 pixels en haut

            try {
                Image image = new Image(new File(seance.getImageseance()).toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(300);  // Définir la largeur selon vos besoins
                imageView.setFitHeight(300); // Définir la hauteur selon vos besoins
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // Gérer l'exception, par exemple, définir une image par défaut
                // imageView.setImage(defaultImage);
            }
            // Ajouter les libellés au VBox
            vbox.getChildren().addAll(imageView,nomLabel, detailsButton, reserverButton);

            // Ajouter le VBox au HBox
            hbox.getChildren().add(vbox);
        }

        // Ajouter le HBox à votre conteneur parent (par exemple, un ScrollPane ou directement à la scène)
        grid.getChildren().add(hbox);*/
        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(5));

        // Créez une liste pour stocker les noms des séances déjà ajoutées à l'interface utilisateur
        List<String> addedSeanceNames = new ArrayList<>();

        for (Seance seance : seanceList) {
            // Vérifiez d'abord si la séance est déjà affichée dans l'interface utilisateur
            if (!addedSeanceNames.contains(seance.getNom())) {
                addedSeanceNames.add(seance.getNom());

                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);

                ImageView imageView = new ImageView();
                Label nomLabel = new Label(seance.getNom());
                nomLabel.setFont(new Font("Arial", 18));

                Button detailsButton = new Button("Détails");
                detailsButton.setStyle("-fx-background-color: #db1f48; -fx-text-fill: white;");
                detailsButton.setOnAction(e -> {
                    try {
                        detailSeance(seance);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                detailsButton.setId("btndetailseance");

                Button reserverButton = new Button("Réserver");
                reserverButton.setStyle("-fx-background-color: #db1f48; -fx-text-fill: white;");
                reserverButton.setOnAction(e -> {
                    try {
                        reserverSeance(seance);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                reserverButton.setId("btnreserverseance");
                VBox.setMargin(reserverButton, new Insets(5, 0, 0, 0));

                try {
                    Image image = new Image(new File(seance.getImageseance()).toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(300);
                    imageView.setFitHeight(300);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                vbox.getChildren().addAll(imageView, nomLabel, detailsButton, reserverButton);
                hbox.getChildren().add(vbox);
            }
        }

        grid.getChildren().add(hbox);

    }
    // Méthode appelée lorsque le bouton "Détails" est cliqué
    public void detailSeance(Seance seance) throws IOException {
        try {

            String nomSeance = seance.getNom(); // Récupérer le nom de la séance
            // Charger le fichier FXML correspondant à la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tableauseanceclient.fxml"));
            Parent root = loader.load();
            Tableauseanceclient controller = loader.getController();
            controller.setNomSeance(nomSeance);
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            Stage stage = (Stage) grid.getScene().getWindow(); // Obtenez la scène actuelle du bouton ou de tout autre nœud
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception, par exemple, afficher un message d'erreur à l'utilisateur
        }
    }

    // Méthode appelée lorsque le bouton "Réserver" est cliqué
    private void reserverSeance(Seance seance) throws IOException {
        try {
            int idSeance = seance.getIdseance(); // Récupérer l id de la séance

            // Charger le fichier FXML correspondant à la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationFormulaire.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Reservationformulaire controller = loader.getController();
            controller.setSeance(seance);
            Scene scene = new Scene(root);
            Stage stage = (Stage) grid.getScene().getWindow(); // Obtenez la scène actuelle du bouton ou de tout autre nœud
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception, par exemple, afficher un message d'erreur à l'utilisateur
        }
    }

}

