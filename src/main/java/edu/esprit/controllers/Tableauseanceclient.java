package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceSeance;
import edu.esprit.services.UserService;
import edu.esprit.utils.SessionManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static edu.esprit.utils.SessionManagement.checkFile;
import static edu.esprit.utils.SessionManagement.deleteSession;

public class Tableauseanceclient {
    @FXML
    private AnchorPane anchorpanegrand;
    @FXML
    private MenuItem logoutitem;

    @FXML
    private MenuButton profilbuttonmenu;

    @FXML
    private MenuItem profilitem;
    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnaccueil;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private TableColumn<Seance,String> colonneduree;

    @FXML
    private TableColumn<Seance,String> colonnehoraire;

    @FXML
    private TableColumn<Seance,Integer> colonnejour;


    @FXML
    private HBox hboxtopseancefront;

    @FXML
    private ImageView imageuser;

    @FXML
    private ImageView logo;

    @FXML
    private Button retour;

    @FXML
    private TableView<Seance> tableauseance;
    SessionManagement sm = new SessionManagement();
    String mail = sm.getEmail();
    // UserService us=new UserService();
    ClientService cs = new ClientService();
    Client u;
    User user;
    UserService userService=new UserService();

    {
        // u = cs.getOneByEmail(mail);
        //System.out.println("client :"+ u);
        user=userService.getOneByEmail(mail);
        System.out.println("user?: "+user);
        if(user.getRole().equals("CLIENT"))
        {
            System.out.println("CLIENTTTTTTTTTTTTTTTTTTTTTT");
        }
    }
    private String nomSeance;
    // Méthode pour définir l'ID de la séance
    public void setNomSeance(String nomSeance)
    {
        this.nomSeance = nomSeance;
        // Mettre à jour les informations de la séance dans la nouvelle page
        updateSeanceInfo();
    }
    private void updateSeanceInfo() {
        // Utiliser le service pour récupérer les informations de la séance par son nom
        /*ServiceSeance ss = new ServiceSeance();
        Seance seance = ss.getSeanceByNom(nomSeance); // Méthode à implémenter dans ServiceSeance

        // Créer une liste observable contenant uniquement la séance actuelle
        ObservableList<Seance> seanceList = FXCollections.observableArrayList();
        seanceList.add(seance);

        // Mettre à jour les colonnes avec les informations de la séance
        colonnehoraire.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        colonnejour.setCellValueFactory(new PropertyValueFactory<>("jourseance"));
        colonneduree.setCellValueFactory(new PropertyValueFactory<>("duree"));

        // Mettre à jour le TableView avec la liste observable
        tableauseance.setItems(seanceList);*/
        // Utiliser le service pour récupérer toutes les séances avec le même nom
        ServiceSeance ss = new ServiceSeance();
        List<Seance> seances = null; // Méthode à implémenter dans ServiceSeance
        try {
            seances = ss.getAllByNom(nomSeance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Créer une liste observable contenant toutes les séances avec le même nom
        ObservableList<Seance> seanceList = FXCollections.observableArrayList(seances);

        // Mettre à jour les colonnes avec les informations de toutes les séances
        colonnehoraire.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        colonnejour.setCellValueFactory(new PropertyValueFactory<>("jourseance"));
        colonneduree.setCellValueFactory(new PropertyValueFactory<>("duree"));

        // Mettre à jour le TableView avec la liste observable contenant toutes les séances
        tableauseance.setItems(seanceList);
        if(user.getRole().equals("CLIENT"))
        {
            profilitem.setText("Profile");
        }
        else
        {
            profilitem.setText("Dashbord");

        }

        profilbuttonmenu.setText(user.getNom());
    }




    @FXML
    void abonnement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesAbonnements.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnabonnement.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void accueil(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/acceuil.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnaccueil.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void alimentaire(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnalimentaire.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void equipement(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementFront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnequipement.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void evenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnevenement.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void planning(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leseancesfront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnplanning.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void reclamation(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) btnreclamation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }
    }

    @FXML
    void retourner(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page "lesseancesfront.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leseancesfront.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            // Récupérer la scène actuelle et la modifier pour afficher la nouvelle vue
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception si le chargement de la vue échoue
        }

    }
    @FXML
    void toProfile(ActionEvent event) throws IOException  {
        if (user.getRole().equals("CLIENT")) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/profil.fxml"));
            anchorpanegrand.getChildren().setAll(pane);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newWindow = new Stage();
            newWindow.setTitle("Admin Dashboard");
            newWindow.setScene(scene);
            newWindow.show();
        }
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        if (checkFile()) {
            deleteSession();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
            anchorpanegrand.getChildren().setAll(pane);
        }
    }


}
