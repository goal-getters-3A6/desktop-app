package edu.esprit.controllers;

import edu.esprit.entities.Seance;
import edu.esprit.services.ServiceSeance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
public class Seanceformulaire {
    private final ServiceSeance ss = new ServiceSeance();
    private Stage stage;
    private Scene scene;
    private Parent root;

    private File selectedFile;
    private String imageUrl;

    @FXML
    private Button browseButton;

    @FXML
    private Button btnajouterseance;

    @FXML
    private Button btnmodifierseance;

    @FXML
    private Button btnsupprimer;

    @FXML
    private TextField dureeid;
    @FXML
    private TableColumn<?, ?> dureeseancetable;

    @FXML
    private ComboBox<Time> horaireid;
    @FXML
    private TableColumn<?, ?> horaireseancetable;

    @FXML
    private ImageView imageView;
    @FXML
    private TableColumn<?, ?> imageseancetable;

    @FXML
    private ComboBox<String> jourid;
    @FXML
    private TableColumn<?, ?> jourseancetable;

    @FXML
    private ComboBox<String> nomid;
    @FXML
    private TableColumn<?, ?> nomseancetable;

    @FXML
    private TextField numsalleid;

    @FXML
    private TableColumn<?, ?> numsalleseance;

    @FXML
    private TextField pathimageid;
    @FXML
    private TableView<Seance> tableseance;

    public void initialize() throws IOException {
        ObservableList<String> elementsnom = FXCollections.observableArrayList("Boxe", "Bodypump", "Bodyattack", "Yoga", "Spinning", "Crossfit");
        ObservableList<Time> elementshoraire = FXCollections.observableArrayList(Time.valueOf("9:00:00"), Time.valueOf("10:00:00"), Time.valueOf("10:30:00"), Time.valueOf("16:00:00"), Time.valueOf("19:00:00"), Time.valueOf("20:00:00"));
        ObservableList<String> elementjours = FXCollections.observableArrayList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");
        nomid.setItems(elementsnom);
        horaireid.setItems(elementshoraire);
        jourid.setItems(elementjours);
        tableseance.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifie si c'est un simple clic
                Seance selectedSeance = tableseance.getSelectionModel().getSelectedItem();
                if (selectedSeance != null) {
                    // Afficher les informations de la séance sélectionnée dans le formulaire
                    displaySeanceInfo(selectedSeance);
                }
            }
        });
        ShowSeance();
    }
    private List<Seance> seanceList;

    public void ShowSeance() throws IOException{
        try {
            seanceList = ss.getAll(); // Récupérer les données de la base de données
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //ObservableList<Seance> observableSeanceList = FXCollections.observableArrayList(seanceList);
       // tableseance.setItems(observableSeanceList); // Mettre à jour les données affichées dans le TableView
        try {
            System.out.println(ss.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(ss.getOneById(26));
        nomseancetable.setCellValueFactory(new PropertyValueFactory<>("nom"));
        horaireseancetable.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        jourseancetable.setCellValueFactory(new PropertyValueFactory<>("jourseance"));
        numsalleseance.setCellValueFactory(new PropertyValueFactory<>("numesalle"));
        dureeseancetable.setCellValueFactory(new PropertyValueFactory<>("duree"));
        imageseancetable.setCellValueFactory(new PropertyValueFactory<>("imageseance"));

        if (tableseance != null && tableseance instanceof TableView) {
            ((TableView<Seance>) tableseance).setItems(FXCollections.observableArrayList(seanceList));
        }
    }
    // Méthode pour afficher les informations de la séance sélectionnée dans le formulaire
    private void displaySeanceInfo(Seance seance) {
        dureeid.setText(seance.getDuree());
        numsalleid.setText(String.valueOf(seance.getNumesalle()));
        pathimageid.setText(seance.getImageseance());
        horaireid.setValue(seance.getHoraire());
        jourid.setValue(seance.getJourseance());
        nomid.setValue(seance.getNom());
    }

    @FXML
    void ajouterseance(ActionEvent event) throws IOException {
        // Contrôle de la saisie:
        String duree = dureeid.getText();
        String numSalle = numsalleid.getText();
        String imageUrl = pathimageid.getText();
        Object horaire = horaireid.getValue();
        Object jour = jourid.getValue();
        Object nom = nomid.getValue();
        boolean isInputValidnumsalle = true;
        boolean isInputValidduree = true;

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
        ShowSeance();

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
    void modifierseance(ActionEvent event) throws IOException {
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
        }

        if (!duree.matches("\\d{2}min")) { // Vérifie si duree commence par 2 chiffres et se termine par "min"
            isInputValidduree = false;
        }
        if (isInputValidduree && isInputValidnumsalle)
        {

        // Mettre à jour la séance dans la base de données
        Seance selectedSeance = tableseance.getSelectionModel().getSelectedItem();
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
            // Rafraîchir l'affichage des séances dans la TableView


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
        ShowSeance();
    }

    // Méthode pour afficher les informations de la séance sélectionnée dans le formulaire

    @FXML
    void supprimerseance(ActionEvent event) throws IOException{
        // Récupérer la séance sélectionnée dans le TableView
        Seance selectedSeance = tableseance.getSelectionModel().getSelectedItem();

        if (selectedSeance != null) {
            // Supprimer la séance de la base de données
            try {
                ss.supprimer(selectedSeance.getIdseance());
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
            ShowSeance();
        } else {
            // Afficher un message à l'utilisateur indiquant qu'aucune séance n'a été sélectionnée
            System.out.println("Veuillez sélectionner une séance à supprimer.");
        }
        clearForm();

    }
    private void clearForm() {
        nomid.setValue(null);
        horaireid.setValue(null);
        jourid.setValue(null);
        numsalleid.clear();
        dureeid.clear();
        pathimageid.clear();
    }
}


