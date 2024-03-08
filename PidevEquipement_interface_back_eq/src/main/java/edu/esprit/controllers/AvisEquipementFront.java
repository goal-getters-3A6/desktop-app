package edu.esprit.controllers;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceAvisEquipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvisEquipementFront {

    @FXML
    private TextArea CommAEqId;

    @FXML
    private TableColumn<?, ?> commAId;

    @FXML
    private TableView<AvisEquipement> tableViewAvisEq;
    private final ServiceAvisEquipement AES = new ServiceAvisEquipement();
    private final ServiceEquipement ES = new ServiceEquipement();

    @FXML
    void AjouterAvisEq(ActionEvent event) {
        try {
            // Récupérer l'équipement correspondant à l'avis
            Equipement equipement = ES.getOneById(33); // ou utilisez une autre méthode pour obtenir l'équipement
System.out.println(equipement.getIdEq());
            // Vérifier si l'équipement existe
            if (equipement != null) {
                // Ajouter l'avis en utilisant l'identifiant de l'équipement récupéré
                AES.ajouter(new AvisEquipement(CommAEqId.getText(), equipement));
                initialize();
                CommAEqId.clear();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {

        try {
            List<AvisEquipement> Ps= new ArrayList<>(AES.getAll());
            ObservableList<AvisEquipement> observableList = FXCollections.observableList(Ps);
            tableViewAvisEq.setItems(observableList);
        }
        catch (SQLException e){}

        commAId.setCellValueFactory(new PropertyValueFactory<>("commAEq"));


    }
    @FXML
    void SupprimerAvisEq(ActionEvent event) {
        try {
            List<AvisEquipement> Ps= new ArrayList<>(AES.getAll());
            ObservableList<AvisEquipement> observableList = FXCollections.observableList(Ps);
            tableViewAvisEq.setItems(observableList);
            ObservableList<AvisEquipement> all, Single;
            all = tableViewAvisEq.getItems();
            Single = tableViewAvisEq.getSelectionModel().getSelectedItems();
            AvisEquipement aeq = Single.get(0);
            AES.supprimer(aeq.getIdAEq());
            Single.forEach(all::remove);
            initialize();
        }  catch (SQLException e){}
    }

    @FXML
    void ModifierAvisEq(ActionEvent event) {
        try {
            ObservableList<AvisEquipement> all, Single;
            all = tableViewAvisEq.getItems();
            Single = tableViewAvisEq.getSelectionModel().getSelectedItems();
            AvisEquipement aeqMod = Single.get(0);

            aeqMod.setCommAEq(CommAEqId.getText());
            AES.modifier(aeqMod);
            initialize();
            CommAEqId.clear();
        } catch (SQLException e) {}
    }

}
