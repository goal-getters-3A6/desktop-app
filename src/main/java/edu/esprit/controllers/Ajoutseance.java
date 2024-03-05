package edu.esprit.controllers;

import edu.esprit.entities.Seance;
import edu.esprit.services.ServiceSeance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;

public class Ajoutseance {
    @FXML
    private Label labelduree;

    @FXML
    private Label labelnumsalle;
    @FXML
    private Button browseButton;
    @FXML
    private Button btntdb1;
    @FXML
    private Button btnabonnement1;

    @FXML
    private Button btnajouterseance;

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
    private Button btnreservation;

    @FXML
    private Button btnstats;
    @FXML
    private TextField dureeid;

    @FXML
    private ComboBox<Time> horaireid;

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<String> jourid;

    @FXML
    private ImageView logo1;

    @FXML
    private ComboBox<String> nomid;

    @FXML
    private TextField numsalleid;

    @FXML
    private TextField pathimageid;

    @FXML
    private ImageView planningimg11;

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

    private final ServiceSeance ss = new ServiceSeance();
    public void initialize() throws IOException
    {
        ObservableList<String> elementsnom = FXCollections.observableArrayList("Boxe", "Bodypump", "Bodyattack", "Yoga", "Spinning", "Crossfit");
        ObservableList<Time> elementshoraire = FXCollections.observableArrayList(Time.valueOf("9:00:00"), Time.valueOf("10:00:00"), Time.valueOf("10:30:00"), Time.valueOf("16:00:00"), Time.valueOf("19:00:00"), Time.valueOf("20:00:00"));
        ObservableList<String> elementjours = FXCollections.observableArrayList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");
        nomid.setItems(elementsnom);
        horaireid.setItems(elementshoraire);
        jourid.setItems(elementjours);

    }



    @FXML
    void ajouterseance(ActionEvent event) {
        // Contrôle de la saisie:
    /*    String duree = dureeid.getText();
        String numSalle = numsalleid.getText();
        String imageUrl = pathimageid.getText();
        imageUrl = imageUrl.replace("file:/", "");
        Object horaire = horaireid.getValue();
        Object jour = jourid.getValue();
        Object nom = nomid.getValue();
        boolean isInputValidnumsalle = true;
        boolean isInputValidduree = true;
        boolean isInputEmpty = false;

        if (numSalle.isEmpty() || duree.isEmpty() || imageUrl.isEmpty() || horaire == null || jour == null || nom == null) {
            isInputEmpty = true;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
        }
        // Vérification de la saisie
        if (!numSalle.matches("[1-5]")) { // Vérifie si numSalle est un chiffre entre 1 et 5
            isInputValidnumsalle = false;
        }

        if (!duree.matches("\\d{2}min")) { // Vérifie si duree commence par 2 chiffres et se termine par "min"
            isInputValidduree = false;
        }
        if (isInputValidduree && isInputValidnumsalle)
        {

            Seance s = new Seance(nom.toString(), Time.valueOf(horaire.toString()), jour.toString(), Integer.parseInt(numSalle), duree, imageUrl);
            try {
                ss.ajouter(s);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("Seance added succesfully");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Exeption");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            clearForm();
        }
        else {
            // Afficher un message à l'utilisateur pour indiquer la saisie invalide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            if(!isInputValidnumsalle)
            {
                numsalleid.setStyle("-fx-text-fill: red;");// Applique le style rouge au champ de texte
                alert.setContentText("Please correct the input field marked in red for numsalle.");

            }
            if(!isInputValidduree)
            {
                dureeid.setStyle("-fx-text-fill: red;");// Applique le style rouge au champ de texte
                alert.setContentText("Please correct the input fields marke in red for duration.");


            }
            alert.showAndWait();
        }
*/

        // Contrôle de la saisie:
        String duree = dureeid.getText();
        String numSalle = numsalleid.getText();
        String imageUrl = pathimageid.getText();
        imageUrl = imageUrl.replace("file:/", "");
        Object horaire = horaireid.getValue();
        Object jour = jourid.getValue();
        Object nom = nomid.getValue();
        boolean isInputValidnumsalle = true;
        boolean isInputValidduree = true;
        boolean isInputEmpty = false;

        // Vérification de la saisie
        if (numSalle.isEmpty() || duree.isEmpty() || imageUrl.isEmpty() || horaire == null || jour == null || nom == null) {
            isInputEmpty = true;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
        } else {
            labelnumsalle.setText("");
        }

        if (!numSalle.matches("[1-5]")) { // Vérifie si numSalle est un chiffre entre 1 et 5
            isInputValidnumsalle = false;
            labelnumsalle.setText("Please enter a valid number between 1 and 5.");
            labelnumsalle.setTextFill(Color.RED);
        } else {
            labelnumsalle.setText("");
        }

        if (!duree.matches("\\d{2}min")) { // Vérifie si duree commence par 2 chiffres et se termine par "min"
            isInputValidduree = false;
            labelduree.setText("Please enter a valid duration in the format 'XXmin'.");
            labelduree.setTextFill(Color.RED);
        } else {
            labelduree.setText("");
        }

        if (!isInputEmpty && isInputValidduree && isInputValidnumsalle) {
            Seance s = new Seance(nom.toString(), Time.valueOf(horaire.toString()), jour.toString(), Integer.parseInt(numSalle), duree, imageUrl);
            try {
                ss.ajouter(s);
                /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("Seance added successfully");
                alert.showAndWait();*/
                Notifications.create()
                        .title("Validation")
                        .text("Seance added successfully")
                        .showInformation();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Exception");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            clearForm();
        }

    }



    @FXML
    void alimentaire(ActionEvent event) {

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
    void browseImage(ActionEvent event) {

        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            // Enregistrez l'URL de l'image dans le champ de texte pathimageid
            pathimageid.setText(selectedFile.toURI().toString());

            // Chargez l'image depuis le fichier sélectionné
            Image image = new Image(selectedFile.toURI().toString());

            // Affichez l'image dans l'ImageView
            imageView.setImage(image);
        }
    }
    private void clearForm() {
        nomid.setValue(null);
        horaireid.setValue(null);
        jourid.setValue(null);
        numsalleid.clear();
        dureeid.clear();
        pathimageid.clear();
        imageView.setImage(null); // Effacer l'image de l'ImageView
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/seanceadmin.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnplanning1.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void reservation(ActionEvent event) {

    }

    @FXML
    void statistiques(ActionEvent event) {

    }

}



