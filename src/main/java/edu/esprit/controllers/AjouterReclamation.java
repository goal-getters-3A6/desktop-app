package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.utils.SessionManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterReclamation {

    private final ServiceReclamation SR = new ServiceReclamation();
    @FXML
    private ComboBox<String> categorieRecid;

    @FXML
    private TextArea descriptionRecid;

    @FXML
    private TextField oddRecid;

    @FXML
    private TextField piéceJointeRecid;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnaccueil;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnreclamation;

    @FXML
    private VBox Vboxid;
    @FXML
    private ComboBox<String> serviceRecid;
    ObservableList<String> list = FXCollections.observableArrayList("Qualité", "Probléme Technique", "Communication","durabilité");
    ObservableList<String> list1 = FXCollections.observableArrayList("Hygiéne", "Sécurité", "Displine");
    private final ServiceReclamation SA = new ServiceReclamation();
    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    // UserService us=new UserService();
    ClientService cs=new ClientService();
    Client u;

    {
        try {
            u = cs.getOneByEmail(mail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void initialize()
    {
        categorieRecid.setItems(list);
        serviceRecid.setItems(list1);

    }
    @FXML
    void choisirfile(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une pièce jointe");

        // Configurer les filtres si nécessaire
         fileChooser.getExtensionFilters().addAll(
             new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"),
               new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
         );

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            piéceJointeRecid.setText(selectedFile.getAbsolutePath());
        }
    }
    @FXML
    void Ajouter(ActionEvent event) {
        try {
            // Contrôle de saisie pour descriptionRecid
            String description = descriptionRecid.getText().trim();
            if (!description.matches("^[A-Z].*\\.$")) {
                showAlert("Erreur de saisie", "La description doit commencer par une majuscule et se terminer par un point.");
                return;
            }

            // Contrôle de saisie pour oddRecid
            String oddValue = oddRecid.getText().trim();
            if (!oddValue.matches("ODD[1-9]|ODD1[0-7]")) {
                showAlert("Erreur de saisie", "La valeur ODD doit être écrite comme par exemlpe ODD1 .");
                return;
            }

            // Contrôle de saisie pour serviceRecid (si nécessaire)
            String selectedService = serviceRecid.getSelectionModel().getSelectedItem();
            if (selectedService == null) {
                // Aucun service sélectionné, vous pouvez afficher une alerte ou prendre une autre action
                showAlert("Information", "Le service peut être vide.");
            }

            // User us1 =new User (1,"mayssa","hakimi");
            SR.ajouter(new Reclamation(categorieRecid.getSelectionModel().getSelectedItem(),descriptionRecid.getText(), oddRecid.getText(),piéceJointeRecid.getText(),serviceRecid.getSelectionModel().getSelectedItem(),u));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Réclamation added succesfully");
            alert.showAndWait();
            clearForm();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        /*} catch (IOException e) {
            throw new RuntimeException(e);*/
       }
    }
    private void clearForm () {

        descriptionRecid.setText("");
        oddRecid.setText("");
        piéceJointeRecid.setText("");


    }
   @FXML
    void AfficherRec(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesReclamations.fxml"));
            Parent root = loader.load();
            oddRecid.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();  // Affichez les détails de l'exception dans la console
        }
    }




    @FXML
    void abonnement(javafx.event.ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesAbonnements.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }
    @FXML
    void accueil(javafx.event.ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/acceuil.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }

    }

    @FXML
    void alimentaire(javafx.event.ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void equipement(javafx.event.ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementFront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void evenement(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void chat(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chatbot.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée à partir du fichier FXML
            Scene scene = new Scene(root);

            // Créer une nouvelle instance de Stage pour la nouvelle fenêtre
            Stage newStage = new Stage();

            // Configurer la nouvelle scène sur le nouveau stage
            newStage.setScene(scene);

            // Montrer la nouvelle fenêtre
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void planning(javafx.event.ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leseancesfront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }
    @FXML
    void reclamation(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }

    }

    }

