package controllers;

import Service.ServiceEvenement;
import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.Date;

public class ModifierEvent implements Initializable {


    @FXML
    private TextField nom_id;

    @FXML
    private TextField adresse_id;

    @FXML
    private TextField nbr_max_id;

    @FXML
    private Button Import_btn;

    @FXML
    private DatePicker dated_id;

    @FXML
    private DatePicker datef_id;

    @FXML
    private ImageView image_id;

    @FXML
    private Button modif_btn;
    private String imagePath;

    @FXML
    private AnchorPane scene_id;


    private final ServiceEvenement SE = new ServiceEvenement();
    private Evenement evenement;


    // Vos champs FXML et autres méthodes

    public void initData(Evenement evenement) {
        // Initialiser les champs avec les détails de l'événement
        this.evenement = evenement;
        nom_id.setText(evenement.getNom_eve());
        adresse_id.setText(evenement.getAdresse_eve());
        if (evenement.getDated_eve() != null) {
            dated_id.setValue(new java.sql.Date(evenement.getDated_eve().getTime()).toLocalDate());
        }

// Vérifier si datef_eve n'est pas nul avant de le convertir en Instant
        if (evenement.getDatef_eve() != null) {
            datef_id.setValue(new java.sql.Date(evenement.getDatef_eve().getTime()).toLocalDate());
        }
        nbr_max_id.setText(String.valueOf(evenement.getNbr_max()));
        // Charger et afficher l'image de l'événement
        String imagePath = evenement.getImage_eve();
        if (imagePath != null) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            image_id.setImage(image);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    @FXML
    void update_new() throws IOException{
        // Check if the event is selected
        if (evenement != null) {
            try {
                // Get the values from the form fields
                String nom = nom_id.getText();
                String adresse = adresse_id.getText();
                int nbr_max = Integer.parseInt(nbr_max_id.getText());
                Date dated_eve = Date.from(dated_id.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date datef_eve = Date.from(datef_id.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());


                if (imagePath != null) {
                    evenement.setImage_eve(imagePath);
                }


                // Update the event object
                evenement.setNom_eve(nom);
                evenement.setAdresse_eve(adresse);
                evenement.setNbr_max(nbr_max);
                evenement.setDated_eve(dated_eve);
                evenement.setDatef_eve(datef_eve);


                // Call the service method to update the event
                SE.modifier(evenement);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("Event mis à jour avec succès");
                alert.showAndWait();

                // Rafraîchir l'affichage dans la classe AfficherEvenement
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
                Parent root = loader.load();
                AfficherEvenement controller = loader.getController();
                getAfficherEvenementController().refreshEvents();
                scene_id.getScene().setRoot(root);

            } catch (SQLException e) {
                // Show error message if update fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Show error message if no event is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un événement à mettre à jour.");
            alert.showAndWait();
        }
    }
    public void Import_modif(ActionEvent evenement) {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(scene_id.getScene().getWindow());

        if (file != null) {
            Image newImage = new Image(file.toURI().toString());
            image_id.setImage(newImage);
            imagePath = file.getAbsolutePath(); // Assurez-vous que imagePath est défini dans la classe
        }
    }




    public void BackToEvents(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
        Parent root=loader.load();
        scene_id.getScene().setRoot(root);
    }

    // Méthode pour obtenir le contrôleur AfficherEvenement
    private AfficherEvenement getAfficherEvenementController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
        try {
            Parent parent = loader.load();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}