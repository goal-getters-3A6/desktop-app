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
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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
    private TableView<?> recid;

    @FXML
    private TableColumn<?, ?> recidC;
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

           // User us1 =new User (1,"mayssa","hakimi");
            SR.ajouter(new Reclamation(categorieRecid.getSelectionModel().getSelectedItem(),descriptionRecid.getText(), oddRecid.getText(),piéceJointeRecid.getText(),serviceRecid.getSelectionModel().getSelectedItem(),u));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Réclamation added succesfully");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        /*} catch (IOException e) {
            throw new RuntimeException(e);*/
       }
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
    void afficherAbByUser(ActionEvent event) {
        int userId = 1;
        List<Reclamation> reclamations = SR.getAllByUser(u.getMail());
        for (Reclamation reclamation : reclamations) {
            // Créer un Label pour chaque propriété de la réclamation
            Label categorieLabel = new Label("Categorie: " + reclamation.getCategorieRec());
            Label descriptionLabel = new Label("Description: " + reclamation.getDescriptionRec());
            // Ajouter les Labels au VBox
            Vboxid.getChildren().addAll(categorieLabel, descriptionLabel);
            // Ajouter un espace entre chaque réclamation
            Vboxid.getChildren().add(new Label("")); // Ajoute un Label vide pour créer un espace

        }}

    }

