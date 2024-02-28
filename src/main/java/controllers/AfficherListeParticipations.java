package controllers;

import Service.Service_Participation;
import Service.Service_evenement;
import Utils.PDFGenerator;
import entities.Evenement;
import entities.Participation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AfficherListeParticipations implements Initializable {

    @FXML
    private ListView<Participation> listView;


    @FXML
    private ComboBox<String> comboBox;

    private final Service_Participation ssp = new Service_Participation();
    private final PDFGenerator pdfGenerator = new PDFGenerator();

    @FXML
    private TextField age_id;

    @FXML
    private TextField email_id;

    @FXML
    private Button genererCertificatsBtn;


    @FXML
    private TextField nom_id;

    @FXML
    private TextField prenom_id;


    @FXML
    private Button supprimer_btn;

    @FXML
    private Button modifier_btn;


    @FXML
    private TextField recherhce_id;

    @FXML
    private PieChart pieChart;

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser le ComboBox avec les options de tri
        comboBox.getItems().addAll("Nom", "Prénom", "Âge");
        try {
            // Charger le fichier CSS
            String css = Objects.requireNonNull(this.getClass().getResource("/css/listViewStyle.css")).toExternalForm();
            listView.getStylesheets().add(css);

            // Créer la liste des participations
            ObservableList<Participation> participations = FXCollections.observableArrayList(ssp.getAll());
            listView.setItems(participations);

            // Utiliser une cellule personnalisée pour afficher chaque élément
            listView.setCellFactory(param -> new CustomListViewCell());

            // Calculer les statistiques sur le nombre de participants par événement
            updateStatistics(); // Appel à la méthode pour mettre à jour les statistiques initiales

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    // Méthode pour recalculer les statistiques
    private void updateStatistics() {
        ObservableList<Participation> participations = listView.getItems();

        // Calculer les statistiques sur le nombre de participants par événement
        Map<String, Long> statistiques = participations.stream()
                .collect(Collectors.groupingBy(p -> p.getEvent().getNom_eve(), Collectors.counting()));

        // Créer une liste de données pour le PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        statistiques.forEach((eventName, count) -> {
            pieChartData.add(new PieChart.Data(eventName, count));
        });

        // Mettre à jour les noms de données pour inclure le nombre de participants
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " - ", (int) data.getPieValue(), " participants"
                        )
                )
        );

        // Mettre à jour les données du PieChart
        pieChart.setData(pieChartData);
    }

    public void SelectData() {

        Participation selectedParticipation = listView.getSelectionModel().getSelectedItem();

        if (selectedParticipation != null) {
            nom_id.setText(selectedParticipation.getNom_p());
            prenom_id.setText(selectedParticipation.getPrenom_p());
            age_id.setText(String.valueOf(selectedParticipation.getAge()));
            email_id.setText(selectedParticipation.getEmail());
        }

    }

    public void delete () {
        Participation prodData = listView.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            // Aucun élément sélectionné, vous pouvez gérer cela en affichant un message d'erreur ou en retournant simplement
            return;
        }

        int id_eve = prodData.getId_p(); // Supposons que getId_eve() renvoie l'identifiant de l'événement
        ssp.supprimer(id_eve); // Appeler la méthode supprimer avec l'identifiant de l'événement
        // Afficher une boîte de dialogue pour informer l'utilisateur que la suppression a réussi
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Validation");
        alert.setContentText("Event supprimé avec succès");
        alert.showAndWait();
        // Recharger les données dans la listView
        initialize(null,null);
        // Mettre à jour les statistiques
        updateStatistics();
    }

    public void update () {

        Participation prodData = listView.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un événement à mettre à jour.");
            alert.showAndWait();
            return;
        }

        try {
            // Mettre à jour les attributs de l'événement
            prodData.setNom_p(nom_id.getText());
            initialize(null,null);
            prodData.setPrenom_p(prenom_id.getText());
            prodData.setAge(Integer.parseInt(age_id.getText()));
            prodData.setEmail(email_id.getText());
            initialize(null,null);
            // Appeler la méthode de mise à jour dans votre service Evenement
            ssp.modifier(prodData);

            // Afficher une boîte de dialogue pour informer l'utilisateur que la mise à jour a réussi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Event mis à jour avec succès");
            alert.showAndWait();



        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
        initialize(null,null);
    }



    @FXML
    void handleGenererCertificatsBtn() {
        List<Participation> participations = new ArrayList<>(listView.getSelectionModel().getSelectedItems());

        if (!participations.isEmpty()) {
            // Afficher une boîte de dialogue pour permettre à l'utilisateur de choisir l'emplacement et le nom du fichier
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer les certificats");
            File selectedFile = fileChooser.showSaveDialog(null);

            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                for (Participation participation : participations) {
                    String fileName = filePath + "_Certificate_" + participation.getId_p() + ".pdf";
                    String eventName = getEventName(participation.getEvent().getId_eve());
                    pdfGenerator.generateCertificate(participation, fileName , eventName);
                }
                showAlert(Alert.AlertType.INFORMATION, "Génération réussie", "Les certificats ont été générés avec succès.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune participation sélectionnée", "Veuillez sélectionner au moins un participant.");
        }
    }

    private String getEventName(int eventId) {
        Service_evenement serviceEvenement = new Service_evenement();
        Evenement event = serviceEvenement.getOneById(eventId);
        return event != null ? event.getNom_eve() : "Nom de l'événement inconnu";
    }



    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    //recherche
    @FXML
    void rechercherPart(javafx.event.ActionEvent actionEvent ) {
        String searchTerm = recherhce_id.getText().toLowerCase();

        try {
            ObservableList<Participation> participations = listView.getItems();

            if (searchTerm.isEmpty()) {
                listView.setItems(FXCollections.observableArrayList(ssp.getAll()));
                clearStyles();
                return;
            }

            Set<Participation> resultats = participations.stream()
                    .filter(p ->
                            p.getNom_p().toLowerCase().contains(searchTerm) ||
                                    p.getPrenom_p().toLowerCase().contains(searchTerm) ||
                                    String.valueOf(p.getAge()).contains(searchTerm) ||
                                    p.getEmail().toLowerCase().contains(searchTerm) ||
                                    (p.getEvent() != null && p.getEvent().getNom_eve().toLowerCase().contains(searchTerm))
                    )
                    .collect(Collectors.toSet());

            listView.getItems().forEach(item -> {
                if (resultats.contains(item)) {
                    listView.lookupAll(".list-cell").forEach(node -> {
                        ListCell<Participation> cell = (ListCell<Participation>) node;
                        if (cell.getItem() != null && cell.getItem().equals(item)) {
                            cell.setStyle("-fx-background-color: green;");
                        }
                    });
                } else {
                    listView.lookupAll(".list-cell").forEach(node -> {
                        ListCell<Participation> cell = (ListCell<Participation>) node;
                        if (cell.getItem() != null && cell.getItem().equals(item)) {
                            cell.setStyle("");
                        }
                    });
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearStyles() {
        listView.lookupAll(".list-cell").forEach(node -> {
            ListCell<Evenement> cell = (ListCell<Evenement>) node;
            cell.setStyle("");
        });
    }




    //trie




    public void comboBoxSelected(javafx.event.ActionEvent actionEvent) {
        String selectedAttribute = comboBox.getValue();

        if (selectedAttribute != null) {
            ObservableList<Participation> participations = listView.getItems();

            // Effectuer le tri en fonction de l'attribut sélectionné
            switch (selectedAttribute) {
                case "Nom":
                    participations.sort(Comparator.comparing(Participation::getNom_p));
                    break;
                case "Prénom":
                    participations.sort(Comparator.comparing(Participation::getPrenom_p));
                    break;
                case "Âge":
                    participations.sort(Comparator.comparingInt(Participation::getAge));


                    // Gérer le cas par défaut (peut-être afficher un message d'erreur)
                    break;
            }

            // Mettre à jour la liste des participations
            listView.setItems(participations);

            // Mettre à jour les statistiques
            updateStatistics();
        }
    }


}






