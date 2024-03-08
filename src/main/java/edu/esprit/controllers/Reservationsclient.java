package edu.esprit.controllers;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static edu.esprit.utils.SessionManagement.checkFile;
import static edu.esprit.utils.SessionManagement.deleteSession;

public class Reservationsclient {
    @FXML
    private TextField Age;
    @FXML
    private Label nomseance;
    @FXML
    private Label nompersonne;

    @FXML
    private Label labelpoids;
    @FXML
    private Label labeltaille;

    @FXML
    private Label labelprenom;
    @FXML
    private Label labelnom;

    @FXML
    private TextField Poids;

    @FXML
    private TextField Taille;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;
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
    private Button btnprofil;
    @FXML
    private MenuItem logoutitem;

    @FXML
    private MenuButton profilbuttonmenu;

    @FXML
    private MenuItem profilitem;
    @FXML
    private Button btnreclamation;

    @FXML
    private TextField textfieldnom;

    @FXML
    private TextField textfieldprenom;
    @FXML
    private TextField textfieldtaille;

    @FXML
    private TextField textfiledpoids;
    @FXML
    private AnchorPane anchorpanegrand;
    @FXML
    private GridPane gridpane;
    @FXML
    private RadioButton homme;
    @FXML
    private RadioButton femme;

    @FXML
    private ListView<Reservation> listview;
    ServiceReservation sr =new ServiceReservation();
    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    // UserService us=new UserService();
    ClientService cs=new ClientService();
    Client u;

    {
        try {
            u = cs.getOneByEmail(mail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    User user;
    UserService userService=new UserService();

    {// u = cs.getOneByEmail(mail);
        //System.out.println("client :"+ u);
        user=userService.getOneByEmail(mail);
        System.out.println("user?: "+user);
        if(user.getRole().equals("CLIENT"))
        {
            System.out.println("CLIENTTTTTTTTTTTTTTTTTTTTTT");
        }
    }
    private final ServiceReservation servicereservation= new ServiceReservation();
    private ToggleGroup sexeToggleGroup;
    @FXML
    private ScrollPane scrollpane;
    public void initialize() {
        if(user.getRole().equals("CLIENT"))
        {
            profilitem.setText("Profile");
        }
        else
        {
            profilitem.setText("Dashbord");

        }

        profilbuttonmenu.setText(user.getNom());
        // Initialiser la liste observable pour stocker les réservations
        ObservableList<Reservation> reservationsList = FXCollections.observableArrayList();
        // Récupérer les réservations depuis le service ou tout autre moyen
        List<Reservation> reservations = null; // Récupérer les réservations depuis votre service
        reservations = sr.getReservationsByUser(mail); // Suppose que vous avez une méthode getAll() dans votre service

        // Ajouter les réservations à la liste observable
        if (reservations != null) {
            reservationsList.addAll(reservations);
        }

        // Lier la liste observable à la ListView
        listview.setItems(reservationsList);

        // Personnaliser l'affichage des éléments de la ListView si nécessaire
        /*listview.setCellFactory(param -> new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation reservation, boolean empty) {
                super.updateItem(reservation, empty);
                if (empty || reservation == null) {
                    setText(null);
                } else {
                    // Assurez-vous que l'utilisateur associé à la réservation est un client
                    if (u instanceof Client) {
                        // Si c'est un client, afficher les détails de la réservation dans la cellule
                      //  Client client = (Client) reservation.getUser();
                        Client client = (Client) u; // Convertir l'utilisateur en client

                        setText("Nom de la séance: " + reservation.getSeance().getNom() +
                                ", Nom de la personne: " + client.getNom() +
                                ", Prénom de la personne: " + client.getPrenom()+"poids: "+client.getPoids()+" Taille: " + client.getTaille());
                    } else {
                        // Si l'utilisateur n'est pas un client, afficher un message par défaut
                        setText("L'utilisateur associé à cette réservation n'est pas un client.");
                    }
                }
            }
        });*/
        listview.setCellFactory(param -> new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation reservation, boolean empty) {
                super.updateItem(reservation, empty);
                if (empty || reservation == null) {
                    setText(null);
                } else {
                    // Assurez-vous que l'utilisateur associé à la réservation est un client
                    //User u = reservation.getUser();

                        // Si c'est un client, afficher les détails de la réservation dans la cellule
                      //  Client client = (Client) u; // Convertir l'utilisateur en client

                        // Créer une HBox pour contenir l'image et les détails de la réservation
                        HBox reservationBox = new HBox();
                        reservationBox.setSpacing(10); // Espacement entre les éléments
                    Font customFont = Font.font("Arial", FontWeight.BOLD, 12); // Exemple de police Arial, gras, taille 12

                        // Charger l'image de la séance

                     //  Image seanceImage = new Image(reservation.getSeance().getImageseance());
                        //ImageView imageView = new ImageView(seanceImage);
                    ImageView imageView = new ImageView(new Image("file:" + reservation.getSeance().getImageseance()));

                    imageView.setFitWidth(200); // Largeur de l'image
                       imageView.setFitHeight(200); // Hauteur de l'image

                        // Créer une VBox pour contenir les informations sur la réservation
                        VBox reservationInfoBox = new VBox();
                        reservationInfoBox.setSpacing(5); // Espacement entre les informations

                        // Afficher les détails de la réservation dans la VBox

                        Label seanceLabel = new Label( reservation.getSeance().getNom());
                        Label jourseance = new Label(reservation.getSeance().getJourseance());

                // Convertir l'objet Time en chaîne de caractères avec le format souhaité
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    String horaireSeanceStr = timeFormat.format(reservation.getSeance().getHoraire());
                    // Créer un Label avec la chaîne de caractères formatée
                    Label horaireSeance = new Label(horaireSeanceStr);
                    Label userNomLabel = new Label( u.getNom());
                        Label userPrenomLabel = new Label( u.getPrenom());
                        Label poidsLabel = new Label("Poids: " + u.getPoids());
                        Label tailleLabel = new Label("Taille: " + u.getTaille());
                        horaireSeance.setFont(customFont);
                        seanceLabel.setFont(customFont);
                        jourseance.setFont(customFont);
                        userNomLabel.setFont(customFont);
                        userPrenomLabel.setFont(customFont);
                        poidsLabel.setFont(customFont);
                        tailleLabel.setFont(customFont);
                        // Ajouter les labels à la VBox
                        reservationInfoBox.getChildren().addAll(seanceLabel,jourseance,horaireSeance, userNomLabel, userPrenomLabel, poidsLabel, tailleLabel);

                        // Ajouter l'image et les informations de réservation à la HBox
                       reservationBox.getChildren().addAll(imageView, reservationInfoBox);
                    //reservationBox.getChildren().addAll( reservationInfoBox);


                    // Définir la HBox comme contenu de la cellule
                        setGraphic(reservationBox);
                        setText(null);
                        setStyle("-fx-background-color: pink;");

                }
            }
        });


        // Gérer les événements de sélection dans la ListView
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour les champs de texte avec les détails de la réservation sélectionnée
                // Assurez-vous que l'utilisateur associé à la réservation est un client


                    labelnom.setText(u.getNom());
                    labelprenom.setText(u.getPrenom());
                    nomseance.setText(newValue.getSeance().getNom());
                   labeltaille.setText(String.valueOf(u.getTaille()));
                   labelpoids.setText(String.valueOf(u.getPoids()));
                    // Vous pouvez également mettre à jour d'autres champs du formulaire ici si nécessaire
               /*  else {
                    // Si l'utilisateur n'est pas un client, effacer les champs du formulaire ou effectuer une autre action appropriée
                    textfieldnom.clear();
                    textfieldprenom.clear();
                    nomseance.setText("");

                    // Effacer d'autres champs du formulaire si nécessaire
                }*/
            }
        });
    }
    private void clearForm () {
        labelnom.setText("");
        labelprenom.setText("");
        nomseance.setText("");
        labelpoids.setText("");
        labeltaille.setText("");
        // Désélectionner les boutons radio du groupe "sexe"
       // sexeToggleGroup.getSelectedToggle().setSelected(false);
    }
    @FXML
    void modifier(ActionEvent event) {
        // Récupérer la réservation sélectionnée dans la ListView
       /* Reservation selectedReservation = listview.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            // Mettre à jour les informations de la réservation
            String nomPersonne = nompersonne.getText();
            String nomseancee = nomseance.getText();
            String ageStr = Age.getText();
            String poidsStr = Poids.getText();
            String tailleStr = Taille.getText();
            String sexe = getSelectedSexe();

            // Vérifier que tous les champs sont remplis
            boolean isValid = true;
            if (nomPersonne.isEmpty() || nomseancee.isEmpty() || ageStr.isEmpty() || poidsStr.isEmpty() || tailleStr.isEmpty() || sexe == null) {
                isValid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
            } else {
                try {
                    // Mettre à jour les informations de la réservation
                    selectedReservation.setNom(nomPersonne);
                   // selectedReservation.setSeance(nompersonne);

                    selectedReservation.setAge(Integer.parseInt(ageStr));
                    selectedReservation.setPoids(Float.parseFloat(poidsStr));
                    selectedReservation.setTaille(Float.parseFloat(tailleStr));
                    selectedReservation.setSexe(sexe);

                    // Mettre à jour la réservation dans la base de données
                    sr.modifier(selectedReservation);

                    // Afficher une alerte de confirmation
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification réussie");
                    alert.setContentText("La réservation a été modifiée avec succès.");
                    alert.showAndWait();

                    // Rafraîchir l'affichage des réservations
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur lors de la modification");
                    alert.setContentText("Une erreur s'est produite lors de la modification de la réservation : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            // Aucune réservation sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à modifier.");
            alert.showAndWait();
        }
        clearForm();*/

        //yekhdm
        // Récupérer la réservation sélectionnée dans la ListView
     /*   Reservation selectedReservation = listview.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            // Récupérer les nouvelles valeurs depuis les champs de texte
            String newNom = nompersonne.getText();
            String newAgeStr = Age.getText();
            String newPoidsStr = Poids.getText();
            String newTailleStr = Taille.getText();
            String newSexe = getSelectedSexe();

            // Définition des constantes pour les messages d'erreur
            final String ERROR_TITLE = "Validation Error";
            final String ERROR_STYLE = "-fx-text-fill: red;";
            final String ERROR_MSG_EMPTY = "Please fill in all the fields.";
            final String ERROR_MSG_AGE = "Age must be a number between 1 and 99.";
            final String ERROR_MSG_POIDS = "Weight must be a positive number greater than 0.";
            final String ERROR_MSG_TAILLE = "Height must be a positive number greater than 0.";

            // Vérifier que tous les champs sont remplis
            if (newNom.isEmpty() ||  newAgeStr.isEmpty() || newPoidsStr.isEmpty() || newTailleStr.isEmpty() || newSexe == null) {
                // Afficher un message d'erreur si un champ est vide
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText(ERROR_MSG_EMPTY);
                alert.showAndWait();
                return; // Arrêter l'exécution de la méthode si un champ est vide
            }

            // Validation de l'âge
            if (!newAgeStr.matches("\\d{1,2}") || Integer.parseInt(newAgeStr) <= 0 || Integer.parseInt(newAgeStr) >= 100) {
                Age.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText(ERROR_MSG_AGE);
                alert.showAndWait();
                return;
            }

            // Validation du poids
            if (!newPoidsStr.matches("\\d+(\\.\\d+)?") || Float.parseFloat(newPoidsStr) <= 0) {
                Poids.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText(ERROR_MSG_POIDS);
                alert.showAndWait();
                return;
            }

            // Validation de la taille
            // Validation de la taille
            if (!newTailleStr.matches("\\d{1,3}(\\.\\d+)?") || Float.parseFloat(newTailleStr) <= 0) {
                Taille.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText(ERROR_MSG_TAILLE);
                alert.showAndWait();
                return;
            }

            // Convertir les valeurs d'âge, poids et taille en nombres
            int newAge = Integer.parseInt(newAgeStr);
            float newPoids = Float.parseFloat(newPoidsStr);
            float newTaille = Float.parseFloat(newTailleStr);

            // Mettre à jour les valeurs de la réservation sélectionnée
            selectedReservation.setNom(newNom);
            selectedReservation.setAge(newAge);
            selectedReservation.setPoids(newPoids);
            selectedReservation.setTaille(newTaille);
            selectedReservation.setSexe(newSexe);

            // Appeler la méthode de service pour modifier la réservation dans la base de données
            try {
                sr.modifier(selectedReservation);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification");
                alert.setContentText("Reservation modified successfully");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("An error occurred while modifying the reservation: " + e.getMessage());
                alert.showAndWait();
            }

            // Rafraîchir l'affichage des réservations dans la ListView
            listview.refresh();
        } else {
            // Aucune réservation sélectionnée, afficher un message d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setContentText("Please select a reservation to modify.");
            alert.showAndWait();
        }*/
        //ykhdm fih fazt poids
        // Récupérer les informations modifiées depuis le formulaire
       /* String nomPersonne = nompersonne.getText();
        String ageStr = Age.getText();
       String poidsStr = Poids.getText();
       String tailleStr = Taille.getText();

        String sexe = getSelectedSexe();

        // Expression régulière pour l'âge
        String ageRegex = "\\d{1,2}";
        // Expression régulière pour le poids
        String poidsRegex = "^\\d+(\\.\\d+)?$";
        // Expression régulière pour la taille
        String tailleRegex = "^\\d+(\\.\\d+)?$";

        // Définition des constantes pour les messages d'erreur
        final String ERROR_TITLE = "Validation Error";
        final String ERROR_STYLE = "-fx-text-fill: red;";

        // Vérifier que les champs sont remplis
        boolean isValid = true;
        if (ageStr.isEmpty() || poidsStr.isEmpty() || tailleStr.isEmpty() || sexe == null) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ERROR_TITLE);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            // Contrôle de saisie pour l'âge
            if (!ageStr.matches(ageRegex) || Integer.parseInt(ageStr) <= 0) {
                isValid = false;
                Age.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir un âge valide (entre 1 et 2 chiffres).");
                alert.showAndWait();
            } else {
                Age.setStyle("");
            }

            // Contrôle de saisie pour le poids
            if (!poidsStr.matches(poidsRegex) || Integer.parseInt(poidsStr) <= 0) {
                isValid = false;
                Poids.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir un poids valide (nombre décimal positif).");
                alert.showAndWait();
            } else {
                Poids.setStyle("");
            }

            // Contrôle de saisie pour la taille
            if (!tailleStr.matches(tailleRegex) || Float.parseFloat(tailleStr) <= 0) {
                isValid = false;
                Taille.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir une taille valide (nombre décimal positif supérieur à zéro).");
                alert.showAndWait();
            } else {
                Taille.setStyle("");
            }
        }

        if (isValid) {
            // Les saisies sont valides, procéder à la modification
            Reservation selectedReservation = listview.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                selectedReservation.setNom(nomPersonne);
                // selectedReservation.setSeance(nompersonne);
                selectedReservation.setAge(Integer.parseInt(ageStr));
               selectedReservation.setPoids(Float.parseFloat(poidsStr));
               selectedReservation.setTaille(Float.parseFloat(tailleStr));

                selectedReservation.setSexe(sexe);


                // Mettre à jour la réservation dans la base de données
                try {
                    sr.modifier(selectedReservation);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification");
                    alert.setContentText("Réservation modifiée avec succès.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Exception");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }

                clearForm(); // Effacer les champs du formulaire après la modification
                listview.refresh();

            } else {
                // Afficher un message à l'utilisateur indiquant qu'aucune réservation n'a été sélectionnée
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez sélectionner une réservation à modifier.");
                alert.showAndWait();
            }
        }*/
        // Récupérer les informations modifiées depuis le formulaire
       /* String nomPersonne = textfieldnom.getText();
        String prenomPersonne=textfieldprenom.getText();
        String poidsStr = Poids.getText();
        String tailleStr = Taille.getText();
        String sexe = getSelectedSexe();


// Expression régulière pour le poids
        String poidsRegex = "^\\d+(\\.\\d+)?$";
// Expression régulière pour la taille
        String tailleRegex = "^\\d+(\\.\\d+)?$";

// Définition des constantes pour les messages d'erreur
        final String ERROR_TITLE = "Validation Error";
        final String ERROR_STYLE = "-fx-text-fill: red;";

// Vérifier que les champs sont remplis
        boolean isValid = true;
        if (poidsStr.isEmpty() || tailleStr.isEmpty() || sexe == null) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ERROR_TITLE);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            // Contrôle de saisie pour l'âge


            // Contrôle de saisie pour le poids
            if (!poidsStr.matches(poidsRegex) || Float.parseFloat(poidsStr) <= 0) {
                isValid = false;
                Poids.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir un poids valide (nombre décimal positif).");
                alert.showAndWait();
            } else {
                Poids.setStyle("");
            }

            // Contrôle de saisie pour la taille
            if (!tailleStr.matches(tailleRegex) || Float.parseFloat(tailleStr) <= 0) {
                isValid = false;
                Taille.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir une taille valide (nombre décimal positif supérieur à zéro).");
                alert.showAndWait();
            } else {
                Taille.setStyle("");
            }
        }

        if (isValid) {
            // Les saisies sont valides, procéder à la modification
            Reservation selectedReservation = listview.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                // selectedReservation.setSeance(nompersonne);
                Client client = (Client) selectedReservation.getUser();
                // Modifier les propriétés du client
                client.setPoids(Float.parseFloat(poidsStr));
                client.setTaille(Float.parseFloat(tailleStr));
                client.setSexe(sexe);



                // Mettre à jour la réservation dans la base de données
                try {
                    sr.modifier(selectedReservation);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification");
                    alert.setContentText("Réservation modifiée avec succès.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Exception");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }

                clearForm(); // Effacer les champs du formulaire après la modification
                listview.refresh();
            } else {
                // Afficher un message à l'utilisateur indiquant qu'aucune réservation n'a été sélectionnée
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez sélectionner une réservation à modifier.");
                alert.showAndWait();
            }
        }*/
        String nomPersonne = textfieldnom.getText().trim();
        String prenomPersonne = textfieldprenom.getText().trim();


// Expressions régulières pour le nom et le prénom
        String nomRegex = "^[A-Za-zÀ-ÿ\\s-]{1,50}$"; // Autorise les lettres, les espaces, les tirets et les apostrophes, jusqu'à 50 caractères
        String prenomRegex = "^[A-Za-zÀ-ÿ-]{1,50}$"; // Autorise les lettres et les tirets, jusqu'à 50 caractères

// Définition des constantes pour les messages d'erreur
        final String ERROR_TITLE = "Validation Error";
        final String ERROR_STYLE = "-fx-text-fill: red;";

// Vérifier que les champs sont remplis et respectent les contraintes de saisie
        boolean isValid = true;
        if (nomPersonne.isEmpty() || prenomPersonne.isEmpty()) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ERROR_TITLE);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            if (!nomPersonne.matches(nomRegex)) {
                isValid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir un nom valide.");
                alert.showAndWait();
            }
            if (!prenomPersonne.matches(prenomRegex)) {
                isValid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez saisir un prénom valide.");
                alert.showAndWait();
            }
        }

        if (isValid) {
            // Les saisies sont valides, procéder à la modification
            Reservation selectedReservation = listview.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                //Client client = (Client) selectedReservation.getUser();
                // Modifier les propriétés du client
               // selectedReservation.setPoids(Float.parseFloat(poidsStr));
               // selectedReservation.setTaille(Float.parseFloat(tailleStr));

               u.setNom(nomPersonne);
                u.setPrenom(prenomPersonne);

                // Mettre à jour la réservation dans la base de données
                try {
                    sr.modifier(selectedReservation);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification");
                    alert.setContentText("Réservation modifiée avec succès.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Exception");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }

                clearForm(); // Effacer les champs du formulaire après la modification
               listview.refresh();
            } else {
                // Afficher un message à l'utilisateur indiquant qu'aucune réservation n'a été sélectionnée
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Veuillez sélectionner une réservation à modifier.");
                alert.showAndWait();
            }

        }

    }
    @FXML
    void supprimer(ActionEvent event) {
        // Récupérer la réservation sélectionnée
        Reservation selectedReservation = listview.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer cette réservation ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la suppression
                try {
                    sr.supprimer(selectedReservation.getIdReservation());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression réussie");
                    successAlert.setContentText("La réservation a été supprimée avec succès.");
                    successAlert.showAndWait();
                    clearForm();
                    // Rafraîchir les données dans la ListView
                    listview.getItems().remove(selectedReservation);
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur lors de la suppression");
                    errorAlert.setContentText("Une erreur s'est produite lors de la suppression de la réservation : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        } else {
            // Afficher un message à l'utilisateur indiquant qu'aucune réservation n'a été sélectionnée
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Aucune réservation sélectionnée");
            noSelectionAlert.setHeaderText(null);
            noSelectionAlert.setContentText("Veuillez sélectionner une réservation à supprimer.");
            noSelectionAlert.showAndWait();
        }
    }
    private String getSelectedSexe() {
        RadioButton selectedRadioButton = (RadioButton) sexeToggleGroup.getSelectedToggle();
        return selectedRadioButton.getText();
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
    void toProfile(ActionEvent event) throws IOException {
        if (user.getRole().equals("CLIENT")) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/profil.fxml"));
            anchorpanegrand.getChildren().setAll(pane);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newWindow = new Stage();
            newWindow.setTitle("Admin Dashboard");
            newWindow.setScene(scene);
            newWindow.show();
        }
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        if (checkFile()) {
            deleteSession();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
            anchorpanegrand.getChildren().setAll(pane);
        }
    }
    @FXML
    void abonnement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesAbonnements.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnabonnement.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void accueil(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/acceuil.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnaccueil.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void alimentaire(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnalimentaire.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void equipement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementFront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnequipement.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void evenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnevenement.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void reclamation(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }
    }


