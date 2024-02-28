package controllers;

import entities.Participation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;

public class CustomListViewCell extends ListCell<Participation> {

    @Override
    protected void updateItem(Participation item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Construire la vue personnalisée de l'élément de la liste
            String participantInfo = "Nom: " + item.getNom_p() + "       "+  "Prénom: " + item.getPrenom_p() + "       " +" Age: " + item.getAge() + "      "+ ", Email: " + item.getEmail();
            String eventName = item.getEvent() != null ? item.getEvent().getNom_eve() : "Nom de l'événement inconnu";
            String displayText = participantInfo + "\nÉvénement: " + eventName;

            // Utiliser une étiquette pour afficher le texte personnalisé
            Label label = new Label(displayText);
            label.setWrapText(true);
            label.setMaxWidth(Region.USE_PREF_SIZE);
            setGraphic(label);
        }
    }

}
