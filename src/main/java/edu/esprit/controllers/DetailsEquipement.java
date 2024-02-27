package edu.esprit.controllers;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceAvisEquipement;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DetailsEquipement {
    private final ServiceAvisEquipement AES = new ServiceAvisEquipement();
    private final ServiceEquipement ES = new ServiceEquipement();
    private Equipement equipement;

    @FXML
    private Label LikeId;

    @FXML
    private Label dislikeId;

    private int like = 0;
    private int dislike = 0;


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
    private edu.esprit.controllers.AfficherEquipementFront AfficherEquipementFront;


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



            Equipement equipement1 = ES.getOneById(idEq);
            if (equipement1 != null) {
                nomEqF1.setText(equipement1.getNomEq());
                nomEqF1.setStyle("-fx-font-size: 25px; -fx-background-color: transparent; -fx-border-color: transparent;");
                // nomEqF1.setEditable(false);
                categEqF.setText(equipement1.getCategEq());
                categEqF.setStyle("-fx-font-size: 25px; -fx-background-color: transparent; -fx-border-color: transparent;");
                //categEqF.setEditable(false);
                descEqF.setText(equipement1.getDescEq());
                descEqF.setStyle(" -fx-background-color: transparent; -fx-border-color: transparent;");
                //descEqF.setEditable(false);
                docEqF.setText(equipement1.getDocEq());
                docEqF.setStyle(" -fx-background-color: transparent; -fx-border-color: transparent;");


            } else {
                // Handle case where Plat is not found
            }
            //  Equipement equipement = ES.getOneById(equipement.getIdEq());
            // List<AvisEquipement> Ps = new ArrayList<>(AES.getOneById(equipement.getIdEq()).getIdAEq());

            List<AvisEquipement> listeAvis = AES.getAllByEquipement(idEq);

            ObservableList<AvisEquipement> observableList = FXCollections.observableList(listeAvis);
            listViewAEqF.setItems(observableList);

            // ObservableList<AvisEquipement> observableList = FXCollections.observableList(Ps);
            //  listViewAEqF.setItems(observableList);

            // Configuration de la cell factory pour afficher le nom de l'équipement avec des boutons
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
                        deleteButton.setOnAction(event -> {
                            supprimerAEquipement(deleteButton); // Appeler la méthode supprimerEquipement avec l'équipement associé
                        });


                        ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/pen.png")));
                        Button editButton = new Button("", editIcon);
                        editIcon.setFitWidth(25);
                        editIcon.setFitHeight(25);
                        editButton.getStyleClass().add("icon-button");
                        int aeq;
                        editButton.setOnAction(event -> modifierAEquipement(editButton)); // Appeler la méthode modifierEquipement avec l'équipement associé

                        // Définir les données utilisateur du bouton editButton avec l'équipement associé
                        editButton.setUserData(item);

                        Label label = new Label();
                        label.setText(item.getCommAEq());



                        HBox iconsContainer = new HBox(label, new Region(), deleteButton, editButton);
                        iconsContainer.setSpacing(150); // Espacement entre les éléments

// Configurer l'espace flexible pour pousser les boutons à la fin de la ligne
                        HBox.setHgrow(new Region(), Priority.ALWAYS);
                        setGraphic(iconsContainer); // Définit le conteneur d'icônes comme élément graphique de la cellule



                    }
                }
            });


        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }






    }

    public void setParentController(AfficherEquipementFront parentController) {
        this.AfficherEquipementFront = parentController;
    }

    @FXML
    void AjouterAvisEqF(ActionEvent event) {
        try {
            this.equipement = equipement;
// Récupérer l'équipement correspondant à l'avis
            Equipement eq = ES.getOneById(equipement.getIdEq()); // ou utilisez une autre méthode pour obtenir l'équipement
          //  System.out.println(equipement.getIdEq());
            if (eq != null) {

                AES.ajouter(new AvisEquipement(CommIdAEq.getText(), eq,false, false));
                initialize(eq.getIdEq()); // Refresh only the reviews section
                CommIdAEq.clear();
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
            // Récupérer l'équipement associé au bouton de suppression
            AvisEquipement Aequipement = (AvisEquipement) deleteButton.getUserData();

            // Supprimer l'équipement de la base de données et de la liste
            AES.supprimer(Aequipement.getIdAEq());
            listViewAEqF.getItems().remove(Aequipement);
        } catch (SQLException e) {
            e.printStackTrace(); // À remplacer par une gestion appropriée des erreurs
        }
    }


    private void modifierAEquipement(Button editButton  ) {

            // Récupérer l'équipement associé au bouton editButton
            try {
                ObservableList<AvisEquipement> all, Single;
                all = listViewAEqF.getItems();
                Single = listViewAEqF.getSelectionModel().getSelectedItems();
               AvisEquipement aeqMod = Single.get(0);

                // Vérifier si l'objet Equipement associé à l'AvisEquipement est non nul
                if (aeqMod.getEquipement() != null) {
                    aeqMod.setCommAEq(CommIdAEq.getText());
                    CommIdAEq.setText(aeqMod.getCommAEq());
                    AES.modifier(aeqMod);
                    //initialize();
                    CommIdAEq.clear();
                } else {
                    // Gérer le cas où l'objet Equipement est nul
                    System.out.println("L'objet Equipement associé à cet AvisEquipement est null.");
                }
            } catch (SQLException e) {
                // Gérer l'exception SQLException
                System.out.println("Une erreur s'est produite lors de la modification de l'AvisEquipement : " + e.getMessage());
            }


    }

    @FXML
    void dislikeEq(ActionEvent event) {
        dislike++;
        dislikeId.setText(Integer.toString(dislike));
        try {
            // Récupérer l'avis équipement associé à la vue
            ObservableList<AvisEquipement> selectedItems = listViewAEqF.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
                AvisEquipement selectedAvisEquipement = selectedItems.get(0);

                // Appeler le service pour incrémenter le nombre de "dislike"
                AES.incrementDislike(selectedAvisEquipement.getIdAEq());
            }
        } catch (SQLException e) {
            e.printStackTrace(); // À remplacer par une gestion appropriée des erreurs
        }
    }

    @FXML
    void likeEq(ActionEvent event) {

        like++;
        LikeId.setText(Integer.toString(like));

    }



}
