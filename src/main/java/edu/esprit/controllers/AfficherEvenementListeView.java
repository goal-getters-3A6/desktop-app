package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.tests.MainFX;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

public class AfficherEvenementListeView implements Initializable {



    @FXML
    private ListView<Evenement> listView;

    @FXML
    private Button calendar_id;
    @FXML
    private TextField recherhce_id;

    @FXML
    private TextField nom_id;

    @FXML
    private TextField adresse_id;

    @FXML
    private DatePicker dated_id;

    @FXML
    private DatePicker datef_id;

    @FXML
    private TextField nbr_max_id;

    private String imagePath;
    @FXML
    private ComboBox<String> comboBox;
    private final ServiceEvenement SE = new ServiceEvenement();

    private List<Evenement> listeEvenements;
    private List<Evenement> evenementss;


    /*@FXML
    private void getWeather(ActionEvent event) {
        // Appelez la méthode main de la classe AppLauncher pour lancer l'application Swing
        AppLauncher.main(new String[]{});
    }*/



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser le ComboBox avec les options de tri
        comboBox.getItems().addAll("Nom", "Date de début", "Date de fin", "Nombre maximum de participants", "Adresse");
        try {



            ObservableList<Evenement> evenements = FXCollections.observableArrayList(SE.getAll());
            listView.setItems(evenements);



            listView.setCellFactory(new Callback<ListView<Evenement>, ListCell<Evenement>>() {
                @Override
                public ListCell<Evenement> call(ListView<Evenement> param) {
                    return new ListCell<Evenement>() {
                        private final Button deleteButton = new Button("Supprimer");

                        {
                            deleteButton.setOnAction(event -> {
                                Evenement evenement = getItem();
                                if (evenement != null) {
                                    delete(evenement);
                                }
                            });
                        }

                        private final Button editButton = new Button("Modifier");

                        {
                            editButton.setOnAction(event -> {
                                Evenement selectedEvenement = getItem();
                                if (selectedEvenement != null) {
                                    update_aff(selectedEvenement);
                                }
                            });
                        }

                        @Override
                        protected void updateItem(Evenement item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setGraphic(null);
                            } else {
                                HBox buttonsContainer = new HBox(deleteButton, editButton);
                                buttonsContainer.setSpacing(10);
                                setGraphic(buttonsContainer);
                                setText("    " + item.getNom_eve() + "              " +
                                        "    " + item.getDated_eve() + "              " +
                                        "    " + item.getDatef_eve() + "              " +
                                        "    " + item.getNbr_max() + "              " +
                                        "    " + item.getAdresse_eve());
                            }
                        }
                    };
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//trie

    @FXML
    void comboBoxSelected(ActionEvent actionEvent) {
        String selectedAttribute = comboBox.getValue();

        if (selectedAttribute != null) {
            ObservableList<Evenement> evenements = listView.getItems();

            // Effectuer le tri en fonction de l'attribut sélectionné
            switch (selectedAttribute) {
                case "Nom":
                    evenements.sort(Comparator.comparing(Evenement::getNom_eve));
                    break;
                case "Date de début":
                    evenements.sort(Comparator.comparing(Evenement::getDated_eve));
                    break;
                case "Date de fin":
                    evenements.sort(Comparator.comparing(Evenement::getDatef_eve));
                    break;
                case "Nombre maximum de participants":
                    evenements.sort(Comparator.comparingInt(Evenement::getNbr_max));
                    break;
                case "Adresse":
                    evenements.sort(Comparator.comparing(Evenement::getAdresse_eve));
                    break;
                // Ajoutez d'autres cas pour trier selon d'autres attributs
                default:
                    // Gérer le cas par défaut (peut-être afficher un message d'erreur)
                    break;
            }

            // Mettre à jour la liste des événements
            listView.setItems(evenements);
        }
    }

    private void update_aff(Evenement evenement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur associé au fichier FXML chargé
            ModifierEvent controller = loader.getController();

            // Initialisez les données dans le contrôleur
            controller.initData(evenement);

            // Créez une scène avec la racine chargée à partir du fichier FXML
            Scene scene = new Scene(root);

            // Obtenez la fenêtre actuelle et configurez la nouvelle scène
            Stage stage = (Stage) listView.getScene().getWindow(); // Supposons que listView est un élément de votre interface actuelle
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private void delete(Evenement evenement) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet événement ?");
        alert.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int id_eve = evenement.getId_eve();
                SE.supprimer(id_eve);
                listView.getItems().remove(evenement);
                refreshListView();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            initialize(null ,null);
        }
    }


    // Méthode de rafraîchissement de la ListView
    @FXML
    private void refreshListView() {
        try {
            listView.getItems().clear(); // Efface les éléments actuels de la ListView
            listView.getItems().addAll(SE.getAll()); // Recharge les données depuis la base de données
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void rechercher(ActionEvent event) {
        String searchTerm = recherhce_id.getText().toLowerCase();

        try {
            ObservableList<Evenement> evenements = listView.getItems();

            if (searchTerm.isEmpty()) {
                listView.setItems(FXCollections.observableArrayList(SE.getAll()));
                clearStyles();
                return;
            }

            Set<Evenement> resultats = evenements.stream()
                    .filter(e ->
                            e.getNom_eve().toLowerCase().contains(searchTerm) ||
                                    e.getDated_eve().toString().toLowerCase().contains(searchTerm) ||
                                    e.getDatef_eve().toString().toLowerCase().contains(searchTerm) ||
                                    String.valueOf(e.getNbr_max()).contains(searchTerm) ||
                                    e.getAdresse_eve().toLowerCase().contains(searchTerm)
                    )
                    .collect(Collectors.toSet());

            listView.getItems().forEach(item -> {
                if (resultats.contains(item)) {
                    listView.lookupAll(".list-cell").forEach(node -> {
                        ListCell<Evenement> cell = (ListCell<Evenement>) node;
                        if (cell.getItem() != null && cell.getItem().equals(item)) {
                            cell.setStyle("-fx-background-color: green;");
                        }
                    });
                } else {
                    listView.lookupAll(".list-cell").forEach(node -> {
                        ListCell<Evenement> cell = (ListCell<Evenement>) node;
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


    @FXML
    void selectItem() {
        Evenement selectedEvenement = listView.getSelectionModel().getSelectedItem();
        if (selectedEvenement != null) {
            // Mettez ici le code pour afficher ou utiliser les données de l'événement sélectionné
            // Par exemple, vous pouvez mettre à jour des champs texte avec les informations de l'événement
            // ou appeler d'autres méthodes pour traiter cet événement sélectionné.
        }
    }





    private void clearStyles() {
        listView.lookupAll(".list-cell").forEach(node -> {
            ListCell<Evenement> cell = (ListCell<Evenement>) node;
            cell.setStyle("");
        });
    }


    @FXML
    void affiche_celendar(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
            Stage stage = (Stage) listView.getScene().getWindow();
            Parent root = loader.load();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();  // Affichez les détails de l'exception dans la console
        }
    }

    @FXML
    private void getWeather(ActionEvent event) {
        // Appelez la méthode main de la classe AppLauncher pour lancer l'application Swing
        MainFX mainFX = new MainFX();
        mainFX.showWeatherApp();
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

    public void evenement_ajou_btn(ActionEvent actionEvent) {

        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_evenement.fxml"));
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


    public void consulter_part(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_liste_participations.fxml"));
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
