package edu.esprit.tests;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.services.AdminService;
import edu.esprit.services.ClientService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        AdminService adminService = new AdminService();
        ClientService clientService = new ClientService();

        // Test pour récupérer tous les admins
        System.out.println("Liste des admins :");
        System.out.println(adminService.getAllAdmins());

        // Test pour ajouter un nouvel admin
        Admin nouvelAdmin = new Admin("John", "Doe", "password", "john@example.com", true, 0, new byte[]{});
        adminService.ajouterAdmin(nouvelAdmin);
        System.out.println("Admin updated successfully!");

        // Test pour récupérer tous les clients
        System.out.println("Liste des clients :");
        System.out.println(clientService.getAllClients());

        // Test pour ajouter un nouveau client
        Client nouveauClient = new Client("Alice", "Smith", new Date(), new Date(), 123456789);
        clientService.ajouterClient(nouveauClient);
        System.out.println("Client ajouté avec succès!");
    }
}
