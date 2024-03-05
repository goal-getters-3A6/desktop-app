package edu.esprit.controllers;

import edu.esprit.entities.Id;
import edu.esprit.services.UserService;
import edu.esprit.utils.GoogleAuthenticator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


import java.sql.SQLException;

public class TfaController {
    @FXML
    private TextField tfacode;
    @FXML
    private javafx.scene.control.Button submit;

    @FXML
    public void VerifyCode() throws SQLException {
        System.out.println(new UserService().getOneById(Id.tmpuser).getTfaSecret());
        System.out.println(GoogleAuthenticator.getTOTPCode(new UserService().getOneById(Id.tmpuser).getTfaSecret()));
        if (tfacode.getText().equals(GoogleAuthenticator.getTOTPCode(new UserService().getOneById(Id.tmpuser).getTfaSecret()))) {
            System.out.println("Logged in successfully");
            Id.tfaVerified = true;
            Stage stage = (Stage) submit.getScene().getWindow();
            stage.close();
        } else {
            String title = "Wrong code!";
            String message = "";
            NotificationType notification = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(2));

        }
    }
}
