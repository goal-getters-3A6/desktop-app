package Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Participation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {

    public void generateCertificate(Participation participation, String filePath, String eventName) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add a background image
            Image background = Image.getInstance("C:\\Users\\louay\\IdeaProjects\\evenement_pidev\\images\\Certificate.png");
            background.setAbsolutePosition(0, 0);
            background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            background.setBorder(Image.BOX);
            background.setBorderWidth(0);
            background.setBorderColor(new GrayColor(0));
            document.add(background);

            // Ajout du titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
            Paragraph title = new Paragraph("Certificat de participation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            document.add(title);
            document.add(new Paragraph(" "));


            // Ajout du nom de l'événement
            Font eventFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK);
            Paragraph eventNameParagraph = new Paragraph(eventName, eventFont);
            eventNameParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(eventNameParagraph);

            // Ajouter de l'espace
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));


            // Add participant and event information
            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.NORMAL, BaseColor.BLACK);
            Chunk participantName = new Chunk(participation.getNom_p() + " " + participation.getPrenom_p(), contentFont);
            Paragraph participantInfo = new Paragraph(" ", contentFont);
            participantInfo.add(participantName);
            participantInfo.setAlignment(Element.ALIGN_CENTER); // Aligner le paragraphe au centre horizontalement
            participantInfo.setLeading(0, 1.5f); // Espacement entre les lignes
            participantInfo.setSpacingAfter(20);
            participantInfo.setAlignment(Element.ALIGN_MIDDLE); // Aligner le paragraphe au milieu verticalement
            participantInfo.setAlignment(Element.ALIGN_CENTER); // Aligner le paragraphe au centre horizontalement
            document.add(participantInfo);

            Chunk eventTitle = new Chunk(eventName, contentFont);
            eventTitle.setBackground(new BaseColor(0x99CCFF));
          /*  Paragraph eventInfo = new Paragraph("a participé à l'événement : ", contentFont);
            eventInfo.add(eventTitle);
            eventInfo.setAlignment(Element.ALIGN_CENTER); // Aligner le paragraphe au centre horizontalement
            eventInfo.setLeading(0, 1.5f); // Espacement entre les lignes
            eventInfo.setSpacingAfter(50);
            eventInfo.setAlignment(Element.ALIGN_MIDDLE); // Aligner le paragraphe au milieu verticalement
            eventInfo.setAlignment(Element.ALIGN_CENTER); // Aligner le paragraphe au centre horizontalement
            document.add(eventInfo);*/

            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}