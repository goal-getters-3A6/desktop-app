package edu.esprit.controllers;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceSeance;
import edu.esprit.services.UserService;
import edu.esprit.utils.DataSource;
import edu.esprit.utils.SessionManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.stage.Stage;

import static edu.esprit.utils.SessionManagement.*;


public class leseancesfront{
    @FXML
    private AnchorPane anchorpanegrand;
    @FXML
    private BorderPane borderpane;

    @FXML
    private Button btnabonnement;
    @FXML
    private Button btnmesreservations;

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
    private GridPane grid;

    @FXML
    private HBox hbox1;
    @FXML
    private MenuItem profilitem;
    @FXML
    private HBox hbox2;

    @FXML
    private HBox hboxtopseancefront;

    @FXML
    private ImageView imageuser;

    @FXML
    private ImageView logo;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vbox;

    @FXML
    private MenuButton profilbuttonmenu;
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
    public leseancesfront() throws SQLException {
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
    void mesreservations(ActionEvent event) {
        try {

            // Charger le fichier FXML correspondant à la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservationsclient.fxml"));
            Parent root = loader.load();
            Reservationsclient controller = loader.getController();
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            Stage stage = (Stage) grid.getScene().getWindow(); // Obtenez la scène actuelle du bouton ou de tout autre nœud
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception, par exemple, afficher un message d'erreur à l'utilisateur
        }

    }

    private final ServiceSeance ss=new ServiceSeance();
     List<Seance> seanceList=ss.getAll();

    public void initialize(){
       // Image image = new Image(u.getImage().isEmpty() ? "https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/default-avatar.png" : u.getImage());
        //javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
       // imageView.setFitHeight(25);
        //imageView.setFitWidth(25);
       // profilbuttonmenu.setGraphic(imageView);
        if(user.getRole().equals("CLIENT"))
        {
            profilitem.setText("Profile");
        }
        else
        {
            profilitem.setText("Dashbord");

        }

        profilbuttonmenu.setText(user.getNom());
        setSeanceGridPaneList();
    }
    private void setSeanceGridPaneList() {

        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(5));

        // Créez une liste pour stocker les noms des séances déjà ajoutées à l'interface utilisateur
        List<String> addedSeanceNames = new ArrayList<>();

        for (Seance seance : seanceList) {
            // Vérifiez d'abord si la séance est déjà affichée dans l'interface utilisateur
            if (!addedSeanceNames.contains(seance.getNom())) {
                addedSeanceNames.add(seance.getNom());

                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);

                ImageView imageView = new ImageView();
                Label nomLabel = new Label(seance.getNom());
                nomLabel.setFont(new Font("Arial", 18));

                Button detailsButton = new Button("Détails");
                detailsButton.setStyle("-fx-background-color: #db1f48; -fx-text-fill: white;");
                detailsButton.setOnAction(e -> {
                    try {
                        detailSeance(seance);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                detailsButton.setId("btndetailseance");

                Button reserverButton = new Button("Réserver");
                reserverButton.setStyle("-fx-background-color: #db1f48; -fx-text-fill: white;");
                reserverButton.setOnAction(e -> {
                    try {
                        reserverSeance(seance);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                reserverButton.setId("btnreserverseance");
                VBox.setMargin(reserverButton, new Insets(5, 0, 0, 0));

                try {
                    Image image = new Image(new File(seance.getImageseance()).toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(300);
                    imageView.setFitHeight(300);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                vbox.getChildren().addAll(imageView, nomLabel, detailsButton, reserverButton);
                hbox.getChildren().add(vbox);
            }
        }

        grid.getChildren().add(hbox);

    }
    // Méthode appelée lorsque le bouton "Détails" est cliqué
    public void detailSeance(Seance seance) throws IOException {
        try {

            String nomSeance = seance.getNom(); // Récupérer le nom de la séance
            // Charger le fichier FXML correspondant à la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tableauseanceclient.fxml"));
            Parent root = loader.load();
            Tableauseanceclient controller = loader.getController();
            controller.setNomSeance(nomSeance);
            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            Stage stage = (Stage) grid.getScene().getWindow(); // Obtenez la scène actuelle du bouton ou de tout autre nœud
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception, par exemple, afficher un message d'erreur à l'utilisateur
        }
    }

    // Méthode appelée lorsque le bouton "Réserver" est cliqué
    private void reserverSeance(Seance seance) throws IOException {
        try {
            int idSeance = seance.getIdseance();
            // Récupérer l id de la séance
            // Charger le fichier FXML correspondant à la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resformualire.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec la vue chargée
            Resformualire controller = loader.getController();
            controller.setSeance(seance);
            System.out.println("lors qlique "+seance);
            Scene scene = new Scene(root);
            Stage stage = (Stage) grid.getScene().getWindow(); // Obtenez la scène actuelle du bouton ou de tout autre nœud
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'exception, par exemple, afficher un message d'erreur à l'utilisateur
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


