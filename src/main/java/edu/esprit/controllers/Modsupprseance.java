package edu.esprit.controllers;

import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceSeance;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;

public class Modsupprseance {
    public  Modsupprseance()
    {

    }
    private Seance selectedSeance;

    public Modsupprseance(Seance selectedSeance) {
        if(selectedSeance == null) {
            throw new IllegalArgumentException("selectedSeance cannot be null");
        }
        this.selectedSeance = selectedSeance;
    }

    @FXML
    private Button browseButton;

    @FXML
    private Button btnabonnement1;
    @FXML
    private Label labelduree;

    @FXML
    private Label labelnumsalle;


    @FXML
    private Button btnalimentaire1;

    @FXML
    private Button btnequipement1;

    @FXML
    private Button btnevenement1;
    private File selectedFile;

    @FXML
    private Button btnplanning1;

    @FXML
    private Button btnreclamation1;

    @FXML
    private Button btntdb1;

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
    private Button btnmodifierseance;

    @FXML
    private Button btnsupprimerseance;
    final ServiceSeance ss =new ServiceSeance();


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
    void abonnement(ActionEvent event) {

    }

    @FXML
    void supprimerseance(ActionEvent event) {
        if (selectedSeance != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Supprimer la séance");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette séance ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    ss.supprimer(selectedSeance.getIdseance());
                    clearForm();
                    // Afficher un message de confirmation de suppression
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Suppression");
                    alert.setContentText("Séance supprimée avec succès");
                    alert.showAndWait();
                } catch (SQLException e) {
                    // En cas d'erreur lors de la suppression, afficher un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Erreur lors de la suppression de la séance : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            // Afficher un message d'erreur si aucune séance n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune séance sélectionnée");
            alert.setContentText("Veuillez sélectionner une séance à supprimer.");
            alert.showAndWait();
        }

    }

    @FXML
    void modifierseance(ActionEvent event) {
        // Récupérer les informations modifiées depuis le formulaire
       /* String duree = dureeid.getText();
        String numSalle = numsalleid.getText();
        String imageUrl = pathimageid.getText();
        Time horaire = horaireid.getValue();
        String jour = jourid.getValue();
        String nom = nomid.getValue();
        boolean isInputValidnumsalle = true;
        boolean isInputValidduree = true;
        System.out.println("dfzefeefezfze"+selectedSeance);

        // Vérification de la saisie
        if (!numSalle.matches("[1-5]")) { // Vérifie si numSalle est un chiffre entre 1 et 5
            isInputValidnumsalle = false;
        }

        if (!duree.matches("\\d{2}min")) { // Vérifie si duree commence par 2 chiffres et se termine par "min"
            isInputValidduree = false;
        }
        if (isInputValidduree && isInputValidnumsalle) {
            // Mettre à jour les propriétés de l'objet selectedSeance avec les nouvelles valeurs
            if (selectedSeance != null) {
                selectedSeance.setDuree(duree);
                selectedSeance.setNumesalle(Integer.parseInt(numSalle));
                selectedSeance.setImageseance(imageUrl);
                selectedSeance.setHoraire(horaire);
                selectedSeance.setJourseance(jour);
                selectedSeance.setNom(nom);

                // Mettre à jour la séance dans la base de données
                try {
                    ss.modifier(selectedSeance);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification");
                    alert.setContentText("Seance modified succesfully");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Exeption");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                clearForm();
            }

        } else {
            // Afficher un message à l'utilisateur pour indiquer la saisie invalide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            if (!isInputValidnumsalle) {
                numsalleid.setStyle("-fx-text-fill: red;");// Applique le style rouge au champ de texte
                alert.setContentText("Please correct the input field marked in red for numsalle.");

            }
            if (!isInputValidduree) {
                dureeid.setStyle("-fx-text-fill: red;");// Applique le style rouge au champ de texte
                alert.setContentText("Please correct the input fields marke in red for duration.");
            }
            alert.showAndWait();
        }*/
       /* if (selectedSeance != null) {
            // Récupérer les informations modifiées depuis le formulaire
            String duree = dureeid.getText();
            String numSalle = numsalleid.getText();
            String imageUrl = pathimageid.getText();
            Time horaire = horaireid.getValue();
            String jour = jourid.getValue();
            String nom = nomid.getValue();
            boolean isInputValidnumsalle = true;
            boolean isInputValidduree = true;
            System.out.println("dfzefeefezfze" + selectedSeance);

            // Vérification de la saisie
            if (!numSalle.matches("[1-5]")) { // Vérifie si numSalle est un chiffre entre 1 et 5
                isInputValidnumsalle = false;
            }

            if (!duree.matches("\\d{2}min")) { // Vérifie si duree commence par 2 chiffres et se termine par "min"
                isInputValidduree = false;
            }
            if (isInputValidduree && isInputValidnumsalle) {
                // Mettre à jour les propriétés de l'objet selectedSeance avec les nouvelles valeurs
                selectedSeance.setDuree(duree);
                selectedSeance.setNumesalle(Integer.parseInt(numSalle));
                selectedSeance.setImageseance(imageUrl);
                selectedSeance.setHoraire(horaire);
                selectedSeance.setJourseance(jour);
                selectedSeance.setNom(nom);

                // Mettre à jour la séance dans la base de données
                try {
                    ss.modifier(selectedSeance);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification");
                    alert.setContentText("Seance modified succesfully");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Exeption");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                clearForm();
            } else {
                // Afficher un message à l'utilisateur pour indiquer la saisie invalide
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                if (!isInputValidnumsalle) {
                    numsalleid.setStyle("-fx-text-fill: red;");// Applique le style rouge au champ de texte
                    alert.setContentText("Please correct the input field marked in red for numsalle.");

                }
                if (!isInputValidduree) {
                    dureeid.setStyle("-fx-text-fill: red;");// Applique le style rouge au champ de texte
                    alert.setContentText("Please correct the input fields marke in red for duration.");
                }
                alert.showAndWait();
            }
        } else {
            // La séance sélectionnée est nulle, affichez un message à l'utilisateur pour l'informer
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a session to modify.");
            alert.showAndWait();
        }*/
        // Récupérer les informations modifiées depuis le formulaire
        String duree = dureeid.getText();
        String numSalle = numsalleid.getText();
        String imageUrl = pathimageid.getText();
        Time horaire = horaireid.getValue();
        String jour = jourid.getValue();
        String nom = nomid.getValue();

        boolean isInputValidnumsalle = true;
        boolean isInputValidduree = true;

        // Vérification de la saisie
        if (!numSalle.matches("[1-5]")) { // Vérifie si numSalle est un chiffre entre 1 et 5
            isInputValidnumsalle = false;
           // labelnumsalle.setTextFill(Color.RED); // Change la couleur du texte en rouge
        }

        if (!duree.matches("\\d{2}min")) { // Vérifie si duree commence par 2 chiffres et se termine par "min"
            isInputValidduree = false;
           // labelduree.setTextFill(Color.RED); // Change la couleur du texte en rouge
        }

        if (isInputValidduree && isInputValidnumsalle) {
            // Mettre à jour les propriétés de l'objet selectedSeance avec les nouvelles valeurs
            if (selectedSeance != null) {
                selectedSeance.setDuree(duree);
                selectedSeance.setNumesalle(Integer.parseInt(numSalle));
                selectedSeance.setImageseance(imageUrl);
                selectedSeance.setHoraire(horaire);
                selectedSeance.setJourseance(jour);
                selectedSeance.setNom(nom);

                // Mettre à jour la séance dans la base de données
                try {
                    ss.modifier(selectedSeance);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modification");
                    alert.setContentText("Seance modified succesfully");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Exeption");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                clearForm();
            }

        } else {
            // Afficher un message à l'utilisateur pour indiquer la saisie invalide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            /*if (!isInputValidnumsalle) {
                numsalleid.setStyle("-fx-text-fill: red;");// Applique le style rouge au champ de texte
                alert.setContentText("Please correct the input field marked in red for numsalle.");

            }
            if (!isInputValidduree) {
                dureeid.setStyle("-fx-text-fill: red;");// Applique le style rouge au champ de texte
                alert.setContentText("Please correct the input fields marke in red for duration.");
            }*/
            if (!isInputValidnumsalle) {
                labelnumsalle.setTextFill(Color.RED);
                labelnumsalle.setText("Please correct the input field for numsalle.");
            }
            else
                labelnumsalle.setText(" ");

            if (!isInputValidduree) {
                labelduree.setTextFill(Color.RED);
                labelduree.setText("Please correct the input field for duration.");
            }
            else
                labelduree.setText(" ");

             alert.showAndWait();

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
    void alimentaire(ActionEvent event) {

    }

    @FXML
    void browseImage(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            // Enregistrez l'URL de l'image dans le champ de texte pathimageid
            pathimageid.setText(selectedFile.toURI().toString());

            // Chargez l'image depuis le fichier sélectionné
            Image image = new Image(selectedFile.toURI().toString());

            // Affichez l'image dans l'ImageView
            imageView.setImage(image);
        }

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
    void reclamation(ActionEvent event) {

    }

    @FXML
    void tableaudebord(ActionEvent event) {

    }
    public void initData(Seance seance) {
        // Mettez à jour les champs du formulaire avec les détails de la séance
       /* nomid.setValue(seance.getNom());
        horaireid.setValue(seance.getHoraire());
        jourid.setValue(seance.getJourseance());
        numsalleid.setText(String.valueOf(seance.getNumesalle()));
        dureeid.setText(seance.getDuree());
        pathimageid.setText(seance.getImageseance());*/
        if(seance == null) {
            throw new IllegalArgumentException("seance cannot be null");
        }
        // Mettez à jour les champs du formulaire avec les détails de la séance
      //  nomid.setValue(seance.getNom());
        if (seance != null) {
            nomid.setValue(seance.getNom());
        }
        horaireid.setValue(seance.getHoraire());
        jourid.setValue(seance.getJourseance());
        numsalleid.setText(String.valueOf(seance.getNumesalle()));
        dureeid.setText(seance.getDuree());
        pathimageid.setText(seance.getImageseance());
        selectedSeance = seance; // Initialise selectedSeance avec la séance passée en paramètre
    }
}
