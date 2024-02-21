package edu.esprit.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import com.itextpdf.text.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Reservationformulaire {

    @FXML
    private Button btnpdf;
    @FXML
    private TableColumn<?, ?> age;
    @FXML
    private TableColumn<?, ?> nompersonne;

    @FXML
    private TableColumn<?, ?> nomseance;

    @FXML
    private TableColumn<?, ?> poids;

    @FXML
    private TableColumn<?, ?> prenom;

    @FXML
    private TableColumn<?, ?> sexe;

    @FXML
    private TableView<Reservation> table;

    @FXML
    private TableColumn<?, ?> taille;
    @FXML
    private TextField Age;

    @FXML
    private TextField Firstname;

    @FXML
    private TextField Lastname;

    @FXML
    private TextField Poids;
    @FXML
    private Button btnmesreservations;

    @FXML
    private Button btnstatistiques;
    @FXML
    private TextField Taille;
    @FXML
    private RadioButton femme;

    @FXML
    private RadioButton homme;
    private ToggleGroup sexeToggleGroup;

    @FXML
    private BorderPane borderpaneformulairereservaion;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnaccueil;

    @FXML
    private Button btnafficherreservation;

    @FXML
    private Button btnajouterreservation;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnmodifierreservation;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private Button btnsupprimerreservation;


    @FXML
    private ImageView imageuser;

    @FXML
    private ImageView logo;


    private Seance seance; // Séance sélectionnée pour la réservation
   private final ServiceReservation sr=new ServiceReservation();
    public void initialize() throws IOException {

        // Créer un groupe pour les RadioButton
        sexeToggleGroup = new ToggleGroup();

        // Ajouter les RadioButton au groupe
        homme.setToggleGroup(sexeToggleGroup);
        femme.setToggleGroup(sexeToggleGroup);
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifie si c'est un simple clic
                Reservation selectedSeance = table.getSelectionModel().getSelectedItem();
                if (selectedSeance != null) {
                    // Afficher les informations de la séance sélectionnée dans le formulaire
                    displayReservationInfo(selectedSeance);
                }
            }
        });
        ShowReservation();

    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    @FXML
    void abonnement(ActionEvent event) {

    }

    @FXML
    void accueil(ActionEvent event) {

    }

    @FXML
    void afficherreservation(ActionEvent event) {

    }

    @FXML
    void ajouterreservation(ActionEvent event) {
        // Récupérer les valeurs saisies dans les champs du formulaire
        String nom = Firstname.getText();
        String prenom = Lastname.getText();
        String ageStr = Age.getText();
        String poidsStr = Poids.getText();
        String tailleStr = Taille.getText();
        String sexe = getSelectedSexe();

        // Définition des constantes pour les messages d'erreur
        final String ERROR_TITLE = "Validation Error";
        final String ERROR_STYLE = "-fx-text-fill: red;";
        final String ERROR_MSG_GENERIC = "Please correct the input fields marked in red.";

        // Vérifier que les champs sont remplis
        boolean isValid = true;
        if (nom.isEmpty() || prenom.isEmpty() || ageStr.isEmpty() || poidsStr.isEmpty() || tailleStr.isEmpty() || sexe == null) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ERROR_TITLE);
            alert.setContentText(ERROR_MSG_GENERIC);
            alert.showAndWait();
        } else {
            // Vérifier que les saisies sont valides
            int age = 0;
            float poids = 0.0f;
            float taille = 0.0f;
            try {
                age = Integer.parseInt(ageStr);
                poids = Float.parseFloat(poidsStr);
                taille = Float.parseFloat(tailleStr);
                // Vérifier que l'âge, le poids et la taille sont supérieurs à 0
                if (age <= 0 || poids <= 0 || taille <= 0) {
                    isValid = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(ERROR_TITLE);
                    alert.setContentText("Please correct the input fields marked in red (<0).");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                isValid = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText(ERROR_MSG_GENERIC);
                alert.showAndWait();
            }
        }

        if (!isValid) {
            // Appliquer le style rouge aux champs de texte vides ou invalides
            if (nom.isEmpty() || !nom.matches("[a-zA-Z]+")) {
                Firstname.setStyle(ERROR_STYLE);
            } else {
                Firstname.setStyle("");
            }
            if (prenom.isEmpty() || !prenom.matches("[a-zA-Z]+")) {
                Lastname.setStyle(ERROR_STYLE);
            } else {
                Lastname.setStyle("");
            }
            if (ageStr.isEmpty() || !ageStr.matches("\\d+")) {
                Age.setStyle(ERROR_STYLE);
            } else {
                Age.setStyle("");
            }
            if (poidsStr.isEmpty() || !poidsStr.matches("\\d+(\\.\\d+)?")) {
                Poids.setStyle(ERROR_STYLE);
            } else {
                Poids.setStyle("");
            }
            if (tailleStr.isEmpty() || !tailleStr.matches("\\d+(\\.\\d+)?")) {
                Taille.setStyle(ERROR_STYLE);
            } else {
                Taille.setStyle("");
            }
            if (sexe == null) {
                femme.setStyle(ERROR_STYLE);
                homme.setStyle(ERROR_STYLE);
            } else {
                femme.setStyle("");
                homme.setStyle("");
            }
        } else {
            // Les saisies sont valides, procéder au traitement
            User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
            ClientService cs = new ClientService();
            User u = cs.getClientById(6);
            int age = Integer.parseInt(ageStr);
            float poids = Float.parseFloat(poidsStr);
            float taille = Float.parseFloat(tailleStr);

            // Obtenir la liste des réservations pour la séance sélectionnée
            List<Reservation> reservationsPourSeance = new ArrayList<>();
            for (Reservation r : resList) {
                if (r.getSeance().equals(seance)) {
                    reservationsPourSeance.add(r);
                }
            }

            // Vérifier si le nombre de réservations pour cette séance est inférieur au nombre maximum
            if (reservationsPourSeance.size() >= seance.getNbMax()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Seance Complète");
                alert.setContentText("La séance est complète. Le nombre maximal de réservations a été atteint.");
                alert.showAndWait();
            } else {
                // Ajouter la réservation
                Reservation nouvelleReservation = new Reservation(seance, nom, prenom, age, poids, taille, sexe, u);
                ServiceReservation serviceReservation = new ServiceReservation();
                try {
                    serviceReservation.ajouter(nouvelleReservation);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Réservation ajoutée avec succès");
                    alert.showAndWait();
                    // Calcul de l'IMC en fonction du sexe
                    double imc;
                    String etat;
                    if (sexe.equals("Homme")) {
                        imc = poids / (taille * taille);
                    } else {
                        imc = poids / (taille * taille) * 0.9;
                    }

// Détermination de l'état pondéral en fonction de l'IMC
                    if (imc < 18.5) {
                        etat = "Insuffisance pondérale";
                    } else if ( imc > 18.5 && imc < 25) {
                        etat = "Poids normal";
                    } else if ( imc>25 && imc < 30) {
                        etat = "Surpoids";
                    } else {
                        etat = "Obésité";
                    }
                    // Affichage de l'IMC et de l'état pondéral
                    Alert alertimc = new Alert(Alert.AlertType.INFORMATION);
                    alertimc.setTitle("IMC");
                    alertimc.setContentText("Votre IMC est : " + imc + "\n" + "Votre état pondéral : " + etat);
                    alertimc.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Exception");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                clearForm();
                try {
                    ShowReservation();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private String getSelectedSexe() {
        RadioButton selectedRadioButton = (RadioButton) sexeToggleGroup.getSelectedToggle();
        return selectedRadioButton.getText();
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
    void modifierreservation(ActionEvent event) {
        // Récupérer les informations modifiées depuis le formulaire
        String nom = Firstname.getText();
        String prenom = Lastname.getText();
        String ageStr = Age.getText();
        String poidsStr = Poids.getText();
        String tailleStr = Taille.getText();
        String sexe = getSelectedSexe();
        final String ERROR_TITLE = "Validation Error";
        final String ERROR_STYLE = "-fx-text-fill: red;";
        final String ERROR_MSG_GENERIC = "Please correct the input fields marked in red.";

            // Mettre à jour la séance dans la base de données
            Reservation selectedSeance = table.getSelectionModel().getSelectedItem();
            if (selectedSeance != null) {
                selectedSeance.setNom(nom);
                selectedSeance.setPrenom(prenom);
                selectedSeance.setAge(Integer.parseInt(ageStr));
                selectedSeance.setPoids(Float.parseFloat(poidsStr));
                selectedSeance.setTaille(Float.parseFloat(tailleStr));
                selectedSeance.setSexe(sexe);


                // Mettre à jour la séance dans la base de données
                try {
                    sr.modifier(selectedSeance);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification");
                    alert.setContentText("reservation modified succesfully");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Exeption");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                // Rafraîchir l'affichage des séances dans la TableView


            }
            clearForm();
        try {
            ShowReservation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
    void supprimerreservation(ActionEvent Actionevent) {

        Reservation selectedSeance = table.getSelectionModel().getSelectedItem();

        if (selectedSeance != null) {
            // Supprimer la séance de la base de données
            try {
                sr.supprimer(selectedSeance.getIdReservation());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression");
                alert.setContentText("Seance Deleted succesfully");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Exeption");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            // Rafraîchir les données dans le TableView
            try {
                ShowReservation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Afficher un message à l'utilisateur indiquant qu'aucune séance n'a été sélectionnée
            System.out.println("Veuillez sélectionner une séance à supprimer.");
        }
        clearForm();
    }
        private void clearForm () {
            Lastname.clear();
            Firstname.clear();
            Age.clear();
            Poids.clear();
            Taille.clear();
            // Désélectionner les boutons radio du groupe "sexe"
            sexeToggleGroup.getSelectedToggle().setSelected(false);

        }
    List<Reservation> resList;
    public void ShowReservation() throws IOException {

        try {
            resList = sr.getAll();
            System.out.println(resList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Reservation> filtredResList= new ArrayList<>();
        User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
        ClientService us = new ClientService();
        User u = us.getClientById(6);
        for (Reservation r:resList) {
            if (r.getUser().equals(u)) {
                filtredResList.add(r);
            }
        }

        nomseance.setCellValueFactory(new PropertyValueFactory<>("seance"));
        nompersonne.setCellValueFactory(new PropertyValueFactory<>("nompersonne"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        poids.setCellValueFactory(new PropertyValueFactory<>("poids"));
        taille.setCellValueFactory(new PropertyValueFactory<>("taille"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        if (table != null && table instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Reservation>) table).setItems(FXCollections.observableArrayList(filtredResList));
        }

    }
    private void displayReservationInfo(Reservation reservation) {
        Lastname.setText(reservation.getNompersonne());
        Firstname.setText(reservation.getPrenom());
        Age.setText(String.valueOf(reservation.getAge())); // Convertir l'âge en chaîne de caractères
        Poids.setText(String.valueOf(reservation.getPoids())); // Convertir le poids en chaîne de caractères
        Taille.setText(String.valueOf(reservation.getTaille())); // Convertir la taille en chaîne de caractères
        // Pour le sexe, il faut sélectionner le bouton radio correspondant
        if (reservation.getSexe().equals("homme") || reservation.getSexe().equals("HOMME")) {
            homme.setSelected(true); // Supposons que vous avez des boutons radio pour "Masculin" et "Féminin"
        } else if (reservation.getSexe().equals("femme") || reservation.getSexe().equals("FEMME")) {
            femme.setSelected(true);
        }
    }


    @FXML
    void pdf(ActionEvent event) {
        // Récupérer les informations de réservation depuis la TableView
        ObservableList<Reservation> reservations = table.getItems();

        // Créer un nouveau document PDF
        Document document = new Document();
        try {
            // Spécifier le chemin et le nom du fichier PDF
            PdfWriter.getInstance(document, new FileOutputStream("reservations.pdf"));

            // Ouvrir le document
            document.open();
            // Ajouter l'image en haut à gauche de chaque page
            Image image = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\logo.png");
            image.scaleAbsolute(100, 50); // Ajustez la taille de l'image selon vos besoins
            image.setAbsolutePosition(36, 800); // Position de l'image en haut à gauche de la page
            document.add(image);
            document.add(image);
            // Ajouter les informations de réservation au document
            for (Reservation reservation : reservations) {
                document.add(new Paragraph("Séance: " + reservation.getSeance().getNom())); // Par exemple, afficher le nom de la séance
                document.add(new Paragraph("Nom : " + reservation.getNompersonne()));
                document.add(new Paragraph("Prénom : " + reservation.getPrenom()));
                document.add(new Paragraph("Âge : " + reservation.getAge()));
                document.add(new Paragraph("Poids : " + reservation.getPoids()));
                document.add(new Paragraph("Taille : " + reservation.getTaille()));
                document.add(new Paragraph("Sexe : " + reservation.getSexe()));
                document.add(new Paragraph("----------------------------------------"));
            }

            // Fermer le document
            document.close();

            // Afficher un message à l'utilisateur indiquant que le PDF a été généré avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setContentText("Reservation information has been saved to reservations.pdf");
            alert.showAndWait();
        } catch (FileNotFoundException | DocumentException e) {
            // Gérer les exceptions
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void mesreservations(ActionEvent event) {

    }
    @FXML
    void rechercher(ActionEvent event) {

    }



    }
