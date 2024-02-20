package edu.esprit.controllers;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Seanceformulaire {
    private final ServiceSeance ss = new ServiceSeance();
    private Stage stage;
    private Scene scene;
    private Parent root;

    private File selectedFile;
    private String imageUrl;
    @FXML
    private Button btnrefraichir;

    @FXML
    private Button btnchercher;
    @FXML
    private Button btnstats;
    @FXML
    private TextField chercherfield;

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

    @FXML
    private ComboBox<String> tri;

    public void initialize() throws IOException {
        ObservableList<String> elementsnom = FXCollections.observableArrayList("Boxe", "Bodypump", "Bodyattack", "Yoga", "Spinning", "Crossfit");
        ObservableList<Time> elementshoraire = FXCollections.observableArrayList(Time.valueOf("9:00:00"), Time.valueOf("10:00:00"), Time.valueOf("10:30:00"), Time.valueOf("16:00:00"), Time.valueOf("19:00:00"), Time.valueOf("20:00:00"));
        ObservableList<String> elementjours = FXCollections.observableArrayList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");
        ObservableList<String> elementstri = FXCollections.observableArrayList("horaire", "nom", "jourseance");
        tri.setItems(elementstri);
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
        imageUrl = imageUrl.replace("file:/", "");
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
    @FXML
    void chercherseance(ActionEvent event) throws IOException {
        String nomSeance = chercherfield.getText(); // Supposons que vous avez un champ de texte nommé nomSeanceField

        // Vérifier si le nom de la séance est vide
        if (nomSeance == null || nomSeance.isEmpty()) {
            // Afficher un message à l'utilisateur pour indiquer que le champ est vide
            showAlert(Alert.AlertType.WARNING, "Champ vide", "Veuillez entrer le nom de la séance.");
            return;
        }

        // Utiliser le service pour rechercher la séance par son nom
        ServiceSeance serviceSeance = new ServiceSeance(); // Créez une instance de ServiceSeance
        Seance seance = serviceSeance.getSeanceByNom(nomSeance); // Utilisez la méthode pour obtenir la séance par son nom
        List<Seance> list;
        try {
            list = ss.getAllByNom(nomSeance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Vérifier si la séance est trouvée
        if (!list.isEmpty()) {
            // Effacer la table pour afficher les résultats de la recherche
            tableseance.getItems().clear();

            // Créer une liste observable contenant la séance trouvée
            ObservableList<Seance> seanceList = FXCollections.observableArrayList(list);

            // Mettre à jour les colonnes avec les informations de la séance
            horaireseancetable.setCellValueFactory(new PropertyValueFactory<>("horaire"));
            jourseancetable.setCellValueFactory(new PropertyValueFactory<>("jourseance"));
            dureeseancetable.setCellValueFactory(new PropertyValueFactory<>("duree"));

            // Mettre à jour le TableView avec la liste observable contenant la séance trouvée
            tableseance.setItems(seanceList);
        } else {
            // Afficher un message si aucune séance n'est trouvée avec ce nom
            showAlert(Alert.AlertType.INFORMATION, "Aucun résultat", "Aucune séance trouvée avec le nom \"" + nomSeance + "\".");
        }

    }


    // Méthode utilitaire pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void triseance(ActionEvent event) {
        String critereTri = tri.getValue(); // Récupérer le critère de tri à partir du ComboBox comboBoxTri

        // Vérifier si le critère de tri est vide
        if (critereTri == null || critereTri.isEmpty()) {
            // Afficher un message à l'utilisateur pour indiquer que le critère de tri n'est pas sélectionné
            showAlert(Alert.AlertType.WARNING, "Critère de tri non sélectionné", "Veuillez sélectionner un critère de tri.");
            return;
        }

        // Récupérer les séances actuellement affichées dans la table
        ObservableList<Seance> seanceList = tableseance.getItems();

        // Vérifier si des séances sont affichées dans la table
        if (!seanceList.isEmpty()) {
            // Trier les séances en fonction du critère sélectionné
            switch (critereTri) {
                case "horaire":
                    Collections.sort(seanceList, Comparator.comparing(Seance::getHoraire));
                    break;
                case "jourseance":
                    Collections.sort(seanceList, Comparator.comparing(Seance::getJourseance));
                    break;
                case "nom":
                    Collections.sort(seanceList, Comparator.comparing(Seance::getNom));
                    break;
                default:
                    // Le critère de tri n'est pas reconnu
                    showAlert(Alert.AlertType.ERROR, "Critère de tri non reconnu", "Le critère de tri sélectionné n'est pas pris en charge.");
                    return;
            }

            // Mettre à jour le TableView avec les séances triées
            tableseance.setItems(seanceList);
        } else {
            // Afficher un message si aucune séance n'est actuellement affichée dans la table
            showAlert(Alert.AlertType.INFORMATION, "Aucune séance", "Aucune séance à trier.");
        }
    }

    @FXML
    void statistiques(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Statistiqueseance.fxml"));
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
    void refraichir(ActionEvent event) {
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
    }





