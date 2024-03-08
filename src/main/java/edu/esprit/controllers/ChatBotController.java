/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.controllers;


import edu.esprit.entities.ChatBot;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ChatBotController implements Initializable {

    @FXML
    private TextField inputTextField;
    @FXML
    private TextArea outputLabel;
    
     @FXML
    private ChatBot chatbot;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        chatbot = new ChatBot();
   
    }

    @FXML
    private void processInput(ActionEvent event) {
          String input = inputTextField.getText();
        String output = chatbot.processInput(input);
        outputLabel.setText(output);
        inputTextField.clear();
    }

    @FXML
    private void Accueil(ActionEvent event) throws IOException {
        
        Parent AjouterParent = FXMLLoader.load(getClass().getResource("/gui/Eventfront.fxml"));
        Scene AjouterScene = new Scene(AjouterParent);

        //this line gets the stage by force
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(AjouterScene);
        window.show();
    }
}
