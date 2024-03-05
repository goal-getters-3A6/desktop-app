package edu.esprit.controllers;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Client;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceAvisEquipement;
import edu.esprit.services.ServiceEquipement;
import edu.esprit.utils.SessionManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;




import java.net.URI;
import java.math.BigDecimal;

public class DetailsEquipement {
    private final ServiceAvisEquipement AES = new ServiceAvisEquipement();
    private final ServiceEquipement ES = new ServiceEquipement();
    private Equipement equipement;
    final String ACCOUNT_SID = "AC3f9de0017be9564b86cb4664a10df6b1";
    final String AUTH_TOKEN = "67c512333f9c1077be2e0fb2263a4373";



    @FXML
    private Label LikeId;

    @FXML
    private Label dislikeId;


    private int likeCount = 0;
    private int dislikeCount = 0;

    private boolean liked = false;
    private boolean disliked = false;

    @FXML
    private TextArea CommIdAEq;

    @FXML
    private Label categEqF;

    @FXML
    private Label descEqF;

    @FXML
    private Label docEqF;

    @FXML
    private ImageView imageViewF;

    @FXML
    private ListView<AvisEquipement> listViewAEqF;

    @FXML
    private Label nomEqF1;

    @FXML
    private Button publier;
    @FXML
    private Button dislikeId1;

    @FXML
    private Button likeId1;

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
    private Button btnreclamation;





    private edu.esprit.controllers.AfficherEquipementFront AfficherEquipementFront;
    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    // UserService us=new UserService();
    ClientService cs=new ClientService();
    Client user;

    {
        try {
            user = cs.getOneByEmail(mail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initData(Equipement equipement) {

        this.equipement = equipement;
        String imagePath = equipement.getImageEq();
        Image image = new Image("file:" + imagePath); // Supposant que le chemin est absolu, sinon ajustez-le en conséquence
        imageViewF.setImage(image);
    }
    // Ajoutez une méthode pour définir l'ImageView
    public void setImageView(ImageView imageView) {
        this.imageViewF = imageView;
    }
    @FXML
    void initialize(int idEq) {
        try {


            ImageView photoIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/like.png")));
            likeId1.setGraphic(photoIcon);  // Utilisez la variable membre ici
            photoIcon.setFitWidth(20);
            photoIcon.setFitHeight(20);
            likeId1.getStyleClass().add("icon-button");

            ImageView photoIcon1 = new ImageView(new Image(getClass().getResourceAsStream("/imgs/dislike.png")));
            dislikeId1.setGraphic(photoIcon1);  // Utilisez la variable membre ici
            photoIcon1.setFitWidth(20);
            photoIcon1.setFitHeight(20);
            dislikeId1.getStyleClass().add("icon-button");
            //updateLabels();

            ImageView photoIcon2 = new ImageView(new Image(getClass().getResourceAsStream("/imgs/envoyer.png")));
            publier.setGraphic(photoIcon2);  // Utilisez la variable membre ici
            photoIcon2.setFitWidth(20);
            photoIcon2.setFitHeight(20);
            publier.getStyleClass().add("icon-button");


            Equipement equipement1 = ES.getOneById(idEq);
            if (equipement1 != null) {
                listViewAEqF.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        // Afficher le commAEq dans le TextArea
                        CommIdAEq.setText(newValue.getCommAEq());
                    }
                });

                nomEqF1.setText(equipement1.getNomEq());
                nomEqF1.setStyle("-fx-font-size: 40px; -fx-background-color: transparent; -fx-border-color: transparent;");
                // nomEqF1.setEditable(false);
                categEqF.setText(equipement1.getCategEq());
                categEqF.setStyle("-fx-font-size: 25px; -fx-background-color: transparent; -fx-border-color: transparent;");
                //categEqF.setEditable(false);
                descEqF.setText(equipement1.getDescEq());
                descEqF.setWrapText(true);
                descEqF.setPrefWidth(200);
                descEqF.setStyle(" -fx-font-size: 14px;-fx-background-color: transparent; -fx-border-color: transparent;");
                //descEqF.setEditable(false);
                docEqF.setText(equipement1.getDocEq());
                docEqF.setWrapText(true);
                docEqF.setPrefWidth(200);
                docEqF.setStyle(" -fx-font-size: 14px;-fx-background-color: transparent; -fx-border-color: transparent;");


            }
            List<AvisEquipement> listeAvis = AES.getAllByEquipement(idEq);

            ObservableList<AvisEquipement> observableList = FXCollections.observableList(listeAvis);
            listViewAEqF.setItems(observableList);


            listViewAEqF.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(AvisEquipement item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/bin.png")));
                        Button deleteButton = new Button("", deleteIcon);
                        deleteIcon.setFitWidth(25);
                        deleteIcon.setFitHeight(25);
                        deleteButton.getStyleClass().add("icon-button");
                        deleteButton.setUserData(item);
                        deleteButton.setOnAction(event -> supprimerAEquipement(deleteButton));

                        ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/pen.png")));
                        Button editButton = new Button("", editIcon);
                        editIcon.setFitWidth(25);
                        editIcon.setFitHeight(25);
                        editButton.getStyleClass().add("icon-button");
                        editButton.setOnAction(event -> modifierAEquipement(editButton));

                        // Définir les données utilisateur du bouton editButton avec l'équipement associé
                        editButton.setUserData(item);
                        String censoredComment = censorBadWord(item.getCommAEq());

                        Label label = new Label();
                        label.setText(censoredComment);
                        Label label1 = new Label();
                        label1.setText(item.getUser().getNom());
                        Label label2 = new Label();
                        label2.setText(item.getUser().getPrenom());

                        // Créer un HBox pour le Label aligné à gauche
                        HBox textHBox = new HBox(10,label1, label2, label);

                        // Créer un HBox pour les boutons
                        HBox buttonsHBox = new HBox();
                        buttonsHBox.setAlignment(Pos.CENTER_RIGHT);

                        if (user.getNom().equals(item.getUser().getNom())) {
                            buttonsHBox.getChildren().addAll(deleteButton, editButton);
                        }

                        // Créer un HBox principal pour contenir le texte et les boutons
                        HBox mainHBox = new HBox(textHBox, buttonsHBox);
                        HBox.setHgrow(buttonsHBox, Priority.ALWAYS); // Pousse les boutons à droite

                        setGraphic(mainHBox);
                    }
                }
            });





        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();// À remplacer par une gestion appropriée des erreurs
        }






    }
    private String censorBadWord(String comment) {
        List<String> badListW = Arrays.asList("hell"); // Ajoutez d'autres mots interdits au besoin

        for (String str : badListW) {
            if (comment.contains(str)) {
                StringBuilder result = new StringBuilder();
                result.append(str.charAt(0));
                for (int i = 0; i < str.length() - 2; ++i) {
                    result.append("*");
                }
                result.append(str.charAt(str.length() - 1));
                comment = comment.replace(str, result.toString());
            }
        }
        return comment;
    }
    private boolean likeClicked = false;
    private boolean dislikeClicked = false;

    @FXML
    void toggleLike(ActionEvent event) {
try{
            if (!likeClicked) { // Vérifier si le bouton Like n'a pas encore été cliqué
                liked = !liked; // Inverser la valeur de liked
                updateDatabaseLike(); // Mettre à jour la base de données avec la nouvelle valeur
                refreshLikesAndDislikes(); // Rafraîchir le nombre de likes et dislikes
                updateLabels();
                likeClicked = true; // Marquer le bouton Like comme cliqué
            }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    @FXML
    void toggleDislike(ActionEvent event) {
        try {
            if (!dislikeClicked) { // Vérifier si le bouton Dislike n'a pas encore été cliqué
                disliked = !disliked; // Inverser la valeur de disliked

                    updateDatabaseDislike(); // Mettre à jour la base de données avec la nouvelle valeur

                refreshLikesAndDislikes(); // Rafraîchir le nombre de likes et dislikes
                updateLabels();
                dislikeClicked = true; // Marquer le bouton Dislike comme cliqué
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void updateDatabaseLike() throws SQLException {
        AES.ajouter(new AvisEquipement(null, equipement, user, liked, false));

    }

    private void updateDatabaseDislike() throws SQLException {
        AES.ajouter(new AvisEquipement(null, equipement, user, false, disliked));


    }

    private void refreshLikesAndDislikes() {
        try {

            // Récupérer le nombre total de Likes et Dislikes pour l'équipement actuel
            int totalLikes = AES.countLikes(equipement.getIdEq());
           // System.out.println(totalLikes);
            int totalDislikes = AES.countDislikes(equipement.getIdEq());

            // Mettre à jour les valeurs de LikeCount et DislikeCount
            likeCount = totalLikes;
            dislikeCount = totalDislikes;

            if (liked && !disliked ) {
                likeCount++;

            }

            if (disliked && !liked) {
                dislikeCount++;
            }

            // Assurer que le nombre de likes et dislikes ne devienne pas négatif
            likeCount = likeCount < 0 ? 0 : likeCount;
            dislikeCount = dislikeCount < 0 ? 0 : dislikeCount;

        } catch (SQLException e) {
            e.printStackTrace(); // À remplacer par une gestion appropriée des erreurs
        }

    }

    private void updateLabels() {

        LikeId.setText("" + likeCount);

        dislikeId.setText("" + dislikeCount);
    }



    public void setParentController(AfficherEquipementFront parentController) {
        this.AfficherEquipementFront = parentController;
    }

    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }

    @FXML
    void AjouterAvisEqF(ActionEvent event) {
        try {
            this.equipement = equipement;

// Récupérer l'équipement correspondant à l'avis
            Equipement eq = ES.getOneById(equipement.getIdEq()); // ou utilisez une autre méthode pour obtenir l'équipement

            refreshLikesAndDislikes();

            if (eq != null) {
                System.out.println(user);
                if (((CommIdAEq.getText().isEmpty())) ) {

                    showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir votre commentaire !");

                }else {


                    AES.ajouter(new AvisEquipement(CommIdAEq.getText(), eq, user));


                   }
                    initialize(eq.getIdEq()); // Refresh only the reviews section
                    CommIdAEq.clear();


                //sms
                // Find your Account Sid and Token at twilio.com/console





                } else {
                // Afficher un message d'erreur si l'équipement n'existe pas
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("L'équipement n'existe pas.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception ex) {
            // Gérer les autres exceptions
            throw new RuntimeException(ex);
        }
    }
    private void supprimerAEquipement(Button deleteButton) {
        try {
            Equipement eq = ES.getOneById(equipement.getIdEq()); // ou utilisez une autre méthode pour obtenir l'équipement

            AvisEquipement Aequipement = (AvisEquipement) deleteButton.getUserData();

            // Vérifier si l'utilisateur actuel est le même que celui associé à l'avis d'équipement
            if ( user.getNom().equals(Aequipement.getUser().getNom()) ) {
                // Afficher une boîte de dialogue de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText(null);
                alert.setContentText("Êtes-vous sûr de vouloir supprimer cet avis d'équipement ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // L'utilisateur a confirmé la suppression
                    // Supprimer l'avis d'équipement de la base de données et de la liste
                    AES.supprimer(Aequipement.getIdAEq());
                    listViewAEqF.getItems().remove(Aequipement);
                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    String personalizedMessage = String.format("Bonjour %s %s, nous tenons à vous informer que votre avis concernant l'équipement %s a été supprimé.", user.getNom(), user.getPrenom(),eq.getNomEq());

                    Message message = Message.creator(
                                    new com.twilio.type.PhoneNumber("+21697336009"),
                                    new com.twilio.type.PhoneNumber("+19123859879"),
                                    personalizedMessage)
                            .create();


                    System.out.println(message.getSid());

                }
            } else {
                // Afficher une alerte indiquant que l'utilisateur actuel ne peut pas supprimer cet avis
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Vous n'êtes pas autorisé à supprimer cet avis d'équipement.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // À remplacer par une gestion appropriée des erreurs
        }
    }




    @FXML
    private void modifierAEquipement(Button editButton) {

        AvisEquipement Aequipement = (AvisEquipement) editButton.getUserData();

        try {
            // Retrieve the AvisEquipement directly, assuming getOneById returns a single AvisEquipement
            AvisEquipement aeqMod = AES.getOneById(Aequipement.getIdAEq());

            if (aeqMod != null) {
                // Vérifier si l'utilisateur actuel est le même que celui associé à l'avis d'équipement
                String nomUtilisateurActuel = user.getNom();

                if (Aequipement != null && nomUtilisateurActuel.equals(Aequipement.getUser().getNom())) {

                    aeqMod.setCommAEq(CommIdAEq.getText());


                    AES.modifier(aeqMod);
                    initialize(aeqMod.getEquipement().getIdEq());
                    CommIdAEq.clear();
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous n'êtes pas autorisé à modifier cet avis d'équipement.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately (e.g., show an error message)
        }
    }









    @FXML
    void afficherEqF(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipementFront.fxml"));
            Parent root = loader.load();
            CommIdAEq.getScene().setRoot(root);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void abonnement(ActionEvent event) {

    }

    @FXML
    void accueil(ActionEvent event) {

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

    }

    @FXML
    void reclamation(ActionEvent event) {

    }



}
