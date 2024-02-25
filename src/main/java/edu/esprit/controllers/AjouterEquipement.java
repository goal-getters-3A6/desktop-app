package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterEquipement {

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


    ObservableList<String> list = FXCollections.observableArrayList("Fitness", "Cardio-training", "Musculation");


    @FXML
    void initialize() {
        categEqId.setItems(list);
    }


    @FXML
    void AjouterEquipement(ActionEvent event) {

        try {


            ES.ajouter(new Equipement(imageEqId.getText(), nomEqId.getText(), categEqId.getValue(), descEqId.getText(), docEqId.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Equipement added succesfully");
            alert.showAndWait();

            FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root=loader.load();
            nomEqId.getScene().setRoot(root);


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void insert_image(ActionEvent event) {
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



    @FXML
    void AfficherEquipement(ActionEvent event) {
try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
        Parent root = loader.load();
        nomEqId.getScene().setRoot(root);
    }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void abonnement(ActionEvent event) {

    }

    @FXML
    void alimentaire(ActionEvent event) {

    }

    @FXML
    void equipement(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {

    }



    @FXML
    void planning(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void tableaudebord(ActionEvent event) {

    }


}
