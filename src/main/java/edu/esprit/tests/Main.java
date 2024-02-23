package edu.esprit.tests;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.services.AdminService;
import edu.esprit.services.ClientService;

import java.util.Date;
import java.util.Set;

public class Main{
    public static void main(String[] args) {
        // Testing AdminService
        AdminService adminService = new AdminService();

        // Adding an admin
        Admin adminToAdd = new Admin("boukraa", "meryem", "admin123", "meryem.boukraa@esprit.tn", null);
        adminService.ajouterAdmin(adminToAdd);

        // Getting admin by ID
        Admin adminById = adminService.getAdminById(1);
        System.out.println("Admin retrieved by ID: " + adminById);

        // Getting all admins
        Set<Admin> allAdmins = adminService.getAllAdmins();
        System.out.println("All admins: ");
        for (Admin admin : allAdmins) {
            System.out.println(admin);
        }


        // Testing ClientService
        ClientService clientService = new ClientService();


        Client clientToAdd = new Client("boukraa", "meryem", "mahdiboukraa@gmail.com", "mdp123", "12345678", true, 0, null, new Date("29/05/2006"));
        clientService.ajouterClient(clientToAdd);

        // Getting client by ID
        Client clientById = clientService.getClientById(3);
        System.out.println("Client retrieved by ID: " + clientById);

        // Getting all clients
        Set<Client> allClients = clientService.getAllClients();
        System.out.println("All clients: ");
        for (Client client : allClients) {
            System.out.println(client);

    }}}
