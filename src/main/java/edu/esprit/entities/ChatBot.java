/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;
/**
 *
 * @author MSI
 */
public class ChatBot {
    public String processInput(String input) {
        if(null == input)return "Malheuresement j'ai pas une réponse à ce genre de Message , Merci d'attendez nos mise à jour systeme !";
        else switch (input) {
            case "Salut":
                return "Bonjour, comment puis-je vous aider ?";
            case "Bonjour":
                return "Bonjour, comment puis-je vous aider ?";
            case "Pouvez vous m'expliquer le concept de cette application":
                return "GoFit vise à simplifier la vie des clients en regroupant toutes les fonctionnalités essentielles au sein d'une seule application, offrant ainsi une expérience utilisateur intégrée et fluide\nAccès facile à toutes les fonctionnalités à partir d'une seule application\nPersonnalisation des expériences en fonction des préférences individuelles " ;
            case "Les services":
                return "-Faire un abonnement de la  salles de sport\n" +
                        "-Réserver des séances  \n" +
                        "-Participer à des événements sportifs  \n" +
                        "-Consulter les équipements \n" +
                        "-Donner des avis sur les équipements\n" +
                        "-Services nutritionnels\n" +
                        "-Réclamation concernant l'environnement se la salle du sport " ;
            case "Merci":
                return "A tout moment , je suis là pour vous aidez !";
            case "Le concept":
                return "Fournir un espace dédié à la pratique d'activités sportives et de remise en forme.  ";
            default:
                return "Malheuresement j'ai pas une reponse à ce genre de Message , Merci d'attendez nos mise à jour systéme !";
        }
    }
}
