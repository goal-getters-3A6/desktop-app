package edu.esprit.controllers;

import edu.esprit.entities.Seance;
import edu.esprit.services.ServiceSeance;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Seanceadmin {
    @FXML
    private Button btnrefraichir;

    @FXML
    private GridPane gridpane;

    @FXML
    private ListView<Seance> listview;

    @FXML
    private ComboBox<String> combobox;
    @FXML
    private ScrollPane scrolpane;
    @FXML
    private ScrollPane scrollpane2;
    @FXML
    private Button btnreservations;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnreclamation;
    @FXML
    private Button btnrecherche;
    @FXML
    private Button btnstats;
    @FXML
    private ListView<Seance> listviewgeneral;
    @FXML
    private TextField textfieldrecherche;

    @FXML
    private Button tri;
    @FXML
    private Button btntdb;
    @FXML
    private Button details;
    @FXML
    private Button btnajouterseance;
    private List<Seance> seanceList;
    private Set<Seance> uniqueSeances = new HashSet<>();
    ServiceSeance ss = new ServiceSeance();


    @FXML
    private ListView<String> listviewdetails;


    @FXML
    void btndetails(ActionEvent event) throws IOException {

        Seance selectedSeance = listview.getSelectionModel().getSelectedItem();
        if (selectedSeance != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modsupprseance.fxml"));
            Parent root = loader.load();
            Modsupprseance modifierSeanceController = loader.getController();
            modifierSeanceController.initData(selectedSeance);
            // Affichez l'interface utilisateur de modification
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        }

        }

    public void initialize() {

        // Ajouter les options de tri au ComboBox
        combobox.getItems().addAll("Jour", "Nom de la séance", "Horaire");

        // Écouter les changements de sélection dans le ComboBox
        combobox.setOnAction(this::btntri);
        try {
            seanceList = ss.getAll(); // Récupérer les données de la base de données

            // Ajoutez les séances uniques à l'ensemble en utilisant une logique de comparaison personnalisée
          /*  for (Seance seance : seanceList) {
                boolean found = false;
                for (Seance uniqueSeance : uniqueSeances) {
                    if (areSeancesEqual(seance, uniqueSeance)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    uniqueSeances.add(seance);
                }
            }*/
            // Affichez les séances uniques dans le ListView
            listview.setItems(FXCollections.observableArrayList(seanceList));
            listview.setCellFactory(param -> new ListCell<Seance>() {
                @Override
                protected void updateItem(Seance seance, boolean empty) {
                    super.updateItem(seance, empty);
                    if (empty || seance == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Font customFont = Font.font("Arial", FontWeight.BOLD, 12); // Exemple de police Arial, gras, taille 12
                        // Créer les éléments pour afficher les détails de la séance
                        Label nameLabel = new Label(seance.getNom());
                        Label horaireLabel = new Label("Horaire: " + seance.getHoraire());
                        Label jourLabel = new Label( seance.getJourseance());
                        Label numSalleLabel = new Label("Numéro de salle: " + seance.getNumesalle());
                        Label dureeLabel = new Label("Durée: " + seance.getDuree());
                        ImageView imageView = new ImageView(new Image(seance.getImageseance()));
                        imageView.setFitWidth(200); // Ajustez la largeur de l'image selon votre besoin
                        imageView.setFitHeight(200); // Ajustez la hauteur de l'image selon votre besoin
                        setGraphic(imageView);
                        nameLabel.setFont(customFont);
                        horaireLabel.setFont(customFont);
                        jourLabel.setFont(customFont);
                        numSalleLabel.setFont(customFont);
                        dureeLabel.setFont(customFont);
                        // setText(seance.getNom());
                        // Organiser les éléments verticalement
                        /*VBox vbox = new VBox(5); // Espacement vertical de 5 pixels
                        vbox.getChildren().addAll(nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel, imageView);

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(vbox);
                        setText(null);*/
                        // Organiser les éléments horizontalement
                       /* HBox hbox = new HBox(10); // Espacement horizontal de 10 pixels
                        hbox.getChildren().addAll(imageView, nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel);

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(hbox);
                        setText(null);*/
                        // Organiser les éléments horizontalement
                        HBox hbox = new HBox(10); // Espacement horizontal de 10 pixels
                        VBox vbox = new VBox(); // VBox pour organiser verticalement les labels
                        vbox.getChildren().addAll(nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel);
                        hbox.getChildren().addAll(imageView, vbox); // Ajouter la VBox à la HBox

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(hbox);
                        setText(null);
                        setStyle("-fx-background-color: pink;");

                    }
                }
            });
            listview.setOnMouseClicked(event -> {
                Seance selectedSeance = listview.getSelectionModel().getSelectedItem();
                if (selectedSeance != null) {
                    ObservableList<String> allDetails = FXCollections.observableArrayList();

                    // Parcourir la liste pour trouver toutes les occurrences de la séance sélectionnée
                    for (Seance seance : seanceList) {
                        if (seance.getNom().equals(selectedSeance.getNom())) {
                            // Construire les détails de la séance et les ajouter à la liste
                            String details = "Nom: " + seance.getNom() + "\n" +
                                    "Horaire: " + seance.getHoraire() + "\n" +
                                    "Jour: " + seance.getJourseance() + "\n" +
                                    "Numéro de salle: " + seance.getNumesalle() + "\n" +
                                    "Durée: " + seance.getDuree() + "\n";
                            allDetails.add(details);
                        }
                    }

                    // Afficher les détails de toutes les occurrences de la séance dans la ListView des détails
                    //listviewdetails.setItems(allDetails);
                }
            });
         /*   listviewdetails.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // Vérifiez si le clic est un double-clic
                    Seance selectedSeance = listview.getSelectionModel().getSelectedItem();
                    if (selectedSeance != null) {
                        new Modsupprseance(selectedSeance);
                    }
                }
            });*/


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        initializeSeanceDetailsListView();

    }


    private void initializeSeanceDetailsListView() {
      /*  listviewdetails.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });*/
    }


    // Méthode pour comparer deux séances et déterminer si elles sont égales
    private boolean areSeancesEqual(Seance seance1, Seance seance2) {
        return seance1.getNom().equals(seance2.getNom());

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
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/seanceadmin.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void ajouter(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajoutseance.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnajouterseance.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void tableaudebord(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btntri(ActionEvent event) {
       /* try {
            // Récupérer toutes les séances
            List<Seance> seances = ss.getAll();

            // Trier les séances par jour en utilisant Stream
            List<Seance> seancesTriees = seances.stream()
                    .sorted(Comparator.comparing(Seance::getJourseance))
                    .collect(Collectors.toList());

            // Mettre à jour la liste affichée avec les séances triées
            listview.setItems(FXCollections.observableArrayList(seancesTriees));
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        /*try {
            // Récupérer toutes les séances
            List<Seance> seances = ss.getAll();

            // Récupérer la méthode de tri sélectionnée
            String methodeTri = combobox.getValue();

            // Trier les séances en fonction de la méthode sélectionnée
            switch (methodeTri) {
                case "Jour":
                    Collections.sort(seances, Comparator.comparing(Seance::getJourseance));
                    break;
                case "Nom de la séance":
                    Collections.sort(seances, Comparator.comparing(Seance::getNom));
                    break;
                case "Horaire":
                    Collections.sort(seances, Comparator.comparing(Seance::getHoraire));
                    break;
            }

            // Mettre à jour la liste affichée avec les séances triées
            listview.setItems(FXCollections.observableArrayList(seances));
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try {
            // Récupérer toutes les séances
            List<Seance> seances = ss.getAll();

            // Récupérer la méthode de tri sélectionnée
            String methodeTri = combobox.getValue();

            // Trier les séances en fonction de la méthode sélectionnée
            List<Seance> seancesTriees = seances.stream().sorted((s1, s2) -> {
                switch (methodeTri) {
                    case "Jour":
                        return s1.getJourseance().compareTo(s2.getJourseance());
                    case "Nom de la séance":
                        return s1.getNom().compareTo(s2.getNom());
                    case "Horaire":
                        return s1.getHoraire().compareTo(s2.getHoraire());
                    default:
                        return 0;
                }
            }).collect(Collectors.toList());

            // Mettre à jour la liste affichée avec les séances triées
            listview.setItems(FXCollections.observableArrayList(seancesTriees));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void rechercher(ActionEvent event) {

        // Obtenez le nom recherché à partir de la ListView
        String nomRecherche = textfieldrecherche.getText();

        // Parcourez la liste des séances pour trouver la séance avec le nom correspondant (ignorant la casse)
        Optional<Seance> seanceRecherchee = seanceList.stream()
                .filter(seance -> seance.getNom().equalsIgnoreCase(nomRecherche))
                .findFirst();

        // Vérifiez si une séance correspondante a été trouvée
        if (seanceRecherchee.isPresent()) {
            // Affichez la séance correspondante dans la ListView
            listview.setItems(FXCollections.observableArrayList(seanceRecherchee.get()));
        } else {
            // Affichez une alerte indiquant que la séance n'a pas été trouvée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Séance non trouvée");
            alert.setHeaderText(null);
            alert.setContentText("Aucune séance trouvée avec le nom spécifié.");
            alert.showAndWait();
        }
    }


    @FXML
    void stats(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Statistiqueseance.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void afficherToutesLesSeances() {
        try {
            seanceList = ss.getAll(); // Récupérer toutes les séances depuis la base de données

            // Afficher toutes les séances dans la ListView
            listview.setItems(FXCollections.observableArrayList(seanceList));
        } catch (SQLException e) {
            e.printStackTrace();
            // Afficher une alerte en cas d'erreur lors de la récupération des séances
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors du rafraîchissement de la liste des séances.");
            alert.showAndWait();
        }
    }

    @FXML
    void refraichir(ActionEvent event) {
        try {
            seanceList = ss.getAll(); // Récupérer les données de la base de données

            // Ajoutez les séances uniques à l'ensemble en utilisant une logique de comparaison personnalisée
            /*for (Seance seance : seanceList) {
                boolean found = false;
                for (Seance uniqueSeance : uniqueSeances) {
                    if (areSeancesEqual(seance, uniqueSeance)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    seanceList.add(seance);
                }
            }*/

            // Affichez les séances uniques dans le ListView
            /*listview.setItems(FXCollections.observableArrayList(seanceList));
            listview.setCellFactory(param -> new ListCell<Seance>() {
                @Override
                protected void updateItem(Seance seance, boolean empty) {
                    super.updateItem(seance, empty);
                    if (empty || seance == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Créer les éléments pour afficher les détails de la séance
                        Label nameLabel = new Label("Nom: " + seance.getNom());
                        Label horaireLabel = new Label("Horaire: " + seance.getHoraire());
                        Label jourLabel = new Label("Jour: " + seance.getJourseance());
                        Label numSalleLabel = new Label("Numéro de salle: " + seance.getNumesalle());
                        Label dureeLabel = new Label("Durée: " + seance.getDuree());
                        ImageView imageView = new ImageView(new Image(seance.getImageseance()));
                        imageView.setFitWidth(100); // Ajustez la largeur de l'image selon votre besoin
                        imageView.setFitHeight(100); // Ajustez la hauteur de l'image selon votre besoin
                        setGraphic(imageView);
                        // setText(seance.getNom());
                        // Organiser les éléments verticalement
                        VBox vbox = new VBox(5); // Espacement vertical de 5 pixels
                        vbox.getChildren().addAll(nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel, imageView);

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(vbox);
                        setText(null);
                        setStyle("-fx-background-color: pink;");
                    }
                }
            });*/
          /*  listview.setItems(FXCollections.observableArrayList(seanceList));
            listview.setCellFactory(param -> new ListCell<Seance>() {
                @Override
                protected void updateItem(Seance seance, boolean empty) {
                    super.updateItem(seance, empty);
                    if (empty || seance == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Créer les éléments pour afficher les détails de la séance
                        Label nameLabel = new Label("Nom: " + seance.getNom());
                        Label horaireLabel = new Label("Horaire: " + seance.getHoraire());
                        Label jourLabel = new Label("Jour: " + seance.getJourseance());
                        Label numSalleLabel = new Label("Numéro de salle: " + seance.getNumesalle());
                        Label dureeLabel = new Label("Durée: " + seance.getDuree());
                        ImageView imageView = new ImageView(new Image(seance.getImageseance()));
                        imageView.setFitWidth(200); // Ajustez la largeur de l'image selon votre besoin
                        imageView.setFitHeight(200); // Ajustez la hauteur de l'image selon votre besoin
                        setGraphic(imageView);*/
                        // setText(seance.getNom());
                        // Organiser les éléments verticalement
                        /*VBox vbox = new VBox(5); // Espacement vertical de 5 pixels
                        vbox.getChildren().addAll(nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel, imageView);

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(vbox);
                        setText(null);*/
                        // Organiser les éléments horizontalement
                       /* HBox hbox = new HBox(10); // Espacement horizontal de 10 pixels
                        hbox.getChildren().addAll(imageView, nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel);

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(hbox);
                        setText(null);*/
                        // Organiser les éléments horizontalement
                      /*  HBox hbox = new HBox(10); // Espacement horizontal de 10 pixels
                        VBox vbox = new VBox(); // VBox pour organiser verticalement les labels
                        vbox.getChildren().addAll(nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel);
                        hbox.getChildren().addAll(imageView, vbox); // Ajouter la VBox à la HBox

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(hbox);
                        setText(null);
                        setStyle("-fx-background-color: pink;");

                    }
                }
            });*/
            // Affichez les séances uniques dans le ListView
            listview.setItems(FXCollections.observableArrayList(seanceList));
            listview.setCellFactory(param -> new ListCell<Seance>() {
                @Override
                protected void updateItem(Seance seance, boolean empty) {
                    super.updateItem(seance, empty);
                    if (empty || seance == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Font customFont = Font.font("Arial", FontWeight.BOLD, 12); // Exemple de police Arial, gras, taille 12
                        // Créer les éléments pour afficher les détails de la séance
                        Label nameLabel = new Label(seance.getNom());
                        Label horaireLabel = new Label("Horaire: " + seance.getHoraire());
                        Label jourLabel = new Label( seance.getJourseance());
                        Label numSalleLabel = new Label("Numéro de salle: " + seance.getNumesalle());
                        Label dureeLabel = new Label("Durée: " + seance.getDuree());
                        ImageView imageView = new ImageView(new Image(seance.getImageseance()));
                        imageView.setFitWidth(200); // Ajustez la largeur de l'image selon votre besoin
                        imageView.setFitHeight(200); // Ajustez la hauteur de l'image selon votre besoin
                        setGraphic(imageView);
                        nameLabel.setFont(customFont);
                        horaireLabel.setFont(customFont);
                        jourLabel.setFont(customFont);
                        numSalleLabel.setFont(customFont);
                        dureeLabel.setFont(customFont);
                        // setText(seance.getNom());
                        // Organiser les éléments verticalement
                        /*VBox vbox = new VBox(5); // Espacement vertical de 5 pixels
                        vbox.getChildren().addAll(nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel, imageView);

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(vbox);
                        setText(null);*/
                        // Organiser les éléments horizontalement
                       /* HBox hbox = new HBox(10); // Espacement horizontal de 10 pixels
                        hbox.getChildren().addAll(imageView, nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel);

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(hbox);
                        setText(null);*/
                        // Organiser les éléments horizontalement
                        HBox hbox = new HBox(10); // Espacement horizontal de 10 pixels
                        VBox vbox = new VBox(); // VBox pour organiser verticalement les labels
                        vbox.getChildren().addAll(nameLabel, horaireLabel, jourLabel, numSalleLabel, dureeLabel);
                        hbox.getChildren().addAll(imageView, vbox); // Ajouter la VBox à la HBox

                        // Afficher les détails de la séance dans la cellule
                        setGraphic(hbox);
                        setText(null);
                        setStyle("-fx-background-color: pink;");

                    }
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    @FXML
    void reservation(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page Statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Adminreservation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle page FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
