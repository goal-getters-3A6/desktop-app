package edu.esprit.controllers;

import edu.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AcceuilController {
    @FXML
    private TextField nomTxt; // Value injected by FXMLLoader
    @FXML
    private TextField prenomTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField mdpTxt;
    UserService userService = new UserService();


    @FXML
    void login() throws Exception {
        if (userService.login(emailTxt.getText(), mdpTxt.getText())) {
            System.out.println("Login success");
        } else {
            System.out.println("Login failed");
        }
    }

}