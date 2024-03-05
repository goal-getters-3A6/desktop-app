package edu.esprit.controllers;

import edu.esprit.entities.Plat;
import javafx.scene.control.ListCell;

public class PlatListCell extends ListCell<Plat> {
    @Override
    protected void updateItem(Plat plat, boolean empty) {
        super.updateItem(plat, empty);
        if (empty || plat == null) {
            setText(null);
        } else {
            setText("♡ " + plat.getNomP()+" || "+ plat.getDescP());
        }
    }
}
