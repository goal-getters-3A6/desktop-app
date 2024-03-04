package edu.esprit.controllers;

import edu.esprit.entities.AvisP;
import edu.esprit.entities.Client;
import edu.esprit.entities.Plat;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServicesAvisPlat;
import edu.esprit.services.ServicesPlat;
import edu.esprit.utils.SessionManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class Details {



    @FXML
    private Label nomLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private Label descLabel;

    @FXML
    private Label alergieLabel;

    @FXML
    private Label etatLabel;

    @FXML
    private Label photopLabel;

    @FXML
    private Label caloriesLabel;
    @FXML
    private Rating starRatingTwo;

    private ServicesPlat servicePlat;
    private final ServicesAvisPlat servicesAvisPlat = new ServicesAvisPlat();
    private edu.esprit.controllers.AfficherPlat AfficherPlat;
    private int platId;
    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    // UserService us=new UserService();
    ClientService cs=new ClientService();
    Client u;

    {
        try {
            u = cs.getOneByEmail(mail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Details() {
        servicePlat = new ServicesPlat(); // Initialize your service
    }
    public void initData(int platId) {

        this.platId = platId;
    }
    public void initialize(int platId) {
        try {

            Plat plat = servicePlat.getOneById(platId);
            if (plat != null) {
                nomLabel.setText(plat.getNomP());
                prixLabel.setText(String.valueOf(plat.getPrixP())+"DT");
                descLabel.setText(plat.getDescP());
                alergieLabel.setText(plat.getAlergieP());
                etatLabel.setText(plat.getEtatP() ? "Enstock" : "rupture stick");
                caloriesLabel.setText(String.valueOf(plat.getCalories())+" CAL");
                // Display photo
                Image image = new Image(plat.getPhotop());
                photopImageView.setImage(image);
                double averageStarRating = servicesAvisPlat.calculateAverageStarRating(platId);
                starRatingTwo.setRating(averageStarRating);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Set<AvisP> comments = servicesAvisPlat.getAllP(platId);

        ObservableList<String> commentsList = FXCollections.observableArrayList();
        for (AvisP avis : comments) {
            commentsList.add(u.getNom()+" "+u.getPrenom()+" : "+avis.getCommAP() + " | Star: " + avis.getStar());
        }
        commentsListView.setItems(commentsList);
    }

    private final ServicesAvisPlat serviceAvis = new ServicesAvisPlat();
    @FXML
    private ListView<String> commentsListView;


    @FXML
    private TextField commAPField;

    @FXML
    private TextField starField;

    @FXML
    private CheckBox favCheckbox;

    @FXML
    private ImageView photopImageView;
    @FXML
    private Rating starRating;
    public void setParentController(AfficherPlat parentController) {
        this.AfficherPlat = parentController;

    }
    @FXML
    void Ajout(ActionEvent event) {
        try {
            this.platId = platId;
            String commAP = commAPField.getText(); // Assuming commAPField is a TextField for comment
            int star = (int) starRating.getRating(); // Get the rating from the Rating component
            boolean fav = favCheckbox.isSelected();

            if (star < 1 || star > 5) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Entrez un star rate entre 1 et 5.");
                alert.showAndWait();
                return;
            }

            Plat platt = servicePlat.getOneById(platId);


            if (serviceAvis.checkIfAvisExistss(platId, u.getId())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Avis Exist");
                alert.setContentText("Vous avez déjà ajouté un avis pour ce plat! si vous avez change votre avis vous pouvez le modifier (:");
                alert.showAndWait();
                return;
            }


            serviceAvis.ajouter(new AvisP(commAP, star, fav, platt, u));
            initialize(platId);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Avis ajouté avec succès");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAvis.fxml"));
            Parent root = loader.load();
            commAPField.getScene().setRoot(root);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Entrez une valeur valide (numeric value).");
            alert.showAndWait();
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Load the AfficherPlat.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();

            // Set the scene back to AfficherPlat.fxml
            nomLabel.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openModifierPage(ActionEvent actionEvent) {
    }
}