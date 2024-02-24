package edu.esprit.controllers;

import edu.esprit.entities.Reservation;
import edu.esprit.services.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Reservationsclient {
    @FXML
    private TextField Age;
    @FXML
    private Label nompersonne;

    @FXML
    private Label nomseance;
    @FXML
    private TextField Poids;

    @FXML
    private TextField Taille;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;

    @FXML
    private GridPane gridpane;
    @FXML
    private RadioButton homme;
    @FXML
    private RadioButton femme;

    @FXML
    private ListView<Reservation> listview;
    ServiceReservation sr =new ServiceReservation();
    private ToggleGroup sexeToggleGroup;
    @FXML
    private ScrollPane scrollpane;
    public void initialize() {
        // Initialiser la liste observable pour stocker les réservations
        ObservableList<Reservation> reservationsList = FXCollections.observableArrayList();
        // Récupérer les réservations depuis le service ou tout autre moyen
        List<Reservation> reservations = null; // Récupérer les réservations depuis votre service
        try {
            reservations = sr.getAll(); // Suppose que vous avez une méthode getAll() dans votre service
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajouter les réservations à la liste observable
        if (reservations != null) {
            reservationsList.addAll(reservations);
        }

        // Lier la liste observable à la ListView
        listview.setItems(reservationsList);

        // Personnaliser l'affichage des éléments de la ListView si nécessaire
        listview.setCellFactory(param -> new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation reservation, boolean empty) {
                super.updateItem(reservation, empty);
                if (empty || reservation == null) {
                    setText(null);
                } else {
                    setText("Nom de la séance: " + reservation.getSeance().getNom() + ", Nom de la personne: " + reservation.getNompersonne()+"prenom: "+reservation.getPrenom()+" L'age"+reservation.getAge()+" Poids: "+reservation.getPoids()+" Taille: "+reservation.getTaille()+" Sexe: "+reservation.getSexe());
                }
            }
        });
        // Gérer les événements de sélection dans la ListView
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour les champs de texte avec les détails de la réservation sélectionnée
                Age.setText(String.valueOf(newValue.getAge()));
                Poids.setText(String.valueOf(newValue.getPoids()));
                Taille.setText(String.valueOf(newValue.getTaille()));
                // Afficher le nom de la séance et le nom de la personne
                nomseance.setText(newValue.getSeance().getNom());
                nompersonne.setText(newValue.getNompersonne() + " " + newValue.getPrenom());
                // Vérifier le sexe
                if ("HOMME".equals(newValue.getSexe())) {
                    homme.setSelected(true);
                    femme.setSelected(false);
                } else if ("FEMME".equals(newValue.getSexe())) {
                    homme.setSelected(false);
                    femme.setSelected(true);
                }

            }
        });
        sexeToggleGroup = new ToggleGroup();

        // Assurez-vous d'ajouter les boutons radio au ToggleGroup
        homme.setToggleGroup(sexeToggleGroup);
        femme.setToggleGroup(sexeToggleGroup);
    }
    private void clearForm () {
        nompersonne.setText("");
        nomseance.setText("");
        Age.clear();
        Poids.clear();
        Taille.clear();
        // Désélectionner les boutons radio du groupe "sexe"
        sexeToggleGroup.getSelectedToggle().setSelected(false);
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
        String nomPersonne = nompersonne.getText();
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
    }


