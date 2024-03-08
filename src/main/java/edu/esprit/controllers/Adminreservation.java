package edu.esprit.controllers;

import edu.esprit.Api.SendEmail;
import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.User;
import edu.esprit.services.AdminService;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.UserService;
import edu.esprit.utils.SessionManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Adminreservation {
    @FXML
    private ListView<Reservation> listview;

    @FXML
    private Button btnrefraichir;

    @FXML
    private Button btnrecherche;
    @FXML
    private Button btnannuler;
    @FXML
    private Button btnstats;
    @FXML
    private Button btntdb1;
    @FXML
    private Button btnreclamation1;
    @FXML
    private Button btnequipement1;

    @FXML
    private Button btnevenement1;

    @FXML
    private Button btnplanning1;
    @FXML
    private Button btnabonnement1;

    @FXML
    private Button btnalimentaire1;
    @FXML
    private Button btntri;

    @FXML
    private ComboBox<String> comboboxtri;

    @FXML
    private GridPane gridpane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private TextField textfieldrecherche;
    @FXML
    private ProgressBar progressBar;
    List<Reservation> resList;
    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    User user;
    //Client u;
    Admin Ad;
    ClientService A=new ClientService();
    UserService userService=new UserService();
    //AdminService adminService=new AdminService();
    {
        user = userService.getOneByEmail(mail);
        System.out.println("mail: "+mail);
        // Ad=adminService.getOneByEmail(mail);
        // u = A.getOneByEmail(mail);

    }
    private final ObservableList<String> criteresTri = FXCollections.observableArrayList("Nom", "Nomseance", "Prenom");

    public void initialize() {
        // Ajouter les critères de tri au ComboBox
        comboboxtri.setItems(criteresTri);

        // Définir le critère de tri par défaut
        comboboxtri.getSelectionModel().selectFirst();
        afficherReservations();
    }


    @FXML
    void recherche(ActionEvent event) {
        if (resList == null) {
            // Récupérer les réservations depuis le service
            try {
                resList = serviceReservation.getAll();
            } catch (SQLException e) {
                e.printStackTrace();
                return; // Sortir de la méthode si une exception se produit lors de la récupération des réservations
            }
        }

// Récupérer le nom saisi par l'utilisateur depuis le champ de recherche
        String nomRecherche = textfieldrecherche.getText().trim();

// Vérifier si le champ de recherche n'est pas vide
        if (!nomRecherche.isEmpty()) {
            // Filtrer les réservations en fonction du nom recherché
            List<Reservation> reservationsFiltrees = resList.stream()
                    .filter(reservation -> {
                        // Vérifier si l'utilisateur associé à la réservation est un client
                        if (reservation.getUser() instanceof Client) {
                            // Si c'est un client, vérifier si le nom correspond au nom recherché
                            String nomPersonne = (reservation.getSeance()).getNom();
                            return nomPersonne.equalsIgnoreCase(nomRecherche);
                        }
                        return false; // Si l'utilisateur n'est pas un client, exclure cette réservation
                    })
                    .collect(Collectors.toList());

            // Mettre à jour la liste affichée avec les résultats de la recherche
            afficherReservations(reservationsFiltrees);
        } else {
            // Si le champ de recherche est vide, afficher toutes les réservations
            afficherReservations(resList);
        }
    }

    @FXML
    void stats(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistiquesreservation.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnstats.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void tri(ActionEvent event) {
        // Récupérer le critère de tri sélectionné par l'utilisateur
        String critereTri = comboboxtri.getValue();

        // Trier la liste des réservations en fonction du critère sélectionné
        List<Reservation> reservationsTrie = trierReservations(critereTri);

        // Mettre à jour la ListView avec la liste triée
        listview.setItems(FXCollections.observableArrayList(reservationsTrie));

    }
    public List<Reservation> trierReservations(String critereTri) {
        List<Reservation> reservations = null;
        try {
            // Récupérer la liste complète des réservations
            reservations = serviceReservation.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (reservations == null) {
            return new ArrayList<>();
        }

        // Utiliser Stream pour trier les réservations en fonction du critère sélectionné
        switch (critereTri) {
            case "Nom":
                return reservations.stream()
                        .sorted(Comparator.comparing(reservation -> reservation.getUser().getNom()))
                        .collect(Collectors.toList());
            case "Nomseance":
                return reservations.stream()
                        .sorted(Comparator.comparing(reservation -> reservation.getSeance().getNom())).collect(Collectors.toList());
            case "Prenom":
                return reservations.stream()
                        .sorted(Comparator.comparing(reservation -> reservation.getUser().getPrenom()))
                        .collect(Collectors.toList());
            default:
                // Retourner la liste non triée si le critère de tri n'est pas reconnu
                return reservations;
        }
    }


    private final ServiceReservation serviceReservation = new ServiceReservation();
    private final ClientService clientService = new ClientService();

    public void afficherReservations() {

       /* try {
            List<Reservation> reservations = serviceReservation.getAll();

            // Créer une ObservableList contenant les réservations
            ObservableList<Reservation> reservationsObservableList = FXCollections.observableArrayList(reservations);

            // Lier la liste observable à la ListView
            listview.setItems(reservationsObservableList);

            // Personnaliser l'affichage des éléments de la ListView avec un ListCell personnalisé
            listview.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } else {
                        // Afficher les détails de la réservation dans la cellule
                        String reservationDetails = "la séance " + reservation.getSeance().getNom() +
                                " Nom de l'utilisateur: " + reservation.getUser().getNom() +
                                " Prénom de l'utilisateur: " + reservation.getUser().getPrenom() ;
                        setText(reservationDetails);

                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try {
            List<Reservation> reservations = serviceReservation.getAll();

            // Créer une ObservableList contenant les réservations
            ObservableList<Reservation> reservationsObservableList = FXCollections.observableArrayList(reservations);

            // Lier la liste observable à la ListView
            listview.setItems(reservationsObservableList);

            // Personnaliser l'affichage des éléments de la ListView avec un ListCell personnalisé
            listview.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } else {
                        // Créer une HBox pour contenir les détails de la réservation
                        HBox reservationDetailsBox = new HBox();
                        reservationDetailsBox.setSpacing(10); // Espacement entre les éléments

                        // Créer une VBox pour contenir les informations sur la réservation
                        VBox reservationInfoBox = new VBox();
                        reservationInfoBox.setSpacing(5); // Espacement entre les informations
                        Font customFont = Font.font("Arial", FontWeight.BOLD, 12); // Exemple de police Arial, gras, taille 12

                        // Afficher les détails de la réservation dans la VBox
                        Label seanceLabel = new Label(reservation.getSeance().getNom());
                        Label userNomLabel = new Label( reservation.getUser().getNom());
                        Label userPrenomLabel = new Label( reservation.getUser().getPrenom());
                        Label jourseance = new Label(reservation.getSeance().getJourseance());

                        // Convertir l'objet Time en chaîne de caractères avec le format souhaité
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        String horaireSeanceStr = timeFormat.format(reservation.getSeance().getHoraire());
                        // Créer un Label avec la chaîne de caractères formatée
                        Label horaireSeance = new Label(horaireSeanceStr);
                        seanceLabel.setFont(customFont);
                        userNomLabel.setFont(customFont);
                        userPrenomLabel.setFont(customFont);
                        jourseance.setFont(customFont);
                        horaireSeance.setFont(customFont);
                        // Ajouter les labels à la VBox
                        reservationInfoBox.getChildren().addAll(seanceLabel,jourseance,horaireSeance, userNomLabel, userPrenomLabel);

                        // Charger l'image de la séance
                       // Image seanceImage = new Image(reservation.getSeance().getImageseance());
                        ImageView imageView = new ImageView(new Image("file:" + reservation.getSeance().getImageseance()));

                       // ImageView imageView = new ImageView(imageView);
                        imageView.setFitWidth(200); // Largeur de l'image
                        imageView.setFitHeight(200); // Hauteur de l'image

                        // Ajouter l'image et les informations de réservation à la HBox
                        reservationDetailsBox.getChildren().addAll(imageView, reservationInfoBox);

                        // Définir la HBox comme contenu de la cellule
                        setGraphic(reservationDetailsBox);
                        setText(null);
                        setStyle("-fx-background-color: pink;");

                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void annuler(ActionEvent event) {
        // Récupérer la réservation sélectionnée dans la ListView
        Reservation selectedReservation = listview.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            // Aucune réservation sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une réservation à annuler.");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir annuler cette réservation ?");
        confirmationDialog.setContentText("Cliquez sur Oui pour confirmer ou sur Non pour annuler.");

        // Définir les boutons de la boîte de dialogue
        ButtonType buttonTypeOui = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNon = new ButtonType("Non", ButtonBar.ButtonData.NO);
        confirmationDialog.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeOui) {
                // L'utilisateur a confirmé, supprimer la réservation de la base de données
                try {
                    serviceReservation.supprimer(selectedReservation.getIdReservation());
                    // Mettre à jour l'affichage de la liste des réservations
                    afficherReservations();
                    System.out.println(mail);
                    SendEmail.send("sarra.hammemi@esprit.tn");
                    progressBar.setProgress(0.5);
                    // Créer une pause de 1 seconde avant de mettre à jour la ProgressBar à 100%
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(evente -> {
                        progressBar.setProgress(1.0);
                    });
                    pause.play();

                } catch (SQLException e) {
                    e.printStackTrace();
                    // Afficher une erreur en cas d'échec de suppression
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Une erreur s'est produite lors de la suppression de la réservation.");
                    alert.showAndWait();

                }
            } else {
                // L'utilisateur a annulé, ne rien faire
                System.out.println("Annulation de la suppression de la réservation.");
            }
        });
    }
    public void afficherReservations(List<Reservation> reservations) {
        ObservableList<Reservation> reservationsData = FXCollections.observableArrayList(reservations);
        listview.setItems(reservationsData);
    }

    @FXML
    void refraichir(ActionEvent event) {
        afficherReservations();

    }

    @FXML
    void tableaudebord(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void reclamation(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationB.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void equipement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void evenement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenementListeView.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void planning(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/seanceadmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void abonnement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnemntsBack.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void alimentaire(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlatUser.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
