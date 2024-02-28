package edu.esprit.controllers;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.sql.*;
import java.text.DecimalFormat;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import edu.esprit.entities.*;
import edu.esprit.services.ClientService;
import edu.esprit.services.UserService;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.UserService;
import edu.esprit.utils.DataSource;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
    private RadioButton homme;
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
      //  remplirComboBox();
    }


    @FXML
    private Label labelpoids;

    @FXML
    private Label labelprenom;

    @FXML
    private Label labeltaille;
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

    }

    @FXML
    void accueil(ActionEvent event) {

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

    {
        try {
            u = cs.getOneByEmail(mail);
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
        System.out.println("user" + u);
        ClientService clientService = new ClientService();
        labelnom.setText(u.getNom());
        labelprenom.setText(u.getPrenom());
        labelpoids.setText(String.valueOf(u.getPoids()));
        labeltaille.setText(String.valueOf(u.getTaille()));
        labelsexe.setText(u.getSexe());
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
    }

    /*private void remplirComboBox() {
        ObservableList<String> horaires = FXCollections.observableArrayList();
        ObservableList<String> jours = FXCollections.observableArrayList();

        try {

            Connection conn = DataSource.getInstance().getCnx(); // Obtenez la connexion à partir de votre DataSource
            //PreparedStatement statement = conn.prepareStatement("SELECT horaire, jourseance FROM seance");
            //ResultSet resultSet = statement.executeQuery();
            PreparedStatement statement = conn.prepareStatement("SELECT horaire, jourseance FROM seance WHERE nom = ?");
            statement.setString(1, seance.getNom());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String horaire = resultSet.getString("horaire");
                String jour = resultSet.getString("jourseance");
                horaires.add(horaire);
                jours.add(jour);
            }
            horaireComboBox.setItems(horaires);
            jourComboBox.setItems(jours);
            System.out.println("la seance " + seance.getNom());

            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


    @FXML
    void ajouterreservation(ActionEvent event) throws SQLException {
        /*ClientService clientService = new ClientService();

        if (u instanceof Client) {
            Client client = (Client) u; // Convertir l'utilisateur en client
            String nom = u.getNom(); // Récupérer le nom du client
            String prenom = u.getPrenom(); // Récupérer le prénom du client
            Float poids = ((Client) u).getPoids();
            Time h= Time.valueOf(horaireComboBox.getValue());
            String j=jourComboBox.getValue();
            ///////////////////////////////
            // Vérifier le nombre maximal de réservations pour la séance
            ServiceReservation serviceReservation = new ServiceReservation();
            // Obtenir la liste des réservations pour la séance sélectionnée
            List<Reservation> reservationsPourSeance = new ArrayList<>();
            for (Reservation r : resList) {
                if (r.getSeance().equals(seance)) {
                    reservationsPourSeance.add(r);
                }
            }
            for (Reservation r : resList) {
                if (r.getSeance().getJourseance().equals(seance.getJourseance() )&& r.getSeance().getHoraire()==seance.getHoraire() ) {
                    System.out.println(r.getSeance().getJourseance()+r.getSeance().getHoraire());
                    reservationsPourSeance.add(r);
                }
            }

            if (reservationsPourSeance.size() >= seance.getNbMax()) {
                // Si le nombre maximal est atteint ou dépassé, afficher une alerte
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerte");
                alert.setContentText("Le nombre maximal de réservations pour cette séance a été atteint.");
                alert.showAndWait();
            } else {
                //float poidsx = u.getPoids();
                float taille = u.getTaille();
                // Ajouter la réservation en utilisant le nom et le prénom du client
                //Reservation nouvelleReservation = new Reservation(seance, u);
                Reservation nouvelleReservation = new Reservation(seanceSelectionnee, u);

                try {
                    serviceReservation.ajouter(nouvelleReservation);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Réservation ajoutée avec succès");
                    alert.showAndWait();
                    // Calcul de l'IMC en fonction du sexe
                    double imc;
                    String etat;
                    if (u.getSexe().equals("Homme")) {
                        imc = poids / (taille * taille);
                    } else {
                        imc = poids / (taille * taille) * 0.9;
                    }
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
                    alertimc.showAndWait();
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
        }*/
        /*ClientService clientService = new ClientService();

        if (u instanceof Client) {
            Client client = (Client) u; // Convertir l'utilisateur en client
            String nom = u.getNom(); // Récupérer le nom du client
            String prenom = u.getPrenom(); // Récupérer le prénom du client
            Float poids = ((Client) u).getPoids();

            // Vérifier le nombre maximal de réservations pour la séance
            ServiceReservation serviceReservation = new ServiceReservation();
            // Obtenir la liste des réservations pour la séance sélectionnée
            List<Reservation> reservationsPourSeance = new ArrayList<>();
            for (Reservation r : resList) {
                if (r.getSeance().equals(seance)) {
                    reservationsPourSeance.add(r);
                }
            }
            if (reservationsPourSeance.size() >= seance.getNbMax()) {
                // Si le nombre maximal est atteint ou dépassé, afficher une alerte
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerte");
                alert.setContentText("Le nombre maximal de réservations pour cette séance a été atteint.");
                alert.showAndWait();
            } else {
                // Afficher les ComboBox pour que le client sélectionne le jour et l'horaire de la séance
                ComboBox<String> jourComboBox = new ComboBox<>();
                ComboBox<String> horaireComboBox = new ComboBox<>();
                // Ajouter tous les jours et horaires disponibles pour la séance sélectionnée
                for (Reservation r : resList) {
                    if (r.getSeance().equals(seance)) {
                        String horaire = r.getSeance().getHoraire().toString();
                        System.out.println();
                        jourComboBox.getItems().add(r.getSeance().getJourseance());
                        horaireComboBox.getItems().add(horaire);
                    }
                }
                // Créer un dialogue pour sélectionner le jour et l'horaire de la séance
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Sélectionner le jour et l'horaire de la séance");
                dialog.setHeaderText("Veuillez sélectionner le jour et l'horaire de la séance :");

                // Créer un bouton "Valider" pour que le client puisse soumettre sa sélection
                ButtonType validerButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(validerButtonType, ButtonType.CANCEL);

                // Créer un layout pour les ComboBox
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                grid.add(new Label("Jour :"), 0, 0);
                grid.add(jourComboBox, 1, 0);
                grid.add(new Label("Horaire :"), 0, 1);
                grid.add(horaireComboBox, 1, 1);

                dialog.getDialogPane().setContent(grid);

                // Récupérer la sélection du client lorsque le bouton "Valider" est cliqué
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == validerButtonType) {
                        return new Pair<>(jourComboBox.getValue(), horaireComboBox.getValue());
                    }
                    return null;
                });

                // Afficher la boîte de dialogue et attendre que le client fasse sa sélection
                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(pair -> {
                    String jourSelectionne = pair.getKey();
                    String horaireSelectionne = pair.getValue();

                    // Ajouter la réservation en utilisant le nom et le prénom du client
                    Reservation nouvelleReservation = new Reservation(seance, u);
                    try {
                        serviceReservation.ajouter(nouvelleReservation);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Validation");
                        alert.setContentText("Réservation ajoutée avec succès");
                        alert.showAndWait();
                        // Effectuer d'autres traitements si nécessaire
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur lors de l'ajout");
                        alert.setContentText("Une erreur s'est produite lors de l'ajout de la réservation : " + e.getMessage());
                        alert.showAndWait();
                    }
                });
            }
        } else {
            // Si l'utilisateur n'est pas un client, afficher un message d'erreur ou effectuer une autre action appropriée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("L'utilisateur n'est pas un client.");
            alert.showAndWait();
        }*/
        ClientService clientService = new ClientService();

        if (u instanceof Client) {
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
                    // Calcul de l'IMC en fonction du sexe
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
                    Alert alertimc = new Alert(Alert.AlertType.INFORMATION);
                    alertimc.setTitle("IMC");
                    alertimc.setContentText("Votre IMC est : " + imcFormate + "\n" + "Votre état pondéral : " + etat+" par cet imc vous pouvez connaitre mieux quelle seance vous voulez choisir pour atteindre votre objectif check nos seances");
                    alertimc.showAndWait();
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

    }

    @FXML
    void equipement(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {

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
    void profil(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

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
}

