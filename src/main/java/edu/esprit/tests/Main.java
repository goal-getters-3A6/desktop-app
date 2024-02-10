package edu.esprit.tests;

import edu.esprit.entities.Admin;

import edu.esprit.services.AdminService;
import edu.esprit.services.ClientService;

public class Main {
    public static void main(String[] args) {
        AdminService adminService = new AdminService();
        ClientService clientService = new ClientService();
        // test retrieve
        System.out.println(adminService.getAllAdmins());
        // test add
        Admin meryem = new Admin("meryem","boukraa");
        adminService.ajouterAdmin(meryem);


        //test clients
        System.out.println(clientService.getAllClients());
    }
}
