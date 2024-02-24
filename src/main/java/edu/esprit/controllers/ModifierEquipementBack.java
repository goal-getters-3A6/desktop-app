package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ImageView ImageViewerEq;

    @FXML
    private ComboBox<String> categEqId;

    @FXML
    private AnchorPane EquipementFX;

    @FXML
    private TextArea descEqId;

    @FXML
    private TextArea docEqId;

    @FXML
    private TextField imageEqId;

    @FXML
    private TextField nomEqId;

    ObservableList<String> list = FXCollections.observableArrayList("Fitness", "Cardio-training", "Musculation");


    @FXML
    void initialize() {
        categEqId.setItems(list);
    }



    private Equipement equipement;
    private AfficherEquipementBack AfficherEquipementBack;



    public void setParentController(AfficherEquipementBack parentController) {
        this.AfficherEquipementBack = parentController;
    }

    @FXML
    void ModifierEquipement(ActionEvent event) {
        try {
            equipement.setNomEq(nomEqId.getText());
            equipement.setImageEq(imageEqId.getText());
            equipement.setCategEq(categEqId.getValue());
            equipement.setDescEq(descEqId.getText());
            equipement.setDocEq(docEqId.getText());

            ES.modifier(equipement);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementBack.fxml"));
            Parent root = loader.load();
            nomEqId.getScene().setRoot(root);
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
        // Remplissez les champs avec les données de l'équipement
        nomEqId.setText(equipement.getNomEq());
        categEqId.setValue(equipement.getCategEq());
        descEqId.setText(equipement.getDescEq());
        docEqId.setText(equipement.getDocEq());
        imageEqId.setText(equipement.getImageEq());
        // Remplissez l'ImageViewerEq avec l'image de l'équipement, si nécessaire
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

}
