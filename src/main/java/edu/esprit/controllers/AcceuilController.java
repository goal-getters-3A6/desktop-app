package edu.esprit.controllers;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.services.AdminService;
import edu.esprit.services.ClientService;
import edu.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AcceuilController {

    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField mdpTxt;
    UserService userService = new UserService();


   /* @FXML
    void login() throws Exception {
        if (userService.login(emailTxt.getText(), mdpTxt.getText())) {
            System.out.println("Login success");
        } else {
            System.out.println("Login failed");
        }
    }*/
        // AdminService adminService = new AdminService();

    @FXML
    void login() {
        try {
            boolean loginSuccess = userService.login(emailTxt.getText(), mdpTxt.getText());
            if (loginSuccess) {
               User user=userService.getUserByEmail(emailTxt.getText());
                if (user.isAdmin()) {
                    redirectToAdminDashboard();
                } else {
                    redirectToUserProfile();
                }
            } else {
                System.out.println("Login failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectToAdminDashboard() {
            try {
                Parent adminDashboard = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(adminDashboard));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void redirectToUserProfile() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/profil.fxml"));
                Parent userProfile = loader.load();
                ProfilController profilController = loader.getController();
                profilController.initData(emailTxt.getText()); // Passer l'email de l'utilisateur au profil
                Stage stage = new Stage();
                stage.setScene(new Scene(userProfile));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}