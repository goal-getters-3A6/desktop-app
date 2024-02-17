package edu.esprit.controllers;

import edu.esprit.services.AdminService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class TestController {
AdminService adminService = new AdminService();
    @FXML
     void hfshf(ActionEvent event) {
    System.out.println(adminService.getAllAdmins());
    }
}
