package edu.esprit.controllers;
import java.io.*;
import java.net.MalformedURLException;
import java.sql.*;
import java.text.DecimalFormat;
import edu.esprit.entities.*;
import edu.esprit.services.*;
import edu.esprit.services.UserService;
import edu.esprit.utils.DataSource;
import edu.esprit.utils.SessionManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
/*import javafx.geometry.Insets;
import javafx.geometry.Pos;*/
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.controlsfx.control.Notifications;

import static edu.esprit.utils.SessionManagement.checkFile;
import static edu.esprit.utils.SessionManagement.deleteSession;

public class Resformualire {
    @FXML
    private Label labelnom;

    @FXML
    private Label labelsexe;


    @FXML
    private TextField Age;

    @FXML
    private TextField Poids;

    @FXML
    private TextField Taille;

    @FXML
    private AnchorPane anchorpanedash;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnaccueil;

    @FXML
    private Button btnajouterreservation;

    @FXML
    private Button btnalimentaire;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btncalculer;
    @FXML
    private Button btnpdf;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnprofil;

    @FXML
    private Button btnreclamation;

    @FXML
    private RadioButton femme;

    @FXML
    private TableColumn<?, ?> dureeseance;
    @FXML
    private RadioButton homme;
    @FXML
    private TableView<Seance> tableseance;
    @FXML
    private AnchorPane anchorpanegrand;

    @FXML
    private TableColumn<?, ?> horaireseance;

    @FXML
    private TableColumn<?, ?> jourseance;

    @FXML
    private ListView<Seance> listview;

    /*  @FXML
    private ComboBox<String> jourComboBox;

    @FXML
    private ComboBox<String> horaireComboBox;*/
    @FXML
    private ImageView logo1;
    private Seance seance;
    final ServiceReservation serviceReservation = new ServiceReservation();

    public void setSeance(Seance seance) {
        this.seance = seance;
        updateSeanceInfo();

    }

    @FXML
    private MenuItem logoutitem;

    @FXML
    private MenuButton profilbuttonmenu;

    @FXML
    private MenuItem profilitem;
    @FXML
    private Label labelpoids;

    @FXML
    private Label labelprenom;

    @FXML
    private Label labeltaille;
    @FXML
    private Label labelduree;

    @FXML
    private Label labelhoraire;

    @FXML
    private Label labeljour;
    ServiceReservation sr = new ServiceReservation();
    List<Reservation> resList;

    {
        try {
            resList = sr.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private ToggleGroup sexeToggleGroup;
    //  List<Reservation> resList;
    // private User user;
    //private User u;
    //int id=Id.user;
    SessionManagement ss = new SessionManagement();
    String mail = ss.getEmail();
    // UserService us=new UserService();
    ClientService cs = new ClientService();
    Client u;
    User user;
    UserService userService=new UserService();

    {
        try {
            user=userService.getOneByEmail(mail);
            u = cs.getOneByEmail(mail);
            System.out.println("user?: "+user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*public void setUser(User user) {
        this.u = user;
        // Mettre à jour les labels avec le nom et le prénom de l'utilisateur

    }*/
    public void setUser(Client user) {
        this.u = user;

    }


    public void initialize() throws IOException {
        //System.out.println("apres clique"+seance);
      //  System.out.println("user" + u);
        ClientService clientService = new ClientService();
        labelnom.setText(u.getNom());
        labelprenom.setText(u.getPrenom());
        labelpoids.setText(String.valueOf(u.getPoids()));
        labeltaille.setText(String.valueOf(u.getTaille()));
        labelsexe.setText(u.getSexe());
        // Création des labels pour les informations utilisateur
// Configuration du style des labels

// Création d'une VBox pour contenir les labels

        // Vérifier si la séance est null
      /*  if (seance != null) {
            // Si la séance n'est pas null, remplir les ComboBox avec les données de la séance
          //  remplirComboBox();
        } else {
            System.out.println("La séance est null. Veuillez vous assurer que la séance est correctement passée.");
        }*/
        // Mettre à jour le style des labels pour les rendre non éditables
        labelnom.setDisable(true);
        labelprenom.setDisable(true);
        listview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                labelhoraire.setText(String.valueOf(newSelection.getHoraire()));
                labeljour.setText(newSelection.getJourseance());
                labelduree.setText(newSelection.getDuree());
            }
        });
      /*  tableseance.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                labelhoraire.setText(String.valueOf(newSelection.getHoraire()));
                labeljour.setText(newSelection.getJourseance());
                labelduree.setText(newSelection.getDuree());
            }
        });*/
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
    private void updateSeanceInfo() {
        /*ServiceSeance ss = new ServiceSeance();
        List<Seance> seances = null; // Méthode à implémenter dans ServiceSeance
        try {
            seances = ss.getAllByNom(seance.getNom());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Créer une liste observable contenant toutes les séances avec le même nom
        ObservableList<Seance> seanceList = FXCollections.observableArrayList(seances);

        // Mettre à jour les colonnes avec les informations de toutes les séances
        horaireseance.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        jourseance.setCellValueFactory(new PropertyValueFactory<>("jourseance"));
        dureeseance.setCellValueFactory(new PropertyValueFactory<>("duree"));

        // Mettre à jour le TableView avec la liste observable contenant toutes les séances
        tableseance.setItems(seanceList);*/
        ServiceSeance ss = new ServiceSeance();
        List<Seance> seances = null; // Implémentez la méthode dans ServiceSeance pour récupérer toutes les séances
        try {
            seances = ss.getAllByNom(seance.getNom());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Créez une liste observable contenant toutes les séances avec le même nom
        ObservableList<Seance> seanceList = FXCollections.observableArrayList(seances);

        // Mettez à jour le ListView avec la liste observable contenant toutes les séances
        listview.setItems(seanceList);

        // Définissez une cell factory pour afficher le nom de la séance dans le ListView
        listview.setCellFactory(param -> new ListCell<Seance>() {
            @Override
            protected void updateItem(Seance item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                   // setText(item.getNom()); // Assurez-vous que getNom() retourne le nom de la séance
                    // Personnalisez l'affichage pour inclure la durée, le jour et l'horaire
                    String text = "Jour: " + item.getJourseance() + ", Horaire: " + item.getHoraire() + ", Durée: " + item.getDuree();
                    setText(text);
                }
            }
        });

        // Ajoutez un écouteur de sélection pour afficher les détails de la séance sélectionnée
        listview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                labelhoraire.setText(newSelection.getHoraire().toString());
                labeljour.setText(newSelection.getJourseance());
                labelduree.setText(newSelection.getDuree());
            }
        });
    }

    @FXML
    void ajouterreservation(ActionEvent event) throws SQLException {
        ClientService clientService = new ClientService();
        if (user.getRole().equals("CLIENT")) {
            Client client = (Client) u; // Convertir l'utilisateur en client
            String nom = u.getNom(); // Récupérer le nom du client
            String prenom = u.getPrenom(); // Récupérer le prénom du client
            Float poids = ((Client) u).getPoids();
           // Time h= Time.valueOf(horaireComboBox.getValue());
           // String j=jourComboBox.getValue();
            // Vérifier le nombre maximal de réservations pour la séance
            // Obtenir la liste des réservations pour la séance sélectionnée
            List<Reservation> reservationsPourSeance = new ArrayList<>();
            for (Reservation r : resList) {
                if (r.getSeance().equals(seance)) {
                    reservationsPourSeance.add(r);
                }
            }
           /* for (Reservation r : resList) {
                if (r.getSeance().getJourseance().equals(seance.getJourseance() )&& r.getSeance().getHoraire()==seance.getHoraire() ) {
                    System.out.println(r.getSeance().getJourseance()+r.getSeance().getHoraire());
                    reservationsPourSeance.add(r);
                }
            }*/

            if (reservationsPourSeance.size() >= seance.getNbMax()) {
                // Si le nombre maximal est atteint ou dépassé, afficher une alerte
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerte");
                alert.setContentText("Le nombre maximal de réservations pour cette séance a été atteint.");
                alert.showAndWait();
            } else {
               // float poidsx = u.getPoids();
               float taille = u.getTaille();
                // Ajouter la réservation en utilisant le nom et le prénom du client
                Reservation nouvelleReservation = new Reservation(seance, u);
              //  System.out.println("zfiezfjezofj"+seance);
                try {
                    serviceReservation.ajouter(nouvelleReservation);
                   // System.out.println("fgrgr"+nouvelleReservation);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Réservation ajoutée avec succès");
                    alert.showAndWait();
                    TextToSpeech.main(new String[]{});
                    // Calcul de l'IMC en fonction du sexe
                  //  double imc;
                  //  String etat;
                   /* if (u.getSexe().equals("HOMME")) {

                    } else {
                        imc = poids / (taille * taille) * 0.9;
                    }*/
                   /* imc = poids / (taille * taille);
                    DecimalFormat df = new DecimalFormat("#.##");
                    String imcFormate = df.format(imc);
                    // Détermination de l'état pondéral en fonction de l'IMC
                    if (imc < 18.5) {
                        etat = "Insuffisance pondérale";
                    } else if (imc > 18.5 && imc < 25) {
                        etat = "Poids normal";
                    } else if (imc > 25 && imc < 30) {
                        etat = "Surpoids";
                    } else {
                        etat = "Obésité";
                    }
                    // Affichage de l'IMC et de l'état pondéral
                    Alert alertimc = new Alert(Alert.AlertType.INFORMATION);
                    alertimc.setTitle("IMC");
                    alertimc.setContentText("Votre IMC est : " + imcFormate + "\n" + "Votre état pondéral : " + etat+" par cet imc vous pouvez connaitre mieux quelle seance vous voulez choisir pour atteindre votre objectif check nos seances");
                    alertimc.showAndWait();*/
                    // Après l'affichage de l'alerte de succès
                    Alert confirmationPdf = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationPdf.setTitle("Générer un PDF");
                    confirmationPdf.setContentText("Voulez-vous générer un PDF de cette réservation ?");
                    ButtonType ouiButton = new ButtonType("Oui");
                    ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationPdf.getButtonTypes().setAll(ouiButton, nonButton);

                    Optional<ButtonType> result = confirmationPdf.showAndWait();
                    if (result.isPresent() && result.get() == ouiButton) {
                        pdf();
                    } else {
                           }
                    // Vous pouvez ajouter d'autres traitements ici si nécessaire
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur lors de l'ajout");
                    alert.setContentText("Une erreur s'est produite lors de l'ajout de la réservation : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            // Si l'utilisateur n'est pas un client, afficher un message d'erreur ou effectuer une autre action appropriée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("L'utilisateur n'est pas un client.");
            alert.showAndWait();
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

    /*@FXML
    void pdf(ActionEvent event) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
            document.open();

            // Ajouter un cadre autour de la page
            Rectangle rect = new Rectangle(577, 825, 18, 15);
            rect.enableBorderSide(1);
            rect.enableBorderSide(2);
            rect.enableBorderSide(4);
            rect.enableBorderSide(8);
            rect.setBorderColor(BaseColor.BLUE);
            rect.setBorderWidth(5);
            document.add(rect);

            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new BaseColor(135, 206, 235)); // Couleur bleu ciel

            document.add(new Chunk(ls));

            Font font = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.CYAN);
            Chunk chunk = new Chunk("Confirmation de la Reservation", font);
            Paragraph p = new Paragraph(chunk);
            p.setAlignment(Element.ALIGN_CENTER); // Centrer le texte
            document.add(p);
            // Ajouter la date actuelle
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            Font dateFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK); // Date en noir

            Chunk dateChunk = new Chunk(date, dateFont);
            Paragraph dateParagraph = new Paragraph(dateChunk);
            dateParagraph.setAlignment(Element.ALIGN_CENTER); // Centrer la date
            document.add(new Chunk(ls)); // Ajouter un autre séparateur de ligne
            document.add(dateParagraph);
            // Ajouter une autre ligne horizontale de couleur bleu ciel
            document.add(new Chunk(ls));
            // Paragraphe dédié au client
            Font clientFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Paragraph clientParagraph = new Paragraph();
            clientParagraph.add(new Phrase("Cher(e) " + u.getNom() + u.getPrenom() + ",\n\n", clientFont));
            clientParagraph.add(new Phrase("Merci d'avoir réservé une séance dans notre salle de sport. Nous sommes impatients de vous accueillir et de vous aider à atteindre vos objectifs de remise en forme.\n\n", clientFont));
            clientParagraph.add(new Phrase("Ci-dessous, vous trouverez les détails de votre réservation :\n\n", clientFont));
            document.add(clientParagraph);
            // Ajouter des espaces vides avant le tableau
            document.add(new Paragraph("\n\n\n\n\n"));

            // Ajouter une image à droite du texte
            Image img1 = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\check.png");
            img1.scaleAbsolute(50f, 40f); // Redimensionner l'image
            img1.setAbsolutePosition(450f, 760); // Positionner l'image
            document.add(img1);
            PdfPTable table = new PdfPTable(1);

            Font fonte = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

            PdfPCell cell;
            p = new Paragraph("Nom de la séance : " + seance.getNom());
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(BaseColor.WHITE); // Gris clair
            table.addCell(cell);

            p = new Paragraph("Nom et prénom du client : " + u.getNom() + u.getPrenom(), fonte);
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(new BaseColor(224, 224, 224)); // Gris clair
            table.addCell(cell);

            p = new Paragraph("Poids du client: " + u.getPoids(), fonte);
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(BaseColor.WHITE);
            table.addCell(cell);

            p = new Paragraph("Taille du client: " + u.getTaille(), fonte);
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(new BaseColor(224, 224, 224)); // Gris clair
            table.addCell(cell);

            p = new Paragraph("Sexe du client: " + u.getSexe(), fonte);
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(BaseColor.WHITE);
            table.addCell(cell);

            document.add(table);

            // Ajouter des espaces vides après le tableau
            document.add(new Paragraph("\n\n\n\n\n\n\n\n"));

            // Ajouter une autre photo en haut à gauche
            Image img2 = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\logo.png");
            img2.scaleAbsolute(60f, 60f); // Redimensionner l'image
            img2.setAbsolutePosition(50f, 745f); // Positionner l'image
            document.add(img2);

            // Ajouter une autre photo à droite tout en bas
            Image img3 = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\.jpg");
            img3.scaleAbsolute(100f, 70); // Redimensionner l'image
            img3.setAbsolutePosition(450f, 50f); // Positionner l'image
            document.add(img3);
            document.add(new Paragraph("\n\n\n\n\n"));

            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Paragraph footer = new Paragraph("Salle de Sport Gofit, 123 Rue de la Paix, 75000 Ariana,nTel : 71501998,Email : info@salledesportgofit.com", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setContentText("Reservation information has been saved to reservations.pdf");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
public void pdf()
{
   /* try {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
        document.open();

        // Ajouter un cadre autour de la page
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLUE);
        rect.setBorderWidth(5);
        document.add(rect);

        LineSeparator ls = new LineSeparator();
        ls.setLineColor(new BaseColor(135, 206, 235)); // Couleur bleu ciel

        document.add(new Chunk(ls));

        Font font = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.CYAN);
        Chunk chunk = new Chunk("Confirmation de la Reservation", font);
        Paragraph p = new Paragraph(chunk);
        p.setAlignment(Element.ALIGN_CENTER); // Centrer le texte
        document.add(p);
        // Ajouter la date actuelle
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Font dateFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK); // Date en noir

        Chunk dateChunk = new Chunk(date, dateFont);
        Paragraph dateParagraph = new Paragraph(dateChunk);
        dateParagraph.setAlignment(Element.ALIGN_CENTER); // Centrer la date
        document.add(new Chunk(ls)); // Ajouter un autre séparateur de ligne
        document.add(dateParagraph);
        // Ajouter une autre ligne horizontale de couleur bleu ciel
        document.add(new Chunk(ls));
        // Paragraphe dédié au client
        Font clientFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        Paragraph clientParagraph = new Paragraph();
        clientParagraph.add(new Phrase("Cher(e) " + u.getNom() + u.getPrenom() + ",\n\n", clientFont));
        clientParagraph.add(new Phrase("Merci d'avoir réservé une séance dans notre salle de sport. Nous sommes impatients de vous accueillir et de vous aider à atteindre vos objectifs de remise en forme.\n\n", clientFont));
        clientParagraph.add(new Phrase("Ci-dessous, vous trouverez les détails de votre réservation :\n\n", clientFont));
        document.add(clientParagraph);
        // Ajouter des espaces vides avant le tableau
        document.add(new Paragraph("\n\n\n\n\n"));

        // Ajouter une image à droite du texte
        Image img1 = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\check.png");
        img1.scaleAbsolute(50f, 40f); // Redimensionner l'image
        img1.setAbsolutePosition(450f, 760); // Positionner l'image
        document.add(img1);
        PdfPTable table = new PdfPTable(1);

        Font fonte = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        PdfPCell cell;
        p = new Paragraph("Nom de la séance : " + seance.getNom());
        cell = new PdfPCell(p);
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(10);
        cell.setBackgroundColor(BaseColor.WHITE); // Gris clair
        table.addCell(cell);

        p = new Paragraph("Nom et prénom du client : " + u.getNom() + u.getPrenom(), fonte);
        cell = new PdfPCell(p);
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(10);
        cell.setBackgroundColor(new BaseColor(224, 224, 224)); // Gris clair
        table.addCell(cell);

        p = new Paragraph("Poids du client: " + u.getPoids(), fonte);
        cell = new PdfPCell(p);
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(10);
        cell.setBackgroundColor(BaseColor.WHITE);
        table.addCell(cell);

        p = new Paragraph("Taille du client: " + u.getTaille(), fonte);
        cell = new PdfPCell(p);
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(10);
        cell.setBackgroundColor(new BaseColor(224, 224, 224)); // Gris clair
        table.addCell(cell);

        p = new Paragraph("Sexe du client: " + u.getSexe(), fonte);
        cell = new PdfPCell(p);
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(10);
        cell.setBackgroundColor(BaseColor.WHITE);
        table.addCell(cell);

        document.add(table);

        // Ajouter des espaces vides après le tableau
        document.add(new Paragraph("\n\n\n\n\n\n\n\n"));

        // Ajouter une autre photo en haut à gauche
        Image img2 = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\logo.png");
        img2.scaleAbsolute(60f, 60f); // Redimensionner l'image
        img2.setAbsolutePosition(50f, 745f); // Positionner l'image
        document.add(img2);

        // Ajouter une autre photo à droite tout en bas
        Image img3 = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\.jpg");
        img3.scaleAbsolute(100f, 70); // Redimensionner l'image
        img3.setAbsolutePosition(450f, 50f); // Positionner l'image
        document.add(img3);
        document.add(new Paragraph("\n\n\n\n\n\n"));

        Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Paragraph footer = new Paragraph("Salle de Sport Gofit, 123 Rue de la Paix, 75000 Ariana,nTel : 71501998,Email : info@salledesportgofit.com", footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PDF Generated");
        alert.setContentText("Reservation information has been saved to output.pdf");
        alert.showAndWait();
    } catch (Exception e) {
        e.printStackTrace();
    }*/
    try {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true);

        // Ajouter un cadre autour de la page
        PDRectangle mediaBox = page.getMediaBox();
        float margin = 15;
        contentStream.setLineWidth(5);
        contentStream.setStrokingColor(0, 0, 255); // Couleur bleue
        contentStream.addRect(margin, margin, mediaBox.getWidth() - 2 * margin, mediaBox.getHeight() - 2 * margin);
        contentStream.stroke();

        // Charger l'image depuis un fichier
        // Charger l'image depuis un fichier
        File file = new File("C:\\Users\\Yosr\\Downloads\\desktop-app\\src\\main\\resources\\imgs\\logo.png");
        PDImageXObject image = PDImageXObject.createFromFileByContent(file, document);
        File filesignature = new File("C:\\Users\\Yosr\\Downloads\\desktop-app\\src\\main\\resources\\imgs\\.jpg");
        PDImageXObject imagesiganture = PDImageXObject.createFromFileByContent(filesignature, document);
        // Définir la position et la taille de l'image
        float x = 55; // Position horizontale de l'image
        float y = 740; // Position verticale de l'image
        float width = 100; // Largeur de l'image
        float height = 50; // Hauteur de l'image

        // Ajouter l'image à la page
        contentStream.drawImage(image, x, y, width, height);

        File filec = new File("C:\\Users\\Yosr\\Downloads\\desktop-app\\src\\main\\resources\\imgs\\check.png");
        PDImageXObject imagecheck = PDImageXObject.createFromFileByContent(filec, document);

        // Définir la position et la taille de l'image
        float imageWidth = 80; // Largeur de l'image
        float imageHeight = 50; // Hauteur de l'image
// Ajouter une ligne horizontale de couleur bleu ciel
        contentStream.setLineWidth(1);
        contentStream.setStrokingColor(135, 206, 235); // Couleur bleu ciel
        float yPosition = mediaBox.getHeight() - 3 * margin;
        contentStream.moveTo(margin, yPosition);
        contentStream.lineTo(mediaBox.getWidth() - margin, yPosition);
        contentStream.stroke();

        // Ajouter le texte "Confirmation de la Reservation" centré
        /*contentStream.setLineWidth(1.5f);
        contentStream.setStrokingColor(0, 255, 255); // Couleur cyan
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset((mediaBox.getWidth() - margin) / 2, mediaBox.getHeight() - 4 * margin);
        contentStream.showText("Confirmation de la Reservation");
        contentStream.endText();*/
        // Calculer la largeur du texte
        float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("Confirmation de la Reservation") / 1000f * 18;
        // Centrer le texte horizontalement
        float centerX = (mediaBox.getWidth() - textWidth) / 2;
        // Définir la position de départ du texte
        float textXPosition = centerX + margin;
        // Ajouter le texte centré
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        contentStream.newLineAtOffset(textXPosition, mediaBox.getHeight() - 4 * margin);
        contentStream.showText("Confirmation de la Reservation");
        contentStream.endText();
        // Définir la position et la taille de l'image
        float imageX = centerX + textWidth + 20; // Ajouter un espace de 10 unités entre le texte et l'image
       // float imageY = mediaBox.getHeight() - 4 * margin - (imageHeight / 2); // Centrer l'image par rapport au texte
        float imageY = 740;// Centrer l'image par rapport au texte

        // Ajouter l'image à la page
        contentStream.drawImage(imagecheck, imageX, imageY, imageWidth, imageHeight);
        // Ajouter la date actuelle centrée
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset((mediaBox.getWidth() - margin) / 2, mediaBox.getHeight() - 6 * margin);
        contentStream.showText(date);
        contentStream.endText();
        contentStream.setLineWidth(1);
        contentStream.setStrokingColor(135, 206, 235); // Couleur bleu ciel
        // Ajouter une autre ligne horizontale de couleur bleu ciel sous la date
        contentStream.setLineWidth(1);
        contentStream.setStrokingColor(135, 206, 235); // Couleur bleu ciel
        float secondYPosition = mediaBox.getHeight() - 7 * margin; // Nouvelle position Y
        contentStream.moveTo(margin, secondYPosition);
        contentStream.lineTo(mediaBox.getWidth() - margin, secondYPosition);
        contentStream.stroke();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Cher(e) " + u.getNom() + u.getPrenom() + ",");
        contentStream.moveTextPositionByAmount(-20, -15); // Déplacer la position du texte vers le bas
        contentStream.showText("Merci d'avoir réservé une séance dans notre salle de sport. ");
        contentStream.moveTextPositionByAmount(-20, -15); // Déplacer la position du texte vers le bas
        contentStream.showText("Nous sommes impatients de vous accueillir et de vous aider à atteindre vos objectifs");
        contentStream.moveTextPositionByAmount(-20, -15); // Déplacer la position du texte vers le bas
        contentStream.showText("Ci-dessous, vous trouverez les détails de votre réservation :");
        contentStream.endText();


        // Dessiner un rectangle autour du texte pour simuler le tableau
        /*contentStream.addRect(100, 600, 400, 100);
        contentStream.stroke();*/
        // ... (le reste du code reste inchangé)


        // Définir la taille des colonnes et des lignes
       /* float margins = 50;
        float yStart = page.getMediaBox().getHeight() - margins;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margins;
        float tableHeight = 100;
        float rowHeight = 20;
        float cellMargin = 10;

        // Dessiner les lignes du tableau
        drawTableLines(contentStream, yStart, tableWidth, tableHeight, rowHeight, margins, cellMargin);

        // Remplir le contenu du tableau
        fillTableContent(contentStream, yStart, tableWidth, rowHeight, margins, cellMargin);*/
        // Calculer la position verticale pour le tableau
        float tableVerticalPosition = 700 - 20; // Position Y de départ pour le tableau (20 est la marge inférieure du paragraphe)

        // Dessiner le tableau
        //drawTable(document, page, tableVerticalPosition);
        // Calculer la hauteur du paragraphe pour obtenir la position Y du texte suivant
        // Calculer la hauteur de la page et du paragraphe
        /*PDRectangle mediaBoxx = page.getMediaBox();
        float pageHeight = mediaBoxx.getHeight();
        float paragraphHeight = 4 * 15; // 4 lignes de texte avec un décalage de 15 unités entre chaque ligne

        // Calculer la position Y pour le texte suivant (centré verticalement)
        float nextTextY = (pageHeight - paragraphHeight) / 2;

        // Ajouter le texte suivant (par exemple, "Autre contenu...")
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(100, nextTextY);
        contentStream.showText("Autre contenu...");
        contentStream.endText();*/
        // Calculer la position Y pour placer les informations un peu plus haut
        float posY = 570; // Ajustez cette valeur selon votre préférence

        // Ajouter le nom de la séance
        contentStream.beginText();
        contentStream.newLineAtOffset(100, posY);
        contentStream.showText("Nom de la séance: " + seance.getNom());
        contentStream.endText();

        // Ajouter le nom et le prénom de l'utilisateur
        contentStream.beginText();
        contentStream.newLineAtOffset(100, posY - 20); // Déplacer vers le bas
        contentStream.showText("Nom et prénom du client: " + u.getNom() + " " + u.getPrenom());
        contentStream.endText();

        // Ajouter le poids de l'utilisateur
        contentStream.beginText();
        contentStream.newLineAtOffset(100, posY - 40); // Déplacer vers le bas
        contentStream.showText("Poids du client: " + u.getPoids());
        contentStream.endText();

        // Ajouter le sexe de l'utilisateur
        contentStream.beginText();
        contentStream.newLineAtOffset(100, posY - 60); // Déplacer vers le bas
        contentStream.showText("Sexe du client: " + u.getSexe());
        contentStream.endText();
        // Définir la position et la taille de l'image
        float imageWidthsignature = 100; // Largeur de l'image
        float imageHeightsignature = 70; // Hauteur de l'image
        float imageX2 = page.getMediaBox().getWidth() - imageWidth - 80; // Position horizontale de l'image
        float imageY2 = 50; // Position verticale de l'image

        // Ajouter l'image à la page
        contentStream.drawImage(imagesiganture, imageX2, imageY2, imageWidthsignature, imageHeightsignature);
        // Ajouter le pied de page
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.newLineAtOffset(80, 50); // Position du pied de page
        contentStream.showText("Salle de Sport Gofit, 123 Rue de la Paix, 75000 Ariana, Tel :71501998 ,Email :info@salledesportgofit.com");
        contentStream.endText();
        contentStream.close();

        // Sauvegarder le document
        document.save("output.pdf");
        document.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PDF Generated");
        alert.setContentText("Reservation information has been saved to output.pdf");
        alert.showAndWait();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private static void drawTable(PDDocument document, PDPage page, float tableVerticalPosition) throws IOException {
        // Créer le contenu du tableau ici
        // Vous pouvez utiliser la méthode fillTableContent() fournie dans la réponse précédente
        // ou créer une nouvelle méthode pour le contenu du tableau

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

        // Définir les dimensions et le contenu du tableau ici
        // Par exemple :
        fillTableContent(contentStream, tableVerticalPosition);

        contentStream.close();
    }

    private static void fillTableContent(PDPageContentStream contentStream, float tableVerticalPosition) throws IOException {
        // Ici, vous pouvez remplir le contenu du tableau comme indiqué dans la réponse précédente
        // en utilisant la position verticale du tableau fournie en argument
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

    private String getSelectedSexe() {
        RadioButton selectedRadioButton = (RadioButton) sexeToggleGroup.getSelectedToggle();
        return selectedRadioButton.getText();
    }

    private void clearForm() {
        labelnom.setText("");
        labelprenom.setText("");
        Age.clear();
        Poids.clear();
        Taille.clear();
        // Désélectionner les boutons radio du groupe "sexe"
        sexeToggleGroup.getSelectedToggle().setSelected(false);

    }
    private static void drawTableLines(PDPageContentStream contentStream, float yStart, float tableWidth,
                                       float tableHeight, float rowHeight, float margin, float cellMargin) throws IOException {
        float nextY = yStart;
        contentStream.setLineWidth(1);
        contentStream.moveTo(margin, nextY);
        contentStream.lineTo(margin + tableWidth, nextY);
        contentStream.stroke();
        nextY -= rowHeight;
        contentStream.moveTo(margin, nextY);
        contentStream.lineTo(margin + tableWidth, nextY);
        contentStream.stroke();

        float y = yStart;
        for (int i = 0; i <= 5; i++) {
            contentStream.moveTo(margin, y);
            contentStream.lineTo(margin, y - tableHeight);
            contentStream.stroke();
            y -= rowHeight;
        }

        contentStream.moveTo(margin + tableWidth, yStart);
        contentStream.lineTo(margin + tableWidth, yStart - tableHeight);
        contentStream.stroke();
    }

    private static void fillTableContent(PDPageContentStream contentStream, float yStart, float tableWidth,
                                         float rowHeight, float margin, float cellMargin) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        float yText = yStart - 15; // Position verticale du texte
        float xText = margin + cellMargin; // Position horizontale du texte

        // Ajouter les cellules du tableau
        contentStream.beginText();
        contentStream.newLineAtOffset(xText, yText);
        contentStream.showText("Nom de la séance : ");
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Nom et prénom du client : ");
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Poids du client : ");
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Taille du client : ");
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Sexe du client : ");
        contentStream.endText();

        // Ajouter les valeurs des cellules
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        xText += 200; // Décalage horizontal pour les valeurs
        contentStream.beginText();
        contentStream.newLineAtOffset(xText, yText);
        contentStream.showText("Nom de la séance");
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Nom et prénom du client");
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Poids du client");
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Taille du client");
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Sexe du client");
        contentStream.endText();
    }
   /* private Seance rechercherSeance(Time horaire, String jour) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Seance seance = null;

        try {
            // Obtenez la connexion à partir de votre DataSource
            conn = DataSource.getInstance().getCnx();

            // Préparez la requête SQL pour rechercher la séance correspondante
            String query = "SELECT * FROM seance WHERE horaire = ? AND jourseance = ?";
            statement = conn.prepareStatement(query);
            statement.setTime(1, horaire);
            statement.setString(2, jour);

            // Exécutez la requête SQL
            resultSet = statement.executeQuery();

            // Si une séance correspondante est trouvée, créez un objet Seance et initialisez-le avec les données de la base de données
            if (resultSet.next()) {
                seance = new Seance();
                seance.setIdseance(resultSet.getInt("ids"));
                seance.setHoraire(resultSet.getTime("horaire"));
                seance.setJourseance(resultSet.getString("jourseance"));
                // Vous pouvez initialiser d'autres attributs de la séance si nécessaire
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermez les ressources JDBC
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLE


        }*/
   @FXML
   void calculer(ActionEvent event) {
       float poids=u.getPoids();
       float taille=u.getTaille();
       double imc;
       String etat;
                   /* if (u.getSexe().equals("HOMME")) {

                    } else {
                        imc = poids / (taille * taille) * 0.9;
                    }*/
       imc = poids / (taille * taille);
       DecimalFormat df = new DecimalFormat("#.##");
       String imcFormate = df.format(imc);
       // Détermination de l'état pondéral en fonction de l'IMC
       if (imc < 18.5) {
           etat = "Insuffisance pondérale";
       } else if (imc > 18.5 && imc < 25) {
           etat = "Poids normal";
       } else if (imc > 25 && imc < 30) {
           etat = "Surpoids";
       } else {
           etat = "Obésité";
       }
       // Affichage de l'IMC et de l'état pondéral
       // Créer et afficher la notification
       Notifications.create()
               .title("IMC")
               .text("Votre IMC est : " + imcFormate + "\n" + "Votre état pondéral : " + etat + "\n" + "Par cet IMC, vous pouvez mieux savoir quelle séance vous voulez choisir pour atteindre votre objectif. Consultez nos séances.")
               .showInformation();
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

