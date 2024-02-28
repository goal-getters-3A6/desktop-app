package edu.esprit.controllers;

import edu.esprit.Api.SendEmail;
import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.User;
import edu.esprit.services.AdminService;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import edu.esprit.utils.SessionManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Adminreservation {
    @FXML
    private ListView<Reservation> listview;

    @FXML
    private Button btnrefraichir;

    @FXML
    private Button btnrecherche;
    @FXML
    private Button btnannuler;
    @FXML
    private Button btnstats;
    @FXML
    private Button btntdb1;
    @FXML
    private Button btnreclamation1;
    @FXML
    private Button btnequipement1;

    @FXML
    private Button btnevenement1;

    @FXML
    private Button btnplanning1;
    @FXML
    private Button btnabonnement1;

    @FXML
    private Button btnalimentaire1;
    @FXML
    private Button btntri;

    @FXML
    private ComboBox<String> comboboxtri;

    @FXML
    private GridPane gridpane;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private TextField textfieldrecherche;
    List<Reservation> resList;
    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    Client u;
    ClientService A=new ClientService();
    {
        try {
            u = A.getOneByEmail(mail);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private final ObservableList<String> criteresTri = FXCollections.observableArrayList("Nom", "Nomseance", "Prenom");

    public void initialize() {
        // Ajouter les critères de tri au ComboBox
        comboboxtri.setItems(criteresTri);

        // Définir le critère de tri par défaut
        comboboxtri.getSelectionModel().selectFirst();
        afficherReservations();
    }


    @FXML
    void recherche(ActionEvent event) {
        // Vérifier si resList est null
       /* if (resList == null) {
            // Récupérer les réservations depuis le service
            try {
                resList = serviceReservation.getAll();
            } catch (SQLException e) {
                e.printStackTrace();
                return; // Sortir de la méthode si une exception se produit lors de la récupération des réservations
            }
        }

        // Récupérer le poids saisi par l'utilisateur depuis le champ de recherche
        String poidsRecherche = textfieldrecherche.getText().trim();

        // Vérifier si le champ de recherche n'est pas vide
        if (!poidsRecherche.isEmpty()) {
            // Convertir le poids recherché en float
            float poidsRechercheFloat = Float.parseFloat(poidsRecherche);

            // Filtrer les réservations en fonction du poids recherché
            List<Reservation> reservationsFiltrees = resList.stream()
                    .filter(reservation -> reservation.getPoids() == poidsRechercheFloat)
                    .collect(Collectors.toList());

            // Mettre à jour la liste affichée avec les résultats de la recherche
            afficherReservations(reservationsFiltrees);
        } else {
            // Si le champ de recherche est vide, afficher toutes les réservations
            afficherReservations(resList);
        }*/
        // Vérifier si resList est null
      /*  if (resList == null) {
            // Récupérer les réservations depuis le service
            try {
                resList = serviceReservation.getAll();
            } catch (SQLException e) {
                e.printStackTrace();
                return; // Sortir de la méthode si une exception se produit lors de la récupération des réservations
            }
        }

        // Récupérer le poids saisi par l'utilisateur depuis le champ de recherche
        String poidsRecherche = textfieldrecherche.getText().trim();

        // Vérifier si le champ de recherche n'est pas vide
        if (!poidsRecherche.isEmpty()) {
            // Convertir le poids recherché en float
            float poidsRechercheFloat = Float.parseFloat(poidsRecherche);

            // Filtrer les réservations en fonction du poids recherché
            List<Reservation> reservationsFiltrees = resList.stream()
                    .filter(reservation -> {
                        // Vérifier si l'utilisateur associé à la réservation est un client
                        if (reservation.getUser() instanceof Client) {
                            // Si c'est un client, vérifier si le poids correspond au poids recherché
                            return ((Client) reservation.getUser()).getPoids() == poidsRechercheFloat;
                        }
                        return false; // Si l'utilisateur n'est pas un client, exclure cette réservation
                    })
                    .collect(Collectors.toList());

            // Mettre à jour la liste affichée avec les résultats de la recherche
            afficherReservations(reservationsFiltrees);
        } else {
            // Si le champ de recherche est vide, afficher toutes les réservations
            afficherReservations(resList);
        }*/
        if (resList == null) {
            // Récupérer les réservations depuis le service
            try {
                resList = serviceReservation.getAll();
            } catch (SQLException e) {
                e.printStackTrace();
                return; // Sortir de la méthode si une exception se produit lors de la récupération des réservations
            }
        }

// Récupérer le nom saisi par l'utilisateur depuis le champ de recherche
        String nomRecherche = textfieldrecherche.getText().trim();

// Vérifier si le champ de recherche n'est pas vide
        if (!nomRecherche.isEmpty()) {
            // Filtrer les réservations en fonction du nom recherché
            List<Reservation> reservationsFiltrees = resList.stream()
                    .filter(reservation -> {
                        // Vérifier si l'utilisateur associé à la réservation est un client
                        if (reservation.getUser() instanceof Client) {
                            // Si c'est un client, vérifier si le nom correspond au nom recherché
                            String nomPersonne = (reservation.getSeance()).getNom();
                            return nomPersonne.equalsIgnoreCase(nomRecherche);
                        }
                        return false; // Si l'utilisateur n'est pas un client, exclure cette réservation
                    })
                    .collect(Collectors.toList());

            // Mettre à jour la liste affichée avec les résultats de la recherche
            afficherReservations(reservationsFiltrees);
        } else {
            // Si le champ de recherche est vide, afficher toutes les réservations
            afficherReservations(resList);
        }
    }

    @FXML
    void stats(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistiquesreservation.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnstats.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void tri(ActionEvent event) {
        // Récupérer le critère de tri sélectionné par l'utilisateur
        String critereTri = comboboxtri.getValue();

        // Trier la liste des réservations en fonction du critère sélectionné
        List<Reservation> reservationsTrie = trierReservations(critereTri);

        // Mettre à jour la ListView avec la liste triée
        listview.setItems(FXCollections.observableArrayList(reservationsTrie));

    }
    public List<Reservation> trierReservations(String critereTri) {
       /* List<Reservation> reservations = null;
        try {
            // Récupérer la liste complète des réservations
            reservations = serviceReservation.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (reservations == null) {
            return new ArrayList<>();
        }

        // Utiliser Stream pour trier les réservations en fonction du critère sélectionné
        switch (critereTri) {
            case "Nom":
                return reservations.stream()
                        .sorted(Comparator.comparing(reservation -> reservation.getUser().getNom()))
                        .collect(Collectors.toList());
            case "Taille":
                return reservations.stream()
                        .sorted(Comparator.comparing(reservation -> reservation.getTaille()))
                        .collect(Collectors.toList());
            case "Sexe":
                return reservations.stream()
                        .sorted(Comparator.comparing(Reservation::getSexe))
                        .collect(Collectors.toList());
            default:
                // Retourner la liste non triée si le critère de tri n'est pas reconnu
                return reservations;
        }*/

        List<Reservation> reservations = null;
        try {
            // Récupérer la liste complète des réservations
            reservations = serviceReservation.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (reservations == null) {
            return new ArrayList<>();
        }

        // Utiliser Stream pour trier les réservations en fonction du critère sélectionné
        switch (critereTri) {
            case "Nom":
                return reservations.stream()
                        .sorted(Comparator.comparing(reservation -> reservation.getUser().getNom()))
                        .collect(Collectors.toList());
            case "Nomseance":
                return reservations.stream()
                        .sorted(Comparator.comparing(reservation -> reservation.getSeance().getNom())).collect(Collectors.toList());
            case "Prenom":
                return reservations.stream()
                        .sorted(Comparator.comparing(reservation -> reservation.getUser().getPrenom()))
                        .collect(Collectors.toList());
            default:
                // Retourner la liste non triée si le critère de tri n'est pas reconnu
                return reservations;
        }
    }


    private final ServiceReservation serviceReservation = new ServiceReservation();
    private final ClientService clientService = new ClientService();

    /* public void initialize() {
          afficherReservations();
      }
      public void afficherReservations() {
          List<Reservation> reservations;
          try {
              reservations = serviceReservation.getAll();
              afficherReservationsDansGridPane(reservations);
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }

      public void afficherReservationsDansGridPane(List<Reservation> reservations) {
          int row = 0;
          for (Reservation reservation : reservations) {
              User user = clientService.getClientById(reservation.getUser().getId());

              gridpane.add(new Label("Nom de la séance : " + reservation.getSeance().getNom()), 0, row);
              gridpane.add(new Label("Nom de l'utilisateur : " + user.getNom()), 1, row);
              gridpane.add(new Label("Prénom de l'utilisateur : " + user.getPrenom()), 2, row);
              gridpane.add(new Label("Âge : " + reservation.getAge()), 3, row);
              gridpane.add(new Label("Poids : " + reservation.getPoids()), 4, row);
              gridpane.add(new Label("Taille : " + reservation.getTaille()), 5, row);
              gridpane.add(new Label("Sexe : " + reservation.getSexe()), 6, row);

              row++;
          }
      }*/

    public void afficherReservations() {
       /* ObservableList<String> reservationsData = FXCollections.observableArrayList();
        try {
            List<Reservation> reservations = serviceReservation.getAll();
            for (Reservation reservation : reservations) {
                String reservationDetails = "Nom de la séance: " + reservation.getSeance().getNom() +
                        ", Nom de l'utilisateur: " + reservation.getUser().getNom() +
                        ", Prénom de l'utilisateur: " + reservation.getUser().getPrenom() +
                        ", Âge: " + reservation.getAge() +
                        ", Poids: " + reservation.getPoids() +
                        ", Taille: " + reservation.getTaille() +
                        ", Sexe: " + reservation.getSexe();
                reservationsData.add(reservationDetails);
            }
            listview.setItems(reservationsData);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
      /*  try {
            List<Reservation> reservations = serviceReservation.getAll();

            // Créer une ObservableList contenant les réservations
            ObservableList<Reservation> reservationsObservableList = FXCollections.observableArrayList(reservations);

            // Lier la liste observable à la ListView
            listview.setItems(reservationsObservableList);

            // Personnaliser l'affichage des éléments de la ListView avec un ListCell personnalisé
            listview.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } else {
                        // Afficher les détails de la réservation dans la cellule
                        String reservationDetails = "Nom de la séance: " + reservation.getSeance().getNom() +
                                ", Nom de l'utilisateur: " + reservation.getUser().getNom() +
                                ", Prénom de l'utilisateur: " + reservation.getUser().getPrenom() +
                                ", Poids: " + reservation.getPoids() +
                                ", Taille: " + reservation.getTaille() +
                                ", Sexe: " + reservation.getSexe();
                        setText(reservationDetails);
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
       /* try {
            List<Reservation> reservations = serviceReservation.getAll();

            // Créer une ObservableList contenant les réservations
            ObservableList<Reservation> reservationsObservableList = FXCollections.observableArrayList(reservations);

            // Lier la liste observable à la ListView
            listview.setItems(reservationsObservableList);

            // Personnaliser l'affichage des éléments de la ListView avec un ListCell personnalisé
            listview.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } else {
                        // Vérifier si l'utilisateur associé à la réservation est un client
                        if (reservation.getUser() instanceof Client) {
                            // Si c'est un client, afficher les détails de la réservation dans la cellule
                            Client client = (Client) reservation.getUser();
                            String reservationDetails = "Nom de la séance: " + reservation.getSeance().getNom() +
                                    ", Nom de l'utilisateur: " + client.getNom() +
                                    ", Prénom de l'utilisateur: " + client.getPrenom() +
                                    ", Poids: " + client.getPoids() +
                                    ", Taille: " + client.getTaille() +
                                    ", Sexe: " + client.getSexe();
                            setText(reservationDetails);
                        } else {
                            // Si l'utilisateur n'est pas un client, afficher un message par défaut
                            setText("L'utilisateur associé à cette réservation n'est pas un client.");
                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
      /*  try {
            List<Reservation> reservations = serviceReservation.getAll();

            // Créer une ObservableList contenant les réservations
            ObservableList<Reservation> reservationsObservableList = FXCollections.observableArrayList(reservations);

            // Lier la liste observable à la ListView
            listview.setItems(reservationsObservableList);

            // Personnaliser l'affichage des éléments de la ListView avec un ListCell personnalisé
            listview.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } else {
                        // Vérifier si l'utilisateur associé à la réservation est un client
                        if (reservation.getUser() instanceof Client) {
                            // Si c'est un client, afficher les détails de la réservation dans la cellule
                            Client client = (Client) reservation.getUser();
                            String reservationDetails = "Nom de la séance: " + reservation.getSeance().getNom() +
                                    ", Nom de l'utilisateur: " + client.getNom() +
                                    ", Prénom de l'utilisateur: " + client.getPrenom();
                            // Vérifier si le client a des informations de poids et de taille
                            if (client.getPoids() > 0 && client.getTaille() > 0 && client.getSexe() != null) {
                                reservationDetails += ", Poids: " + client.getPoids() +
                                        ", Taille: " + client.getTaille() +
                                        ", Sexe: " + client.getSexe();
                            } else {
                                // Si les informations de poids et de taille ne sont pas disponibles, afficher un message par défaut
                                reservationDetails += ", Les informations de poids, taille ou sexe ne sont pas disponibles.";
                            }
                            setText(reservationDetails);
                        } else {
                            // Si l'utilisateur n'est pas un client, afficher un message par défaut
                            setText("L'utilisateur associé à cette réservation n'est pas un client.");
                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
       /* try {
            List<Reservation> reservations = serviceReservation.getAll();

            // Créer une ObservableList contenant les réservations
            ObservableList<Reservation> reservationsObservableList = FXCollections.observableArrayList(reservations);

            // Lier la liste observable à la ListView
            listview.setItems(reservationsObservableList);

            // Personnaliser l'affichage des éléments de la ListView avec un ListCell personnalisé
            listview.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } else {
                        // Récupérer l'utilisateur associé à la réservation
                        User user = reservation.getUser();
                        if (user != null) {
                            // Vérifier le rôle de l'utilisateur
                            if ("CLIENT".equals(user.getRole())) {
                                // Si c'est un client, afficher les détails de la réservation avec les informations du client
                                Client client = (Client) user;
                                System.out.println("client"+client);
                                String reservationDetails = "Nom de la séance: " + reservation.getSeance().getNom() +
                                        ", Nom de l'utilisateur: " + client.getNom() +
                                          ", Prénom de l'utilisateur: " + client.getPrenom()+
                                        ", Poids: " + client.getPoids() +
                                        ", Taille: " + client.getTaille() +
                                        ", Sexe: " + client.getSexe();
                                // Vérifier si le client a des informations de poids et de taille
                               if (client.getPoids() > 0 && client.getTaille() > 0 && client.getSexe() != null) {
                                    reservationDetails += ", Poids: " + client.getPoids() +
                                            ", Taille: " + client.getTaille() +
                                            ", Sexe: " + client.getSexe();
                                } else {
                                    // Si les informations de poids et de taille ne sont pas disponibles, afficher un message par défaut
                                    reservationDetails += ", Les informations de poids, taille ou sexe ne sont pas disponibles.";
                                }
                                setText(reservationDetails);
                            } else {
                                // Si ce n'est pas un client, afficher les détails de la réservation avec le nom de l'utilisateur
                                setText("Nom de la séance: " + reservation.getSeance().getNom() +
                                        ", Nom de l'utilisateur: " + user.getNom() +
                                        ", Prénom de l'utilisateur: " + user.getPrenom());
                            }
                        } else {
                            setText("Aucun utilisateur associé à cette réservation.");
                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
       /* try {
            List<Reservation> reservations = serviceReservation.getAll();
            System.out.println(reservations);

            // Créer une ObservableList contenant les réservations
            ObservableList<Reservation> reservationsObservableList = FXCollections.observableArrayList(reservations);

            // Lier la liste observable à la ListView
            listview.setItems(reservationsObservableList);

            // Personnaliser l'affichage des éléments de la ListView avec un ListCell personnalisé
            listview.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } /*else {
                        // Récupérer l'utilisateur associé à la réservation
                        User user = reservation.getUser();
                        if (user != null && "CLIENT".equals(user.getRole())) {
                            // Si c'est un client, afficher les détails de la réservation avec les informations du client
                            Client client = (Client) user;
                            String reservationDetails = "Nom de la séance: " + reservation.getSeance().getNom()
                                    ;
                            setText(reservationDetails);
                        } else {
                            // Si ce n'est pas un client ou si l'utilisateur est null, afficher un message par défaut
                            setText("Nom de la séance: " + reservation.getSeance().getNom() +
                                    ", Nom de l'utilisateur: " + (user != null ? user.getNom() : "Non défini") +
                                    ", Prénom de l'utilisateur: " + (user != null ? user.getPrenom() : "Non défini"));
                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try {
            List<Reservation> reservations = serviceReservation.getAll();

            // Créer une ObservableList contenant les réservations
            ObservableList<Reservation> reservationsObservableList = FXCollections.observableArrayList(reservations);

            // Lier la liste observable à la ListView
            listview.setItems(reservationsObservableList);

            // Personnaliser l'affichage des éléments de la ListView avec un ListCell personnalisé
            listview.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } else {
                        // Afficher les détails de la réservation dans la cellule
                        String reservationDetails = "Nom de la séance: " + reservation.getSeance().getNom() +
                                ", Nom de l'utilisateur: " + reservation.getUser().getNom() +
                                ", Prénom de l'utilisateur: " + reservation.getUser().getPrenom() ;
                        setText(reservationDetails);
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void annuler(ActionEvent event) {
        // Récupérer la réservation sélectionnée dans la ListView
        Reservation selectedReservation = listview.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            // Aucune réservation sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une réservation à annuler.");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir annuler cette réservation ?");
        confirmationDialog.setContentText("Cliquez sur Oui pour confirmer ou sur Non pour annuler.");

        // Définir les boutons de la boîte de dialogue
        ButtonType buttonTypeOui = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNon = new ButtonType("Non", ButtonBar.ButtonData.NO);
        confirmationDialog.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeOui) {
                // L'utilisateur a confirmé, supprimer la réservation de la base de données
                try {
                    serviceReservation.supprimer(selectedReservation.getIdReservation());
                    // Mettre à jour l'affichage de la liste des réservations
                    afficherReservations();
                    System.out.println(mail);
                    SendEmail.send(mail);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Afficher une erreur en cas d'échec de suppression
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Une erreur s'est produite lors de la suppression de la réservation.");
                    alert.showAndWait();

                }
            } else {
                // L'utilisateur a annulé, ne rien faire
                System.out.println("Annulation de la suppression de la réservation.");
            }
        });
    }
    public void afficherReservations(List<Reservation> reservations) {
        ObservableList<Reservation> reservationsData = FXCollections.observableArrayList(reservations);
        listview.setItems(reservationsData);
    }

    @FXML
    void refraichir(ActionEvent event) {
        afficherReservations();

    }

    @FXML
    void tableaudebord(ActionEvent event) {

    }
    @FXML
    void reclamation(ActionEvent event) {

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
    void abonnement(ActionEvent event) {

    }

    @FXML
    void alimentaire(ActionEvent event) {

    }
}
