package edu.esprit.controllers;

import edu.esprit.entities.Id;
import edu.esprit.entities.User;
import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.esprit.utils.ExcerciseData.getExcerciseData;
import static edu.esprit.utils.SessionManagement.*;

public class AcceuilController {

    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField mdpTxt;

    @FXML
    private AnchorPane acceuilpane;

    @FXML
    private Hyperlink forgotpassword;
    @FXML
    private Hyperlink toregister;

    @FXML
    private MenuButton profilbuttonmenu;
    @FXML
    private MenuItem profilitem;

    @FXML
    private Button loginbtn;

    @FXML
    private VBox slideshowvbox;
    @FXML
    private HBox slideshowhbox;
    @FXML
    private Button btnplanning;

    UserService userService = new UserService();
    User u = new User();
    int j = 0;
    double orgCliskSceneX, orgReleaseSceneX;
    Button lbutton, rButton;
    ImageView imageView;

    @FXML
    public void toRegister(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/register.fxml"));
        acceuilpane.getChildren().setAll(pane);
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            orgCliskSceneX = t.getSceneX();
        }
    };

    public void initialize() {
        try {
            ArrayList data = getExcerciseData();

            lbutton = new Button("<");
            rButton = new Button(">");

            // Create images array
            List<Image> imageList = new ArrayList<>();
            List<String> titleList = new ArrayList<>();
            List<String> primerList = new ArrayList<>();
            List<String> stepsList = new ArrayList<>();

            for (int i = 0; i < data.size(); i += 4) {
                String imageUrl = (String) data.get(i + 2);
                if (!imageUrl.isEmpty()) {
                    imageList.add(new Image(imageUrl));
                    titleList.add((String) data.get(i));
                    primerList.add((String) data.get(i + 1));
                    stepsList.add(String.join("\n", (List<String>) data.get(i + 3)));
                }
            }

            // Convert the lists to arrays
            Image[] images = imageList.toArray(new Image[0]);
            String[] titles = titleList.toArray(new String[0]);
            String[] primers = primerList.toArray(new String[0]);
            String[] steps = stepsList.toArray(new String[0]);

            imageView = new ImageView(images[j]);
            imageView.setCursor(Cursor.CLOSED_HAND);

            Label titleLabel = new Label(titles[j]);
            Label primerLabel = new Label(primers[j]);
            Label stepsLabel = new Label(steps[j]);

            imageView.setOnMousePressed(circleOnMousePressedEventHandler);

            imageView.setOnMouseReleased(e -> {
                orgReleaseSceneX = e.getSceneX();
                if (orgCliskSceneX > orgReleaseSceneX) {
                    lbutton.fire();
                } else {
                    rButton.fire();
                }
            });

            rButton.setOnAction(e -> {
                j = (j + 1) % images.length;
                imageView.setImage(images[j]);
                titleLabel.setText(titles[j]);
                primerLabel.setText(primers[j]);
                stepsLabel.setText(steps[j]);
            });

            lbutton.setOnAction(e -> {
                j = (j - 1 + images.length) % images.length;
                imageView.setImage(images[j]);
                titleLabel.setText(titles[j]);
                primerLabel.setText(primers[j]);
                stepsLabel.setText(steps[j]);
            });

                imageView.setFitHeight(450);
                imageView.setFitWidth(1100);
                slideshowhbox.getChildren().addAll(lbutton, imageView, rButton);
                slideshowvbox.getChildren().addAll(titleLabel, primerLabel, stepsLabel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (checkFile()) {
            mdpTxt.setVisible(false);
            emailTxt.setVisible(false);
            forgotpassword.setVisible(false);
            toregister.setVisible(false);
            loginbtn.setVisible(false);
            u = userService.getOneByEmail(getEmail());
            if (u.getRole().equals("ADMIN")) {
                profilitem.setText("Dashboard");
            } else {
                profilitem.setText("Profile");
            }
            Image image = new Image(u.getImage().isEmpty() ? "https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/default-avatar.png" : u.getImage());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);
            profilbuttonmenu.setGraphic(imageView);
            profilbuttonmenu.setText(u.getNom());

        } else {
            mdpTxt.setVisible(true);
            emailTxt.setVisible(true);
            forgotpassword.setVisible(true);
            toregister.setVisible(true);
            profilbuttonmenu.setVisible(false);
        }
    }

    @FXML
    void login() throws SQLException, IOException{
        Integer userId;
        if ((userId = userService.checklogin(emailTxt.getText(), mdpTxt.getText())) != null) {
            User u = userService.getOneById(userId);
            Id.tmpuser= u.getId();
            if (u.isTfa()){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/2FA.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage newWindow = new Stage();
                newWindow.setTitle("Two Factor Authentication");
                newWindow.setScene(scene);
                newWindow.showAndWait();
            }
            if (Id.tfaVerified || !u.isTfa()) {
            Id.user = userId;
            if (u.getRole().equals("ADMIN")) {
                if (!checkFile()) {
                    saveSession(emailTxt.getText(), mdpTxt.getText());

                }
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage newWindow = new Stage();
                newWindow.setTitle("Admin Dashboard");
                newWindow.setScene(scene);
                newWindow.show();
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
                acceuilpane.getChildren().setAll(pane);
                String title = "Welcome!";
                String message = "";
                NotificationType notification = NotificationType.SUCCESS;
                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndDismiss(Duration.seconds(4));

            } else {
                if (!checkFile()) {
                    saveSession(emailTxt.getText(), mdpTxt.getText());

                }
                AnchorPane panee = FXMLLoader.load(getClass().getResource("/profil.fxml"));
                acceuilpane.getChildren().setAll(panee);
                String title = "Welcome!";
                String message = "";
                NotificationType notification = NotificationType.SUCCESS;

                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndDismiss(Duration.seconds(4));

            }
        } else {
            String title = "Something went wrong!";
            String message = "Verify your informations !";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(4));
        }
        }
    }

    @FXML
    private void forgotPassword(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/forgotPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("Recover Password");
        newWindow.setScene(scene);
        newWindow.show();
    }
    @FXML
    private void toProfile(ActionEvent event) throws IOException {
        if (u.getRole().equals("ADMIN")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newWindow = new Stage();
            newWindow.setTitle("Admin Dashboard");
            newWindow.setScene(scene);
            newWindow.show();
        } else{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/profil.fxml"));
            acceuilpane.getChildren().setAll(pane);
        }
    }
    @FXML
    private void logout(ActionEvent event) throws IOException {
        if (checkFile()) {
            deleteSession();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
            acceuilpane.getChildren().setAll(pane);
        }
    }


    @FXML
    void abonnement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TypeAbonnements.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) slideshowvbox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void accueil(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/acceuil.fxml"));
            acceuilpane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void alimentaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) slideshowvbox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void equipement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementFront.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) slideshowvbox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void evenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_evenement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) slideshowvbox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }}



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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) slideshowvbox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
    }

}
}