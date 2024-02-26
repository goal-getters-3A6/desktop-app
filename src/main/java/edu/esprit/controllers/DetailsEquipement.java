package edu.esprit.controllers;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceAvisEquipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DetailsEquipement {
    private final ServiceAvisEquipement AES = new ServiceAvisEquipement();
    private final ServiceEquipement ES = new ServiceEquipement();
    private Equipement equipement;


    @FXML
    private TextArea CommIdAEq;

    @FXML
    private Label categEqF;

    @FXML
    private Label descEqF;

    @FXML
    private Label docEqF;

    @FXML
    private ImageView imageViewF;

    @FXML
    private ListView<String> listViewAEqF;

    @FXML
    private Label nomEqF1;

    @FXML
    private Button publier;

    @FXML
    void initialize(int idEq) {
        try {

            Equipement equipement1 = ES.getOneById(idEq);
            if (equipement1 != null) {
                nomEqF1.setText(equipement1.getNomEq());
                nomEqF1.setStyle("-fx-font-size: 25px; -fx-background-color: transparent; -fx-border-color: transparent;");
                // nomEqF1.setEditable(false);
                categEqF.setText(equipement1.getCategEq());
                categEqF.setStyle("-fx-font-size: 25px; -fx-background-color: transparent; -fx-border-color: transparent;");
                //categEqF.setEditable(false);
                descEqF.setText(equipement1.getDescEq());
                descEqF.setStyle(" -fx-background-color: transparent; -fx-border-color: transparent;");
                //descEqF.setEditable(false);
                docEqF.setText(equipement1.getDocEq());
                docEqF.setStyle(" -fx-background-color: transparent; -fx-border-color: transparent;");


            } else {
                // Handle case where Plat is not found
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        List<AvisEquipement> comments = null;
        try {
            comments = AES.getAllByEquipement(idEq);


            ObservableList<String> commentsList = FXCollections.observableArrayList();
            for (AvisEquipement avisEq : comments) {
                commentsList.add(avisEq.getCommAEq());
            }
            listViewAEqF.setItems(commentsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void refreshReviews(int idEq) {
        try {
            List<AvisEquipement> comments = AES.getAllByEquipement(idEq);

            ObservableList<String> commentsList = FXCollections.observableArrayList();
            for (AvisEquipement avisEq : comments) {
                commentsList.add(avisEq.getCommAEq());
            }
            listViewAEqF.setItems(commentsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AjouterAvisEqF(ActionEvent event) {
        try {

            Equipement eq = ES.getOneById(equipement.getIdEq());
            if (eq != null) {
                AES.ajouter(new AvisEquipement(CommIdAEq.getText(), eq));
                refreshReviews(eq.getIdEq()); // Refresh only the reviews section
                CommIdAEq.clear();
            } else {
                // Afficher un message d'erreur si l'équipement n'existe pas
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("L'équipement n'existe pas.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception ex) {
            // Gérer les autres exceptions
            throw new RuntimeException(ex);
        }
    }



}
