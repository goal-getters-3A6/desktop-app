package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ModifierEquipementBack {

    private final ServiceEquipement ES = new ServiceEquipement();
    @FXML
    private AnchorPane EquipementFX;

    @FXML
    private ImageView ImageViewerEq;

    @FXML
    private Button btnabonnement1;

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
    private Button btntdb1;

    @FXML
    private ComboBox<String> categEqId;

    @FXML
    private TextArea descEqId;

    @FXML
    private TextArea docEqId;

    @FXML
    private TextField imageEqId;

    @FXML
    private ImageView logo1;

    @FXML
    private TextField nomEqId;

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
    private Button ici1;

    ObservableList<String> list = FXCollections.observableArrayList("Fitness", "Cardio-training", "Musculation");


    @FXML
    void initialize() {
        categEqId.setItems(list);

        ImageView photoIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/photo.png")));
        ici1.setGraphic(photoIcon);  // Utilisez la variable membre ici
        photoIcon.setFitWidth(20);
        photoIcon.setFitHeight(20);
        ici1.getStyleClass().add("icon-button");
    }



    private Equipement equipement;
    private AfficherEquipementBack AfficherEquipementBack;



    public void setParentController(AfficherEquipementBack parentController) {
        this.AfficherEquipementBack = parentController;
    }
    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }
    @FXML
    void ModifierEquipement(ActionEvent event) {
        try {
            equipement.setNomEq(nomEqId.getText());
            equipement.setImageEq(imageEqId.getText());
            equipement.setCategEq(categEqId.getValue());
            equipement.setDescEq(descEqId.getText());
            equipement.setDocEq(docEqId.getText());

                if (((nomEqId.getText().isEmpty())) || ((descEqId.getText().isEmpty())) || ((docEqId.getText().isEmpty())) || ImageViewerEq.getImage() == null ) {

                    showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");

                }else if (!Character.isUpperCase(nomEqId.getText().charAt(0))  )  {
                    showAlert(Alert.AlertType.ERROR, "Données erronées", "Vérifier les données", "Le nom, doit commencer par une majuscule.");
                } else if ( !nomEqId.getText().matches("[A-Za-z]+")) {
                    showAlert(Alert.AlertType.ERROR, "Données erronées", "Vérifier les données", " Le nom doit contenir uniquement des lettres alphabétiques.");
                } else if ( nomEqId.getText().length() > 30) {
                    showAlert(Alert.AlertType.ERROR, "Données erronées", "Vérifier les données", " Le nom doit  avoir une longueur maximale de 30 caractères.");
                }else{
            ES.modifier(equipement);


                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Equipement modifié avec succès");
                    alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root = loader.load();
            nomEqId.getScene().setRoot(root);}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void insert_image(ActionEvent event) {
        FileChooser open = new FileChooser();
        Stage stage = (Stage) EquipementFX.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath();
            path = path.replace("\\", "\\\\");
            imageEqId.setText(path);
            Image image = new Image(file.toURI().toString(), 110, 110, false, true);
            ImageViewerEq.setImage(image);
        } else {
            System.out.println("NO DATA EXIST!");
        }
    }

    public void initData(Equipement equipement) {
        this.equipement = equipement;

        nomEqId.setText(equipement.getNomEq());
        categEqId.setValue(equipement.getCategEq());
        descEqId.setText(equipement.getDescEq());
        docEqId.setText(equipement.getDocEq());
        imageEqId.setText(equipement.getImageEq());


        String imagePath = equipement.getImageEq();
        Image image = new Image("file:" + imagePath); // Supposant que le chemin est absolu, sinon ajustez-le en conséquence
        ImageViewerEq.setImage(image);
    }

    @FXML
    void AnnulerEquipement(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root = loader.load();
            nomEqId.getScene().setRoot(root);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void tableaudebord(ActionEvent actionEvent) {
    }

    public void evenement(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenementListeView.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void equipement(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abonnement(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnemntsBack.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void alimentaire(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlatUser.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void reclamation(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationB.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void planning(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/seanceadmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
