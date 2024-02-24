package edu.esprit.controllers;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Resformualire {
    @FXML
    private Label labelnom;

    @FXML
    private Label labelprenom;
    @FXML
    private TextField Age;

    @FXML
    private TextField Poids;

    @FXML
    private TextField Taille;

    @FXML
    private AnchorPane anchorpanedash;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnaccueil;

    @FXML
    private Button btnajouterreservation;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnpdf;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private RadioButton femme;

    @FXML
    private RadioButton homme;

    @FXML
    private ImageView logo1;
    private Seance seance;
    @FXML
    void abonnement(ActionEvent event) {

    }

    @FXML
    void accueil(ActionEvent event) {

    }
    private ToggleGroup sexeToggleGroup;
    List<Reservation> resList;
    private User user;

    public void setUser(User user) {
        this.user = user;
        // Mettre à jour les labels avec le nom et le prénom de l'utilisateur
        labelnom.setText(user.getNom());
        labelprenom.setText(user.getPrenom());
    }
    public void initialize() throws IOException {

        // Créer un groupe pour les RadioButton
        sexeToggleGroup = new ToggleGroup();

        // Ajouter les RadioButton au groupe
        homme.setToggleGroup(sexeToggleGroup);
        femme.setToggleGroup(sexeToggleGroup);
        // Récupérer l'utilisateur à partir de votre système d'authentification
        ClientService clientService = new ClientService();
        User user = clientService.getClientById(6); // Utilisez l'ID approprié pour récupérer l'utilisateur

        // Mettre à jour les labels avec le nom et le prénom de l'utilisateur
        labelnom.setText(user.getNom());
        labelprenom.setText(user.getPrenom());

        // Mettre à jour le style des labels pour les rendre non éditables
        labelnom.setDisable(true);
        labelprenom.setDisable(true);

    }
    public void setSeance(Seance seance) {
        this.seance = seance;
    }


    @FXML
    void ajouterreservation(ActionEvent event) {

      /*  User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
        ClientService cs = new ClientService();
        User u = cs.getClientById(6);
        // Récupérer les valeurs saisies dans les champs du formulaire
        String nom = labelnom.getText();
        String prenom = labelprenom.getText();
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

            }
        }*/


//yekhdm

        // Récupérer les valeurs saisies dans les champs du formulaire
        String ageStr = Age.getText();
        String poidsStr = Poids.getText();
        String tailleStr = Taille.getText();
        String sexe = getSelectedSexe();
        System.out.println("age"+ageStr+"poids"+poidsStr+"taille"+tailleStr+"sexe"+sexe);
        // Définition des constantes pour les messages d'erreur
        final String ERROR_TITLE = "Validation Error";
        final String ERROR_STYLE = "-fx-text-fill: red;";
        final String ERROR_MSG_GENERIC = "Please correct the input fields marked in red.";

        // Vérifier que les champs sont remplis
        boolean isValid = true;
        if (ageStr.isEmpty() || poidsStr.isEmpty() || tailleStr.isEmpty() || sexe == null) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ERROR_TITLE);
            alert.setContentText(ERROR_MSG_GENERIC);
            alert.showAndWait();
        }else {
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
                    alert.setContentText("Impossible ! Age or Size or weight can be less than 0");
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
            if (ageStr.isEmpty() || !ageStr.matches("\\d+")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Impossible ! Age empty or not a number");
                alert.showAndWait();
            } else {
                Age.setStyle("");
            }
            if (poidsStr.isEmpty() || !poidsStr.matches("\\d+(\\.\\d+)?")) {
                Poids.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Impossible ! the weight is empty or not a number");
                alert.showAndWait();
            } else {
                Poids.setStyle("");
            }
            if (tailleStr.isEmpty() || !tailleStr.matches("\\d+(\\.\\d+)?")) {
                Taille.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Impossible ! the Size is empty or not a number");
                alert.showAndWait();


            } else {
                Taille.setStyle("");
            }
            if (sexe == null) {
                femme.setStyle(ERROR_STYLE);
                homme.setStyle(ERROR_STYLE);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(ERROR_TITLE);
                alert.setContentText("Impossible ! the SEX is not selected");
                alert.showAndWait();
            } else {
                femme.setStyle("");
                homme.setStyle("");
            }
        } else {
            // Les saisies sont valides, procéder au traitement
            // Récupérer l'utilisateur à partir de votre système d'authentification
            ClientService clientService = new ClientService();
            User user = clientService.getClientById(6); // Utilisez l'ID approprié pour récupérer l'utilisateur

            int age = Integer.parseInt(ageStr);
            float poids = Float.parseFloat(poidsStr);
            float taille = Float.parseFloat(tailleStr);

            // Obtenir la liste des réservations pour la séance sélectionnée
            // Traitement des réservations

            // Ajouter la réservation
            Reservation nouvelleReservation = new Reservation(seance, user.getNom(), user.getPrenom(), age, poids, taille, sexe, user);
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
                } else if (imc > 18.5 && imc < 25) {
                    etat = "Poids normal";
                } else if (imc > 25 && imc < 30) {
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
            clearForm(); // Effacer les champs du formulaire après l'ajout de la réservation

        }

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
    void pdf(ActionEvent event) {

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
    private String getSelectedSexe() {
        RadioButton selectedRadioButton = (RadioButton) sexeToggleGroup.getSelectedToggle();
        return selectedRadioButton.getText();
    }
    private void clearForm () {
        labelnom.setText("");
        labelprenom.setText("");
        Age.clear();
        Poids.clear();
        Taille.clear();
        // Désélectionner les boutons radio du groupe "sexe"
        sexeToggleGroup.getSelectedToggle().setSelected(false);

    }

}
