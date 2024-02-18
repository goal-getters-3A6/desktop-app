package edu.esprit.controllers;

import edu.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController{
    @FXML
    private TextField emailTxt; // Value injected by FXMLLoader
    @FXML
    private PasswordField mdpTxt; // Value injected by FXMLLoader
    UserService userService = new UserService();
    @FXML
    void register() throws   Exception{
        if(userService.register( emailTxt.getText(), mdpTxt.getText())){
            System.out.println("Register success");
        }else{
            System.out.println("Register failed");
        }
    }
}

