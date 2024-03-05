package edu.esprit.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.esprit.entities.Id;
import edu.esprit.entities.User;

import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import static edu.esprit.utils.SessionManagement.saveSession;


public class ChangePasswordController implements Initializable {

    @FXML
    private PasswordField newpassword;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private Button submit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void changePassword(ActionEvent event) throws SQLException {
        if (newpassword.getText().equals(confirmpassword.getText())) {
            UserService u = new UserService();
            User uu = u.getOneById(Id.user);
            u.changePassword(uu.getId(), newpassword.getText());
            saveSession(uu.getMail() , newpassword.getText());

            String title = "Password changed";
            String message = "Your password was updated !";
            NotificationType notification = NotificationType.SUCCESS;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(4));
            submit.getScene().getWindow().hide();

        } else {

            String title = "Error";
            String message = "Make sure that your passwords match !";
            NotificationType notification = NotificationType.ERROR;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(4));
        }
    }

}
