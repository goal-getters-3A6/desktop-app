package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.esprit.entities.Id;
import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import static edu.esprit.utils.SendEmail.sendMail;

public class ForgotPasswordController implements Initializable {

    @FXML
    private TextField emailTxt;
    @FXML
    private TextField code;
    @FXML
    private Label label;
    @FXML
    private Text text;
    @FXML
    private Button submit;
    @FXML
    private Button submitemail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        text.setVisible(false);
        label.setVisible(false);
        code.setVisible(false);
        submit.setVisible(false);
    }

    @FXML
    private void SubmitEmail(ActionEvent event) throws Exception {
        if (new UserService().getOneByEmail(emailTxt.getText()) != null) {
            sendMail(emailTxt.getText());
            text.setVisible(true);
            label.setVisible(true);
            code.setVisible(true);
            submit.setVisible(true);
            submitemail.setVisible(false);
            String title = "Code sent";
            String message = "Check your email to get your code !";
            NotificationType notification = NotificationType.SUCCESS;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(4));

        } else {
            String title = "Wrong Email";
            String message = "Please verify the entered email !";
            NotificationType notification = NotificationType.ERROR;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(4));
        }

    }

    @FXML
    private void VerifyCode(ActionEvent event) throws IOException {
        if (Id.code.toString().equals(code.getText())) {
            Id.user = new UserService().getOneByEmail(emailTxt.getText()).getId();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/changePassword.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newWindow = new Stage();
            newWindow.setTitle("Change Password");
            newWindow.setScene(scene);
            newWindow.show();
            submit.getScene().getWindow().hide();
        } else {
            String title = "Wrong Code";
            String message = "The code doesn't match the code we just sent !";
            NotificationType notification = NotificationType.ERROR;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndDismiss(Duration.seconds(4));
        }
    }

}
