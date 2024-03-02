package controllers;

import Service.ServiceEvenement;
import entities.Evenement;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.scene.paint.Color;
public class AjouterEvenement {
    private final ServiceEvenement SE = new ServiceEvenement();




    @FXML
    private DatePicker dated_id;
    @FXML
    private DatePicker datef_id;

    @FXML
    private TextField adresse_id;

    @FXML
    private Button ajouter_button;





   /* @FXML
    private TextField details_id;*/

    @FXML
    private TextField nom_id;
    @FXML
    private TextField nbr_max_id;
    @FXML
    private Label nomMessage;

    @FXML
    private Label adresseMessage;

    @FXML
    private Label nbrMaxMessage;


    //////affichage/////

    @FXML
    private TableView<Evenement> tableview;
    @FXML
    private TableColumn<Evenement,String > NOM;
    @FXML
    private TableColumn<Evenement,Date > DATE_DEBUT;
    @FXML
    private TableColumn<Evenement,Date > DATE_FIN;
    @FXML
    private TableColumn<Evenement, Integer> NOMBRE_MAX_DES_PARTICIPANTS;
    @FXML
    private TableColumn<Evenement,String > ADRESSE;

    /////importer photos ///////////

    @FXML
    private AnchorPane principal;
    @FXML
    private Button Import_btn;
    private Image image;

    @FXML
    private ImageView image_id;

    private String imagePath;

    @FXML
    void Ajouter(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (nom_id.getText().isEmpty() || adresse_id.getText().isEmpty() || nbr_max_id.getText().isEmpty()) {
            showValidationMessage(nomMessage, "Veuillez remplir ce champ");
            showValidationMessage(adresseMessage, "Veuillez remplir ce champ");
            showValidationMessage(nbrMaxMessage, "Veuillez remplir ce champ");
            return;
        }

        // Vérifier que la date de début est antérieure à la date de fin
        if (dated_id.getValue().isAfter(datef_id.getValue())) {
            showAlert("Erreur de saisie", "La date de début doit être antérieure à la date de fin");
            return;
        }


        // Vérifier que la date de début de l'événement n'est pas dépassée
        if (dated_id.getValue().isBefore(LocalDate.now())) {
            showAlert("Erreur de saisie", "La date de début de l'événement est déjà passée");
            return;
        }

        // Effectuer l'ajout de l'événement en appelant la méthode d'ajout de service
        Evenement prodData = new Evenement();
        prodData.setNom_eve(nom_id.getText());
        prodData.setAdresse_eve(adresse_id.getText());
        prodData.setNbr_max(Integer.parseInt(nbr_max_id.getText()));

        try {
            // Récupérer les valeurs sélectionnées dans les DatePicker
            LocalDate dated_eve_local = dated_id.getValue();
            LocalDate datef_eve_local = datef_id.getValue();

            // Convertir LocalDate en Date
            Date dated_eve = Date.from(dated_eve_local.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date datef_eve = Date.from(datef_eve_local.atStartOfDay(ZoneId.systemDefault()).toInstant());

            prodData.setDated_eve(dated_eve);
            prodData.setDatef_eve(datef_eve);

            // Appeler la méthode d'ajout de service avec l'objet Evenement
            SE.ajouter(prodData , imagePath);


            // Afficher un message de succès
            showSuccessMessage("Event ajouté avec succès");
        } catch (SQLException e) {
            showErrorMessage("Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Validation");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher les messages de contrôle de saisie sous chaque champ de texte avec une animation
    private void showValidationMessage(Label label, String message) {
        label.setTextFill(Color.RED); // Définir la couleur du texte en rouge
        label.setText(message);
        label.setVisible(true);

        // Animation de fondu
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(500), label);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
    }




    // Méthode utilitaire pour afficher une boîte de dialogue d'erreur
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



///////affichage////////////////


//    @FXML
//    void initialize() {
//
//        try {
//            List<Evenement> Ps = new ArrayList<>(SE.getAll());
//            ObservableList<Evenement> observableList = FXCollections.observableList(Ps);
//            tableview.setItems(observableList);
//        } catch (SQLException e) {
//        }
//        NOM.setCellValueFactory(new PropertyValueFactory<>("nom_eve"));
//        DATE_DEBUT.setCellValueFactory(new PropertyValueFactory<>("dated_eve"));
//        DATE_FIN.setCellValueFactory(new PropertyValueFactory<>("datef_eve"));
//        NOMBRE_MAX_DES_PARTICIPANTS.setCellValueFactory(new PropertyValueFactory<>("nbr_max"));
//        ADRESSE.setCellValueFactory(new PropertyValueFactory<>("adresse_eve"));
//
//    }


    public void Import(ActionEvent  evenement) {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(principal.getScene().getWindow());

        if (file != null) {

            imagePath = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 200, 200, false, true);

            image_id.setImage(image);
        }
    }


    public void SelectData() {
        Evenement prodData = tableview.getSelectionModel().getSelectedItem();
        if (prodData != null) {
            nom_id.setText(prodData.getNom_eve());
            adresse_id.setText(prodData.getAdresse_eve());

            // Convertir java.util.Date en java.sql.Date
            java.sql.Date dated_eve_sql = new java.sql.Date(prodData.getDated_eve().getTime());
            java.sql.Date datef_eve_sql = new java.sql.Date(prodData.getDatef_eve().getTime());

            // Convertir java.sql.Date en LocalDate
            dated_id.setValue(dated_eve_sql.toLocalDate());
            datef_id.setValue(datef_eve_sql.toLocalDate());

            nbr_max_id.setText(String.valueOf(prodData.getNbr_max()));

            String path = "File:" + prodData.getImage_eve();
            image = new Image(path, 200, 200, false, true);
            image_id.setImage(image);
        }
    }

//   public void update() {
////        Evenement prodData = tableview.getSelectionModel().getSelectedItem();
////        if (prodData != null) {
////            try {
////                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////                Date dated_eve = Date.from(dated_id.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
////                Date datef_eve = Date.from(datef_id.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
////
////                prodData.setNom_eve(nom_id.getText());
////                prodData.setAdresse_eve(adresse_id.getText());
////                prodData.setDated_eve(dated_eve);
////                prodData.setDatef_eve(datef_eve);
////                prodData.setNbr_max(Integer.parseInt(nbr_max_id.getText()));
////
////                if (imagePath != null) {
////                    prodData.setImage_eve(imagePath);
////                }
////
////                SE.modifier(prodData);
////
////                Alert alert = new Alert(Alert.AlertType.INFORMATION);
////                alert.setTitle("Validation");
////                alert.setContentText("Event mis à jour avec succès");
////                alert.showAndWait();
////
////                //initialize(); // Recharger les données dans la TableView
////            } catch (SQLException e) {
////                Alert alert = new Alert(Alert.AlertType.ERROR);
////                alert.setTitle("Erreur");
////                alert.setContentText(e.getMessage());
////                alert.showAndWait();
////            }
////        } else {
////            Alert alert = new Alert(Alert.AlertType.ERROR);
////            alert.setTitle("Erreur");
////            alert.setContentText("Veuillez sélectionner un événement à mettre à jour.");
////            alert.showAndWait();
////        }
//  }




    public void delete () {
        Evenement prodData = tableview.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            // Aucun élément sélectionné, vous pouvez gérer cela en affichant un message d'erreur ou en retournant simplement
            return;
        }

        try {
            int id_eve = prodData.getId_eve(); // Supposons que getId_eve() renvoie l'identifiant de l'événement
            SE.supprimer(id_eve); // Appeler la méthode supprimer avec l'identifiant de l'événement
            // Afficher une boîte de dialogue pour informer l'utilisateur que la suppression a réussi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Event supprimé avec succès");
            alert.showAndWait();
            // Recharger les données dans la TableView
            //initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            //initialize();
        }
    }





    @FXML
    void Afficher_btn(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/afficher_evenement_new .fxml"));
        Parent root=loader.load();
        principal.getScene().setRoot(root);
    }

}


