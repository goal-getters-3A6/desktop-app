package controllers;

import Service.ServiceParticipation;
import Service.ServiceEvenement;
import entities.Evenement;
import entities.Participation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AjouterParticipation {


    public AnchorPane NomId;


    @FXML
    private TextField age_id;

    @FXML
    private TextField email_id;

    @FXML
    private TextField nom_id;

    @FXML
    private TextField prenom_id;

    private int eventId;

    @FXML
    private Label nomMessage;

    @FXML
    private Label prenomMessage;

    @FXML
    private Label ageMessage;

    @FXML
    private Label emailMessage;
    private Evenement event;

    final private ServiceParticipation ssp = new ServiceParticipation();
    final private ServiceEvenement se = new ServiceEvenement();

   /* @FXML
    public void initialize() throws SQLException {
        try {
            // Charger les noms des événements dans le ComboBox
            Set<Evenement> evenements = se.getAll();
            for (Evenement e : evenements) {
                evenementComboBox.getItems().add(e.getNom_eve());
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw e; // Vous pouvez choisir de relancer l'exception après l'avoir gérée
        }
    }*/


    // Add a method to set the event ID
   /* public void setEventId(int eventId) throws SQLException {
        this.eventId = eventId;
        // Set the selected event in the ComboBox
        Evenement event = se.getOneById(eventId);
        if (event != null) {
            evenementComboBox.setValue(event.getNom_eve());
        }
    }
*/
    public void setEventId(int eventId) throws SQLException {
        this.eventId = eventId;
    }
    public void setEvent(Evenement event) {
        this.event = event;
    }


    @FXML
    public void Ajouter_participation(ActionEvent actionEvent) {
        // Réinitialiser les messages d'erreur
        resetErrorLabels();

        // Vérifier que tous les champs sont remplis
        if (nom_id.getText().isEmpty() || prenom_id.getText().isEmpty() || age_id.getText().isEmpty() || email_id.getText().isEmpty()) {
            showErrorLabel(nomMessage, "Veuillez remplir ce champ");
            showErrorLabel(prenomMessage, "Veuillez remplir ce champ");
            showErrorLabel(ageMessage, "Veuillez remplir ce champ");
            showErrorLabel(emailMessage, "Veuillez remplir ce champ");
            return;
        }

        // Vérifier que l'âge est valide (supérieur à 15 et inférieur à 70)
        try {
            int age = Integer.parseInt(age_id.getText());
            if (age <= 15 || age >= 70) {
                showErrorLabel(ageMessage, "L'âge doit être compris entre 15 et 70 ans");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorLabel(ageMessage, "Veuillez entrer un âge valide");
            return;
        }

        // Vérifier que l'e-mail est au bon format
        String email = email_id.getText();
        if (!isValidEmail(email)) {
            showErrorLabel(emailMessage, "Veuillez entrer une adresse e-mail valide");
            return;
        }

        // Toutes les validations sont réussies, vous pouvez ajouter la participation
        try {
            Participation participation = new Participation(
                    nom_id.getText(),
                    prenom_id.getText(),
                    Integer.parseInt(age_id.getText()),
                    email_id.getText(),
                    event
            );
            ssp.ajouter(participation);

            showSuccessMessage("Participation ajoutée avec succès");

            // Recharger la vue AfficherEvenement.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
            Parent root = loader.load();
            NomId.getScene().setRoot(root);
        } catch (SQLException e) {
            showErrorMessage("SQL Exception: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Méthode pour réinitialiser les labels d'erreur
    private void resetErrorLabels() {
        nomMessage.setText("");
        prenomMessage.setText("");
        ageMessage.setText("");
        emailMessage.setText("");
    }
    // Méthode pour valider le format de l'e-mail
    private boolean isValidEmail(String email) {
        // Utilisez une expression régulière pour valider le format de l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Méthode pour afficher un message d'erreur dans un label
    private void showErrorLabel(Label label, String message) {
        label.setTextFill(Color.RED);
        label.setText(message);
    }

    // Méthode pour afficher un message d'erreur dans une alerte
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher un message de succès dans une alerte
    private void showSuccessMessage(String message) {
        showAlert(Alert.AlertType.INFORMATION, "Succès", message);
    }

    // Méthode pour afficher un message d'erreur dans une alerte
    private void showErrorMessage(String message) {
        showAlert(Alert.AlertType.ERROR, "Erreur", message);
    }


    @FXML
    void retourner(ActionEvent event) {
        try {
            // Charger la vue AfficherEvenement.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
            Parent root = loader.load();

            // Obtenir n'importe quel nœud de la scène actuelle et configurer la nouvelle racine
            Scene currentScene = NomId.getScene(); // Ici, NomId est un exemple de nœud dans votre scène actuelle
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setEventId1(int eventId) throws SQLException {
        this.eventId = eventId;
        // Récupérer l'événement correspondant à partir de son ID
        this.event = se.getOneById(eventId);
        if (event == null) {
            throw new IllegalArgumentException("L'événement avec l'ID spécifié n'existe pas");
        }
    }

}
