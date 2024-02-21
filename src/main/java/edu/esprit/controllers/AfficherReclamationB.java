package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherReclamationB {
    private final ServiceReclamation SR = new ServiceReclamation();
    @FXML
    private TableColumn<Reclamation, String> CatégorieRéclamationid;

    @FXML
    private TableColumn<Reclamation, String> DescriptionRéclamtionid;

    @FXML
    private TableColumn<Reclamation, String> Etatid;

    @FXML
    private TableColumn<Reclamation, String> ODDid;

    @FXML
    private TableColumn<Reclamation, String> PiéceJointeid;

    @FXML
    private TableColumn<Reclamation, String> Serviceid;

    @FXML
    private TableView<Reclamation> tabRecid;
    @FXML
    void initialize() {

        try {
            List<Reclamation> rec = new ArrayList<>(SR.getAll());
            ObservableList<Reclamation> observableList = FXCollections.observableList(rec);
            tabRecid.setItems(observableList);
        }
        catch (SQLException e){}

        CatégorieRéclamationid.setCellValueFactory(new PropertyValueFactory<>("categorieRec"));
        DescriptionRéclamtionid.setCellValueFactory(new PropertyValueFactory<>("descriptionRec"));
        PiéceJointeid.setCellValueFactory(new PropertyValueFactory<>("piéceJointeRec"));
        ODDid.setCellValueFactory(new PropertyValueFactory<>("oddRec"));
        Serviceid.setCellValueFactory(new PropertyValueFactory<>("serviceRec"));
        Etatid.setCellValueFactory(new PropertyValueFactory<>("etatRec"));


    }
    @FXML
    void supprimerRec(ActionEvent event) {
        try {
            List<Reclamation> rec = new ArrayList<>(SR.getAll());
            ObservableList<Reclamation> observableList = FXCollections.observableList(rec);
            tabRecid.setItems(observableList);
            ObservableList<Reclamation> all, Single;
            all = tabRecid.getItems();
            Single = tabRecid.getSelectionModel().getSelectedItems();
            Reclamation re = Single.get(0);
            SR.supprimer(re.getIdRec());
            Single.forEach(all::remove);
            initialize();
        }  catch (SQLException e){}
    }
}
